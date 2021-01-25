package com.j3dream.core.util;

import java.io.File;

/**
 * <p>文件名称: UtilsBridge </p>
 * <p>所属包名: com.j3dream.core.util</p>
 * <p>描述:  </p>
 * <p>创建时间: 2020/10/19 17:19 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class UtilsBridge {

    public static File getFileByPath(String filePath) {
        return FileUtils.getFileByPath(filePath);
    }

    public static boolean isSpace(String context) {
        return StringUtils.isSpace(context);
    }

    public static boolean createOrExistsDir(File file) {
        return FileUtils.createOrExistsDir(file);
    }

    public static boolean createOrExistsFile(File file) {
        return FileUtils.createOrExistsFile(file);
    }

    public static String getStackTrace(Throwable throwable) {
        return ThrowableUtils.getStackTrace(throwable);
    }
}
