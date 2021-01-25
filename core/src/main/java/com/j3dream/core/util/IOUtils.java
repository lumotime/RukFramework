package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;
import com.j3dream.core.interf.OnIOProgressUpdateListener;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * <p>文件名称: IOUtils </p>
 * <p>所属包名: com.bloodsport.lib.core.util</p>
 * <p>描述: 用于操作IO的工具 </p>
 * <p>创建时间: 2020/3/13 15:42 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class IOUtils {
    /**
     * 文件结尾
     */
    public static final int EOF = -1;

    /**
     * 默认的缓冲区大小, 用于{@link #copyLarge(InputStream, OutputStream)}
     */
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    /**
     * {@link #writeFileFromIS(String, InputStream, boolean, com.j3dream.core.interf.OnIOProgressUpdateListener)}缓冲区大小
     * 默认值为{@link #DEFAULT_BUFFER_SIZE}
     */
    private static int sBufferSize;

    static {
        sBufferSize = DEFAULT_BUFFER_SIZE;
    }

    private IOUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    // close
    // ------------------------------------------------------------------------

    /**
     * 关闭Http连接
     *
     * @param conn 要关闭的连接
     * @since 2.4
     */
    public static void close(final URLConnection conn) {
        if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection) conn).disconnect();
        }
    }

    /**
     * 关闭输入流
     *
     * @param is 要关闭的输入流
     * @return 关闭结果
     */
    public static boolean closeIs(final InputStream is) {
        try {
            is.close();
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 关闭输入流
     *
     * @param os 要关闭的输出流
     * @return 关闭结果
     */
    public static boolean closeOs(final OutputStream os) {
        try {
            os.close();
            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    /**
     * 从输入流中读取写入文件
     *
     * @param filePath 待写入的文件地址
     * @param is       输入流
     * @return 写入结果
     */
    public static boolean writeFileFromIS(final String filePath, final InputStream is) {
        return writeFileFromIS(getFileByPath(filePath), is, false, null);
    }

    /**
     * 从输入流中读取写入文件
     *
     * @param filePath 待写入的文件地址
     * @param is       输入流
     * @param append   是否以追加的方式进行写入
     * @return 写入结果
     */
    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final boolean append) {
        return writeFileFromIS(getFileByPath(filePath), is, append, null);
    }

    /**
     * 从输入流中读取写入文件
     *
     * @param file 待写入的文件实例
     * @param is   输入流
     * @return 写入结果
     */
    public static boolean writeFileFromIS(final File file, final InputStream is) {
        return writeFileFromIS(file, is, false, null);
    }

    /**
     * 从输入流中读取写入文件
     *
     * @param file   待写入的文件实例
     * @param is     输入流
     * @param append 是否以追加的方式进行写入
     * @return 写入结果
     */
    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,
                                          final boolean append) {
        return writeFileFromIS(file, is, append, null);
    }

    /**
     * 从输入流中读取写入文件
     *
     * @param filePath 待写入的文件地址
     * @param is       输入流
     * @param listener 处理进度监听器
     * @return 写入结果
     */
    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final OnIOProgressUpdateListener listener) {
        return writeFileFromIS(getFileByPath(filePath), is, false, listener);
    }

    /**
     * 从输入流中读取写入文件
     *
     * @param filePath 待写入的文件地址
     * @param is       输入流
     * @param append   是否以追加的方式进行写入
     * @param listener 处理进度监听器
     * @return 写入结果
     */
    public static boolean writeFileFromIS(final String filePath,
                                          final InputStream is,
                                          final boolean append,
                                          final OnIOProgressUpdateListener listener) {
        return writeFileFromIS(getFileByPath(filePath), is, append, listener);
    }

    /**
     * 从输入流中读取写入文件
     *
     * @param file     待写入的文件地址
     * @param is       输入流
     * @param append   是否以追加的方式进行写入
     * @param listener 处理进度监听器
     * @return 写入结果
     */
    public static boolean writeFileFromIS(final File file,
                                          final InputStream is,
                                          final boolean append,
                                          final OnIOProgressUpdateListener listener) {
        if (is == null || !createOrExistsFile(file)) {
            return false;
        }
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append), sBufferSize);
            if (listener == null) {
                byte[] data = new byte[sBufferSize];
                for (int len; (len = is.read(data)) != -1; ) {
                    os.write(data, 0, len);
                }
            } else {
                double totalSize = is.available();
                int curSize = 0;
                listener.onProgressUpdate(0);
                byte[] data = new byte[sBufferSize];
                for (int len; (len = is.read(data)) != -1; ) {
                    os.write(data, 0, len);
                    curSize += len;
                    listener.onProgressUpdate(curSize / totalSize);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 字节流拷贝
    // ----------------------------------------------------------------------

    /**
     * 字节流拷贝
     * <p>
     * 该方法适合用于小型流的复制如果流长度大于 2GB,将无法返回正常的流长度, 请使用
     * {@link #copyLarge(InputStream, OutputStream)} 进行大型流拷贝
     *
     * @param is 待拷贝输入流
     * @param os 待拷贝输出流
     * @return 拷贝的流长度, 如果流长度大于 Integer.MAX_VALUE 则会返回 -1
     * @throws IOException          发生IOE 错误
     * @throws NullPointerException 输入/输出流为空
     */
    public static int copy(InputStream is, OutputStream os) throws IOException {
        long length = copyLarge(is, os);
        if (length > Integer.MAX_VALUE) {
            return -1;
        } else {
            return (int) length;
        }
    }

    /**
     * 大型字节流拷贝
     *
     * @param is 待拷贝输入流
     * @param os 待拷贝输出流
     * @return 拷贝结果
     * @throws IOException          发生IOE 错误
     * @throws NullPointerException 输入/输出流为空
     */
    public static long copyLarge(InputStream is, OutputStream os) throws IOException {
        return copy(is, os, DEFAULT_BUFFER_SIZE);
    }

    /**
     * 字节流拷贝
     *
     * @param is         待拷贝的输入流
     * @param os         待拷贝的输出流
     * @param bufferSize 缓冲区大小
     * @return 拷贝结果
     * @throws IOException          发生IOE 错误
     * @throws NullPointerException 输入/输出流为空
     */
    public static long copy(final InputStream is, final OutputStream os, final int bufferSize)
            throws IOException {
        return copyLarge(is, os, new byte[bufferSize]);
    }

    /**
     * 字节流拷贝
     *
     * @param is     待拷贝的输入流
     * @param os     待拷贝的输出流
     * @param buffer 缓冲区
     * @return 拷贝结果
     * @throws IOException          发生IO 异常
     * @throws NullPointerException 输入/输出流为空
     */
    public static long copyLarge(final InputStream is, final OutputStream os, final byte[] buffer)
            throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = is.read(buffer))) {
            os.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    // 字符流拷贝
    // ------------------------------------------------------------------------

    /**
     * 输入指定的编码将字符从 字节输入流 复制到 字符输出流 上
     *
     * @param is            字节输入流
     * @param writer        字符输出流
     * @param inputEncoding 编码格式
     * @throws IOException          发生IO 异常
     * @throws NullPointerException 输入/输出流为空
     */
    public static void copy(final InputStream is, final Writer writer, final Charset inputEncoding) throws IOException {
        final InputStreamReader in = new InputStreamReader(is, toCharset(inputEncoding));
    }

    /**
     * 字符流拷贝
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝的流长度, 如果流长度大于 Integer.MAX_VALUE 则会返回 -1
     * @throws IOException          发生IO 异常
     * @throws NullPointerException 输入/输出流为空
     */
    public static int copy(final Reader reader, final Writer writer) throws IOException {
        final long count = copyLarge(reader, writer);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * 从字符输入流(阅读器)中读取字节输入到字节输出流中, 并清楚中间层的缓存
     *
     * @param reader         字符输入流(阅读器)
     * @param os             字节输入
     * @param outputEncoding 编码集
     * @throws IOException 发生IO异常
     */
    public static void copy(final Reader reader, final OutputStream os, final Charset outputEncoding)
            throws IOException {
        final OutputStreamWriter out = new OutputStreamWriter(os, toCharset(outputEncoding));
        copy(reader, out);
        out.flush();
    }

    /**
     * 大型字符流拷贝
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @return 拷贝的流长度
     * @throws IOException          发生IO 异常
     * @throws NullPointerException 输入/输出流为空
     */
    public static long copyLarge(final Reader reader, final Writer writer) throws IOException {
        return copyLarge(reader, writer, new char[DEFAULT_BUFFER_SIZE]);
    }

    /**
     * 大型字符流拷贝
     *
     * @param reader 字符输入流
     * @param writer 字符输出流
     * @param buffer 字符流缓冲区
     * @return 拷贝的流长度
     * @throws IOException          发生IO 异常
     * @throws NullPointerException 输入/输出流为空
     */
    public static long copyLarge(final Reader reader, final Writer writer, final char[] buffer) throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = reader.read(buffer))) {
            writer.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 判断两个 输入流 是否相等
     *
     * @param is1 第一个输入流
     * @param is2 第二个输入流
     * @return {@code true} 两个流内容相等或两个都不存在 {@code false} 其他情况
     * @throws IOException 发生IO 异常
     */
    public static boolean contentEquals(InputStream is1, InputStream is2)
            throws IOException {
        if (is1 == is2) {
            return true;
        }
        if (!(is1 instanceof BufferedInputStream)) {
            is1 = new BufferedInputStream(is1);
        }
        if (!(is2 instanceof BufferedInputStream)) {
            is2 = new BufferedInputStream(is2);
        }
        int ch = is1.read();
        while (EOF != ch) {
            final int ch2 = is2.read();
            if (ch != ch2) {
                return false;
            }
            ch = is1.read();
        }

        final int ch2 = is2.read();
        return ch2 == EOF;
    }

    /**
     * 判断两个 输入流 是否相等
     *
     * @param reader1 第一个输入流
     * @param reader2 第二个输入流
     * @return {@code true} 两个流内容相等或两个都不存在 {@code false} 其他情况
     * @throws IOException 过程中出现的异常
     */
    public static boolean contentEquals(Reader reader1, Reader reader2)
            throws IOException {
        if (reader1 == reader2) {
            return true;
        }

        reader1 = toBufferedReader(reader1);
        reader2 = toBufferedReader(reader2);
        int ch = reader1.read();
        while (EOF != ch) {
            final int ch2 = reader2.read();
            if (ch != ch2) {
                return false;
            }
            ch = reader1.read();
        }

        final int ch2 = reader2.read();
        return ch2 == EOF;
    }

    /**
     * 将输入流转化成 BufferedReader
     *
     * @param reader 待转换的字符输入流
     * @return 转换后的输入流
     */
    public static BufferedReader toBufferedReader(final Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    /**
     * 将输入流转化成 BufferedReader
     *
     * @param reader 待转换的字符输入流
     * @return 转换后的输入流
     */
    public static BufferedReader buffer(final Reader reader) {
        return toBufferedReader(reader);
    }

    /**
     * 将输入流转化成 BufferedInputStream
     *
     * @param is 待转换的输入流
     * @return 转换后的输入流
     * @throws NullPointerException 发生空指针异常
     */
    public static BufferedInputStream toBufferedInputStream(final InputStream is) {
        return is instanceof BufferedInputStream ? (BufferedInputStream) is : new BufferedInputStream(is);
    }

    /**
     * 将输入流转化成 BufferedInputStream
     *
     * @param is 待转换的输入流
     * @return 转换后的输入流
     * @throws NullPointerException 发生空指针异常
     */
    public static BufferedInputStream buffer(final InputStream is) {
        return toBufferedInputStream(is);
    }

    /**
     * 如果输入的字符集为空, 则返回平台默认的字符集
     *
     * @param charset 字符集
     * @return 变换处理的字符集
     */
    public static Charset toCharset(final Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    /**
     * 将输入流转化成字节数组
     *
     * @param is 输入流
     * @return 字节数组
     * @throws IOException          发生IO 异常
     * @throws NullPointerException 发生空指针异常
     */
    public static byte[] toByteArray(final InputStream is) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copy(is, output);
            return output.toByteArray();
        }
    }

    /**
     * 将一定长度的输入流转换成字节数组
     *
     * @param is   输入流
     * @param size 指定的长度
     * @return 字节数组
     * @throws IOException          发生IO 异常
     * @throws NullPointerException 发生空指针异常
     */
    public static byte[] toByteArray(final InputStream is, final long size) throws IOException {

        if (size > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + size);
        }

        return toByteArray(is, (int) size);
    }

    /**
     * 将一定长度的输入流转换成字节数组
     *
     * @param is   输入流
     * @param size 指定的长度
     * @return 转换后的字节数组
     * @throws IOException          发生IO 异常
     * @throws NullPointerException 发生空指针异常
     */
    public static byte[] toByteArray(final InputStream is, final int size) throws IOException {
        if (size < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + size);
        }
        if (size == 0) {
            return new byte[0];
        }
        final byte[] data = new byte[size];
        int offset = 0;
        int read;
        while (offset < size && (read = is.read(data, offset, size - offset)) != EOF) {
            offset += read;
        }
        if (offset != size) {
            throw new IOException("Unexpected read size. current: " + offset + ", expected: " + size);
        }
        return data;
    }

    /**
     * 将一定长度的输入流转换成字节数组
     *
     * @param reader   字符流
     * @param encoding 编码格式
     * @return 转换后的字节数组
     * @throws IOException              发生IO异常
     * @throws IllegalArgumentException 传入的编码为空
     * @throws NullPointerException     空指针异常
     */
    public static byte[] toByteArray(final Reader reader, final String encoding) throws IOException {
        return toByteArray(reader, toCharset(Charset.forName(encoding)));
    }

    /**
     * 将一定长度的输入流转换成字节数组
     *
     * @param reader   字符流
     * @param encoding 编码格式
     * @return 转换后的字节数组
     * @throws IOException          发生IO异常
     * @throws NullPointerException 空指针异常
     */
    public static byte[] toByteArray(final Reader reader, final Charset encoding) throws IOException {
        try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            copy(reader, output, toCharset(encoding));
            return output.toByteArray();
        }
    }

    /**
     * 按字节从字节写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param isForce  是否写入文件
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(FileUtils.getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * 按字节从字节写入文件
     *
     * @param filePath The path of file.
     * @param bytes    The bytes.
     * @param append   True to append, false otherwise.
     * @param isForce  True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByChannel(final String filePath,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(FileUtils.getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 按字节从字节写入文件
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param isForce True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    /**
     * 按字节从字节写入文件
     *
     * @param file    The file.
     * @param bytes   The bytes.
     * @param append  True to append, false otherwise.
     * @param isForce True to force write file, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean writeFileFromBytesByChannel(final File file,
                                                      final byte[] bytes,
                                                      final boolean append,
                                                      final boolean isForce) {
        if (bytes == null || !FileUtils.createOrExistsFile(file)) {
            return false;
        }
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            if (fc == null) {
                return false;
            }
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) fc.force(true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return the bytes in file by channel.
     *
     * @param filePath The path of file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByChannel(final String filePath) {
        return readFile2BytesByChannel(FileUtils.getFileByPath(filePath));
    }

    /**
     * Return the bytes in file by channel.
     *
     * @param file The file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByChannel(final File file) {
        if (!FileUtils.isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            if (fc == null) {
                return new byte[0];
            }
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(byteBuffer)) > 0)) break;
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Return the string in file.
     *
     * @param filePath The path of file.
     * @return the string in file
     */
    public static String readFile2String(final String filePath) {
        return readFile2String(UtilsBridge.getFileByPath(filePath), null);
    }

    /**
     * Return the string in file.
     *
     * @param filePath    The path of file.
     * @param charsetName The name of charset.
     * @return the string in file
     */
    public static String readFile2String(final String filePath, final String charsetName) {
        return readFile2String(UtilsBridge.getFileByPath(filePath), charsetName);
    }

    /**
     * Return the string in file.
     *
     * @param file The file.
     * @return the string in file
     */
    public static String readFile2String(final File file) {
        return readFile2String(file, null);
    }

    /**
     * Return the string in file.
     *
     * @param file        The file.
     * @param charsetName The name of charset.
     * @return the string in file
     */
    public static String readFile2String(final File file, final String charsetName) {
        byte[] bytes = readFile2BytesByStream(file);
        if (bytes == null) return null;
        if (UtilsBridge.isSpace(charsetName)) {
            return new String(bytes);
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        }
    }

    /**
     * Return the bytes in file by stream.
     *
     * @param filePath The path of file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(final String filePath) {
        return readFile2BytesByStream(UtilsBridge.getFileByPath(filePath), null);
    }

    /**
     * Return the bytes in file by stream.
     *
     * @param filePath The path of file.
     * @param listener The progress update listener.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(final String filePath,
                                                final OnProgressUpdateListener listener) {
        return readFile2BytesByStream(UtilsBridge.getFileByPath(filePath), listener);
    }

    /**
     * Return the bytes in file by stream.
     *
     * @param file     The file.
     * @param listener The progress update listener.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(final File file,
                                                final OnProgressUpdateListener listener) {
        if (!FileUtils.isFileExists(file)) return null;
        try {
            ByteArrayOutputStream os = null;
            InputStream is = new BufferedInputStream(new FileInputStream(file), sBufferSize);
            try {
                os = new ByteArrayOutputStream();
                byte[] b = new byte[sBufferSize];
                int len;
                if (listener == null) {
                    while ((len = is.read(b, 0, sBufferSize)) != -1) {
                        os.write(b, 0, len);
                    }
                } else {
                    double totalSize = is.available();
                    int curSize = 0;
                    listener.onProgressUpdate(0);
                    while ((len = is.read(b, 0, sBufferSize)) != -1) {
                        os.write(b, 0, len);
                        curSize += len;
                        listener.onProgressUpdate(curSize / totalSize);
                    }
                }
                return os.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the bytes in file by stream.
     *
     * @param file The file.
     * @return the bytes in file
     */
    public static byte[] readFile2BytesByStream(final File file) {
        return readFile2BytesByStream(file, null);
    }

    /**
     * 创建目录\文件等
     *
     * @param filePath 文件地址
     * @return 创建结果
     */
    private static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 创建目录\文件等
     *
     * @param file 待创建的实例
     * @return 创建结果
     */
    private static boolean createOrExistsFile(final File file) {
        if (file == null) {
            return false;
        }
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建目录\文件等
     *
     * @param file 待创建的实例
     * @return 创建结果
     */
    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public interface OnProgressUpdateListener {
        void onProgressUpdate(double progress);
    }
}
