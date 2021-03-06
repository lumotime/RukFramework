package com.j3dream.android.common.arch.proxy;

/**
 * <p>文件名称: IProxy </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp.proxy</p>
 * <p>描述: 代理活动中 Presenter 绑定解绑操作 </p>
 * <p>创建时间: 2020/4/24 11:15 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface IProxy {

    /**
     * 绑定Presenter
     */
    void bindPresenter();

    /**
     * 绑定Presenter
     */
    void unBindPresenter();
}
