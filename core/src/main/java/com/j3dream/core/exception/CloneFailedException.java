package com.j3dream.core.exception;

/**
 * <p>文件名称: CloneFailedException </p>
 * <p>所属包名: com.lumotime.core.exception</p>
 * <p>描述: 类克隆失败异常 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/9 10:19 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class CloneFailedException extends BaseException {

    public CloneFailedException(String message) {
        super(message);
    }

    public CloneFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloneFailedException(Throwable cause) {
        super(cause);
    }
}
