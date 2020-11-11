package com.j3dream.android.common.log;

import android.util.Log;

import com.google.common.collect.Lists;
import com.j3dream.android.common.util.AppUtils;
import com.j3dream.android.common.util.StringFormatUtils;
import com.j3dream.core.util.StringUtils;
import com.j3dream.core.util.ThrowableUtils;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.UnknownFormatConversionException;

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
    public void vTag(String tag, String message, Object... args) {
        log(V, StringUtils.null2Length0(tag), message, args);
    }

    @Override
    public void d(String message, Object... args) {
        log(D, null, message, args);
    }

    @Override
    public void dTag(String tag, String message, Object... args) {
        log(D, StringUtils.null2Length0(tag), message, args);
    }

    @Override
    public void i(String message, Object... args) {
        log(I, null, message, args);
    }

    @Override
    public void iTag(String tag, String message, Object... args) {
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
    public void wTag(Throwable throwable, String tag, String message, Object... args) {
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
    public void eTag(Throwable throwable, String tag, String message, Object... args) {
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
    public void wtfTag(Throwable throwable, String tag, String message, Object... args) {
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
    public void fileTag(String tag, String message, Object... args) {
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

        boolean isFormat = true;
        if (logMessage.length() > 255) {
            isFormat = false;
        } else {
            try {
                messageBuilder.append(String.format(logMessage, logArgs.toArray()));
            } catch (MissingFormatArgumentException | UnknownFormatConversionException ex) {
                isFormat = false;
            }
        }
        if (!isFormat) {
            for (Object logArg : logArgs) {
                messageBuilder.append(logArg).append(NEWLINE);
            }
            messageBuilder.append(logMessage);
        }

        String writeRawMessage = pMessage(new LogProxy(level), logTag, messageBuilder.toString(), 1024);
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
        boolean isFormat = true;
        if (logMessage.length() > 255) {
            isFormat = false;
        } else {
            try {
                messageBuilder.append(String.format(logMessage, logArgs.toArray()));
            } catch (MissingFormatArgumentException | UnknownFormatConversionException ex) {
                isFormat = false;
            }
        }
        if (!isFormat) {
            for (Object logArg : logArgs) {
                messageBuilder.append(logArg).append(NEWLINE);
            }
            messageBuilder.append(logMessage);
        }
        String writeRawMessage = pError(new LogProxy(level), logTag, messageBuilder.toString(), throwable, 255);
        if (logConfig.isWriteLog()) {
            Logger.getLoggerWriter().write(level, logTag, writeRawMessage);
        }
    }

    private String pMessage(LogProxy logProxy, String tag, String message, int singleLineLength) {
        StringBuilder builder = new StringBuilder();
        builder.append(TOP_BORDER).append(NEWLINE);
        String bodyMessage = pBaseMessage(message, singleLineLength);
        builder.append(bodyMessage);
        builder.append(BOTTOM_BORDER).append(NEWLINE);
        message = builder.toString();
        String[] split = message.split(NEWLINE);
        for (String lineText : split) {
            logProxy.p(tag, lineText);
        }
        return message;
    }

    private String pError(LogProxy logProxy, String tag, String message, Throwable throwable, int singleLineLength) {
        StringBuilder builder = new StringBuilder();
        builder.append(TOP_BORDER).append(NEWLINE);
        String bodyMessage = pBaseMessage(message, singleLineLength);
        builder.append(bodyMessage);
        if (throwable != null) {
            builder.append(MIDDLE_BORDER).append(NEWLINE);
            String stackTrace = ThrowableUtils.getStackTrace(throwable);
            String[] split = stackTrace.split(NEWLINE);
            for (String s : split) {
                builder.append(LEFT_BORDER).append(s).append(NEWLINE);
            }
        }
        builder.append(BOTTOM_BORDER).append(NEWLINE);
        message = builder.toString();
        String[] split = message.split(NEWLINE);
        for (String lineText : split) {
            logProxy.p(tag, lineText);
        }
        return message;
    }

    private String pBaseMessage(String message, int singleLineLength) {
        StringBuilder builder = new StringBuilder();
        // 打印线程信息
        String threadInfo = LEFT_BORDER + Thread.currentThread().getName();
        builder.append(threadInfo).append(NEWLINE);
        String middle = MIDDLE_BORDER;

        builder.append(middle).append(NEWLINE);
        // 打印调用栈信息
        String appPackageName = AppUtils.getAppPackageName();
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            String stackTraceInfo = stackTraceElement.toString();
            if (!stackTraceInfo.contains(appPackageName)) {
                continue;
            }
            String stackTraceInfoLine = LEFT_BORDER + stackTraceInfo;
            builder.append(stackTraceInfoLine).append(NEWLINE);
        }
        builder.append(middle).append(NEWLINE);
        // 打印日志信息
        if (message.length() <= singleLineLength * 10 && message.contains(NEWLINE)) {
            String[] split = message.split(NEWLINE);
            for (String lineText : split) {
                builder.append(LEFT_BORDER).append(lineText).append(NEWLINE);
            }
        } else if (message.length() <= singleLineLength) {
            builder.append(LEFT_BORDER).append(message).append(NEWLINE);
        } else {
            int line = message.length() / singleLineLength;
            if (singleLineLength * line < message.length()) {
                line += 1;
            }
            for (int i = 0; i < line; i++) {
                int lineMaxLength = (i + 1) * singleLineLength;
                String substring = message.substring(i * singleLineLength,
                        Math.min(lineMaxLength, message.length()));
                builder.append(LEFT_BORDER).append(substring).append(NEWLINE);
            }
        }
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
