package com.j3dream.android.common.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * <p>文件名称: SizeUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.util</p>
 * <p>描述: 单位转换工具 </p>
 * <p>创建时间: 2020/3/13 16:12 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class SizeUtils {

    private SizeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 待转换参数
     * @return 转换后参数
     */
    public static int dp2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param pxValue 待转换参数
     * @return 转换后参数
     */
    public static int px2dp(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spVal 待转换参数
     * @return 转换后的值
     */
    public static int sp2px(float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, Utils.getApp().getResources().getDisplayMetrics());
    }

    /**
     * px转sp
     *
     * @param pxVal 待转换参数
     * @return 转换后的值
     */
    public static float px2sp(float pxVal) {
        return (pxVal / Utils.getApp().getResources().getDisplayMetrics().scaledDensity);
    }
}
