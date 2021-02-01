package com.j3dream.android.common.util;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.annotation.StringDef;

import com.j3dream.android.common.constant.Constants;
import com.j3dream.android.common.interf.IntentConstants;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.SEND_SMS;

/**
 * <p>文件名称: IntentUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.utils</p>
 * <p>描述: 意图操作工具类 </p>
 * <p>创建时间: 2020/3/13 11:28 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class IntentUtils implements IntentConstants {

    private IntentUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 显示一个网页
     *
     * @param context 调用上下文
     * @param url     跳转的Url
     */
    public static void displayWebPage(@NonNull Context context, @NonNull String url) {
        Intent intent = newIntent();
        Uri uri = Uri.parse(url);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 拨打目标电话
     *
     * @param context 调用上下文
     * @param phone   拨打的号码
     */
    @RequiresPermission(CALL_PHONE)
    public static void callPhone(@NonNull Context context, @NonNull String phone) {
        Intent intent = newIntent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(Constants.TEL + phone);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 发送目标短消息
     *
     * @param context   调用上下文
     * @param addressee 收件人
     * @param content   消息内容
     */
    @RequiresPermission(SEND_SMS)
    public static void sendSMS(@NonNull Context context, @NonNull String addressee, @NonNull String content) {
        Intent intent = newIntent();
        Uri uri = Uri.parse(Constants.SMS_TO + addressee);
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(uri);
        intent.putExtra(Constants.SMS_BODY, content);
        context.startActivity(intent);
    }

    /**
     * 打开联系人列表
     *
     * @param context 调用上下文
     */
    public static void goContactsList(@NonNull Context context) {
        Intent intent = newIntent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setType(Constants.CONTACTS_LIST_INTENT_TYPE);
        context.startActivity(intent);
    }

    /**
     * 打开收音机
     *
     * @param context 调用上下文
     */
    public static void goRadio(@NonNull Context context) {
        Intent intent = newIntent();
        intent.setAction(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        context.startActivity(intent);
    }

    /**
     * 前往目标活动
     *
     * @param activity 调用上下文
     * @param clazz    目标活动地址
     */
    public static void goActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> clazz, int requestCode) {
        goActivityForResult(activity, clazz, new Bundle(), requestCode);
    }

    /**
     * 前往目标活动
     *
     * @param activity 调用上下文
     * @param clazz    目标活动地址
     * @param bundle   bundle数据
     */
    public static void goActivityForResult(@NonNull Activity activity, @NonNull Class<? extends Activity> clazz, Bundle bundle, int requestCode) {
        Intent intent = newIntent();
        intent.setClass(activity, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 前往目标活动
     *
     * @param context 调用上下文
     * @param clazz   目标活动地址
     */
    public static void goActivity(@NonNull Context context, @NonNull Class<? extends Activity> clazz) {
        goActivity(context, clazz, new Bundle());
    }

    /**
     * 前往目标活动
     *
     * @param context 调用上下文
     * @param clazz   目标活动地址
     * @param bundle  bundle数据
     */
    public static void goActivity(@NonNull Context context, @NonNull Class<? extends Activity> clazz, Bundle bundle) {
        Intent intent = newIntent();
        intent.setClass(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 启动一个后台任务
     *
     * @param context 调用上下文
     * @param clazz   目标活动地址
     */
    public static void startService(@NonNull Context context, @NonNull Class<? extends Service> clazz) {
        startService(context, clazz);
    }

    /**
     * 启动一个后台任务
     *
     * @param context 调用上下文
     * @param clazz   目标活动地址
     * @param bundle  bundle数据
     */
    public static void startService(@NonNull Context context, @NonNull Class<? extends Service> clazz, Bundle bundle) {
        Intent intent = newIntent();
        intent.setClass(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startService(intent);
    }

    /**
     * 创建一个意图
     *
     * @return 创建意图
     */
    public static Intent newIntent() {
        return new Intent();
    }

    /**
     * 创建一个带有目标的意图
     *
     * @param context 应用上下文
     * @param clazz   目标活动地址
     * @return 创建意图
     */
    public static Intent newIntent(@NonNull Context context, @NonNull Class<? extends Activity> clazz) {
        Intent intent = newIntent();
        intent.setClass(context, clazz);
        return intent;
    }

    /**
     * 跳转相关系统设置
     *
     * @param context 启动活动
     * @param action  动作
     */
    public static void goSystemSetting(@NonNull Context context, @SettingIntentModel String action) {
        Intent intent = newIntent();
        ComponentName componentName = new ComponentName(SYSTEM_SETTINGS_PACKAGE, action);
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_VIEW);
        context.startActivity(intent);
    }

    /**
     * 返回启动应用程序详细信息设置的意图
     *
     * @return 启动应用程序详细信息设置的意图
     */
    public static Intent getLaunchAppDetailsSettingsIntent() {
        return getLaunchAppDetailsSettingsIntent(AppUtils.getAppPackageName(), false);
    }

    /**
     * 返回启动应用程序详细信息设置的意图
     *
     * @param pkgName 包名称
     * @return 启动应用程序详细信息设置的意图
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String pkgName) {
        return getLaunchAppDetailsSettingsIntent(pkgName, false);
    }

    /**
     * 返回启动应用程序详细信息设置的意图
     *
     * @param pkgName 包名称
     * @return 启动应用程序详细信息设置的意图
     */
    public static Intent getLaunchAppDetailsSettingsIntent(final String pkgName, final boolean isNewTask) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + pkgName));
        return getIntent(intent, isNewTask);
    }

    /**
     * 获取展示App自启动设置的意图
     *
     * @return 自启动设置意图
     */
    public static Intent getLaunchAppAutoStartSettingIntent() {
        return getLaunchAppAutoStartSettingIntent(AppUtils.getAppPackageName(), false);
    }

    /**
     * 获取展示App自启动设置的意图
     *
     * @param pkgName 应用程序包名
     * @return 自启动设置意图
     */
    public static Intent getLaunchAppAutoStartSettingIntent(final String pkgName) {
        return getLaunchAppAutoStartSettingIntent(pkgName, false);
    }

    /**
     * 获取展示App自启动设置的意图
     *
     * @param pkgName   应用程序包名
     * @param isNewTask 是否使用一个新栈
     * @return 自启动设置意图
     */
    public static Intent getLaunchAppAutoStartSettingIntent(final String pkgName, final boolean isNewTask) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("package:" + pkgName));
        if (RomUtils.isEmui()) {
            ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
            intent.setComponent(componentName);
        } else if (RomUtils.isMiui()) {
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
            intent.setComponent(componentName);
        } else if (RomUtils.isOppo()) {
            ComponentName componentName = null;
            if (Build.VERSION.SDK_INT >= 26) {
                componentName = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity");
            } else {
                componentName = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.startup.StartupAppListActivity");
            }
            intent.setComponent(componentName);
            //上面的程式碼不管用了，因為oppo手機也是手機管家進行自啟動管理
        } else if (RomUtils.isVivo()) {
            ComponentName componentName = null;
            if (Build.VERSION.SDK_INT >= 26) {
                componentName = new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
            } else {
                componentName = new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.SoftwareManagerActivity");
            }
            intent.setComponent(componentName);
        } else if (RomUtils.isFlyme()) {
            // 通過測試，發現魅族是真噁心，也是夠了，之前版本還能檢視到關於設定自啟動這一介面，
            // 系統更新之後，完全找不到了，心裡默默Fuck！
            // 針對魅族，我們只能通過魅族內建手機管家去設定自啟動，
            // 所以我在這裡直接跳轉到魅族內建手機管家介面，具體結果請看圖
            ComponentName componentName = ComponentName.unflattenFromString("com.meizu.safe" +
                    "/.permission.PermissionMainActivity");
            intent.setComponent(componentName);
        } else {
            // 以上只是市面上主流機型，由於公司你懂的，所以很不容易才湊齊以上裝置
            // 針對於其他裝置，我們只能調整當前系統app檢視詳情介面
            // 在此根據使用者手機當前版本跳轉系統設定介面
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", pkgName, null));
            } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.FROYO) {
                intent.setAction(Intent.ACTION_VIEW);
                intent.setClassName("com.android.settings",
                        "com.android.settings.InstalledAppDetails");
                intent.putExtra("com.android.settings.ApplicationPkgName",
                        pkgName);
            }
            intent = new Intent(Settings.ACTION_SETTINGS);
        }
        return getIntent(intent, isNewTask);
    }

    private static Intent getIntent(final Intent intent, final boolean isNewTask) {
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    @StringDef(value = {
            SYSTEM_SETTING,
            SYSTEM_ACCESSIBILITY_SETTING,
            ACTIVITY_PICKER,
            SYSTEM_APN,
            SYSTEM_APPLICATION,
            SYSTEM_BATTERY_INFO,
            SYSTEM_DATE_TIME_SETTING,
            SYSTEM_DEVELOP,
            SYSTEM_ABOUT_DEVICE,
            SYSTEM_DISPLAY_SETTINGS,
            SYSTEM_INSTALLED_APP_DETAIL,
            SYSTEM_PRIVACY_SETTING,
            SYSTEM_PROXY_SETTING,
            SYSTEM_TEST,
            SYSTEM_USAGE_STATS,
            SYSTEM_WIFI_SETTING
    })
    @Retention(RetentionPolicy.SOURCE)
    private @interface SettingIntentModel {
    }
}
