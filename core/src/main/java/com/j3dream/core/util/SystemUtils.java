package com.j3dream.core.util;

import java.util.Locale;

/**
 * <p>文件名称: SystemUtils </p>
 * <p>所属包名: com.j3dream.core.util</p>
 * <p>描述:  </p>
 * <p>创建时间: 2020/10/19 10:59 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class SystemUtils {

    private SystemUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 退出应用程序
     */
    public static void exit() {
        System.exit(-1);
    }

    /**
     * 获取设备核心数
     *
     * @return 设备Cpu核心数
     */
    public static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取系统语言编码
     *
     * @return 当前系统语言编码 中文-中国 2 zh-CN
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上支持的语言列表
     *
     * @return 支持的语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }
}
