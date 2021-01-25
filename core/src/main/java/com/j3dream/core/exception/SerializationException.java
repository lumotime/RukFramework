package com.j3dream.core.exception;

/**
 * <p>文件名称: SerializationException </p>
 * <p>所属包名: com.lumotime.core.exception</p>
 * <p>描述: 序列化异常 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/21 17:40 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class SerializationException extends BaseException {

    public SerializationException(String message) {
        super(message);
    }

    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SerializationException(Throwable cause) {
        super(cause);
    }
}
