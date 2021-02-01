package com.j3dream.android.common.util;

import android.os.SystemClock;

/**
 * <p>文件名称: TimeDateUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.utils</p>
 * <p>描述: 时间日期工具类, 用于获取和操作日期和时间 </p>
 * <p>创建时间: 2020/3/13 09:32 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class AndroidTimeUtils {

    public AndroidTimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取当前线程的时间戳
     *
     * @return 当前线程时间戳
     */
    public static long getCurThreadTimestamp() {
        return SystemClock.currentThreadTimeMillis();
    }

    /**
     * 获取当前线程的时间戳
     *
     * @return 当前线程时间戳(String)
     */
    public static String getCurThreadTimestampForString() {
        return String.valueOf(getCurThreadTimestamp());
    }

    /**
     * 从设备开机到现在的时间，单位毫秒，含系统深度睡眠时间
     *
     * @return 开机到现在的时间
     */
    public static long getElapsedRealTime() {
        return SystemClock.elapsedRealtime();
    }

    /**
     * 从设备开机到现在的时间，单位毫秒，不含系统深度睡眠时间
     *
     * @return 开机到现在的时间
     */
    public static long getUptime() {
        return SystemClock.elapsedRealtime();
    }
}