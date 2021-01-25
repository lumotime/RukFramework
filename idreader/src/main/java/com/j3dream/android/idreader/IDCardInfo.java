package com.j3dream.android.idreader;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * <p>文件名称: IdCardDataMod </p>
 * <p>所属包名: cn.ftkj.visitor.tool.ams</p>
 * <p>描述: 身份证数据模型 </p>
 * <p>创建时间: 2020/5/9 10:13 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class IDCardInfo implements Serializable {

    private static final long serialVersionUID = -2037823156352460703L;

    private String cardNumber;
    private String address;
    private String name;
    private String gender;
    private String nation;
    private String birthday;
    private Bitmap avatar;
    private String fingerPrint;

    public IDCardInfo(String cardNumber, String address, String name, String gender, String nation, String birthday, Bitmap avatar) {
        this.cardNumber = cardNumber;
        this.address = address;
        this.name = name;
        this.gender = gender;
        this.nation = nation;
        this.birthday = birthday;
        this.avatar = avatar;
    }

    public IDCardInfo(String cardNumber, String address, String name, String gender, String nation, String birthday, Bitmap avatar, String fingerPrint) {
        this.cardNumber = cardNumber;
        this.address = address;
        this.name = name;
        this.gender = gender;
        this.nation = nation;
        this.birthday = birthday;
        this.avatar = avatar;
        this.fingerPrint = fingerPrint;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    @Override
    public String toString() {
        return "IdCardDataMod{" +
                "cardNumber='" + cardNumber + '\'' +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", nation='" + nation + '\'' +
                ", birthday='" + birthday + '\'' +
                ", avatar=" + avatar +
                ", fingerPrint='" + fingerPrint + '\'' +
                '}';
    }
}
