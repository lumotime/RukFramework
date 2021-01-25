package com.j3dream.android.net.model;

import java.io.Serializable;

/**
 * <p>文件名称: NetResponseModel </p>
 * <p>所属包名: com.bloodsport.net</p>
 * <p>描述: 基础的网络回调数据模型 </p>
 * <p>创建时间: 2020-02-09 17:08 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class NetRespMod<T> implements Serializable {

    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "NetResponseModel{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
