package com.j3dream.android.net.logger;

import android.util.Log;

import androidx.annotation.NonNull;

import com.j3dream.android.net.NetConfigurator;
import com.j3dream.core.util.StringUtils;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <p>文件名称: CustomNetworkLogger </p>
 * <p>所属包名: com.bloodsport.net</p>
 * <p>描述: 自定义的网络框架日志打印框架 </p>
 * <p>创建时间: 2020-02-09 17:02 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class FrameworkNetLogger implements HttpLoggingInterceptor.Logger {

    private String tag;

    public FrameworkNetLogger() {
    }

    public FrameworkNetLogger(String tag) {
        this.tag = tag;
    }

    @Override
    public void log(@NonNull String message) {
        Log.d(StringUtils.isEmpty(tag) ? getNetLoggerTag() : tag, message);
    }

    /**
     * 获取网络日志打印的log Tag
     *
     * @return log Tag
     */
    private String getNetLoggerTag() {
        return NetConfigurator.getInstance().getNetConfig().getLoggerTag();
    }
}