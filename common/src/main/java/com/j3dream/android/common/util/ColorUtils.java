package com.j3dream.android.common.util;

import android.graphics.Color;

import androidx.annotation.ColorInt;

import com.j3dream.android.common.constant.Constants;
import com.j3dream.core.util.RandomUtils;
import com.j3dream.core.util.StringUtils;

/**
 * <p>文件名称: ColorUtils </p>
 * <p>所属包名: cn.novakj.j3.core.util</p>
 * <p>描述: 颜色工具类 </p>
 * <p>创建时间: 2020/4/27 17:49 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ColorUtils {

    private static final char[] HEX_DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static final int ALPHA_COLOR_LENGTH = 8;
    private static final int NORMAL_COLOR_LENGTH = 6;

    private ColorUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 随一个生成一个颜色, 默认支持透明度, 基础的颜色(全色系颜色)
     *
     * @return 随机颜色
     */
    @ColorInt
    public static int nextColor() {
        return nextColor(true, ColorStyle.NORMAL);
    }

    /**
     * 随一个生成一个颜色
     *
     * @param isSupportAlpha 是否支持透明度
     * @param style          支持颜色的色系
     *                       <ul>
     *                               <li>{@link ColorStyle#NORMAL} 全色系颜色</li>
     *                               <li>{@link ColorStyle#LIGHT} 偏亮颜色</li>
     *                               <li>{@link ColorStyle#DARK} 暗色颜色</li>
     *                       </ul>
     * @return 随机颜色
     */
    @ColorInt
    public static int nextColor(boolean isSupportAlpha, ColorStyle style) {
        int colorLength = isSupportAlpha ? ALPHA_COLOR_LENGTH : NORMAL_COLOR_LENGTH;
        StringBuilder colorStrBuilder = new StringBuilder("#");
        for (int i = 0; i < 2; i++) {
            colorStrBuilder.append(HEX_DIGITS[RandomUtils.nextInt(HEX_DIGITS.length)]);
        }
        for (int i = 2; i < colorLength; i++) {
            int hexCharIndex = style == ColorStyle.NORMAL
                    ? RandomUtils.nextInt(HEX_DIGITS.length) : style == ColorStyle.LIGHT
                    ? RandomUtils.nextInt(11, HEX_DIGITS.length) : RandomUtils.nextInt(0, 10);
            colorStrBuilder.append(HEX_DIGITS[hexCharIndex]);
        }
        String colorStr = StringUtils.toString(colorStrBuilder);
        return Color.parseColor(
                "#".equals(colorStr) || colorStr.length() < 4 || colorStr.charAt(0) != '#'
                        ? "#FFFFFF" : colorStr
        );
    }

    public enum ColorStyle {
        NORMAL,
        LIGHT,
        DARK
    }
}
