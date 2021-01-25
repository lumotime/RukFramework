package com.j3dream.android.common.base;

import android.app.Application;

import androidx.annotation.NonNull;

import com.j3dream.android.common.R;
import com.j3dream.android.common.interf.ICompatApplication;
import com.j3dream.android.common.log.Logger;
import com.j3dream.android.common.util.Utils;
import com.j3dream.core.util.ObjectUtils;
import com.j3dream.core.util.executor.ThreadPoolUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>文件名称: BaseApplication </p>
 * <p>所属包名: com.bloodsport.lib.core.base</p>
 * <p>描述: 基础的应用实例 </p>
 * <p>创建时间: 2020/3/16 11:15 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BaseApplication extends Application {

    /**
     * 在主线程中进行初始化操作
     */
    protected abstract void initApplicationInMain();

    /**
     * 在后台线程中进行初始化操作
     */
    protected abstract void initApplicationInBack();

    /**
     * 需要扫描应用组建的包
     *
     * @return 应用组件的包名
     */
    @NonNull
    protected String[] scanAppCompatPackage() {
        return new String[0];
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化工具类
        Utils.init(this);
        // 初始化相关服务
        runInitServiceInBackThread();
        initApplicationInMain();
        try {
            initModulesAppCompat(scanModulesAppCompat());
        } catch (Exception ex) {
            Logger.e("init app module failure; ", ex);
        }
        initGlobalCrashHandler();
    }

    @NonNull
    protected String initLogTag() {
        return getString(R.string.default_app_logger_tag);
    }

    /**
     * 初始化全局的异常处理器
     */
    protected void initGlobalCrashHandler() {

    }

    /**
     * 在后台线程中启动初始化服务
     */
    private void runInitServiceInBackThread() {
        ThreadPoolUtils.runInBackground(new Runnable() {
            @Override
            public void run() {
                initApplicationInBack();
            }
        });
    }

    /**
     * 初始化全部的应用初始化组件
     *
     * @param modules 待初始化的应用初始化组件
     */
    private void initModulesAppCompat(final Map<String, ICompatApplication> modules) {
        final Set<String> keySet = modules.keySet();
        ThreadPoolUtils.runInBackground(new Runnable() {
            @Override
            public void run() {
                for (String key : keySet) {
                    ICompatApplication iCompatApplication = modules.get(key);
                    if (iCompatApplication != null) {
                        iCompatApplication.initModuleApplicationInBackThread(BaseApplication.this);
                        Logger.i("init module in back thread success" + key);
                    } else {
                        Logger.i("init module in back thread failure: " + key);
                    }
                }
            }
        });
        for (String module : keySet) {
            ICompatApplication iCompatApplication = modules.get(module);
            if (iCompatApplication != null) {
                iCompatApplication.initModuleApplicationInMainThread(BaseApplication.this);
                Logger.i("init module in main thread success: " + module);
            } else {
                Logger.i("init module in main thread failure: " + module);
            }
        }
    }

    /**
     * 扫描全部的应用初始化组件
     *
     * @return 全部的初始化组件
     */
    private Map<String, ICompatApplication> scanModulesAppCompat() {
        String[] modulesPackage = scanAppCompatPackage();
        if (ObjectUtils.isEmpty(modulesPackage)) {
            return Collections.emptyMap();
        }
        Map<String, ICompatApplication> listAppCompat = new HashMap<>();
        for (String modulePackage : modulesPackage) {
            try {
                Class<?> clazz = Class.forName(modulePackage);
                Object objAppCompat = clazz.newInstance();
                if (objAppCompat instanceof ICompatApplication) {
                    listAppCompat.put(modulePackage, (ICompatApplication) objAppCompat);
                }
            } catch (ClassNotFoundException ex) {
                Logger.e("not found has application compat: " + modulePackage);
            } catch (IllegalAccessException ex) {
                Logger.e("application compat new instance failure: " + modulePackage);
            } catch (InstantiationException ex) {
                Logger.e("application compat convert failure: " + modulePackage);
            }
        }
        return listAppCompat;
    }
}
