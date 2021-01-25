package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;
import com.j3dream.core.constant.TextConstants;

/**
 * <p>文件名称: StringUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: 字符串工具, 封装对字符串进行操作的工具类集合 </p>
 * <p>创建时间: 2020/3/13 13:20 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class StringUtils implements TextConstants {

    private StringUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 设置输入单次的首字母小写
     *
     * @param s 输入
     * @return 首字母小写的输入
     */
    public static String lowerFirstLetter(String s) {
        if (s == null || s.length() == 0) {
            return EMPTY;
        }
        if (!Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) + 32) + s.substring(1);
    }

    /**
     * 设置输入单次的首字母大写
     *
     * @param s 输入
     * @return 首字母大写的输入
     */
    public static String upperFirstLetter(String s) {
        if (s == null || s.length() == 0) {
            return EMPTY;
        }
        if (!Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return (char) (s.charAt(0) - 32) + s.substring(1);
    }


    /**
     * 将null 转换为 defaultVal, 当defaultVal也为空时将返回 ""
     *
     * @param s        输入
     * @param defValue 默认字符
     * @return 转换后的字符
     */
    public static String null2DefValue(final CharSequence s, CharSequence defValue) {
        return toString(s == null ? defValue : s);
    }

    /**
     * 将 null 转换为空字符串
     *
     * @param s 输入
     * @return 转换后的字符
     */
    public static String null2Length0(final String s) {
        return s == null ? EMPTY : s;
    }

    /**
     * 将 null 转换为空字符串
     *
     * @param charSequence 输入
     * @return 转换后的字符
     */
    public static String null2Length0(final CharSequence charSequence) {
        return charSequence == null ? EMPTY : charSequence.toString();
    }

    /**
     * 获取字符串长度
     *
     * @param sequence 待操作的字符串
     * @return 字符串长度
     */
    public static int length(CharSequence sequence) {
        return sequence == null ? 0 : sequence.length();
    }

    /**
     * 字符反转
     *
     * @param s 输入
     * @return 反转后的字符
     */
    public static String reverse(final String s) {
        if (s == null) {
            return EMPTY;
        }
        int len = s.length();
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转换为半角字符串
     *
     * @param s 输入
     * @return 转换后的半角字符串
     */
    public static String toDBC(final String s) {
        if (s == null || s.length() == 0) {
            return EMPTY;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转换为全角字符串
     *
     * @param s 输入
     * @return 转换后的全角字符串
     */
    public static String toSBC(final String s) {
        if (s == null || s.length() == 0) {
            return EMPTY;
        }
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 判断两个输入是否相等
     *
     * @param a 输入
     * @param b 输入
     * @return {@code true} equals {@code false} don't equals
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两个输入是否不相等
     *
     * @param sequence  输入
     * @param sequence1 输入
     * @return {@code true} don't equals {@code false} equals
     */
    public static boolean isNotEquals(CharSequence sequence, CharSequence sequence1) {
        return !equals(sequence, sequence1);
    }

    /**
     * 判断是否为空
     *
     * @param sequence 待判断的字符
     * @return {@code true} null string {@code false} don't null string
     */
    public static boolean isEmpty(CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param sequence 待判断的字符
     * @return {@code true} don't null string {@code false} null string
     */
    public static boolean isNotEmpty(CharSequence sequence) {
        return !isEmpty(sequence);
    }

    /**
     * 是否是空字符串或是否其中包含空格
     *
     * @param s 输入
     * @return {@code true} 空字符串或包含空格 ${@code false} no
     */
    public static boolean isSpace(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组合字符串数组, 使用connector进行连接
     *
     * @param source    字符串列表
     * @param connector 连字符
     * @return string
     */
    public static String joinArray(String[] source, String connector) {
        return String.join(connector, source);
    }

    /**
     * 从字符串列表中找到目标字符串的下标
     *
     * @param arr     字符串数组
     * @param value   目标字符串
     * @param isExact 精确查找,模糊查找（模糊查找如果有多个只会返回第一个符合条件的）
     * @return 查找目标字符串的下标
     */
    public static int getStrIndexFormArray(CharSequence[] arr, CharSequence value, boolean isExact) {
        for (int i = 0; i < arr.length; i++) {
            if (isExact && equals(arr[i], value)) {
                return i;
            } else {
                if (StringUtils.null2Length0(arr[i]).contains(value)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 输出对象文本
     *
     * @param obj 待处理的实例
     * @return 对象文本
     */
    public static String toString(Object obj) {
        return obj == null ? NULL_TEXT : obj.toString();
    }
}
