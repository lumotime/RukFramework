package com.j3dream.android.common.log;

/**
 * <p>文件名称: Logger </p>
 * <p>所属包名: com.j3dream.android.common.log</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/10 09:53 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class Logger {

    private static ILogger sUsedLogger;
    private static LogConfig sLogConfig;
    private static ILoggerWriter sLoggerWriter;

    public static void initLogger(ILogger logger) {
        synchronized (Logger.class) {
            sUsedLogger = logger;
        }
    }

    public static void initLogger(ILogger logger, LogConfig logConfig) {
        synchronized (Logger.class) {
            sUsedLogger = logger;
            sLogConfig = logConfig;
        }
    }

    public static void initLogConfig(LogConfig logConfig) {
        synchronized (Logger.class) {
            sLogConfig = logConfig;
        }
    }

    public static void initLogWriter(ILoggerWriter loggerWriter) {
        synchronized (Logger.class) {
            sLoggerWriter = loggerWriter;
        }
    }

    public static LogConfig getLogConfig() {
        return getOrCreateDefaultLogConfig();
    }

    public static ILoggerWriter getLoggerWriter() {
        return getOrCreateDefaultLogWriter();
    }

    public static String getLevelDesc(int level) {
        switch (level) {
            case ILogger.V:
                return "VERBOSE";
            case ILogger.D:
                return "DEBUG";
            case ILogger.I:
                return "INFO";
            case ILogger.W:
                return "WARN";
            case ILogger.E:
                return "ERROR";
            case ILogger.A:
                return "ASSERT";
            case Integer.MAX_VALUE:
                return "FILE";
            default:
                return "UNKNOWN";
        }
    }

    private static ILogger getOrCreateDefaultLogger() {
        if (sUsedLogger == null) {
            synchronized (Logger.class) {
                if (sUsedLogger == null) {
                    sUsedLogger = new AndroidDefaultLogger();
                }
            }
        }
        return sUsedLogger;
    }

    private static LogConfig getOrCreateDefaultLogConfig() {
        if (sLogConfig == null) {
            synchronized (Logger.class) {
                if (sLogConfig == null) {
                    sLogConfig = new LogConfig.Builder().build();
                }
            }
        }
        return sLogConfig;
    }

    private static ILoggerWriter getOrCreateDefaultLogWriter() {
        if (sLoggerWriter == null) {
            synchronized (Logger.class) {
                if (sLoggerWriter == null) {
                    sLoggerWriter = new AndroidDefaultWriter();
                }
            }
        }
        return sLoggerWriter;
    }

    public static void v(String message, Object... args) {
        getOrCreateDefaultLogger().v(message, args);
    }

    public static void vTag(String tag, String message, Object... args) {
        getOrCreateDefaultLogger().v(tag, message, args);
    }

    public static void d(String message, Object... args) {
        getOrCreateDefaultLogger().d(message, args);
    }

    public static void dTag(String tag, String message, Object... args) {
        getOrCreateDefaultLogger().d(tag, message, args);
    }

    public static void i(String message, Object... args) {
        getOrCreateDefaultLogger().i(message, args);
    }

    public static void iTag(String tag, String message, Object... args) {
        getOrCreateDefaultLogger().i(tag, message, args);
    }

    public static void w(String message, Object... args) {
        getOrCreateDefaultLogger().w(message, args);
    }

    public static void w(Throwable throwable, String message, Object... args) {
        getOrCreateDefaultLogger().w(throwable, message, args);
    }

    public static void wTag(Throwable throwable, String tag, String message, Object... args) {
        getOrCreateDefaultLogger().w(throwable, tag, message, args);
    }

    public static void e(String message, Object... args) {
        getOrCreateDefaultLogger().e(message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        getOrCreateDefaultLogger().e(throwable, message, args);
    }

    public static void eTag(Throwable throwable, String tag, String message, Object... args) {
        getOrCreateDefaultLogger().e(throwable, tag, message, args);
    }

    public static void wtf(String message, Object... args) {
        getOrCreateDefaultLogger().wtf(message, args);
    }

    public static void wtf(Throwable throwable, String message, Object... args) {
        getOrCreateDefaultLogger().wtf(throwable, message, args);
    }

    public static void wtfTag(Throwable throwable, String tag, String message, Object... args) {
        getOrCreateDefaultLogger().wtf(throwable, tag, message, args);
    }

    public static void json(String jsonMessage) {
        getOrCreateDefaultLogger().json(jsonMessage);
    }

    public static void json(String tag, String jsonMessage) {
        getOrCreateDefaultLogger().json(tag, jsonMessage);
    }

    public static void xml(String xmlMessage) {
        getOrCreateDefaultLogger().xml(xmlMessage);
    }

    public static void xml(String tag, String xmlMessage) {
        getOrCreateDefaultLogger().xml(tag, xmlMessage);
    }

    public static void file(String message, Object... args) {
        getOrCreateDefaultLogger().file(message, args);
    }

    public static void fileTag(String tag, String message, Object... args) {
        getOrCreateDefaultLogger().file(tag, message, args);
    }
}
