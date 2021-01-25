package com.j3dream.core.constant;

/**
 * <p>文件名称: RegexConstants </p>
 * <p>所属包名: com.lumotime.core.constant</p>
 * <p>描述: 正则常量列表 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 10:43 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface RegexConstants {

    ///////////////////////////////////////////////////////////////////////////
    // 规则来自于 http://blankj.com
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 手机号规则
     */
    String REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$";
    /**
     * 手机号段规则
     * <p>中国移动: 134(0-8), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 198</p>
     * <p>中国联通: 130, 131, 132, 145, 155, 156, 166, 171, 175, 176, 185, 186</p>
     * <p>中国电信: 133, 153, 173, 177, 180, 181, 189, 199, 191</p>
     * <p>全球: 1349</p>
     * <p>虚拟: 170</p>
     */
    String REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5-8])|(18[0-9])|(19[1,8,9]))\\d{8}$";
    /**
     * 电话匹配规则
     */
    String REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}$";
    /**
     * 15位身份证号规则
     */
    String REGEX_ID_CARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
    /**
     * 18位身份证号规则
     */
    String REGEX_ID_CARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";
    /**
     * 电子邮箱规则
     */
    String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 链接规则
     */
    String REGEX_URL = "[a-zA-z]+://[^\\s]*";
    /**
     * 中文规则
     */
    String REGEX_ZH = "^[\\u4e00-\\u9fa5]+$";
    /**
     * 常见用户名规则
     * <p>允许输入 "a-z", "A-Z", "0-9", "_", "Chinese character"</p>
     * <p>禁止以 "_" 结尾</p>
     * <p>长度必须为 6 to 20</p>
     */
    String REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$";
    /**
     * 日期规则 "yyyy-MM-dd".
     */
    String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";
    /**
     * IP地址规则
     */
    String REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";

    ///////////////////////////////////////////////////////////////////////////
    // 规则来自于 http://tool.oschina.net/regex， 其他更多查看: http://toutiao.com/i6231678548520731137
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Regex of double-byte characters.
     */
    String REGEX_DOUBLE_BYTE_CHAR = "[^\\x00-\\xff]";
    /**
     * Regex of blank line.
     */
    String REGEX_BLANK_LINE = "\\n\\s*\\r";
    /**
     * Regex of QQ number.
     */
    String REGEX_QQ_NUM = "[1-9][0-9]{4,}";
    /**
     * Regex of postal code in China.
     */
    String REGEX_CHINA_POSTAL_CODE = "[1-9]\\d{5}(?!\\d)";
    /**
     * Regex of positive integer.
     */
    String REGEX_POSITIVE_INTEGER = "^[1-9]\\d*$";
    /**
     * Regex of negative integer.
     */
    String REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$";
    /**
     * Regex of integer.
     */
    String REGEX_INTEGER = "^-?[1-9]\\d*$";
    /**
     * Regex of non-negative integer.
     */
    String REGEX_NOT_NEGATIVE_INTEGER = "^[1-9]\\d*|0$";
    /**
     * Regex of non-positive integer.
     */
    String REGEX_NOT_POSITIVE_INTEGER = "^-[1-9]\\d*|0$";
    /**
     * Regex of positive float.
     */
    String REGEX_POSITIVE_FLOAT = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";
    /**
     * Regex of negative float.
     */
    String REGEX_NEGATIVE_FLOAT = "^-[1-9]\\d*\\.\\d*|-0\\.\\d*[1-9]\\d*$";
}
