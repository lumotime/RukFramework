package com.j3dream.core.util.idworker;

/**
 * <p>文件名称: SnowflakeIdWork </p>
 * <p>所属包名: cn.ftxxkj.system.verification.common.util.idworker</p>
 * <p>描述: 雪花算法 </p>
 * <p>创建时间: 2020/8/12 09:20 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
class SnowflakeIdWork implements IdWork {

    @Override
    public String nextId() {
        return String.format("%016x", SnowFlake.SNOW_FLAKE.nextId());
    }

    private static class SnowFlake {

        /**
         * 内部类对象（单例模式）
         */
        private static final SnowFlake SNOW_FLAKE = new SnowFlake();
        /**
         * 起始的时间戳
         */
        private final long START_TIMESTAMP = 1557489395327L;
        /**
         * 序列号占用位数
         */
        private final long SEQUENCE_BIT = 12;
        /**
         * 机器标识占用位数
         */
        private final long MACHINE_BIT = 10;
        /**
         * 时间戳位移位数
         */
        private final long TIMESTAMP_LEFT = SEQUENCE_BIT + MACHINE_BIT;
        /**
         * 最大序列号  （4095）
         */
        private final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);
        /**
         * 最大机器编号 （1023）
         */
        private final long MAX_MACHINE_ID = ~(-1L << MACHINE_BIT);
        /**
         * 生成id机器标识部分
         */
        private long machineIdPart;
        /**
         * 序列号
         */
        private long sequence = 0L;
        /**
         * 上一次时间戳
         */
        private long lastStamp = -1L;

        /**
         * 构造函数初始化机器编码
         */
        private SnowFlake() {
            //模拟这里获得本机机器编码
            long localIp = 4321;
            //localIp & MAX_MACHINE_ID最大不会超过1023,在左位移12位
            machineIdPart = (localIp & MAX_MACHINE_ID) << SEQUENCE_BIT;
        }

        /**
         * 获取雪花ID
         */
        public synchronized long nextId() {
            long currentStamp = timeGen();
            //避免机器时钟回拨
            while (currentStamp < lastStamp) {
                // //服务器时钟被调整了,ID生成器停止服务.
                throw new RuntimeException(String.format("时钟已经回拨.  Refusing to generate id for %d milliseconds", lastStamp - currentStamp));
            }
            if (currentStamp == lastStamp) {
                // 每次+1
                sequence = (sequence + 1) & MAX_SEQUENCE;
                // 毫秒内序列溢出
                if (sequence == 0) {
                    // 阻塞到下一个毫秒,获得新的时间戳
                    currentStamp = getNextMill();
                }
            } else {
                //不同毫秒内，序列号置0
                sequence = 0L;
            }
            lastStamp = currentStamp;
            //时间戳部分+机器标识部分+序列号部分
            return (currentStamp - START_TIMESTAMP) << TIMESTAMP_LEFT | machineIdPart | sequence;
        }

        /**
         * 阻塞到下一个毫秒，直到获得新的时间戳
         */
        private long getNextMill() {
            long mill = timeGen();
            //
            while (mill <= lastStamp) {
                mill = timeGen();
            }
            return mill;
        }

        /**
         * 返回以毫秒为单位的当前时间
         */
        protected long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
