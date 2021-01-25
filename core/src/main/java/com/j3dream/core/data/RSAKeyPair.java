package com.j3dream.core.data;

/**
 * <p>文件名称: KeyPair </p>
 * <p>所属包名: com.lumotime.base.data</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/9/17 14:57 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class RSAKeyPair {
    private String publicKey;
    private String privateKey;

    public RSAKeyPair(String publicKey, String privateKey) {
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
        return "KeyPair{" +
                "publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                '}';
    }
}
