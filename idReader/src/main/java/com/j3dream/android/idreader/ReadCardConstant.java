package com.j3dream.android.idreader;

/**
 * <p>@ProjectName:     SocietyInspectInputSystem</p>
 * <p>@ClassName:       ReadCardConstant</p>
 * <p>@PackageName:     com.example.dell.checkproject.constant</p>
 * <b>
 * <p>@Description:     读卡服务相关常量集</p>
 * </b>
 * <p>@author:          Rivkaer Jia</p>
 * <p>@date:            19-11-18 下午1:18</p>
 * <p>@email:           cnrivkaer@outlook.com</p>
 */
public interface ReadCardConstant {

    /**
     * 两次读取相同身份证的有效时间间隔
     */
    long LIMIT_LAST_RECEIVER_READ_CARD_INFO_VALID_TIME_INTERVAL = 1500L;

    String TIP_CUR_READ_PERSON_CARD_INFO_EMPTY_ERROR = "本次读卡信息为空,请重新刷卡!!!";

    String ID_CARD_INFO_HYPHEN = "-";

    String TAG_PREFERENCE_SERVICE_READ_INTERVAL = "TAG_PREFERENCE_SERVICE_READ_INTERVAL";

    String HINT_START_READER_SUCCESS = "读卡器开启成功";
    String HINT_START_READER_FAILURE = "读卡器开启失败";
    String HINT_STOP_READER_SUCCESS = "读卡器关闭成功";
    String HINT_STOP_READER_FAILURE = "读卡器开启失败";

    /**
     * 开启指纹传感器成功
     */
    String ACTION_START_FINGERPRINT_SENSOR_SUCCESS = "cybertech.intent.action.OPEN_FINGERPRINT_SENSOR_SUCCEED";
    /**
     * 读取身份证信息成功
     */
    String ACTION_READ_PERSON_CARD_RESULT_SUCCESS = "cybertech.intent.action.READ_IDCARD_SUCCEED";
    /**
     * 读取身份证信息失败
     */
    String ACTION_READ_PERSON_CARD_RESULT_WARNING = "cybertech.intent.action.READ_IDCARD_WARN";
    /**
     * 对比结果
     */
    String ACTION_COMPAIR_RESULT = "cybertech.intent.action.COMPAIR_RESULT";
    /**
     * Action 读卡驱动包名
     */
    String PACKAGE_CYBERTECH_IDCARD = "com.cybertech.idcard";
    /**
     * 广播开始读卡
     */
    String BROADCAST_READ_CARD_START_READ = "cybertech.intent.START_READCARD";
    /**
     * 广播结束读卡
     */
    String BROADCAST_READ_CARD_STOP_READ = "cybertech.intent.STOP_READCARD";

}
