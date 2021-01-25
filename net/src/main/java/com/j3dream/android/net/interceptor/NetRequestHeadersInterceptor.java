package com.j3dream.android.net.interceptor;

import com.j3dream.core.util.ObjectUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>文件名称: CommonNetRequestHeadersInterceptor </p>
 * <p>所属包名: com.bloodsport.net</p>
 * <p>描述: 通用机场的网络请求头追加拦截器 </p>
 * <p>创建时间: 2020-02-14 11:19 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class NetRequestHeadersInterceptor implements Interceptor {

    private final Map<String, String> requestHeaders = new HashMap<>();

    public NetRequestHeadersInterceptor(Map<String, String> headers) {
        if (ObjectUtils.isEmpty(headers)) {
            return;
        }

        requestHeaders.putAll(headers);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();
        for (Map.Entry<String, String> itemEntry : requestHeaders.entrySet()) {
            requestBuilder.addHeader(itemEntry.getKey(), itemEntry.getValue());
        }

        return chain.proceed(requestBuilder.build());
    }
}
