package com.j3dream.android.common.log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <p>文件名称: ILogger </p>
 * <p>所属包名: com.bloodsport.core.logger</p>
 * <p>描述: Logger的代理器 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/10 14:19 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface ILogger {

    int V = Log.VERBOSE;
    int D = Log.DEBUG;
    int I = Log.INFO;
    int W = Log.WARN;
    int E = Log.ERROR;
    int A = Log.ASSERT;

    // log v
    void v(String message, Object... args);

    void v(String tag, String message, Object... args);

    // log d
    void d(String message, Object... args);

    void d(String tag, String message, Object... args);

    // log i
    void i(String message, Object... args);

    void i(String tag, String message, Object... args);

    // log w
    void w(String message, Object... args);

    void w(Throwable throwable, String message, Object... args);

    void w(Throwable throwable, String tag, String message, Object... args);

    // log e
    void e(String message, Object... args);

    void e(Throwable throwable, String message, Object... args);

    void e(Throwable throwable, String tag, String message, Object... args);

    // log wtf
    void wtf(String message, Object... args);

    void wtf(Throwable throwable, String message, Object... args);

    void wtf(Throwable throwable, String tag, String message, Object... args);

    // log json
    void json(String jsonMessage);

    void json(String tag, String jsonMessage);

    // log xml
    void xml(String xmlMessage);

    void xml(String tag, String xmlMessage);

    // log file
    void file(String message, Object... args);

    void file(String tag, String message, Object... args);

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    @interface LEVEL {
    }
}
