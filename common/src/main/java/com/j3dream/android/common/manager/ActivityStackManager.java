package com.j3dream.android.common.manager;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.LinkedList;
import java.util.Stack;

/**
 * <p>文件名称: ActivityManager </p>
 * <p>所属包名: com.jnft.controller.utils</p>
 * <p>描述: 应用的活动控制 </p>
 * <p>创建时间: 2020-02-18 11:44 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class ActivityStackManager {

    private final Stack<Activity> sActivityList;
    private SwitchBackgroundCallbacks mCurActivityLifecycleCallbacks;

    private ActivityStackManager() {
        sActivityList = new Stack<>();
    }

    public static ActivityStackManager getInstance() {
        return ActivityManagerHolder.HOLDER;
    }

    public void registerActivityStackManager(Application application) {
        if (application == null) {
            return;
        }
        mCurActivityLifecycleCallbacks = new SwitchBackgroundCallbacks();
        application.registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
    }

    public void unregisterActivityStackManager(Application application) {
        if (application == null || mCurActivityLifecycleCallbacks == null) {
            return;
        }
        application.unregisterActivityLifecycleCallbacks(mCurActivityLifecycleCallbacks);
    }

    /**
     * @param activity 作用说明 ：添加一个activity到管理里
     */
    public boolean pushActivity(Activity activity) {
        return sActivityList.add(activity);
    }

    /**
     * @param activity 作用说明 ：删除一个activity在管理里
     */
    public boolean popActivity(Activity activity) {
        return sActivityList.remove(activity);
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (sActivityList == null || sActivityList.isEmpty()) {
            return null;
        }
        return sActivityList.get(sActivityList.size() - 1);
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishCurrentActivity() {
        if (sActivityList == null || sActivityList.isEmpty()) {
            return;
        }
        Activity activity = sActivityList.pop();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (sActivityList == null || sActivityList.isEmpty()) {
            return;
        }
        if (activity != null) {
            sActivityList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束除最新目标活动之外的所有 Activity
     *
     * @param activity 目标活动
     */
    public void finishAllActivitiesExceptNewest(Activity activity) {
        if (sActivityList == null || sActivityList.isEmpty()) {
            return;
        }
        if (activity != null) {
            sActivityList.remove(activity);
            for (Activity itemActivity : sActivityList) {
                itemActivity.finish();
                sActivityList.remove(itemActivity);
            }
            sActivityList.add(activity);
        }
    }

    /**
     * 结束除最新目标活动之外的所有 Activity
     *
     * @param clz 目标活动类型
     */
    public void finishAllActivitiesExceptNewest(@NonNull final Class<? extends Activity> clz) {
        if (sActivityList == null || sActivityList.isEmpty()) {
            return;
        }
        int count = 0;
        for (Activity activity : sActivityList) {
            if (activity.getClass().equals(clz) && count < 1) {
                count++;
            } else {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (sActivityList == null || sActivityList.isEmpty()) {
            return;
        }
        for (Activity activity : sActivityList) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 按照指定类名找到activity
     *
     * @param cls
     * @return
     */
    public Activity findActivity(Class<?> cls) {
        Activity targetActivity = null;
        if (sActivityList != null) {
            for (Activity activity : sActivityList) {
                if (activity.getClass().equals(cls)) {
                    targetActivity = activity;
                    break;
                }
            }
        }
        return targetActivity;
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (sActivityList == null) {
            return;
        }
        for (Activity activity : sActivityList) {
            activity.finish();
        }
        sActivityList.clear();
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 作用说明 ：获取当前最顶部activity的实例
     */
    public Activity getTopActivity() {
        Activity mBaseActivity = null;
        synchronized (sActivityList) {
            final int size = sActivityList.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = sActivityList.get(size);
        }
        return mBaseActivity;

    }

    /**
     * @return 作用说明 ：获取当前最顶部的acitivity 名字
     */
    public String getTopActivityName() {
        Activity mBaseActivity = null;
        synchronized (sActivityList) {
            final int size = sActivityList.size() - 1;
            if (size < 0) {
                return null;
            }
            mBaseActivity = sActivityList.get(size);
        }
        return mBaseActivity.getClass().getName();
    }

    /**
     * 获取应用活动栈
     *
     * @return 应用活动栈
     */
    public LinkedList<Activity> getActivityStack() {
        return new LinkedList<>(sActivityList);
    }

    private static class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            ActivityStackManager.getInstance().pushActivity(activity);
        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }

        @Override
        public void onActivityResumed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityPaused(@NonNull Activity activity) {

        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
            ActivityStackManager.getInstance().popActivity(activity);
        }
    }

    private static final class ActivityManagerHolder {
        static final ActivityStackManager HOLDER = new ActivityStackManager();
    }
}
