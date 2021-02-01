package com.j3dream.android.net.manager;

import com.j3dream.android.net.NetConfigurator;
import com.j3dream.android.net.NetConstant;
import com.j3dream.android.net.config.NetConfig;
import com.j3dream.android.net.interceptor.ContactSecurityInterceptor;
import com.j3dream.android.net.interceptor.DynamicTimeoutInterceptor;
import com.j3dream.android.net.interceptor.NetRequestHeadersInterceptor;
import com.j3dream.android.net.logger.FrameworkNetLogger;
import com.j3dream.core.util.ObjectUtils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Collection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <p>文件名称: OKHttpManager </p>
 * <p>所属包名: com.bloodsport.net</p>
 * <p>描述: 网络请求框架中用于管理Okhttp实例的管理器 </p>
 * <p>创建时间: 2020-02-09 17:01 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class OKHttpManager {

    private static OKHttpManager sOkHttpManager;
    private OkHttpClient mOkHttpClick;

    private OKHttpManager() {
        init();
    }

    public static OKHttpManager getInstance() {
        if (sOkHttpManager == null) {
            synchronized (OKHttpManager.class) {
                if (sOkHttpManager == null) {
                    sOkHttpManager = new OKHttpManager();
                }
            }
        }
        return sOkHttpManager;
    }

    /**
     * 获取 support base okhttpclient builder
     *
     * @return base okhttpclient builder
     */
    public static OkHttpClient.Builder getBaseOkHttpClientBuilder() {
        // 获取网络框架的配置信息
        NetConfig netFrameConfig = NetConfigurator.getInstance().getNetConfig();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (!ObjectUtils.isEmpty(netFrameConfig.getInterceptors())) {
            Collection<Interceptor> interceptors = netFrameConfig.getInterceptors().values();
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }
        if (netFrameConfig.getSecurityGrade() > NetConstant.DEFAULT_ALLOW_CONTACT_SECURITY) {
            builder.addInterceptor(new ContactSecurityInterceptor());
        }
        builder.addInterceptor(new NetRequestHeadersInterceptor(netFrameConfig.getHeaders()));
        if (netFrameConfig.isOpenLog()) {
            builder.addInterceptor(new HttpLoggingInterceptor(new FrameworkNetLogger())
                    .setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        builder.addInterceptor(new DynamicTimeoutInterceptor());
        settingSSL(builder);
        builder.followRedirects(true);
        return builder;
    }

    /**
     * 设置OKHttpClient证书配置
     *
     * @param builder OKHttpClient构造器
     */
    private static void settingSSL(OkHttpClient.Builder builder) {
        // 忽略证书验证
        X509TrustManager xtm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
            HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            builder.sslSocketFactory(sslContext.getSocketFactory()).hostnameVerifier(DO_NOT_VERIFY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private void init() {
    }

    /**
     * 获取全局唯一的 OkHttpClient 实例
     *
     * @return 全局唯一的 OkHttpClient 实例
     */
    public OkHttpClient getHttpClick() {
        if (mOkHttpClick == null) {
            mOkHttpClick = getBaseOkHttpClientBuilder().build();
        }
        return mOkHttpClick;
    }
}
