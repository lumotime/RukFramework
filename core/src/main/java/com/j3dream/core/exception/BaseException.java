package com.j3dream.core.exception;

/**
 * <p>文件名称: GlobalException </p>
 * <p>所属包名: com.lumotime.core.exception</p>
 * <p>描述: 全局基础异常 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 10:19 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
