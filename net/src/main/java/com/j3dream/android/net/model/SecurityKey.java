package com.j3dream.android.net.model;

import java.io.Serializable;

/**
 * <p>文件名称: SecurityKey </p>
 * <p>所属包名: com.bloodsport.net.model</p>
 * <p>描述: 安全的密钥存储对象 </p>
 * <p>创建时间: 2020-02-19 16:15 </p>
 * <p>公司信息: 济南丰通信息科技 技术部</p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class SecurityKey implements Serializable {

    private static final long serialVersionUID = 788204036564487329L;

    /**
     * 服务器的公钥
     */
    private String publicKey;
    /**
     * 客户端的私钥
     */
    private String privateKey;

    public SecurityKey() {
    }

    public SecurityKey(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String toString() {
        return "SecurityKey{" +
                "publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
