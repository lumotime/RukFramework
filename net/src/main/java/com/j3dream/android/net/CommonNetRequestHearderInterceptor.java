package com.j3dream.android.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>文件名称: CommonNetRequestHearderInterceptor </p>
 * <p>所属包名: com.bloodsport.net</p>
 * <p>描述: 通用机场的网络请求头追加拦截器 </p>
 * <p>创建时间: 2020-02-14 11:19 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class CommonNetRequestHearderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Connection", "close").build();
        return chain.proceed(request);
    }
}
