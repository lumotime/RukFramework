package com.j3dream.android.common.exception;

import com.j3dream.core.exception.BaseException;

/**
 * <p>文件名称: InitContentViewException </p>
 * <p>所属包名: com.j3dream.android.common.exception</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/10/26 11:15 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class InitContentViewException extends BaseException {
    public InitContentViewException(String message) {
        super(message);
    }

    public InitContentViewException(String message, Throwable cause) {
        super(message, cause);
    }

    public InitContentViewException(Throwable cause) {
        super(cause);
    }
}
