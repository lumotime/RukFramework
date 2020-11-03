package com.j3dream.android.net.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.j3dream.android.common.util.AndroidFiles;
import com.j3dream.android.common.util.AndroidThreadPoolUtils;
import com.j3dream.android.net.exception.NetHandleException;
import com.j3dream.core.util.FileUtils;
import com.j3dream.core.util.ObjectUtils;
import com.j3dream.core.util.RegexUtils;
import com.j3dream.core.util.StringUtils;
import com.j3dream.core.util.executor.ThreadPoolUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * <p>文件名称: Downloader </p>
 * <p>所属包名: com.lumotime.net.utils</p>
 * <p>描述: 文件数据下载器 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/17 17:49 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class Downloader {

    /**
     * 默认的文件下载目录, /sdcard/Android/{package-name}/file/Downloader/
     */
    private static final File DEFAULT_DOWNLOAD_FILE_DIR
            = AndroidFiles.getAppExternalFileDir(Downloader.class.getSimpleName());
    private static Downloader sDownloader;
    protected final OkHttpClient okHttpClient;

    private Downloader() {
        okHttpClient = new OkHttpClient.Builder()
                .build();
    }

    public static Downloader get() {
        if (sDownloader == null) {
            synchronized (Downloader.class) {
                if (sDownloader == null) {
                    sDownloader = new Downloader();
                }
            }
        }
        return sDownloader;
    }

    /**
     * 获取 url 中的文件名
     *
     * @param url 原始的链接
     * @return 文件名
     */
    private static String getUrlFilename(String url) {
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        int questionIndex = url.indexOf("?");
        int argStartIndex = questionIndex > 0 ? questionIndex : url.length();
        int lastBackslashIndex = Integer.MAX_VALUE;
        do {
            if (lastBackslashIndex <= url.length()) {
                url = url.substring(0, lastBackslashIndex);
            }
            lastBackslashIndex = url.lastIndexOf("/");
        } while (lastBackslashIndex > argStartIndex);
        return url.substring(lastBackslashIndex + 1, argStartIndex);
    }

    public void download(@NonNull final String url, @NonNull final OnDownloadListener listener) {
        download(url, null, listener);
    }

    public void download(@NonNull final String url, final String destFileDir, @NonNull final OnDownloadListener listener) {
        download(url, destFileDir, null, null, null, listener);
    }

    public void download(@NonNull final String url, final String destFileName, final RequestBody requestBody,
                         final Map<String, String> headers, @NonNull final OnDownloadListener listener) {
        download(url, null, destFileName, requestBody, headers, listener);
    }

    public void download(@NonNull final String url, final String destFileDir, final String destFileName, final RequestBody requestBody,
                         final Map<String, String> headers, @NonNull final OnDownloadListener listener) {
        if (ObjectUtils.isEmpty(listener)) {
            throw new IllegalArgumentException("OnDownloadListener is empty, please input a listener." +
                    " receive download status data.");
        }
        // url 是否为空 或 是否是一个合规的url
        if (StringUtils.isEmpty(url) || !RegexUtils.isURL(url)) {
            listener.onDownloadFailed(new IllegalArgumentException("please input a standard url!"));
            return;
        }
        File fileDir = null;
        if (StringUtils.isNotEmpty(destFileDir)) {
            fileDir = FileUtils.getFileByPath(destFileDir);
        } else {
            fileDir = DEFAULT_DOWNLOAD_FILE_DIR;
        }
        String filename = null;
        if (StringUtils.isNotEmpty(destFileName)) {
            filename = destFileName;
        } else {
            filename = getUrlFilename(url);
        }

        if (StringUtils.isEmpty(filename)) {
            listener.onDownloadFailed(new IllegalArgumentException("system cannot create this file!"));
            return;
        }
        // 待写入的本地文件
        final File destFile = new File(fileDir, filename);
        FileUtils.createOrExistsFile(destFile);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);
        // 添加设置头部
        for (String key : headers.keySet()) {
            String value = headers.get(key);
            if (StringUtils.isNotEmpty(value)) {
                requestBuilder.addHeader(key, value);
            }
        }
        // 添加请求数据
        if (ObjectUtils.isNotEmpty(requestBody)) {
            requestBuilder.post(requestBody);
        }
        Request request = requestBuilder.build();
        // enqueue callback
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException ex) {
                listener.onDownloadFailed(ex);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                if (ObjectUtils.isEmpty(body) || (!response.isSuccessful())) {
                    ThreadPoolUtils.runInBackground(new Runnable() {
                        @Override
                        public void run() {
                            listener.onDownloadFailed(
                                    new NetHandleException("don't download this net file!")
                            );
                        }
                    });
                    return;
                }
                BufferedSource source = body.source();
                BufferedSink destFileSink = Okio.buffer(Okio.sink(destFile));
                byte[] buffer = new byte[1024];
                long contentLength = body.contentLength();
                long total = 0;
                int len;
                int rate = 0;
                while ((len = source.read(buffer)) != -1) {
                    destFileSink.write(buffer);
                    total += len;
                    final int progress = (int) ((total * 1.0f / contentLength) * 100);
                    Log.d("TAG", "download rate: " + progress + "%");
                    // 避免更新进度过快的问题
                    if (progress > rate) {
                        AndroidThreadPoolUtils.runOnMainThread(new Runnable() {
                            @Override
                            public void run() {
                                listener.onDownloadProgress(progress);
                            }
                        });
                        rate = progress;
                    }
                }
                // 成功的下载文件
                AndroidThreadPoolUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        listener.onDownloadSuccess(destFile);
                    }
                });
            }
        });
    }

    /**
     * 下载进度监听器
     */
    public interface OnDownloadListener {

        /**
         * 成功下载文件
         *
         * @param file 文件的对象
         */
        void onDownloadSuccess(File file);

        /**
         * 正在下载文件中 ...
         *
         * @param progress 下载文件进度
         */
        void onDownloadProgress(int progress);

        /**
         * 文件下载失败
         *
         * @param ex 文件下载失败的错误信息
         */
        void onDownloadFailed(Exception ex);
    }
}