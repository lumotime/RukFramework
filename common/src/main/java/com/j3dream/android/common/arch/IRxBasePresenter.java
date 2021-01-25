package com.j3dream.android.common.arch;

/**
 * <p>文件名称: IRxBasePresenter </p>
 * <p>所属包名: com.lumotime.arch.mvp</p>
 * <p>描述:  </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/17 14:04 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface IRxBasePresenter extends IBasePresenter {

    /**
     * 解除页面上的全部订阅
     */
    void finishAllDisposable();
}
