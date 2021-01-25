package com.j3dream.android.common.log;

import android.annotation.SuppressLint;

import com.google.common.collect.Lists;
import com.j3dream.core.util.FileUtils;
import com.j3dream.core.util.StringUtils;
import com.j3dream.core.util.TimeDateUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>文件名称: AndroidDefaultWriter </p>
 * <p>所属包名: com.j3dream.android.common.log</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/10 12:07 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class AndroidDefaultWriter implements ILoggerWriter {

    private static final String LOG_DIR_DAY_TIME_FORMAT = TimeDateUtils.FORMAT_YYYY_MM_DD;
    private static final String NEWLINE = "\n";
    private static final String LOG_FILE_NAME_FORMAT = "log_%d.log";
    private static final String LOG_ERROR_FILE_NAME = "log_error.log";
    private static final String MESSAGE_HEADER_FORMAT = "[%s]_TAG-%s_T:%s";
    private ThreadPoolExecutor writeLogThreadPoolExecutor = new ThreadPoolExecutor(
            1, 1, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue(128)
    );

    private StringBuffer mMessageCacheBuffer = new StringBuffer();

    private static List<File> orderByName(File file) {
        File[] files = file.listFiles();
        if (files == null) {
            return Lists.newArrayList();
        }
        List<File> fileList = Arrays.asList(files);
        ArrayList<File> notErrorFileList = Lists.newArrayList();
        for (int i = 0; i < fileList.size(); i++) {
            File itemFile = fileList.get(i);
            if (!itemFile.getName().contains(LOG_ERROR_FILE_NAME)) {
                notErrorFileList.add(itemFile);
            }
        }
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile())
                    return -1;
                if (o1.isFile() && o2.isDirectory())
                    return 1;
                return o1.getName().compareTo(o2.getName());
            }
        });
        return fileList;
    }

    private File createNewLogFile(File parentFile) {
        @SuppressLint("DefaultLocale")
        String filename = String.format(LOG_FILE_NAME_FORMAT, orderByName(parentFile).size());
        File targetWriteFile = FileUtils.getFile(parentFile, filename);
        try {
            boolean newFile = targetWriteFile.createNewFile();
            if (newFile)
                return targetWriteFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private class WriteLoggerToDiskRunnable implements Runnable {
        private File writeLogDir;
        private String waitWriteLogContext;
        private long maxSingleFileLength = 200 * 1024 * 1024;

        public WriteLoggerToDiskRunnable(File writeLogDir, String waitWriteLogContext, long maxSingleFileLength) {
            this.writeLogDir = writeLogDir;
            this.waitWriteLogContext = StringUtils.null2Length0(waitWriteLogContext);
            this.maxSingleFileLength = maxSingleFileLength;
        }

        @Override
        public void run() {
            boolean writeLogDirExists = FileUtils.createOrExistsDir(writeLogDir);
            if (!writeLogDirExists || !writeLogDir.canWrite()) {
                return;
            }
            File dayLogDir = FileUtils.getFile(writeLogDir,
                    TimeDateUtils.timestamp2FormatTimeString(LOG_DIR_DAY_TIME_FORMAT));
            boolean writeDayLogDirExists = FileUtils.createOrExistsDir(dayLogDir);
            if (!writeDayLogDirExists || !dayLogDir.canWrite()) {
                return;
            }
            List<File> files = orderByName(dayLogDir);
            File lastFiles = files.size() > 0 ? files.get(files.size() - 1) : null;
            File waitWriteFile;
            if (lastFiles != null && FileUtils.getFileLength(lastFiles) + waitWriteLogContext.length()
                    <= maxSingleFileLength) {
                waitWriteFile = lastFiles;
            } else {
                waitWriteFile = createNewLogFile(dayLogDir);
                if (waitWriteFile == null) {
                    return;
                }
            }
            FileUtils.writeFileFromString(waitWriteFile, waitWriteLogContext, true);
        }
    }
    private class WriteErrorLoggerToDiskRunnable implements Runnable {
        private File writeLogDir;
        private String waitWriteLogContext;

        public WriteErrorLoggerToDiskRunnable(File writeLogDir, String waitWriteLogContext) {
            this.writeLogDir = writeLogDir;
            this.waitWriteLogContext = waitWriteLogContext;
        }

        @Override
        public void run() {
            boolean writeLogDirExists = FileUtils.createOrExistsDir(writeLogDir);
            if (!writeLogDirExists || !writeLogDir.canWrite()) {
                return;
            }
            File dayLogDir = FileUtils.getFile(writeLogDir,
                    TimeDateUtils.timestamp2FormatTimeString(LOG_DIR_DAY_TIME_FORMAT));
            boolean writeDayLogDirExists = FileUtils.createOrExistsDir(dayLogDir);
            if (!writeDayLogDirExists || !dayLogDir.canWrite()) {
                return;
            }
            File errorLogFile = FileUtils.getFile(dayLogDir, LOG_ERROR_FILE_NAME);
            boolean orExistsFile = FileUtils.createOrExistsFile(errorLogFile);
            if (!orExistsFile) {
                return;
            }
            FileUtils.writeFileFromString(errorLogFile, waitWriteLogContext, true);
        }
    }

    @Override
    public void write(int level, String tag, String message) {
        if (StringUtils.isEmpty(message)) {
            return;
        }
        message = NEWLINE
                + String.format(MESSAGE_HEADER_FORMAT,
                Logger.getLevelDesc(level), StringUtils.null2Length0(tag),
                TimeDateUtils.timestamp2FormatTimeString())
                + NEWLINE + message + NEWLINE;
        LogConfig logConfig = Logger.getLogConfig();
        int writeLevel = logConfig.getWriteLevel();
        if (level < writeLevel) {
            return;
        }

        mMessageCacheBuffer.append(message);
        if (mMessageCacheBuffer.length() < logConfig.getWriteMaxCacheLength()) {
            return;
        }
        message = mMessageCacheBuffer.toString();
        mMessageCacheBuffer.delete(0, mMessageCacheBuffer.length());

        File writeLogDir = logConfig.getWriteLogDir();
        if (level > ILogger.W && level <= ILogger.A) {
            writeLogThreadPoolExecutor.submit(new WriteErrorLoggerToDiskRunnable(writeLogDir, message));
        } else {
            writeLogThreadPoolExecutor.submit(new WriteLoggerToDiskRunnable(writeLogDir, message,
                    logConfig.getWriteLogSingleFileLength()));
        }
    }
}
