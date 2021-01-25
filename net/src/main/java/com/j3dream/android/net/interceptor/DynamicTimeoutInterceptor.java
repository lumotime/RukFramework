package com.j3dream.android.net.interceptor;

import com.j3dream.android.net.NetConfigurator;
import com.j3dream.android.net.annotation.DynamicTimeout;
import com.j3dream.android.net.config.NetConfig;

import java.io.IOException;
import java.lang.reflect.Method;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Invocation;

/**
 * <p>文件名称: DynamicTimeoutInterceptor </p>
 * <p>所属包名: com.j3dream.android.net.interceptor</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/26 14:39 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class DynamicTimeoutInterceptor implements Interceptor {

    private NetConfig netConfig;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Chain newChain = chain;
        Request request = chain.request();
        Invocation invocation = request.tag(Invocation.class);
        final Method method = invocation != null ? invocation.method() : null;
        final DynamicTimeout dynamicTimeout = method != null ? method.getAnnotation(DynamicTimeout.class) : null;
        if (dynamicTimeout == null) {
            if (netConfig == null) {
                netConfig = NetConfigurator.getInstance().getNetConfig();
            }
            if (netConfig.isTimeout()) {
                newChain = newChain
                        .withConnectTimeout(netConfig.getConnectTimeout(), netConfig.getTimeUnit())
                        .withWriteTimeout(netConfig.getConnectTimeout(), netConfig.getTimeUnit())
                        .withReadTimeout(netConfig.getConnectTimeout(), netConfig.getTimeUnit());
            }
            return newChain.proceed(request);
        }
        if (dynamicTimeout.connectTimeout() > 0) {
            newChain = newChain.withConnectTimeout(dynamicTimeout.connectTimeout(), dynamicTimeout.unit());
        }
        if (dynamicTimeout.readTimeout() > 0) {
            newChain = newChain.withReadTimeout(dynamicTimeout.readTimeout(), dynamicTimeout.unit());
        }
        if (dynamicTimeout.writeTimeout() > 0) {
            newChain = newChain.withWriteTimeout(dynamicTimeout.connectTimeout(), dynamicTimeout.unit());
        }
        return newChain.proceed(request);
    }
}
