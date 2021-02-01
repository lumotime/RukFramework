package com.j3dream.android.net;

import com.j3dream.android.common.util.AppUtils;

/**
 * <p>文件名称: NetFrameConstant </p>
 * <p>所属包名: com.bloodsport.net</p>
 * <p>描述: 网络框架常量实体 </p>
 * <p>创建时间: 2020-02-19 15:09 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface NetConstant {

    String TAG = "J3Dream_NET";

    /**
     * Logger 默认打印TAG
     */
    String TAG_NET_FRAME_LOGGER = AppUtils.getAppName();
    /**
     * 默认的加密字端名称
     */
    String DEFAULT_ENCRYPT_KEY_NAME = "bloodsport-key";
    /**
     * 通讯放行的安全等级
     */
    int DEFAULT_ALLOW_CONTACT_SECURITY = 0;
    /**
     * 跳过加密所需要的头
     */
    String HEARD_SKIP_ENCRYPT = "Content-Encrypt-Skip:J3Code";
    String HEARD_SKIP_ENCRYPT_TAG = "Content-Encrypt-Skip";
}
