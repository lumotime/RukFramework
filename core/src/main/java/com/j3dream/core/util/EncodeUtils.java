package com.j3dream.core.util;

import com.j3dream.core.constant.TextConstants;

import org.apache.commons.codec.binary.Base64;

/**
 * <p>文件名称: EncodeUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.util</p>
 * <p>描述: 编码工具类 </p>
 * <p>创建时间: 2020/3/13 16:25 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class EncodeUtils implements TextConstants {

    public EncodeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Base64编码
     *
     * @param bytes 待编码数据
     * @return 编码后的数据
     */
    public static byte[] encodeBase64(byte[] bytes) {
        return Base64.encodeBase64(bytes);
    }

    /**
     * Base64解码
     *
     * @param bytes 待解码数据
     * @return 解码后的数据
     */
    public static byte[] decodeBase64(byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * Base64编码
     *
     * @param bytes 待编码数据
     * @return 编码后的数据
     */
    public static String encodeBase64String(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * Base64解码
     *
     * @param body 待解码数据
     * @return 解码后的数据
     */
    public static byte[] decodeBase64(String body) {
        return Base64.decodeBase64(body);
    }

    /**
     * 字节数组转HEX字符串
     *
     * @param bytes 输入的字节数组
     * @return HEX 字符串
     */
    public static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        int len = bytes.length;
        if (len <= 0) {
            return "";
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * 十六进制字符转为十进制
     *
     * @param numberChar 带转换字符
     * @return 十进制树
     */
    private static int hex2Dec(final char numberChar, final int radix) throws NumberFormatException {
        String number = String.valueOf(numberChar).toUpperCase();
        return Integer.valueOf(number, radix);
    }
}
