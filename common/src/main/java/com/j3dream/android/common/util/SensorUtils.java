package com.j3dream.android.common.util;

import android.content.Context;
import android.os.Vibrator;

import androidx.annotation.RequiresPermission;

import com.j3dream.android.common.constant.Constants;

import static android.Manifest.permission.VIBRATE;

/**
 * <p>文件名称: SensorUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.util</p>
 * <p>描述: 传感器操作工具类 </p>
 * <p>创建时间: 2020/3/13 15:54 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class SensorUtils {
    /**
     * 震感响应器
     */
    private static Vibrator vibrator;

    private SensorUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 进行持续milliseconds的震感反馈
     *
     * @param milliseconds 震感反馈持续的市场 (毫秒)
     */
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long milliseconds) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) {
            return;
        }
        vibrator.vibrate(milliseconds);
    }

    /**
     * 长时间触发震感反馈
     *
     * @param pattern 长时间数组
     * @param repeat  重复模式索引, 如果不想重复可以设置为 -1
     */
    @RequiresPermission(VIBRATE)
    public static void vibrate(final long[] pattern, final int repeat) {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) {
            return;
        }
        vibrator.vibrate(pattern, repeat);
    }

    /**
     * 关闭震感反馈
     */
    @RequiresPermission(VIBRATE)
    public static void cancelVibrate() {
        Vibrator vibrator = getVibrator();
        if (vibrator == null) {
            return;
        }
        vibrator.cancel();
    }

    /**
     * 获取震感响应器
     *
     * @return 震感响应器
     */
    private static Vibrator getVibrator() {
        if (vibrator == null) {
            vibrator = (Vibrator) Utils.getApp().getSystemService(Context.VIBRATOR_SERVICE);
        }
        return vibrator;
    }
}
