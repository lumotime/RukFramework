package com.j3dream.android.common.log;

/**
 * <p>文件名称: ILoggerWriter </p>
 * <p>所属包名: com.j3dream.android.common.log</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/10 12:03 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface ILoggerWriter {

    /**
     * 写入日志信息
     *
     * @param level   日志等级
     * @param message 日志信息
     */
    void write(@ILogger.LEVEL int level, String tag, String message);
}
