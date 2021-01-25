package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;
import com.j3dream.core.constant.RegexConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>文件名称: RegexUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: 正则匹配工具类， 提供常见的正则匹配工具 </p>
 * <p>创建时间: 2020/3/13 13:37 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class RegexUtils implements RegexConstants {

    private RegexUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 返回输入是否匹配简单手机的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobileSimple(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 返回输入是否与确切手机的正则表达式匹配
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isMobileExact(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, input);
    }

    /**
     * 返回输入是否匹配电话号码的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isTel(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_TEL, input);
    }

    /**
     * 返回输入是否匹配长度为15的身份证号码的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard15(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD15, input);
    }

    /**
     * 返回输入是否匹配长度为18的身份证号码的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard18(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ID_CARD18, input);
    }

    /**
     * 返回输入是否匹配电子邮件的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmail(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_EMAIL, input);
    }

    /**
     * 返回输入是否匹配url的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isURL(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_URL, input);
    }

    /**
     * 返回输入是否匹配汉字正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isZh(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_ZH, input);
    }

    /**
     * 返回输入是否匹配用户名的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isUsername(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_USERNAME, input);
    }

    /**
     * 返回输入是否匹配模式为“ yyyy-MM-dd”的日期的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isDate(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_DATE, input);
    }

    /**
     * 返回输入是否匹配ip地址的正则表达式
     *
     * @param input 输入
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIP(final CharSequence input) {
        return isMatch(RegexConstants.REGEX_IP, input);
    }

    /**
     * 正则匹配
     *
     * @param regex 规则
     * @param input 输入
     * @return 是否正确 {@code true}: yes  {@code false}: no
     */
    public static boolean isMatch(final String regex, final CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 返回匹配正则表达式的输入列表
     *
     * @param regex 规则
     * @param input 输入
     * @return 输入列表与正则表达式匹配
     */
    public static List<String> getMatches(final String regex, final CharSequence input) {
        if (input == null) return Collections.emptyList();
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 将输入拆分为正则表达式的匹配项
     *
     * @param regex 规则
     * @param input 输入
     * @return 通过在正则表达式匹配项附近拆分输入而计算出的字符串数组
     */
    public static String[] getSplits(final String regex, final String input) {
        if (input == null) return new String[0];
        return input.split(regex);
    }

    /**
     * 用给定的替换字符串替换与* regex匹配的输入序列的第一个子序列
     *
     * @param regex       规则
     * @param input       输入
     * @param replacement 替换字符串
     * @return 通过用替换字符串替换第一个匹配的子序列并根据需要替换捕获的子序列而构造的字符串
     */
    public static String getReplaceFirst(final String regex,
                                         final String input,
                                         final String replacement) {
        if (input == null) return "";
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 用给定的替换字符串替换与*模式匹配的输入序列的每个子序列
     *
     * @param regex       规则
     * @param input       输入
     * @param replacement 替换字符串
     * @return 过用替换字符串替换每个匹配子序列，并根据需要替换捕获的子序列来构造的字符串
     */
    public static String getReplaceAll(final String input,
                                       final String regex,
                                       final String replacement) {
        if (input == null) return "";
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }
}
