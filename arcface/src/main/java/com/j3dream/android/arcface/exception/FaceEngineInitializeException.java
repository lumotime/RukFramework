package com.j3dream.android.arcface.exception;

/**
 * <p>文件名称: FaceEngineInitializeException </p>
 * <p>所属包名: com.lumotime.arcface.exception</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/9/12 10:41 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class FaceEngineInitializeException extends FaceEngineException {
    public FaceEngineInitializeException(String message) {
        super(message);
    }

    public FaceEngineInitializeException(String message, Throwable cause) {
        super(message, cause);
    }
}