package com.j3dream.android.common.data;

import android.graphics.drawable.Drawable;

/**
 * <p>文件名称: AppInfo </p>
 * <p>所属包名: com.j3dream.android.common.util</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/11 11:38 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class AppInfo {

    /**
     * app名字
     */
    private String appName;
    private long firstInstallTime;
    private long lastUpdateTime;
    /**
     * 包名
     */
    private String packageName;

    /**
     * 包签名
     */
    private String packageSignSHA1;

    /**
     * 包签名
     */
    private String packageSignSHA256;

    /**
     * 版本号
     */
    private long appVersionCode;

    /**
     * 版本名字
     */
    private String appVersionName;

    /**
     * 目标系统版本号
     */
    private int targetSdkVersion;

    /**
     * 最低系统版本号
     **/
    private int minSdkVersion;

    /**
     * 描述
     */
    private CharSequence description;

    /**
     * 图标
     */
    private Drawable icon;

    /**
     * 是否是系统APP
     */
    private boolean isSystem;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(long firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(long appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public int getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public void setTargetSdkVersion(int targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(int minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public CharSequence getDescription() {
        return description;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public String getPackageSignSHA1() {
        return packageSignSHA1;
    }

    public void setPackageSignSHA1(String packageSignSHA1) {
        this.packageSignSHA1 = packageSignSHA1;
    }

    public String getPackageSignSHA256() {
        return packageSignSHA256;
    }

    public void setPackageSignSHA256(String packageSignSHA256) {
        this.packageSignSHA256 = packageSignSHA256;
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "appName='" + appName + '\'' +
                ", firstInstallTime=" + firstInstallTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", packageName='" + packageName + '\'' +
                ", packageSignSHA1='" + packageSignSHA1 + '\'' +
                ", packageSignSHA256='" + packageSignSHA256 + '\'' +
                ", appVersionCode=" + appVersionCode +
                ", appVersionName='" + appVersionName + '\'' +
                ", targetSdkVersion=" + targetSdkVersion +
                ", minSdkVersion=" + minSdkVersion +
                ", description=" + description +
                ", icon=" + icon +
                ", isSystem=" + isSystem +
                '}';
    }
}
