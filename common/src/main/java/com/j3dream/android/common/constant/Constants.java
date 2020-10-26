package com.j3dream.android.common.constant;

import androidx.collection.SimpleArrayMap;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * <p>文件名称: Constant </p>
 * <p>所属包名: com.j3dream.android.common.constant</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/10/26 11:09 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface Constants extends com.j3dream.core.constant.Constants {
    String SMS_TO = "smsto:";
    String SMS_BODY = "sms_body";
    String TEL = "tel:";
    String CONTACTS_LIST_INTENT_TYPE = "vnd.android.cursor.item/phone";

    //# TimeDateUtils
    long DAY_TIMESTAMP = 1000 * 60 * 60 * 24;
    String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    String[] DEFAULT_WEEK_DAY = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    String FORMAT_WEEK_YYYY_MM_DD = "YYYY-MM-dd";
    String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    String FORMAT_YYYY_MM = "yyyy-MM";
    String FORMAT_YYYY = "yyyy";

    //# SystemUtils
    //特殊的AndroidId,如果获取到#KEY_SPECIAL_ANDROID_ID则认为获取异常
    String KEY_SPECIAL_ANDROID_ID = "9774d56d682e549c";
    String METHOD_GET_IMEI = "getImei";
    String CMD_SU = "su";
    String[] ROOT_PERMISSION_FILE_DIR_PATH_LIST = {"/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
            "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
            "/system/sbin/", "/usr/bin/", "/vendor/bin/"};
    Set<String> CHINA_OPERATOR_YI_DONG = Sets.newHashSet("46000", "46002", "46007", "46020");
    Set<String> CHINA_OPERATOR_LIAN_TONG = Sets.newHashSet("46001", "46006", "46009");
    Set<String> CHINA_OPERATOR_DIAN_XIN = Sets.newHashSet("46003", "46005", "46011");
    String CHINA_OPERATOR_YI_DONG_NAME = "中国移动";
    String CHINA_OPERATOR_LIAN_TONG_NAME = "中国联通";
    String CHINA_OPERATOR_DIAN_XIN_NAME = "中国电信";

    //# RegexUtils
    SimpleArrayMap<String, String> CITY_MAP = new SimpleArrayMap<>();

    //# RandomUtils
    int DEFAULT_RANDOM_TEXT_LENGTH = 8;
    String DEFAULT_RANDOM_TEXT_SEED_KEY = "0123456789QWERTYUIOPLKJHGFDSAZXCVBNMqwertyuioplkjhgfdsazxcvbnm";
    String UUID_CONNECT_SYMBOL = "-";

    //# AppUtils
    String PACKAGE_INTENT_TYPE = "application/vnd.android.package-archive";
    String PACKAGE = "package:";
    String PROVIDER_SUFFIX = ".provider";
}
