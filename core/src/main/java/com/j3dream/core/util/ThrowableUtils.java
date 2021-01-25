package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>文件名称: ThrowableUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: 异常信息获取类 </p>
 * <p>创建时间: 2020/3/13 13:19 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ThrowableUtils {

    public ThrowableUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 获取完整的堆栈信息
     *
     * @param throwable 堆栈异常信息
     * @return throwable raw string
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
        } finally {
            pw.close();
        }
        return sw.toString();
    }
}