package com.j3dream.android.net.config;

import com.google.common.collect.Maps;
import com.j3dream.android.common.util.AppUtils;
import com.j3dream.android.net.NetConstant;
import com.j3dream.android.net.model.SecurityKey;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;

/**
 * <p>文件名称: NetFrameConfig </p>
 * <p>所属包名: com.bloodsport.net.config</p>
 * <p>描述: 网络框架配置信息 </p>
 * <p>创建时间: 2020-02-19 14:48 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class NetConfig {
    /**
     * 是否开启Log
     */
    private boolean openLog;
    /**
     * 日志打印器的tag
     */
    private String loggerTag;
    /**
     * 加密等级
     */
    private int securityGrade;
    /**
     * 默认追加的头
     */
    private Map<String, String> headers = new HashMap<>();
    /**
     * 网络库中connectTimeout/readTimeout/writeTimeout的设置的具体的时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 链接超时时间
     */
    private long connectTimeout;
    /**
     * 读取超时时间
     */
    private long readTimeout;
    /**
     * 写入超时时间
     */
    private long writeTimeout;
    /**
     * 服务的host
     */
    private String serviceHost;
    /**
     * 默认加密Key的名称
     */
    private String encryptKeyName = NetConstant.DEFAULT_ENCRYPT_KEY_NAME;

    private Map<String, Interceptor> interceptors;

    private SecurityKey securityKey;

    private int respSuccessCode;

    private NetConfig() {
    }

    public Map<String, Interceptor> getInterceptors() {
        return interceptors;
    }

    public String getEncryptKeyName() {
        return encryptKeyName;
    }

    public SecurityKey getSecurityKey() {
        return securityKey;
    }

    public boolean isOpenLog() {
        return openLog;
    }

    public String getLoggerTag() {
        return loggerTag;
    }

    public int getSecurityGrade() {
        return securityGrade;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void addHeaders(Map<String, String> newHeaders) {
        headers = new HashMap<>(headers);
        headers.putAll(newHeaders);
    }

    public void refreshServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public String getServiceHost() {
        return serviceHost;
    }

    public int getRespSuccessCode() {
        return respSuccessCode;
    }

    public static class Builder {
        private boolean openLog = AppUtils.isAppDebug();
        private String loggerTag = NetConstant.TAG_NET_FRAME_LOGGER;
        private int securityGrade = 0;
        private Map<String, String> headers = new HashMap<>();
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        private long connectTimeout = 30;
        private long readTimeout = 60;
        private long writeTimeout = 60;
        private String serviceHost;
        private String encryptKeyName = NetConstant.DEFAULT_ENCRYPT_KEY_NAME;
        private SecurityKey securityKey;
        private int respSuccessCode;
        private Map<String, Interceptor> interceptors = Maps.newHashMap();

        public Builder setOpenLog(boolean openLog) {
            this.openLog = openLog;
            return this;
        }

        public Builder setLoggerTag(String loggerTag) {
            this.loggerTag = loggerTag;
            return this;
        }

        public Builder setSecurityGrade(int securityGrade) {
            this.securityGrade = securityGrade;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder setRespSuccessCode(int respSuccessCode) {
            this.respSuccessCode = respSuccessCode;
            return this;
        }

        public Builder setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(long writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setServiceHost(String serviceHost) {
            this.serviceHost = serviceHost;
            return this;
        }

        public Builder setEncryptKeyName(String encryptKeyName) {
            this.encryptKeyName = encryptKeyName;
            return this;
        }

        public Builder setSecurityKey(SecurityKey securityKey) {
            this.securityKey = securityKey;
            return this;
        }

        public Builder setInterceptors(Map<String, Interceptor> interceptors) {
            this.interceptors = interceptors;
            return this;
        }

        public NetConfig build() {
            NetConfig config = new NetConfig();
            config.openLog = this.openLog;
            config.loggerTag = this.loggerTag;
            config.securityGrade = this.securityGrade;
            config.headers = this.headers;
            config.timeUnit = this.timeUnit;
            config.connectTimeout = this.connectTimeout;
            config.readTimeout = this.readTimeout;
            config.writeTimeout = this.writeTimeout;
            config.serviceHost = this.serviceHost;
            config.encryptKeyName = this.encryptKeyName;
            config.securityKey = this.securityKey;
            config.respSuccessCode = this.respSuccessCode;
            config.interceptors = this.interceptors;
            return config;
        }
    }
}
