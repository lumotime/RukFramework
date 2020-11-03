package com.j3dream.android.arcface.data;

/**
 * 人脸比中信息结果
 */
public class CorrectUserInfo extends UserFeatureInfo {

    private String realAvatar;
    /**
     * 人脸特征相似度
     */
    private Float similar;

    public CorrectUserInfo() {
    }

    public CorrectUserInfo(UserFeatureInfo userFeatureInfo, Float similar) {
        this.similar = similar;
        setUserId(userFeatureInfo.getUserId());
        setUsername(userFeatureInfo.getUsername());
        setFeature(userFeatureInfo.getFeature());
        setIdNumber(userFeatureInfo.getIdNumber());
    }

    public String getRealAvatar() {
        return realAvatar;
    }

    public void setRealAvatar(String realAvatar) {
        this.realAvatar = realAvatar;
    }

    public Float getSimilar() {
        return similar;
    }

    public void setSimilar(Float similar) {
        this.similar = similar;
    }

    @Override
    public String toString() {
        return "UserCompareInfo{" +
                "similar=" + similar +
                "super=" + super.toString() +
                '}';
    }
}