package com.j3dream.android.common.arch;

/**
 * <p>文件名称: IBasePresenter </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp</p>
 * <p>描述: 基础控制层接口, 定义View的解绑和绑定 </p>
 * <p>创建时间: 2020/4/24 11:06 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface IBasePresenter {

    /**
     * 绑定View
     *
     * @param view 持有活动的view
     */
    void attach(IBaseView view);

    /**
     * 解绑View
     */
    void detach();
}
