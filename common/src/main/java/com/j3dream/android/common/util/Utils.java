package com.j3dream.android.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import androidx.annotation.Nullable;

import com.j3dream.android.common.constant.Constants;
import com.j3dream.android.common.manager.ActivityStackManager;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * <p>文件名称: Utils </p>
 * <p>所属包名: com.bloodsport.lib.core</p>
 * <p>描述: 基础工具类集 </p>
 * <p>创建时间: 2020/3/13 09:24 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class Utils {

    /**
     * 全局持有的全局应用上下文
     */
    @SuppressLint("StaticFieldLeak")
    private volatile static Application sApplication;
    private static ActivityStackManager.ActivityCallbacks ACTIVITY_CALLBACK
            = new ActivityStackManager.ActivityCallbacks();

    private Utils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    public static void init(final Context context) {
        if (context == null) {
            init(getApplicationByReflect());
            return;
        }
        init((Application) context.getApplicationContext());
    }

    public static void init(final Application app) {
        if (sApplication == null) {
            if (app == null) {
                sApplication = getApplicationByReflect();
            } else {
                sApplication = app;
            }
            sApplication.registerActivityLifecycleCallbacks(ACTIVITY_CALLBACK);
        } else {
            if (app != null && app.getClass() != sApplication.getClass()) {
                sApplication.unregisterActivityLifecycleCallbacks(ACTIVITY_CALLBACK);
                sApplication = app;
                sApplication.registerActivityLifecycleCallbacks(ACTIVITY_CALLBACK);
            }
        }
    }

    /**
     * 获取应用上下文
     *
     * @return 应用上下文
     */
    public static Application getApp() {
        if (sApplication != null) {
            return sApplication;
        }
        Application app = getApplicationByReflect();
        init(app);
        return app;
    }

    public static Context getTopActivityOrApp() {
        if (isAppForeground()) {
            Activity topActivity = ACTIVITY_CALLBACK.getTopActivity();
            return topActivity == null ? Utils.getApp() : topActivity;
        } else {
            return Utils.getApp();
        }
    }

    static boolean isAppForeground() {
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am == null) return false;
        List<ActivityManager.RunningAppProcessInfo> info = am.getRunningAppProcesses();
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (aInfo.processName.equals(Utils.getApp().getPackageName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ActivityStackManager.ActivityCallbacks getActivityCallbacks() {
        return ACTIVITY_CALLBACK;
    }

    /**
     * @return 全局上下文
     * @see 'https://github.com/Blankj/AndroidUtilCode/blob/master/lib/utilcode/src/main/java/com/blankj/utilcode/util/Utils.java'
     */
    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    public static List<Activity> getActivityList() {
        return ACTIVITY_CALLBACK.getActivityList();
    }

    @Nullable
    public static Activity getTopActivity() {
        return ACTIVITY_CALLBACK.getTopActivity();
    }
}
