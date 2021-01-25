package com.j3dream.core.exception;

/**
 * <p>文件名称: FileException </p>
 * <p>所属包名: com.j3dream.core.exception</p>
 * <p>描述:  </p>
 * <p>创建时间: 2020/10/19 10:55 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class FileException extends BaseException {
    public FileException(String message) {
        super(message);
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileException(Throwable cause) {
        super(cause);
    }
}
