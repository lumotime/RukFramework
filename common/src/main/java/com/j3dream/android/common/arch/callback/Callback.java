package com.j3dream.android.common.arch.callback;

/**
 * <p>文件名称: Callback </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp.callback</p>
 * <p>描述:  </p>
 * <p>创建时间: 2020/4/24 15:24 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface Callback<T> {

    void handleMessage(T message);

    void failure(Throwable throwable);
}
