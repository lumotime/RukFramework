package com.j3dream.android.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;

import com.j3dream.android.common.constant.Constants;
import com.j3dream.android.common.data.CPUInfo;
import com.j3dream.android.common.data.SysBuildInfo;
import com.j3dream.android.common.log.Logger;
import com.j3dream.core.constant.TextConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

import static android.Manifest.permission.READ_PHONE_STATE;
import static com.j3dream.android.common.constant.Constants.CHINA_OPERATOR_DIAN_XIN;
import static com.j3dream.android.common.constant.Constants.CHINA_OPERATOR_DIAN_XIN_NAME;
import static com.j3dream.android.common.constant.Constants.CHINA_OPERATOR_LIAN_TONG;
import static com.j3dream.android.common.constant.Constants.CHINA_OPERATOR_LIAN_TONG_NAME;
import static com.j3dream.android.common.constant.Constants.CHINA_OPERATOR_YI_DONG;
import static com.j3dream.android.common.constant.Constants.CHINA_OPERATOR_YI_DONG_NAME;
import static com.j3dream.android.common.constant.Constants.CMD_SU;
import static com.j3dream.android.common.constant.Constants.ROOT_PERMISSION_FILE_DIR_PATH_LIST;

/**
 * <p>文件名称: SystemUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.utils</p>
 * <p>描述: 系统工具类, 操作获取常用的系统信息 </p>
 * <p>创建时间: 2020/3/13 12:04 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class AndroidSystemUtils {

    private AndroidSystemUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 获取cpu平台信息
     *
     * @return cpu平台信息
     */
    public static String getCpuHardware() {
        return Build.HARDWARE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机系统定制厂商
     *
     * @return 手机系统定制厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取移动设备制造商
     *
     * @return 移动设备制造商
     */
    public static String getDeviceProduct() {
        return Build.PRODUCT;
    }

    /**
     * 获取移动设备硬件制造商
     *
     * @return 移动设备硬件制造商
     */
    public static String getDeviceManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * 获取当前设备的ANDROID_ID
     *
     * @return ANDROID_ID
     */
    @SuppressLint("HardwareIds")
    public static String getAndroidID() {
        String id = Settings.Secure.getString(
                Utils.getApp().getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        if (Constants.KEY_SPECIAL_ANDROID_ID.equals(id)) {
            return TextConstants.EMPTY;
        }
        return id == null ? TextConstants.EMPTY : id;
    }

    /**
     * 获取当前设备序列号
     *
     * @return 设备序列号
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    @RequiresPermission(READ_PHONE_STATE)
    public static String getSerial() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? Build.getSerial() : Build.SERIAL;
    }

    /**
     * 获取设置的abi支持列表
     *
     * @return abi支持列表
     */
    public static String[] getSystemABIs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Build.SUPPORTED_ABIS;
        } else {
            if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
                return new String[]{Build.CPU_ABI, Build.CPU_ABI2};
            }
            return new String[]{Build.CPU_ABI};
        }
    }

    /**
     * 获取当前设备的imsi
     *
     * @return 当前设备IMSI
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    @RequiresPermission(READ_PHONE_STATE)
    public static String getIMSI() {
        return getTelephonyManager().getSubscriberId();
    }

    /**
     * 获取当前设备的imei
     *
     * @return 当前设备IMEI
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    @RequiresPermission(READ_PHONE_STATE)
    public static String getIMEI() {
        String imei = TextConstants.EMPTY;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            imei = getTelephonyManager().getDeviceId();
        } else {
            Method method = null;
            try {
                method = getTelephonyManager().getClass().getMethod(Constants.METHOD_GET_IMEI);
                imei = (String) method.invoke(getTelephonyManager());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return imei;
    }

    /**
     * 获取设备终端类型
     *
     * @return 当前设备终端类型
     * <ul>
     * <li>{@link TelephonyManager#PHONE_TYPE_NONE}</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_GSM }</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_CDMA}</li>
     * <li>{@link TelephonyManager#PHONE_TYPE_SIP }</li>
     * </ul>
     */
    public static int getPhoneType() {
        TelephonyManager tm = getTelephonyManager();
        return tm.getPhoneType();
    }

    /**
     * 获取sim卡运营商名称
     *
     * @return 运营商名称
     */
    public static String getSimOperatorName() {
        TelephonyManager tm = getTelephonyManager();
        return tm.getSimOperatorName();
    }

    /**
     * 获取sim卡运营商名称
     *
     * @return 运营商名称
     */
    public static String getSimOperatorByMnc() {
        TelephonyManager tm = getTelephonyManager();
        String operator = tm.getSimOperator();
        if (operator == null) {
            return TextConstants.EMPTY;
        }

        if (CHINA_OPERATOR_DIAN_XIN.contains(operator)) {
            return CHINA_OPERATOR_DIAN_XIN_NAME;
        }

        if (CHINA_OPERATOR_LIAN_TONG.contains(operator)) {
            return CHINA_OPERATOR_LIAN_TONG_NAME;
        }

        if (CHINA_OPERATOR_YI_DONG.contains(operator)) {
            return CHINA_OPERATOR_YI_DONG_NAME;
        }

        return operator;
    }

    /**
     * 判断当前设备是否是平板
     *
     * @return {@code true} yes {@code false} no
     */
    public static boolean isTablet() {
        return (Utils.getApp().getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 判断当前设备是否是手机
     *
     * @return {@code true} yes {@code false} no
     */
    public static boolean isPhone() {
        TelephonyManager tm = getTelephonyManager();
        return tm.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 判断当前设备sim卡是否已经准备好
     *
     * @return {@code true} yes {@code false} no
     */
    public static boolean isSimCardReady() {
        TelephonyManager tm = getTelephonyManager();
        return tm.getSimState() == TelephonyManager.SIM_STATE_READY;
    }

    /**
     * 判断设备root状态
     *
     * @return {@code true}: yes {@code false}: no
     */
    public static boolean isDeviceRooted() {
        for (String location : ROOT_PERMISSION_FILE_DIR_PATH_LIST) {
            if (new File(location + CMD_SU).exists()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断设备adb开启状态
     *
     * @return {@code true} yes {@code false} no
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isDeviceAdbEnabled() {
        return Settings.Secure.getInt(
                Utils.getApp().getContentResolver(),
                Settings.Global.ADB_ENABLED, 0
        ) > 0;
    }

    /**
     * 判断当前设备是否是虚拟机
     *
     * @return {@code true}: yes {@code false}: no
     */
    public static boolean isEmulator() {
        boolean checkProperty = Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
        if (checkProperty) {
            return true;
        }
        String operatorName = "";
        TelephonyManager tm = getTelephonyManager();
        if (tm != null) {
            String name = tm.getNetworkOperatorName();
            if (name != null) {
                operatorName = name;
            }
        }
        boolean checkOperatorName = operatorName.toLowerCase().equals("android");
        if (checkOperatorName) {
            return true;
        }
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        boolean checkDial = intent.resolveActivity(Utils.getApp().getPackageManager()) == null;
        return checkDial;
    }

    /**
     * 判断是否支持闪光灯
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isFlashlightEnable() {
        return Utils.getApp()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    /**
     * 获取系统 Build 信息
     *
     * @return 系统 Build 信息
     */
    @SuppressLint("MissingPermission")
    @Nullable
    public static SysBuildInfo getSystemBuildInfo() {
        try {
            SysBuildInfo sysBuildInfo = new SysBuildInfo();
            sysBuildInfo.setBoard(Build.BOARD);
            sysBuildInfo.setBootloader(Build.BOOTLOADER);
            sysBuildInfo.setBrand(Build.BRAND);
            sysBuildInfo.setDevice(Build.DEVICE);
            sysBuildInfo.setDisplay(Build.DISPLAY);
            sysBuildInfo.setFingerprint(Build.FINGERPRINT);
            sysBuildInfo.setHardware(Build.HARDWARE);
            sysBuildInfo.setHost(Build.HOST);
            sysBuildInfo.setId(Build.ID);
            sysBuildInfo.setManufacturer(Build.MANUFACTURER);
            sysBuildInfo.setModel(Build.MODEL);
            sysBuildInfo.setProduct(Build.PRODUCT);
            sysBuildInfo.setRadio(Build.getRadioVersion());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try {
                    sysBuildInfo.setSerial(Build.getSerial());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                sysBuildInfo.setSerial(Build.SERIAL);
            }
            sysBuildInfo.setTags(Build.TAGS);
            sysBuildInfo.setTime(Build.TIME);
            sysBuildInfo.setType(Build.TYPE);
            sysBuildInfo.setUser(Build.USER);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                sysBuildInfo.setOsVersion(Build.VERSION.BASE_OS);
                sysBuildInfo.setPreviewSdkInt(Build.VERSION.PREVIEW_SDK_INT);
                sysBuildInfo.setSecurityPatch(Build.VERSION.SECURITY_PATCH);
            }
            sysBuildInfo.setReleaseVersion(Build.VERSION.RELEASE);
            sysBuildInfo.setIncremental(Build.VERSION.INCREMENTAL);
            sysBuildInfo.setSdkInt(Build.VERSION.SDK_INT);
            return sysBuildInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取系统 CPU 信息
     *
     * @return CPU 信息
     */
    @Nullable
    public static CPUInfo getSystemCpuInfo() {
        try {
            CPUInfo cpuInfo = new CPUInfo();
            try {
                FileReader fr = new FileReader("/proc/cpuinfo");
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String result = line.toLowerCase();
                    String[] array = result.split(":\\s+", 2);
                    Logger.d("CPU INFO: " + result);
                    //cpu名字
                    if (array[0].startsWith("model name")) {
                        cpuInfo.setCpuName(array[1]);
                    }
                    //cpu架构
                    else if (array[0].startsWith("cpu part")) {
                        String cpuPartInfo = array[1];
                        cpuInfo.setCpuPart(cpuPartInfo);
                    }
                    //cpu品牌
                    else if (array[0].startsWith("hardware")) {
                        cpuInfo.setCpuHardware(array[1]);
                    }
                    //cpu速度
                    else if (array[0].startsWith("bogomips")) {
                        cpuInfo.setBogomips(array[1]);
                    }
                    //cpu细节描述
                    else if (array[0].startsWith("features")) {
                        cpuInfo.setFeatures(array[1]);
                    }
                    //cpu ARM架构
                    else if (array[0].startsWith("cpu implementer")) {
                        cpuInfo.setCpuImplementer(array[1]);
                    }
                    //cpu 指令集架构
                    else if (array[0].startsWith("cpu architecture")) {
                        cpuInfo.setCpuArchitecture(array[1]);
                    }
                    //cpu 变化
                    else if (array[0].startsWith("cpu variant")) {
                        cpuInfo.setCpuVariant(array[1]);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            cpuInfo.setCpuFreq(CPUInfoHelper.getCurCpuFreq() + "KHZ");
            cpuInfo.setCpuMaxFreq(CPUInfoHelper.getMaxCpuFreq() + "KHZ");
            cpuInfo.setCpuMinFreq(CPUInfoHelper.getMinCpuFreq() + "KHZ");
            cpuInfo.setCpuCores(CPUInfoHelper.getHeart());
            cpuInfo.setCpuTemp(CPUInfoHelper.getCpuTemp() + "℃");
            cpuInfo.setCpuAbi(CPUInfoHelper.putCpuAbi());
            return cpuInfo;
        } catch (Exception ex) {
            return null;
        }
    }

    private static TelephonyManager getTelephonyManager() {
        return (TelephonyManager) Utils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
    }
}