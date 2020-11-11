package com.j3dream.android.common.log;

import com.j3dream.android.common.util.AndroidFiles;
import com.j3dream.android.common.util.AppUtils;
import com.j3dream.core.util.FileUtils;

import java.io.File;

/**
 * <p>文件名称: LogConfig </p>
 * <p>所属包名: com.j3dream.android.common.log</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/10 10:41 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class LogConfig {

    private static final String DEFAULT_WRITE_LOG_EXTERNAL_DIR_NAME = "logs";
    private String tag;
    private int logLevel;
    private boolean logSwitch;
    private int writeLevel;
    private boolean writeLog;
    private File writeLogDir;
    private long writeMaxCacheLength;
    private long writeLogSingleFileLength;

    private LogConfig() {
    }

    public String getTag() {
        return tag;
    }

    private void setTag(String tag) {
        this.tag = tag;
    }

    public int getWriteLevel() {
        return writeLevel;
    }

    private void setWriteLevel(int writeLevel) {
        this.writeLevel = writeLevel;
    }

    public File getWriteLogDir() {
        return writeLogDir;
    }

    private void setWriteLogDir(File writeLogDir) {
        this.writeLogDir = writeLogDir;
    }

    public long getWriteLogSingleFileLength() {
        return writeLogSingleFileLength;
    }

    private void setWriteLogSingleFileLength(long writeLogSingleFileLength) {
        this.writeLogSingleFileLength = writeLogSingleFileLength;
    }

    public int getLogLevel() {
        return logLevel;
    }

    private void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public boolean isLogSwitch() {
        return logSwitch;
    }

    private void setLogSwitch(boolean logSwitch) {
        this.logSwitch = logSwitch;
    }

    public boolean isWriteLog() {
        return writeLog;
    }

    private void setWriteLog(boolean writeLog) {
        this.writeLog = writeLog;
    }

    public long getWriteMaxCacheLength() {
        return writeMaxCacheLength;
    }

    private void setWriteMaxCacheLength(long writeMaxCacheLength) {
        this.writeMaxCacheLength = writeMaxCacheLength;
    }

    public static class Builder {

        private String tag = AppUtils.getAppName();
        private boolean writeLog = false;
        private int writeLevel = ILogger.E;
        private int logLevel = ILogger.I;
        private boolean logSwitch = true;
        private File writeLogDir = AndroidFiles.getAppExternalFileDir(DEFAULT_WRITE_LOG_EXTERNAL_DIR_NAME);
        private long writeLogSingleFileLength = 200 * 1024 * 1024;
        private long writeMaxCacheLength = 514 * 1024;

        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder setWriteLevel(@ILogger.LEVEL int writeLevel) {
            this.writeLevel = writeLevel;
            return this;
        }

        public Builder setLogLevel(@ILogger.LEVEL int logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder setLogSwitch(boolean logSwitch) {
            this.logSwitch = logSwitch;
            return this;
        }

        public Builder setWriteLogDir(String writeLogDirPath) {
            setWriteLogDir(FileUtils.getFileByPath(writeLogDirPath));
            return this;
        }

        public Builder setWriteLogDir(File writeLogDir) {
            this.writeLogDir = writeLogDir;
            return this;
        }

        public Builder setWriteLogSingleFileLength(long writeLogSingleFileLength) {
            this.writeLogSingleFileLength = writeLogSingleFileLength;
            return this;
        }

        public Builder setWriteLog(boolean writeLog) {
            this.writeLog = writeLog;
            return this;
        }

        public Builder setWriteMaxCacheLength(long writeMaxCacheLength) {
            this.writeMaxCacheLength = writeMaxCacheLength;
            return this;
        }

        public LogConfig build() {
            LogConfig logConfig = new LogConfig();
            logConfig.setTag(this.tag);
            logConfig.setWriteLevel(this.writeLevel);
            logConfig.setWriteLogDir(this.writeLogDir);
            logConfig.setLogLevel(this.logLevel);
            logConfig.setLogSwitch(this.logSwitch);
            logConfig.setWriteLog(this.writeLog);
            logConfig.setWriteMaxCacheLength(this.writeMaxCacheLength);
            logConfig.setWriteLogSingleFileLength(this.writeLogSingleFileLength);
            return logConfig;
        }
    }
}
