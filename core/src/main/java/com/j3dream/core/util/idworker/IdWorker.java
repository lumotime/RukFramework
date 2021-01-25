package com.j3dream.core.util.idworker;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>文件名称: IdWorker </p>
 * <p>所属包名: cn.ftxxkj.system.verification.common.util</p>
 * <p>描述: Id 工作者 </p>
 * <p>创建时间: 2020/8/12 09:12 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class IdWorker {

    private static ConcurrentHashMap<Type, IdWork> WORKERS = new ConcurrentHashMap<>();

    private IdWorker() {
        throw new UnsupportedOperationException("u can't new instance");
    }

    /**
     * 获取Id生产者任务
     *
     * @param type 生产者类型
     * @return 生产者
     */
    public static IdWork get(Type type) {
        if (type == null) {
            if (WORKERS.contains(Type.SNOWFLAKE)) {
                return WORKERS.get(Type.SNOWFLAKE);
            } else {
                IdWork idWork = new SnowflakeIdWork();
                WORKERS.put(Type.SNOWFLAKE, idWork);
                return idWork;
            }
        }
        switch (type) {
            case UUID:
                if (WORKERS.contains(Type.UUID)) {
                    return WORKERS.get(Type.UUID);
                } else {
                    IdWork idWork = new UuidIdWork();
                    WORKERS.put(Type.UUID, idWork);
                    return idWork;
                }
            case SNOWFLAKE:
            default:
                if (WORKERS.contains(Type.SNOWFLAKE)) {
                    return WORKERS.get(Type.SNOWFLAKE);
                } else {
                    IdWork idWork = new SnowflakeIdWork();
                    WORKERS.put(Type.SNOWFLAKE, idWork);
                    return idWork;
                }
        }
    }

    /**
     * 获取下一个可用的id
     *
     * @return 生成的id
     */
    public static String nextId() {
        return nextId(null);
    }

    /**
     * 根据类型获取下一个生成的id
     *
     * @param type id生成类型
     * @return 生成的id
     */
    public static String nextId(Type type) {
        return get(type).nextId();
    }

    /**
     * 获取 uuid 随机字符串
     *
     * @return uuid 随机字符串
     */
    public static String nextUUID() {
        return get(Type.UUID).nextId();
    }

    public enum Type {
        UUID,
        SNOWFLAKE
    }
}
