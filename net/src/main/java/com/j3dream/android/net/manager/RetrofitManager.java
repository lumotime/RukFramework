package com.j3dream.android.net.manager;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j3dream.android.net.NetConfigurator;
import com.j3dream.android.net.adapter.LocalDateTimeAdapter;
import com.j3dream.android.net.config.NetConfig;
import com.j3dream.android.net.exception.NetHandleException;
import com.j3dream.core.util.ObjectUtils;
import com.j3dream.core.util.StringUtils;

import java.util.Calendar;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>文件名称: RetrofitManager </p>
 * <p>所属包名: com.bloodsport.net</p>
 * <p>描述: 用户管理Network框架中Retrofit实例 </p>
 * <p>创建时间: 2020-02-09 16:52 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class RetrofitManager {

    private static RetrofitManager sRetrofitManager;
    private Retrofit mRetrofit;

    private RetrofitManager() {
    }

    public static RetrofitManager getInstance() {
        if (sRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (sRetrofitManager == null) {
                    sRetrofitManager = new RetrofitManager();
                }
            }
        }
        return sRetrofitManager;
    }

    /**
     * 获取 support base retrofit builder
     *
     * @return base retrofit builder
     */
    public static Retrofit.Builder getBaseRetrofitBuilder() {
        return getBaseRetrofitBuilder(null);
    }

    /**
     * 获取 support base retrofit builder
     *
     * @param gson 支持 gson 实例切换
     * @return base retrofit builder
     */
    public static Retrofit.Builder getBaseRetrofitBuilder(Gson gson) {
        NetConfig netFrameConfig = NetConfigurator.getInstance().getNetConfig();
        if (netFrameConfig == null || StringUtils.isEmpty(netFrameConfig.getServiceHost())) {
            throw new NetHandleException("please check init network frame configs or add service host is added!!!");
        }
        return createRetrofitBuilder(netFrameConfig, gson);
    }

    public Retrofit newRetrofit() {
        return newRetrofit(true);
    }

    /**
     * 创建一个 Retrofit
     *
     * @param netConfig
     * @return
     */
    private static Retrofit.Builder createRetrofitBuilder(NetConfig netConfig, Gson gson) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(netConfig.getServiceHost());
        builder.addConverterFactory(GsonConverterFactory.create(gson == null ? createGson() : gson));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.client(OKHttpManager.getInstance().getHttpClick());
        return builder;
    }

    public Retrofit getDefaultRetrofit() {
        return mRetrofit;
    }

    private static Gson createGson() {
        return new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(Calendar.class, new LocalDateTimeAdapter())
                .create();
    }

    /**
     * 获取Retrofit实例
     *
     * @return Retrofit实例
     */
    public Retrofit newRetrofit(boolean isForceRefreshHost) {
        NetConfig netFrameConfig = NetConfigurator.getInstance().getNetConfig();
        if (netFrameConfig == null || StringUtils.isEmpty(netFrameConfig.getServiceHost())) {
            throw new NetHandleException("please check init network frame configs or add service host is added!!!");
        }
        if (mRetrofit == null || (isForceRefreshHost &&
                !StringUtils.null2Length0(Uri.parse(netFrameConfig.getServiceHost()).getHost())
                        .equals(ObjectUtils.toString(mRetrofit.baseUrl().host())))) {
            mRetrofit = createRetrofitBuilder(netFrameConfig, null).build();
        }
        return mRetrofit;
    }
}
