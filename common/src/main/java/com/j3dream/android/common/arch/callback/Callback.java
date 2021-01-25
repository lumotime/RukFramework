package com.j3dream.android.common.arch.callback;

/**
 * <p>文件名称: Callback </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp.callback</p>
 * <p>描述:  </p>
 * <p>创建时间: 2020/4/24 15:24 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface Callback<T> {

    void handleMessage(T message);

    void failure(Throwable throwable);
}
