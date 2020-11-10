package com.j3dream.android.common.log;

import android.util.Log;

import com.google.common.collect.Lists;
import com.j3dream.android.common.util.StringFormatUtils;
import com.j3dream.core.util.StringUtils;
import com.j3dream.core.util.ThrowableUtils;

import java.util.ArrayList;

/**
 * <p>文件名称: AndroidDefaultLogger </p>
 * <p>所属包名: com.j3dream.android.common.log</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/10 10:00 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class AndroidDefaultLogger implements ILogger {

    private static final String FORMAT_CURLY_BRACES = "\\{\\}";
    private static final String RAW_CURLY_BRACES = "{}";
    private static final String NEWLINE = "\n";
    private static final String TOP_CORNER = "┌";
    private static final String MIDDLE_CORNER = "├";
    private static final String LEFT_BORDER = "│ ";
    private static final String BOTTOM_CORNER = "└";
    private static final String SIDE_DIVIDER =
            "────────────────────────────────────────────────────────";
    private static final String MIDDLE_DIVIDER =
            "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
    private static final String TOP_BORDER = TOP_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + MIDDLE_DIVIDER + MIDDLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_CORNER + SIDE_DIVIDER + SIDE_DIVIDER;

    @Override
    public void v(String message, Object... args) {
        log(V, null, message, args);
    }

    @Override
    public void v(String tag, String message, Object... args) {
        log(V, StringUtils.null2Length0(tag), message, args);
    }

    @Override
    public void d(String message, Object... args) {
        log(D, null, message, args);
    }

    @Override
    public void d(String tag, String message, Object... args) {
        log(D, StringUtils.null2Length0(tag), message, args);
    }

    @Override
    public void i(String message, Object... args) {
        log(I, null, message, args);
    }

    @Override
    public void i(String tag, String message, Object... args) {
        log(I, StringUtils.null2Length0(tag), message, args);
    }

    @Override
    public void w(String message, Object... args) {
        loge(W, null, null, message, args);
    }

    @Override
    public void w(Throwable throwable, String message, Object... args) {
        loge(W, throwable, null, message, args);
    }

    @Override
    public void w(Throwable throwable, String tag, String message, Object... args) {
        loge(W, throwable, tag, message, args);
    }

    @Override
    public void e(String message, Object... args) {
        loge(E, null, null, message, args);
    }

    @Override
    public void e(Throwable throwable, String message, Object... args) {
        loge(E, throwable, null, message, args);
    }

    @Override
    public void e(Throwable throwable, String tag, String message, Object... args) {
        loge(E, throwable, tag, message, args);
    }

    @Override
    public void wtf(String message, Object... args) {
        loge(A, null, null, message, args);
    }

    @Override
    public void wtf(Throwable throwable, String message, Object... args) {
        loge(A, throwable, null, message, args);
    }

    @Override
    public void wtf(Throwable throwable, String tag, String message, Object... args) {
        loge(A, throwable, tag, message, args);
    }

    @Override
    public void json(String jsonMessage) {
        log(I, null, StringFormatUtils.formatJson(jsonMessage));
    }

    @Override
    public void json(String tag, String jsonMessage) {
        log(I, StringUtils.null2Length0(tag), StringFormatUtils.formatJson(jsonMessage));
    }

    @Override
    public void xml(String xmlMessage) {
        log(I, null, StringFormatUtils.formatXml(xmlMessage));
    }

    @Override
    public void xml(String tag, String xmlMessage) {
        log(I, StringUtils.null2Length0(tag), StringFormatUtils.formatXml(xmlMessage));
    }

    @Override
    public void file(String message, Object... args) {
        log(Integer.MAX_VALUE, null, message, args);
    }

    @Override
    public void file(String tag, String message, Object... args) {
        log(Integer.MAX_VALUE, tag, message, args);
    }

    private void log(int level, String tag, String message, Object... args) {
        LogConfig logConfig = Logger.getLogConfig();
        String logTag = tag;
        if (logTag == null) {
            logTag = logConfig.getTag();
        }
        StringBuilder messageBuilder = new StringBuilder();
        String logMessage = StringUtils.null2Length0(message);
        ArrayList<Object> logArgs = Lists.newArrayList(args == null ? new String[0] : args);
        if (logMessage.length() > 255) {
            for (Object logArg : logArgs) {
                messageBuilder.append(logArg).append(NEWLINE);
            }
            messageBuilder.append(logMessage);
        } else {
            messageBuilder.append(String.format(logMessage, logArgs.toArray()));
        }
        String writeRawMessage = pMessage(new LogProxy(level), logTag, message, 255);
        if (level >= Integer.MAX_VALUE || logConfig.isWriteLog()) {
            Logger.getLoggerWriter().write(level, logTag, writeRawMessage);
        }
    }

    private void loge(int level, Throwable throwable, String tag, String message, Object... args) {
        LogConfig logConfig = Logger.getLogConfig();
        String logTag = tag;
        if (logTag == null) {
            logTag = logConfig.getTag();
        }
        StringBuilder messageBuilder = new StringBuilder();
        String logMessage = StringUtils.null2Length0(message);
        ArrayList<Object> logArgs = Lists.newArrayList(args == null ? new String[0] : args);
        if (logMessage.length() > 255) {
            for (Object logArg : logArgs) {
                messageBuilder.append(logArg).append(NEWLINE);
            }
            messageBuilder.append(logMessage);
        } else {
            messageBuilder.append(String.format(logMessage, logArgs.toArray()));
        }
        String writeRawMessage = pError(new LogProxy(level), logTag, message, throwable, 255);
        if (logConfig.isWriteLog()) {
            Logger.getLoggerWriter().write(level, logTag, writeRawMessage);
        }
    }

    private String pMessage(LogProxy logProxy, String tag, String message, int singleLineLength) {
        Thread thread = Thread.currentThread();
        StringBuilder builder = new StringBuilder();
        builder.append(TOP_BORDER).append(NEWLINE);
        logProxy.p(tag, TOP_BORDER);
        String threadInfo = LEFT_BORDER + thread.getName();
        builder.append(threadInfo).append(NEWLINE);
        logProxy.p(tag, threadInfo);
        String middle = MIDDLE_BORDER;
        builder.append(middle).append(NEWLINE);
        logProxy.p(tag, middle);
        if (message.length() <= singleLineLength) {
            builder.append(LEFT_BORDER).append(message).append(NEWLINE);
            logProxy.p(tag, LEFT_BORDER + message);
        } else {
            int line = message.length() / singleLineLength;
            if (singleLineLength * line < message.length()) {
                line += 1;
            }
            for (int i = 0; i < line; i++) {
                int lineMaxLength = i * singleLineLength + 1;
                String substring = message.substring(i * singleLineLength,
                        Math.min(lineMaxLength, message.length()));
                builder.append(LEFT_BORDER).append(substring).append(NEWLINE);
                logProxy.p(tag, LEFT_BORDER + substring);
            }
        }
        builder.append(BOTTOM_BORDER).append(NEWLINE);
        logProxy.p(tag, BOTTOM_BORDER);
        return builder.toString();
    }

    private String pError(LogProxy logProxy, String tag, String message, Throwable throwable, int singleLineLength) {
        Thread thread = Thread.currentThread();
        StringBuilder builder = new StringBuilder();
        builder.append(TOP_BORDER).append(NEWLINE);
        String topMessage = TOP_BORDER;
        logProxy.p(tag, topMessage);
        String threadInfo = LEFT_BORDER + thread.getName();
        builder.append(threadInfo).append(NEWLINE);
        logProxy.p(tag, threadInfo);
        String middle = MIDDLE_BORDER;
        builder.append(middle).append(NEWLINE);
        logProxy.p(tag, middle);
        if (message.length() <= singleLineLength) {
            builder.append(LEFT_BORDER).append(message).append(NEWLINE);
            logProxy.p(tag, LEFT_BORDER + message);
        } else {
            int line = message.length() / singleLineLength;
            if (singleLineLength * line < message.length()) {
                line += 1;
            }
            for (int i = 0; i < line; i++) {
                int lineMaxLength = i * singleLineLength + 1;
                String substring = message.substring(i * singleLineLength,
                        Math.min(lineMaxLength, message.length()));
                builder.append(LEFT_BORDER).append(substring).append(NEWLINE);
                logProxy.p(tag, LEFT_BORDER + substring);
            }
        }

        if (throwable != null) {
            String stackTrace = ThrowableUtils.getStackTrace(throwable);
            String[] split = stackTrace.split(NEWLINE);
            for (String s : split) {
                builder.append(LEFT_BORDER).append(s).append(NEWLINE);
                logProxy.p(tag, LEFT_BORDER + s);
            }
        }
        builder.append(BOTTOM_BORDER);
        logProxy.p(tag, BOTTOM_BORDER);
        return builder.toString();
    }

    private static class LogProxy {
        private int level;

        public LogProxy(int level) {
            this.level = level;
        }

        public void p(String tag, String message) {
            switch (level) {
                case ILogger.V:
                    Log.v(tag, message);
                    break;
                case ILogger.D:
                    Log.d(tag, message);
                    break;
                case ILogger.W:
                    Log.w(tag, message);
                    break;
                case ILogger.E:
                    Log.e(tag, message);
                    break;
                case ILogger.A:
                    Log.wtf(tag, message);
                    break;
                default:
                    Log.i(tag, message);
                    break;
            }
        }
    }
}
