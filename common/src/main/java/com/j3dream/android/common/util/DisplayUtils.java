package com.j3dream.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * <p>文件名称: DisplayUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.util</p>
 * <p>描述: 显示工具类 </p>
 * <p>创建时间: 2020/3/13 16:13 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class DisplayUtils {

    private DisplayUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取显示尺寸中的长边大小
     *
     * @return 显示长边大小
     */
    public static int getLongSideSize() {
        return Math.max(getDisplayMetricsHeight(), getDisplayMetricsWidth());
    }

    /**
     * 获取屏幕宽度
     *
     * @return 屏幕宽度
     */
    @SuppressLint("ObsoleteSdkInt")
    public static int getDisplayMetricsWidth() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * 获取屏幕高度
     *
     * @return 屏幕高度
     */
    @SuppressLint("ObsoleteSdkInt")
    public static int getDisplayMetricsHeight() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取应用屏幕的宽度（单位：px）
     *
     * @return 应用屏幕的宽度（单位：px）
     */
    public static int getAppScreenWidth() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.x;
    }

    /**
     * 获取应用屏幕的高度（单位：px）
     *
     * @return 应用屏幕的高度（单位：px）
     */
    public static int getAppScreenHeight() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * 获取屏幕的像素密度
     *
     * @return 屏幕的像素密度
     */
    public static float getDisplayMetrics() {
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        return dm.density;
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isLandscape() {
        return Utils.getApp().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isPortrait() {
        return Utils.getApp().getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_PORTRAIT;
    }

}
