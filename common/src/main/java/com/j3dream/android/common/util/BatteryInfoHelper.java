package com.j3dream.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.BatteryManager;

import com.j3dream.android.common.constant.Constants;

/**
 * <p>文件名称: BatteryInfoHelper </p>
 * <p>所属包名: com.j3dream.android.common.util</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/11 12:48 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
class BatteryInfoHelper {

    public static String batteryHealth(int health) {
        String healthBat = Constants.UNKNOWN;
        switch (health) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                healthBat = "cold";
                break;
            case BatteryManager.BATTERY_HEALTH_DEAD:
                healthBat = "dead";
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                healthBat = "good";
                break;
            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                healthBat = "overVoltage";
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                healthBat = "overheat";
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                healthBat = "unknown";
                break;
            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                healthBat = "unspecified";
                break;
            default:
                break;
        }
        return healthBat;
    }

    public static String batteryStatus(int status) {
        String healthBat = Constants.UNKNOWN;
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                healthBat = "charging";
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                healthBat = "disCharging";
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                healthBat = "full";
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                healthBat = "notCharging";
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                healthBat = "unknown";
                break;
            default:
                break;
        }
        return healthBat;
    }

    public static String batteryPlugged(int status) {
        String healthBat = Constants.UNKNOWN;
        switch (status) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                healthBat = "ac";
                break;
            case BatteryManager.BATTERY_PLUGGED_USB:
                healthBat = "usb";
                break;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                healthBat = "wireless";
                break;
            default:
                break;
        }
        return healthBat;
    }

    @SuppressLint("public Api")
    public static String getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String powerProfileClass = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile = Class.forName(powerProfileClass)
                    .getConstructor(Context.class)
                    .newInstance(context);
            batteryCapacity = (double) Class.forName(powerProfileClass)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return batteryCapacity + "mAh";
    }
}
