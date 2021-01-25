package com.j3dream.core.enums;

/**
 * <p>文件名称: TimeSpanType </p>
 * <p>所属包名: com.lumotime.core.enums</p>
 * <p>描述: 时间跨度类型 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 11:32 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public enum TimeSpanType {
    /**
     *
     */
    MSEC(1),
    /**
     * 秒
     */
    SEC(1000),
    /**
     * 分钟
     */
    MIN(60000),
    /**
     * 小时
     */
    HOUR(3600000),
    /**
     * 天
     */
    DAY(86400000),
    /**
     * 星期
     */
    WEEK(604800000);
    private long size;

    TimeSpanType(long size) {
        this.size = size;
    }

    public long getSize() {
        return size;
    }
}
