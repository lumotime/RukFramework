package com.j3dream.android.common.interf;

import androidx.annotation.NonNull;

/**
 * <p>文件名称: OnRequestPermissionsResultListener </p>
 * <p>所属包名: com.lumotime.base.interf</p>
 * <p>描述: 请求权限列表结果监听器 </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/17 15:34 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface OnRequestPermissionsResultListener {

    void onResult(int requestCode, @NonNull String[] permissions, boolean grant);
}
