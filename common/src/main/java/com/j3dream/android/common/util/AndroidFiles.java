package com.j3dream.android.common.util;

import java.io.File;

/**
 * <p>@ProjectName: Bloodsport</p>
 * <p>@ClassName: FileUtils</p>
 * <p>@PackageName: com.bloodsport.core.util</p>
 * <b>
 * <p>@Description: 基础文件操作类</p>
 * </b>
 * <p>@author:  Rivkaer Jia</p>
 * <p>@date:19-11-8 上午11:25</p>
 * <p>@email:cnrivkaer@outlook.com</p>
 * <p>@see:(参考)  https://github.com/Blankj/AndroidUtilCode</p>
 */
public final class AndroidFiles {

    public AndroidFiles() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取私有的文件路径
     *
     * @return 私有的文件路径
     * <p>
     * 获取内置存储下的文件目录，可以用来保存不能公开给其他应用的一些敏感数据如用户个人信息
     * 示例: /data/data/{APP_PACKAGE}/files/
     * </p>
     */
    public static File getAppFileDir() {
        return Utils.getApp().getFilesDir();
    }

    /**
     * 获取私有的应用缓存路径
     *
     * @return 应用缓存路径
     * <p>
     * 示例: /data/data/{APP_PACKAGE}/cache/
     * </p>
     */
    public static File getAppCacheFileDir() {
        return Utils.getApp().getCacheDir();
    }

    /**
     * 获取公有的应用缓存路径
     *
     * @return 应用缓存路径
     * <p>
     * 示例: /sdcard/Android/{APP_PACKAGE}/cache/
     * </p>
     */
    public static File getAppExternalCacheDir() {
        return Utils.getApp().getExternalCacheDir();
    }

    /**
     * 获取公有的应用文件路径
     *
     * @return 应用缓存路径
     * <p>
     * 示例: /sdcard/Android/{APP_PACKAGE}/files/${type}
     * </p>
     */
    public static File getAppExternalFileDir(String type) {
        return Utils.getApp().getExternalFilesDir(type);
    }
}