package com.j3dream.android.idreader;

/**
 * <p>文件名称: OnReadCardListener </p>
 * <p>所属包名: com.jnft.controller.driver.ams</p>
 * <p>描述: 读卡信息回调 </p>
 * <p>创建时间: 2020-02-10 19:11 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface OnReadIdCardListener {

    /**
     * 读卡成功回调
     */
    void onIDCardReadResult(IDCardInfo IDCardInfo);

    /**
     * 读卡出现错误
     */
    void onIDCardReadFailure(Throwable error);
}
