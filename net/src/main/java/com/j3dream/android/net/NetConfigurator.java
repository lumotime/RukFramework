package com.j3dream.android.net;

import com.j3dream.android.net.config.NetConfig;
import com.j3dream.core.util.ObjectUtils;

/**
 * <p>文件名称: NetFrameConfigurator </p>
 * <p>所属包名: com.bloodsport.net</p>
 * <p>描述:  网络框架配置者，用于配置相关的网络框架信息 </p>
 * <p>创建时间: 2020-02-19 14:44 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class NetConfigurator {

    private NetConfig mFrameConfig;

    {
        mFrameConfig = createDefaultConfig();
    }

    public static NetConfigurator getInstance() {
        return NetFrameConfiguratorHolder.HOLDER;
    }

    /**
     * 网络框架初始化
     *
     * @param netFrameConfig 网络框架初始化
     */
    public void init(NetConfig netFrameConfig) {
        if (netFrameConfig == null) {
            return;
        }

        this.mFrameConfig = netFrameConfig;
    }

    /**
     * 获取网络框架配置信息
     *
     * @return 网络框架配置信息
     */
    public NetConfig getNetConfig() {
        if (ObjectUtils.isEmpty(mFrameConfig)) {
            mFrameConfig = createDefaultConfig();
        }
        return mFrameConfig;
    }

    /**
     * 创建默认的配置
     *
     * @return 默认的配置
     */
    private NetConfig createDefaultConfig() {
        return new NetConfig.Builder()
                .setLoggerTag(NetConstant.TAG_NET_FRAME_LOGGER)
                .setOpenLog(true)
                .build();
    }

    private static final class NetFrameConfiguratorHolder {
        private static final NetConfigurator HOLDER = new NetConfigurator();
    }
}
