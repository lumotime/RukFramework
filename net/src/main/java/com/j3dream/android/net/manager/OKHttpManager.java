package com.j3dream.android.net.manager;

import com.j3dream.android.net.NetConfigurator;
import com.j3dream.android.net.NetConstant;
import com.j3dream.android.net.config.NetConfig;
import com.j3dream.android.net.interceptor.ContactSecurityInterceptor;
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
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class OKHttpManager {

    private static OkHttpClient mOkHttpClick;

    private OKHttpManager() {
    }

    public static OKHttpManager getInstance() {
        return OKHttpManagerHolder.instance;
    }

    /**
     * 设置OKHttpClient证书配置
     *
     * @param builder OKHttpClient构造器
     */
    private void settingSSL(OkHttpClient.Builder builder) {
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

    public OkHttpClient.Builder getBaseOkHttpClientBuilder() {
        // 获取网络框架的配置信息
        NetConfig netFrameConfig = NetConfigurator.getInstance().getNetConfig();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(netFrameConfig.getConnectTimeout(), netFrameConfig.getTimeUnit());
        builder.readTimeout(netFrameConfig.getReadTimeout(), netFrameConfig.getTimeUnit());
        builder.writeTimeout(netFrameConfig.getWriteTimeout(), netFrameConfig.getTimeUnit());
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
        settingSSL(builder);
        builder.followRedirects(true);
        return builder;
    }

    public OkHttpClient getHttpClick() {
        if (mOkHttpClick == null) {
            mOkHttpClick = getBaseOkHttpClientBuilder().build();
        }
        return mOkHttpClick;
    }

    private static class OKHttpManagerHolder {
        static final OKHttpManager instance = new OKHttpManager();
    }
}
