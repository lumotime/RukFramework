package com.j3dream.android.arcface.exception;

/**
 * <p>文件名称: FaceEngineException </p>
 * <p>所属包名: com.lumotime.arcface.exception</p>
 * <p>描述: 人脸引擎异常 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/6/29 10:22 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class FaceEngineException extends FaceHandleException {

    public FaceEngineException(String message) {
        super(message);
    }

    public FaceEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
