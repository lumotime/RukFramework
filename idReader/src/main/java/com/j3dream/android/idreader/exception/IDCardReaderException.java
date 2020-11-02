package com.j3dream.android.idreader.exception;

import com.j3dream.core.exception.BaseException;

/**
 * <p>文件名称: IDCardReaderException </p>
 * <p>所属包名: com.j3dream.android.idreader.exception</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/11/2 11:01 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class IDCardReaderException extends BaseException {
    public IDCardReaderException(String message) {
        super(message);
    }

    public IDCardReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public IDCardReaderException(Throwable cause) {
        super(cause);
    }
}
