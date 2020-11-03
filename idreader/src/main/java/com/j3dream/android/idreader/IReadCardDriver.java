package com.j3dream.android.idreader;

import android.content.Context;

import androidx.annotation.Nullable;

/**
 * <p>文件名称: IReadCardDriver </p>
 * <p>所属包名: com.jnft.controller.driver.ams</p>
 * <p>描述: 读卡启动器标准 </p>
 * <p>创建时间: 2020-02-10 19:18 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface IReadCardDriver {

    /**
     * 开始读取身份证信息
     *
     * @param packagePath 安全包名地址
     */
    void startReadingCardInfo(Context mContext, @Nullable String packagePath);

    /**
     * 结束读取身份证信息
     */
    void stopReadingCardInfo(Context mContext, @Nullable String packagePath);
}
