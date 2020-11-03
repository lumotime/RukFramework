package com.j3dream.android.net.utils;

import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.IntDef;
import androidx.annotation.RequiresPermission;

import com.j3dream.android.common.util.Utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>文件名称: NetworkUtils </p>
 * <p>所属包名: com.bloodsport.core.util</p>
 * <p>描述: 网络相关的工作集 </p>
 * <p>创建时间: 2020-01-07 15:35 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class NetworkUtils {

    /**
     * 没有连接网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;

    /**
     * 得到当前网络的状态
     *
     * @return 网络链接状态
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static int getNetWorkState() {
        return getNetWorkState(Utils.getApp());
    }

    /**
     * 得到当前网络的状态
     *
     * @param context 当前调用者的上下文
     * @return 网络链接状态
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    @NetworkType
    public static int getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    /**
     * 得到当前网络的状态
     *
     * @param context 当前调用者的上下文
     * @return 网络链接状态
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 当获取服务时获取服务失败，则直接返回未连接
        if (connectivityManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        } else {
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
        }
    }

    @IntDef(value = {
            NETWORK_NONE, NETWORK_MOBILE, NETWORK_WIFI
    })
    @Retention(value = RetentionPolicy.SOURCE)
    @Target(value = {ElementType.METHOD, ElementType.PARAMETER})
    public @interface NetworkType {
    }
}