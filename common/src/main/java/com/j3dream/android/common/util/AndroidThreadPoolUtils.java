package com.j3dream.android.common.util;

import android.os.Handler;
import android.os.Looper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>文件名称: AndroidPoolUtils </p>
 * <p>所属包名: com.j3dream.android.common.util</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/11/3 12:00 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class AndroidThreadPoolUtils {

    /**
     * 用于记录后台等待的Runnable，第一个参数外面的Runnable，第二个参数是等待中的Runnable
     */
    private static HashMap<Runnable, Runnable> mapToMainHandler = new HashMap<Runnable, Runnable>();
    private static Thread mainThread;
    private static Handler mainHandler;

    static {
        Looper mainLooper = Looper.getMainLooper();
        mainThread = mainLooper.getThread();
        mainHandler = new Handler(mainLooper);
    }

    public static boolean isOnMainThread() {
        return mainThread == Thread.currentThread();
    }

    public static void runOnMainThread(Runnable r) {
        if (isOnMainThread()) {
            r.run();
        } else {
            mainHandler.post(r);
        }
    }

    public static void runOnMainThread(Runnable r, long delayMillis) {
        if (delayMillis <= 0) {
            runOnMainThread(r);
        } else {
            mainHandler.postDelayed(r, delayMillis);
        }
    }

    /**
     * 对runOnMainThread的，移除Runnable
     *
     * @param runnable 待移除观察队列的任务
     */
    public static void removeCallbackOnMainThread(Runnable runnable) {
        mainHandler.removeCallbacks(runnable);
    }

    public static void removeCallbackInBackground(Runnable runnable) {
        Runnable mainRunnable = mapToMainHandler.get(runnable);
        if (mainRunnable != null) {
            mainHandler.removeCallbacks(mainRunnable);
        }
    }

    public static StringBuilder logAllThreadStackTrace() {
        StringBuilder builder = new StringBuilder();
        Map<Thread, StackTraceElement[]> liveThreads = Thread.getAllStackTraces();
        for (Iterator<Thread> i = liveThreads.keySet().iterator(); i.hasNext(); ) {
            Thread key = i.next();
            builder.append("Thread ").append(key.getName())
                    .append("\n");
            StackTraceElement[] trace = liveThreads.get(key);
            for (int j = 0; j < trace.length; j++) {
                builder.append("\tat ").append(trace[j]).append("\n");
            }
        }
        return builder;
    }
}
