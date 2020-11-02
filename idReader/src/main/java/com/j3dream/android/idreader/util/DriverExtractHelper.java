package com.j3dream.android.idreader.util;

import android.text.TextUtils;

import com.j3dream.android.idreader.DriverDeviceType;

import java.util.List;
import java.util.Set;

/**
 * <p>文件名称: DriverExtractHelper </p>
 * <p>所属包名: com.j3dream.android.idreader.util</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/11/2 11:04 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class DriverExtractHelper {

    /**
     * 提取读卡服务安全报名
     *
     * @param model
     * @return
     */
    public static Set<String> extractSafetyPackage(String model) {
        DriverDeviceType[] types = DriverDeviceType.values();
        for (DriverDeviceType type : types) {
            List<String> models = type.getModels();
            for (String itemPackage : models) {
                if (TextUtils.equals(itemPackage, model))
                    return type.getPackagePath();
            }
        }
        return DriverDeviceType.DEFAULT.getPackagePath();
    }
}
