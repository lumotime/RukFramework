package com.j3dream.core.util;

import com.j3dream.core.constant.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

/**
 * <p>文件名称: FileUtils </p>
 * <p>所属包名: com.lumotime.core.util</p>
 * <p>描述: 文件操作工具类 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 10:47 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class FileUtils implements Constants {

    public FileUtils() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORT_CREATE_INSTANTIATE);
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 输入的文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 其他情况
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param file 输入的文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 其他情况
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 输入的文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 其他情况
     */
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param file 输入的文件
     * @return {@code true}: 存在或创建成功<br>{@code false}: 其他情况
     */
    public static boolean createOrExistsFile(final File file) {
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
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 输入的文件路径
     * @return {@code true}: 删除历史文件并创建成功<br>{@code false}: 其他情况
     */
    public static boolean createFileByDeleteOldFile(final String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 输入的文件
     * @return {@code true}: 删除历史文件并创建成功<br>{@code false}: 其他情况
     */
    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) {
            return false;
        }
        // 文件存在且未成功删除，然后返回false
        if (file.exists() && !file.delete()) {
            return false;
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
     * 重命名文件或目录
     *
     * @param filePath 输入的文件路径
     * @param newName  新的名字
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * 得到制定路径生成的File实例
     *
     * @param filePath 文件路径
     * @return file实例
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 根据文件夹及文件名创建文件实例
     *
     * @param parentFile 文件夹路径
     * @param child      文件名称
     * @return file实例
     */
    public static File getFile(final File parentFile, final String child) {
        return isSpace(child) ? null : new File(parentFile, child);
    }

    /**
     * 根据文件夹及文件名创建文件实例
     *
     * @param parent 文件夹路径
     * @param child  文件名称
     * @return file实例
     */
    public static File getFile(final String parent, final String child) {
        return isSpace(child) || isSpace(parent) ? null : new File(parent, child);
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 输入的文件路径
     * @return {@code true}: 文件存在<br>{@code false}: 文件不存在
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 输入的文件
     * @return {@code true}: 文件存在<br>{@code false}: 文件不存在
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 判断是否为目录
     *
     * @param dirPath 文件路径
     * @return {@code true}: yes {@code false}: no
     */
    public static boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * 判断是否为目录
     *
     * @param file 文件实例
     * @return {@code true}: yes {@code false}: no
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 判断是否为文件
     *
     * @param filePath 文件路径
     * @return {@code true}: yes {@code false}: no
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断是否为文件
     *
     * @param file 文件实例
     * @return {@code true}: yes {@code false}: no
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * 判断输入的文件路径是否为null 或 空字符
     *
     * @param s input
     * @return {@code true} yes ${@code false} false
     */
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

    /**
     * 重命名文件或目录
     *
     * @param file    输入的文件实例
     * @param newName 新的名字
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean rename(final File file, final String newName) {
        if (file == null) {
            return false;
        }
        if (!file.exists()) {
            return false;
        }
        if (isSpace(newName)) {
            return false;
        }
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    /**
     * 复制文件夹
     *
     * @param srcDirPath  原始文件夹路径
     * @param destDirPath 目标文件夹路径
     * @return 移动结果
     */
    public static boolean copyDir(final String srcDirPath,
                                  final String destDirPath) {
        return copyDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 复制文件夹
     *
     * @param srcDir  原始文件夹
     * @param destDir 目标文件夹
     * @return 移动结果
     */
    public static boolean copyDir(final File srcDir,
                                  final File destDir) {
        return copyOrMoveDir(srcDir, destDir, false);
    }

    /**
     * 零拷贝复制文件
     *
     * @param srcFilePath  原文件地址
     * @param destFilePath 目标文件地址
     * @return {@code true} 复制文件家成功 {@code false} 其他情况
     */
    public static boolean zeroCopyFile(final String srcFilePath,
                                       final String destFilePath) {
        return zeroCopyFile(new File(srcFilePath), new File(destFilePath));
    }

    /**
     * 零拷贝复制文件
     *
     * @param srcFile  原文件
     * @param destFile 目标文件
     * @return {@code true} 复制文件家成功 {@code false} 其他情况
     */
    public static boolean zeroCopyFile(final File srcFile,
                                       final File destFile) {
        if (srcFile == null || destFile == null) {
            return false;
        }
        if (srcFile.equals(destFile)) {
            return false;
        }
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        if (!destFile.exists() || !destFile.isFile()) {
            return false;
        }
        try {
            FileChannel srcChannel = new FileInputStream(srcFile).getChannel();
            FileChannel destChannel = new FileOutputStream(destFile).getChannel();
            srcChannel.transferTo(0, srcChannel.size(), destChannel);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * 复制文件
     *
     * @param srcFilePath  原始文件路径
     * @param destFilePath 目标文件路径
     * @return 移动结果
     */
    public static boolean copyFile(final String srcFilePath,
                                   final String destFilePath) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 复制文件
     *
     * @param srcFile  原始文件
     * @param destFile 目标文件
     * @return 移动结果
     */
    public static boolean copyFile(final File srcFile,
                                   final File destFile) {
        return copyOrMoveFile(srcFile, destFile, false);
    }

    /**
     * 移动文件夹
     *
     * @param srcDirPath  原始文件夹路径
     * @param destDirPath 目标文件夹路径
     * @return 移动结果
     */
    public static boolean moveDir(final String srcDirPath,
                                  final String destDirPath) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath));
    }

    /**
     * 移动文件夹
     *
     * @param srcDir  原始文件夹
     * @param destDir 目标文件夹
     * @return 移动结果
     */
    public static boolean moveDir(final File srcDir,
                                  final File destDir) {
        return copyOrMoveDir(srcDir, destDir, true);
    }

    /**
     * 复制或者移动文件
     *
     * @param srcFilePath  原始文件路径
     * @param destFilePath 目标文件路径
     * @return 操作结果
     */
    public static boolean moveFile(final String srcFilePath,
                                   final String destFilePath) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath));
    }

    /**
     * 复制或者移动文件
     *
     * @param srcFile  原始文件
     * @param destFile 目标文件
     * @return 操作结果
     */
    public static boolean moveFile(final File srcFile,
                                   final File destFile) {
        return copyOrMoveFile(srcFile, destFile, true);
    }

    /**
     * 复制或者移动文件夹
     *
     * @param srcDir  原始文件夹
     * @param destDir 目标文件夹
     * @param isMove  是否是移动
     * @return 操作结果
     */
    private static boolean copyOrMoveDir(final File srcDir,
                                         final File destDir,
                                         final boolean isMove) {
        return copyOrMoveDir(srcDir, destDir, new OnFunctionListener() {
            @Override
            public boolean onOperate() {
                return true;
            }
        }, isMove);
    }

    /**
     * 删除一个文件夹下的所有文件
     *
     * @param dirPath 指定的文件路径
     * @return 删除结果
     */
    public static boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    /**
     * 删除一个文件夹下的所有文件
     *
     * @param dir 指定的文件路径
     * @return 删除结果
     */
    public static boolean deleteAllInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    /**
     * 删除在符合条件的文件
     *
     * @param dirPath 文件路径
     * @param filter  过滤器
     * @return 删除结果
     */
    public static boolean deleteFilesInDirWithFilter(final String dirPath,
                                                     final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 删除在制定目录下的文件
     *
     * @param dirPath 文件路径
     * @return 删除结果
     */
    public static boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * 删除在制定目录下的文件
     *
     * @param dir 文件实例
     * @return 删除结果
     */
    public static boolean deleteFilesInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    /**
     * 复制或者移动文件夹
     *
     * @param srcDir   原始文件夹
     * @param destDir  目标文件夹
     * @param listener 监听器
     * @param isMove   是否是移动
     * @return 操作结果
     */
    private static boolean copyOrMoveDir(final File srcDir,
                                         final File destDir,
                                         final OnFunctionListener listener,
                                         final boolean isMove) {
        if (srcDir == null || destDir == null) {
            return false;
        }
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) {
            return false;
        }
        if (!srcDir.exists() || !srcDir.isDirectory()) {
            return false;
        }
        if (destDir.exists()) {
            if (listener == null || listener.onOperate()) {
                if (!deleteAllInDir(destDir)) {
                    return false;
                }
            } else {
                return true;
            }
        }
        if (!createOrExistsDir(destDir)) {
            return false;
        }
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) {
                    return false;
                }
            } else if (file.isDirectory()) {
                if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) {
                    return false;
                }
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 删除在符合条件的文件
     *
     * @param dir    制定目录
     * @param filter 文件过滤器
     * @return 删除结果
     */
    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) {
                            return false;
                        }
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 删除文件夹
     *
     * @param dirPath 文件地址
     * @return 删除结果
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除文件夹
     *
     * @param dir 文件实例
     * @return 删除结果
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) {
            return false;
        }
        if (!dir.exists()) {
            return true;
        }
        if (!dir.isDirectory()) {
            return false;
        }
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件地址
     * @return 返回文件结果
     */
    public static boolean deleteFile(final String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * 删除文件
     *
     * @param file 文件实例
     * @return 返回删除结果
     */
    public static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    private static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final OnFunctionListener listener,
                                          final boolean isMove) {
        if (srcFile == null || destFile == null) {
            return false;
        }
        if (srcFile.equals(destFile)) {
            return false;
        }
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        if (destFile.exists()) {
            if (listener == null || listener.onOperate()) {
                if (!destFile.delete()) {
                    return false;
                }
            } else {
                return true;
            }
        }
        if (!createOrExistsDir(destFile.getParentFile())) {
            return false;
        }
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile))
                    && !(isMove && !deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制或移动文件
     *
     * @param srcFile  原始文件实例
     * @param destFile 目标文件实例
     * @param isMove   是否是移动
     * @return 复制或移动结果
     */
    private static boolean copyOrMoveFile(final File srcFile,
                                          final File destFile,
                                          final boolean isMove) {
        return copyOrMoveFile(srcFile, destFile, new OnFunctionListener() {
            @Override
            public boolean onOperate() {
                return true;
            }
        }, isMove);
    }

    /**
     * 写入文件到输入留中
     *
     * @param file 文件实例
     * @param is   输入流
     * @return 写入结果
     */
    private static boolean writeFileFromIS(final File file,
                                           final InputStream is) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte[] data = new byte[8192];
            int len;
            while ((len = is.read(data, 0, 8192)) != -1) {
                os.write(data, 0, len);
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

    /**
     * 获取目录下所有文件
     *
     * @param dirPath 目标路径
     * @return 文件列表
     */
    public static List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(dirPath, false);
    }

    /**
     * 获取目录下所有文件
     *
     * @param dir 目标文件实例
     * @return 文件列表
     */
    public static List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    /**
     * 获取目录下所有文件
     *
     * @param dirPath     目标文件路径
     * @param isRecursive 是否递归获取
     * @return 文件列表
     */
    public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * 获取目录下所有文件
     *
     * @param dir         目标文件实例
     * @param isRecursive 是否递归获取
     * @return 文件列表
     */
    public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    /**
     * 获取目录下所有过滤的文件
     *
     * @param dirPath 目标文件路径
     * @param filter  过滤器
     * @return 文件列表
     */
    public static List<File> listFilesInDirWithFilter(final String dirPath,
                                                      final FileFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    /**
     * 获取目录下所有过滤的文件
     *
     * @param dir    目标文件实例
     * @param filter 过滤器
     * @return 文件列表
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    /**
     * 获取目录下所有过滤的文件
     *
     * @param dirPath     目标文件路径
     * @param filter      过滤器
     * @param isRecursive 是否递归
     * @return 文件列表
     */
    public static List<File> listFilesInDirWithFilter(final String dirPath,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * 获取目录下所有过滤的文件
     *
     * @param dir         目标文件实例
     * @param filter      过滤器
     * @param isRecursive 是否递归
     * @return 文件列表
     */
    public static List<File> listFilesInDirWithFilter(final File dir,
                                                      final FileFilter filter,
                                                      final boolean isRecursive) {
        if (!isDir(dir)) {
            return null;
        }
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    list.addAll(listFilesInDirWithFilter(file, filter, true));
                }
            }
        }
        return list;
    }

    /**
     * 获取文件最后的修改时间
     *
     * @param filePath 文件路径
     * @return 修改时间
     */
    public static long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    /**
     * 获取文件最后的修改时间
     *
     * @param file 文件实例
     * @return 修改时间
     */
    public static long getFileLastModified(final File file) {
        if (file == null) {
            return -1;
        }
        return file.lastModified();
    }

    /**
     * 获取文件编码格式
     *
     * @param filePath 文件路径
     * @return 文件编码格式
     */
    public static String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * 获取文件编码格式
     *
     * @param file 文件实例
     * @return 文件编码格式
     */
    public static String getFileCharsetSimple(final File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param dirPath 目录路径
     * @return 文件夹大小
     */
    public static String getDirSize(final String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    /**
     * 获取文件夹大小
     *
     * @param dir 文件夹实例
     * @return 文件夹大小
     */
    public static String getDirSize(final File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * 获取目录长度
     *
     * @param dirPath 文件夹地址
     * @return 目录长度
     */
    public static long getDirLength(final String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    /**
     * 获取目录长度
     *
     * @param dir 目录实例
     * @return 目录长度
     */
    public static long getDirLength(final File dir) {
        if (!isDir(dir)) {
            return -1;
        }
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * 获取文件长度
     *
     * @param filePath 文件路径
     * @return 文件长度
     */
    public static long getFileLength(final String filePath) {
        boolean isURL = filePath.matches("[a-zA-z]+://[^\\s]*");
        if (isURL) {
            try {
                HttpsURLConnection conn = (HttpsURLConnection) new URL(filePath).openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    return conn.getContentLength();
                }
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getFileLength(getFileByPath(filePath));
    }

    /**
     * 获取文件长度
     *
     * @param file 目标文件
     * @return 文件长度
     */
    public static long getFileLength(final File file) {
        if (!isFile(file)) {
            return -1;
        }
        return file.length();
    }

    /**
     * 获取文件的MD5编码
     *
     * @param filePath 文件路径
     * @return MD5编码
     */
    public static String getFileMD5ToString(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToString(file);
    }

    /**
     * 获取文件的MD5编码
     *
     * @param file 文件
     * @return MD5编码
     */
    public static String getFileMD5ToString(final File file) {
        return bytes2HexString(getFileMD5(file));
    }

    /**
     * 获取文件的MD5编码
     *
     * @param filePath 文件路径
     * @return MD5编码
     */
    public static byte[] getFileMD5(final String filePath) {
        return getFileMD5(getFileByPath(filePath));
    }

    /**
     * 获取文件的MD5编码
     *
     * @param file 文件
     * @return MD5编码
     */
    public static byte[] getFileMD5(final File file) {
        if (file == null) {
            return null;
        }
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(buffer) > 0)) {
                    break;
                }
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将字符串写入文件
     *
     * @param filePath 文件路径
     * @param content  写入的内容
     * @return 写入结果
     */
    public static boolean writeFileFromString(final String filePath, final String content) {
        return writeFileFromString(getFileByPath(filePath), content, false);
    }

    /**
     * 将字符串写入文件
     *
     * @param filePath 文件路径
     * @param content  写入的内容
     * @param append   追加的内容
     * @return 写入结果
     */
    public static boolean writeFileFromString(final String filePath,
                                              final String content,
                                              final boolean append) {
        return writeFileFromString(getFileByPath(filePath), content, append);
    }

    /**
     * 将字符串写入文件
     *
     * @param file    目标文件
     * @param content 写入的内容
     * @return 写入结果
     */
    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

    /**
     * 将字符串写入文件
     *
     * @param file    目标文件
     * @param content 写入的内容
     * @param append  是否追加
     * @return 写入结果
     */
    public static boolean writeFileFromString(final File file,
                                              final String content,
                                              final boolean append) {
        if (file == null || content == null) {
            return false;
        }
        if (!createOrExistsFile(file)) {
            return false;
        }
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format(Locale.getDefault(), "%.3fMB", (double) byteNum / 1048576);
        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum / 1073741824);
        }
    }

    private static String bytes2HexString(final byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        int len = bytes.length;
        if (len <= 0) {
            return "";
        }
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    public interface OnFunctionListener {

        /**
         * 操作结果
         *
         * @return 结果
         */
        boolean onOperate();
    }
}
