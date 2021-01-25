package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <p>文件名称: TerminalUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: 终端工具类 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 10:58 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class TerminalUtils {

    public TerminalUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 向终端中输出文本内容(不自动换行)
     *
     * @param message 文本内容
     */
    public static void print(String message) {
        System.out.print(message);
    }

    /**
     * 向终端中输出文本内容(自动换行)
     *
     * @param message 文本内容
     */
    public static void println(String message) {
        System.out.println(message);
    }

    /**
     * 执行Shell命令
     *
     * @param cmd 待执行的命令
     * @return 命令执行结果
     */
    public static String execByRuntime(String cmd) {
        Process process = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            inputStreamReader = new InputStreamReader(process.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

            int read;
            char[] buffer = new char[4096];
            StringBuilder output = new StringBuilder();
            while ((read = bufferedReader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
            if (null != process) {
                try {
                    process.destroy();
                } catch (Throwable t) {
                    t.printStackTrace();
                }
            }
        }
    }
}
