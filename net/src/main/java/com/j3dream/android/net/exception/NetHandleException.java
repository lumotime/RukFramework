package com.j3dream.android.net.exception;

import android.annotation.SuppressLint;

import com.j3dream.core.exception.BaseException;
import com.j3dream.core.util.StringUtils;

/**
 * <p>文件名称: NetException </p>
 * <p>所属包名: cn.novakj.j3.net.exception</p>
 * <p>描述: 网络框架异常 </p>
 * <p>创建时间: 2020/4/20 17:12 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class NetHandleException extends BaseException {

    private int code;
    private String sourceMessage;

    @SuppressLint("DefaultLocale")
    public NetHandleException(String message, int code) {
        this(String.format("request network service failure: code => %d, message => %s", code, StringUtils.null2Length0(message)));
        this.code = code;
        this.sourceMessage = message;
    }

    public NetHandleException(String message) {
        super(message);
    }

    public NetHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getCode() {
        return code;
    }

    public String getSourceMessage() {
        return sourceMessage;
    }
}
