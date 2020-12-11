package com.j3dream.android.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.j3dream.android.common.constant.Constants;
import com.j3dream.android.common.data.AppInfo;
import com.j3dream.android.common.manager.ActivityStackManager;
import com.j3dream.core.constant.TextConstants;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.j3dream.android.common.constant.Constants.PACKAGE;
import static com.j3dream.android.common.constant.Constants.PACKAGE_INTENT_TYPE;
import static com.j3dream.android.common.constant.Constants.PROVIDER_SUFFIX;
import static com.j3dream.core.constant.Constants.HEX_DIGITS;

/**
 * <p>文件名称: AppUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.util</p>
 * <p>描述: 应用工具类，可以获取应用的相关信息 </p>
 * <p>创建时间: 2020/3/13 16:35 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class AppUtils {

    private AppUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 安装应用程序
     * 包含大于25的目标API {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}
     *
     * @param filePath 安装包文件路径
     */
    public static void installApp(final String filePath) {
        installApp(getFileByPath(filePath));
    }

    /**
     * 安装应用程序
     * 包含大于25的目标API {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}
     *
     * @param file 安装包文件
     */
    public static void installApp(final File file) {
        if (!isFileExists(file)) return;
        Utils.getApp().startActivity(getInstallAppIntent(file, true));
    }

    /**
     * 安装应用程序
     * 包含大于25的目标API {@code <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />}
     *
     * @param activity    调用上下文
     * @param file        安装包文件
     * @param requestCode 如果> = 0，则将返回此代码活动退出时的onActivityResult()
     */
    public static void installApp(final Activity activity,
                                  final File file,
                                  final int requestCode) {
        if (!isFileExists(file)) return;
        activity.startActivityForResult(getInstallAppIntent(file), requestCode);
    }

    /**
     * 卸载应用程序
     *
     * @param packageName 应用报名
     */
    public static void uninstallApp(final String packageName) {
        if (isSpace(packageName)) return;
        Utils.getApp().startActivity(getUninstallAppIntent(packageName, true));
    }

    /**
     * 卸载应用程序
     *
     * @param activity    调用上下文
     * @param packageName 应用报名
     * @param requestCode 如果> = 0，则将返回此代码活动退出时的onActivityResult()
     */
    public static void uninstallApp(final Activity activity,
                                    final String packageName,
                                    final int requestCode) {
        if (isSpace(packageName)) return;
        activity.startActivityForResult(getUninstallAppIntent(packageName), requestCode);
    }

    /**
     * 返回是否已安装该应用程序
     *
     * @param pkgName 应用包名
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppInstalled(@NonNull final String pkgName) {
        PackageManager packageManager = Utils.getApp().getPackageManager();
        try {
            return packageManager.getApplicationInfo(pkgName, 0) != null;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回它是否是调试应用程序
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug() {
        return isAppDebug(Utils.getApp().getPackageName());
    }

    /**
     * 返回它是否是调试应用程序
     *
     * @param packageName 应用包名
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppDebug(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回是否是系统应用程序
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem() {
        return isAppSystem(Utils.getApp().getPackageName());
    }

    /**
     * 返回是否是系统应用程序
     *
     * @param packageName 应用包名
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppSystem(final String packageName) {
        if (isSpace(packageName)) return false;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回应用程序是否为前台
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground() {
        return isAppForeground(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序是否为前台
     *
     * @param packageName 应用包名
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppForeground(@NonNull final String packageName) {
        return !isSpace(packageName) && packageName.equals(getForegroundProcessName());
    }

    /**
     * 返回应用程序是否正在运行
     *
     * @param pkgName 应用包名
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isAppRunning(@NonNull final String pkgName) {
        int uid;
        PackageManager packageManager = Utils.getApp().getPackageManager();
        try {
            ApplicationInfo ai = packageManager.getApplicationInfo(pkgName, 0);
            if (ai == null) return false;
            uid = ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        ActivityManager am = (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(Integer.MAX_VALUE);
            if (taskInfo != null && taskInfo.size() > 0) {
                for (ActivityManager.RunningTaskInfo aInfo : taskInfo) {
                    if (pkgName.equals(aInfo.baseActivity.getPackageName())) {
                        return true;
                    }
                }
            }
            List<ActivityManager.RunningServiceInfo> serviceInfo = am.getRunningServices(Integer.MAX_VALUE);
            if (serviceInfo != null && serviceInfo.size() > 0) {
                for (ActivityManager.RunningServiceInfo aInfo : serviceInfo) {
                    if (uid == aInfo.uid) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 启动应用程序
     *
     * @param packageName 应用包名
     */
    public static void launchApp(final String packageName) {
        if (isSpace(packageName)) return;
        Intent launchAppIntent = getLaunchAppIntent(packageName, true);
        if (launchAppIntent == null) {
            return;
        }
        Utils.getApp().startActivity(launchAppIntent);
    }

    /**
     * 启动应用程序
     *
     * @param activity    调用上下文
     * @param packageName 应用报名
     * @param requestCode 如果> = 0，则活动退出时，此返回值将在onActivityResult()中返回
     */
    public static void launchApp(final Activity activity,
                                 final String packageName,
                                 final int requestCode) {
        if (isSpace(packageName)) return;
        Intent launchAppIntent = getLaunchAppIntent(packageName);
        if (launchAppIntent == null) {
            return;
        }
        activity.startActivityForResult(launchAppIntent, requestCode);
    }

    /**
     * 重新启动该应用程序
     */
    public static void relaunchApp() {
        relaunchApp(false);
    }

    /**
     * 重新启动该应用程序
     *
     * @param isKillProcess 是否退出当前应用程序
     */
    public static void relaunchApp(final boolean isKillProcess) {
        Intent intent = getLaunchAppIntent(Utils.getApp().getPackageName(), true);
        if (intent == null) {
            return;
        }
        intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
        );
        Utils.getApp().startActivity(intent);
        if (!isKillProcess) return;
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 启动应用程序的详细信息设置
     */
    public static void launchAppDetailsSettings() {
        launchAppDetailsSettings(Utils.getApp().getPackageName());
    }

    /**
     * 启动应用程序的详细信息设置
     *
     * @param packageName 应用包名
     */
    public static void launchAppDetailsSettings(final String packageName) {
        if (isSpace(packageName)) return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE + packageName));
        Utils.getApp().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    /**
     * 推出杀死应用
     */
    public static void exitApp() {
        ActivityStackManager.finishAllActivities();
        System.exit(0);
    }

    /**
     * 返回应用程序的图标
     *
     * @return 应用程序的图标
     */
    public static Drawable getAppIcon() {
        return getAppIcon(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序的图标
     *
     * @param packageName 应用包名
     * @return 应用程序的图标
     */
    public static Drawable getAppIcon(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadIcon(pm);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回应用程序的图标资源标识符
     *
     * @return 应用程序的图标资源标识符
     */
    public static int getAppIconId() {
        return getAppIconId(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序的图标资源标识符
     *
     * @param packageName 应用包名
     * @return 应用程序的图标资源标识符
     */
    public static int getAppIconId(final String packageName) {
        if (isSpace(packageName)) return 0;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? 0 : pi.applicationInfo.icon;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 返回应用程序的程序包名称
     *
     * @return 应用程序的程序包名称
     */
    public static String getAppPackageName() {
        return Utils.getApp().getPackageName();
    }

    /**
     * 返回应用程序的名称
     *
     * @return 应用程序的名称
     */
    public static String getAppName() {
        return getAppName(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序的名称
     *
     * @param packageName 应用包名
     * @return 应用程序的名称
     */
    public static String getAppName(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回应用程序的路径
     *
     * @return 应用程序的路径
     */
    public static String getAppPath() {
        return getAppPath(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序的路径
     *
     * @param packageName 应用包名
     * @return 应用程序的路径
     */
    public static String getAppPath(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.applicationInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回应用程序的版本名称
     *
     * @return 应用程序的版本名称
     */
    public static String getAppVersionName() {
        return getAppVersionName(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序的版本名称
     *
     * @param packageName 应用包名
     * @return 应用程序的版本名称
     */
    public static String getAppVersionName(final String packageName) {
        if (isSpace(packageName)) return "";
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? null : pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回应用程序的版本代码
     *
     * @return 应用程序的版本代码
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序的版本代码
     *
     * @param packageName 应用包名
     * @return 应用程序的版本代码
     */
    public static int getAppVersionCode(final String packageName) {
        if (isSpace(packageName)) return -1;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return pi == null ? -1 : pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 返回应用程序的签名
     *
     * @return 应用程序的签名
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序的签名
     *
     * @param packageName 应用包名
     * @return 应用程序的签名
     */
    public static Signature[] getAppSignature(final String packageName) {
        if (isSpace(packageName)) return null;
        try {
            PackageManager pm = Utils.getApp().getPackageManager();
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回SHA1值的应用程序签名
     *
     * @return SHA1值的应用程序签名
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(Utils.getApp().getPackageName());
    }

    /**
     * 返回SHA1值的应用程序签名
     *
     * @param packageName 应用包名
     * @return SHA1值的应用程序签名
     */
    public static String getAppSignatureSHA1(final String packageName) {
        return getAppSignatureHash(packageName, "SHA1");
    }

    /**
     * 返回SHA256值的应用程序签名
     *
     * @return SHA256值的应用程序签名
     */
    public static String getAppSignatureSHA256() {
        return getAppSignatureSHA256(Utils.getApp().getPackageName());
    }

    /**
     * 返回SHA256值的应用程序签名
     *
     * @param packageName 应用包名
     * @return SHA256值的应用程序签名
     */
    public static String getAppSignatureSHA256(final String packageName) {
        return getAppSignatureHash(packageName, "SHA256");
    }

    /**
     * 返回MD5值的应用程序签名
     *
     * @return MD5值的应用程序签名
     */
    public static String getAppSignatureMD5() {
        return getAppSignatureMD5(Utils.getApp().getPackageName());
    }

    /**
     * 返回MD5值的应用程序签名
     *
     * @param packageName 应用包名
     * @return MD5值的应用程序签名
     */
    public static String getAppSignatureMD5(final String packageName) {
        return getAppSignatureHash(packageName, "MD5");
    }

    /**
     * 返回应用程序的用户ID
     *
     * @return MD5值的应用程序签名
     */
    public static int getAppUid() {
        return getAppUid(Utils.getApp().getPackageName());
    }

    /**
     * 返回应用程序的用户ID
     *
     * @param pkgName 输入报名
     * @return MD5值的应用程序签名
     */
    public static int getAppUid(String pkgName) {
        try {
            ApplicationInfo ai = Utils.getApp().getPackageManager().getApplicationInfo(pkgName, 0);
            if (ai != null) {
                return ai.uid;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 获取已安装的应用列表
     *
     * @return 安装的应用列表
     */
    @Nullable
    public static List<PackageInfo> getAppListPackageInfo() {
        try {
            List<PackageInfo> installedPackages = Utils.getApp().getPackageManager().getInstalledPackages(0);
            return installedPackages;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取已安装的应用列表
     *
     * @return 安装的应用列表
     */
    @Nullable
    public static List<AppInfo> getAppListInfo() {
        try {
            final PackageManager packageManager = Utils.getApp().getPackageManager();
            List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
            return Lists.transform(installedPackages, new Function<PackageInfo, AppInfo>() {
                @NullableDecl
                @Override
                public AppInfo apply(@NullableDecl PackageInfo packageInfo) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setPackageName(packageInfo.packageName);
                    appInfo.setAppVersionName(packageInfo.versionName);
                    appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                    appInfo.setDescription(packageInfo.applicationInfo.loadDescription(packageManager));
                    appInfo.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
                    appInfo.setTargetSdkVersion(packageInfo.applicationInfo.targetSdkVersion);
                    appInfo.setLastUpdateTime(packageInfo.lastUpdateTime);
                    appInfo.setFirstInstallTime(packageInfo.firstInstallTime);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        appInfo.setMinSdkVersion(packageInfo.applicationInfo.minSdkVersion);
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        appInfo.setAppVersionCode(packageInfo.getLongVersionCode());
                    } else {
                        appInfo.setAppVersionCode(packageInfo.versionCode);
                    }
                    appInfo.setPackageSignSHA1(getAppSignatureSHA1(packageInfo.packageName));
                    appInfo.setPackageSignSHA256(getAppSignatureSHA256(packageInfo.packageName));
                    appInfo.setSystem(isAppSystem(packageInfo.packageName));
                    return appInfo;
                }
            });
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取当前应用信息
     *
     * @return 当前应用信息
     */
    @Nullable
    public static AppInfo getAppInfo() {
        try {
            PackageManager packageManager = Utils.getApp().getPackageManager();
            PackageInfo packageInfo = packageManager
                    .getPackageInfo(getAppPackageName(), 0);
            AppInfo appInfo = new AppInfo();
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setAppVersionName(packageInfo.versionName);
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            appInfo.setIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            appInfo.setTargetSdkVersion(packageInfo.applicationInfo.targetSdkVersion);
            appInfo.setLastUpdateTime(packageInfo.lastUpdateTime);
            appInfo.setFirstInstallTime(packageInfo.firstInstallTime);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                appInfo.setMinSdkVersion(packageInfo.applicationInfo.minSdkVersion);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                appInfo.setAppVersionCode(packageInfo.getLongVersionCode());
            } else {
                appInfo.setAppVersionCode(packageInfo.versionCode);
            }
            appInfo.setPackageSignSHA1(getAppSignatureSHA1(packageInfo.packageName));
            appInfo.setPackageSignSHA256(getAppSignatureSHA256(packageInfo.packageName));
            appInfo.setSystem(isAppSystem(packageInfo.packageName));
            return appInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getAppSignatureHash(final String packageName, final String algorithm) {
        if (isSpace(packageName)) return "";
        Signature[] signature = getAppSignature(packageName);
        if (signature == null || signature.length <= 0) return TextConstants.EMPTY;
        return bytes2HexString(hashTemplate(signature[0].toByteArray(), algorithm))
                .replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static Intent getInstallAppIntent(final File file) {
        return getInstallAppIntent(file, false);
    }

    private static Intent getInstallAppIntent(final File file, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = PACKAGE_INTENT_TYPE;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            String authority = Utils.getApp().getPackageName() + PROVIDER_SUFFIX;
            data = FileProvider.getUriForFile(Utils.getApp(), authority, file);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        Utils.getApp().grantUriPermission(Utils.getApp().getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(data, type);
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    private static Intent getUninstallAppIntent(final String packageName) {
        return getUninstallAppIntent(packageName, false);
    }

    private static Intent getUninstallAppIntent(final String packageName, final boolean isNewTask) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse(PACKAGE + packageName));
        return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
    }

    private static Intent getLaunchAppIntent(final String packageName) {
        return getLaunchAppIntent(packageName, false);
    }

    private static Intent getLaunchAppIntent(final String packageName, final boolean isNewTask) {
        String launcherActivity = getLauncherActivity(packageName);
        if (!launcherActivity.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName(packageName, launcherActivity);
            intent.setComponent(cn);
            return isNewTask ? intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) : intent;
        }
        return null;
    }

    private static String getLauncherActivity(@NonNull final String pkg) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pkg);
        PackageManager pm = Utils.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        int size = info.size();
        if (size == 0) return "";
        for (int i = 0; i < size; i++) {
            ResolveInfo ri = info.get(i);
            if (ri.activityInfo.processName.equals(pkg)) {
                return ri.activityInfo.name;
            }
        }
        return info.get(0).activityInfo.name;
    }

    private static String getForegroundProcessName() {
        ActivityManager am =
                (ActivityManager) Utils.getApp().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> pInfo = am.getRunningAppProcesses();
        if (pInfo != null && pInfo.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo aInfo : pInfo) {
                if (aInfo.importance
                        == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return aInfo.processName;
                }
            }
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            PackageManager pm = Utils.getApp().getPackageManager();
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            List<ResolveInfo> list =
                    pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() <= 0) {
                return TextConstants.EMPTY;
            }
            try {// Access to usage information.
                ApplicationInfo info =
                        pm.getApplicationInfo(Utils.getApp().getPackageName(), 0);
                AppOpsManager aom =
                        (AppOpsManager) Utils.getApp().getSystemService(Context.APP_OPS_SERVICE);
                if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        info.uid,
                        info.packageName) != AppOpsManager.MODE_ALLOWED) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Utils.getApp().startActivity(intent);
                }
                if (aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        info.uid,
                        info.packageName) != AppOpsManager.MODE_ALLOWED) {
                    return TextConstants.EMPTY;
                }
                UsageStatsManager usageStatsManager = (UsageStatsManager) Utils.getApp()
                        .getSystemService(Context.USAGE_STATS_SERVICE);
                List<UsageStats> usageStatsList = null;
                if (usageStatsManager != null) {
                    long endTime = System.currentTimeMillis();
                    long beginTime = endTime - 86400000 * 7;
                    usageStatsList = usageStatsManager
                            .queryUsageStats(UsageStatsManager.INTERVAL_BEST,
                                    beginTime, endTime);
                }
                if (usageStatsList == null || usageStatsList.isEmpty()) return null;
                UsageStats recentStats = null;
                for (UsageStats usageStats : usageStatsList) {
                    if (recentStats == null
                            || usageStats.getLastTimeUsed() > recentStats.getLastTimeUsed()) {
                        recentStats = usageStats;
                    }
                }
                return recentStats == null ? null : recentStats.getPackageName();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return TextConstants.EMPTY;
    }
}