package com.j3dream.android.idreader;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>文件名称: DriverDeviceType </p>
 * <p>所属包名: com.jnft.controller.driver.ams</p>
 * <p>描述: 常规的读卡设备类型枚举 </p>
 * <p>创建时间: 2020-02-10 19:26 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public enum DriverDeviceType {
    DEFAULT(Collections.singletonList("*"), new HashSet<>(Arrays.asList(
            "com.example.dell.myapplication", "com.sdses.ssInterface"
    ))),
    HAN_DE_HUO_ER(Collections.singletonList("ax6737_65_n"), Collections.singleton("com.sdses.ssInterface")),
    ZHONG_YUAN_XUN_BA(Collections.singletonList("Hi60"), Collections.singleton("com.YinanSoft.CardReaderTester")),
    ZHONG_XIN(Collections.singletonList("ID510"), Collections.singleton("com.zkteco.android.service.zkbox")),
    YIN_AN(Arrays.asList("SK-S600", "JWZD-600A", "EIDR-JW01"),
            new HashSet<>(Arrays.asList("com.ShandongSoft.CardReaderTester", "com.sdses.ssInterface"))),
    KANG_JIA(Collections.singletonList("X1"), Collections.singleton("com.konka.idcard")),
    ZHONG_KONG(Arrays.asList("giktech"), new HashSet<>(Arrays.asList(
            "com.example.scarx.idcardreader", "com.sdses.ssInterface"
    )));
    private List<String> models;
    private Set<String> packagePath;

    DriverDeviceType(List<String> models, Set<String> packagePath) {
        this.models = models;
        this.packagePath = packagePath;
    }

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

    public Set<String> getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(Set<String> packagePath) {
        this.packagePath = packagePath;
    }

    @Override
    public String toString() {
        return "DriverDeviceType{" +
                "models=" + models +
                ", packagePath='" + packagePath + '\'' +
                '}';
    }
}
