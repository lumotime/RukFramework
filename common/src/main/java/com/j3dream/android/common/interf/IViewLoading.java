package com.j3dream.android.common.interf;

/**
 * <p>文件名称: IViewLoading </p>
 * <p>所属包名: com.bloodsport.basic.interf</p>
 * <p>描述: 视图加载接口 </p>
 * <p>创建时间: 2020/3/16 11:34 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public interface IViewLoading {

    /**
     * 展示加载动画
     */
    void showLoading();

    /**
     * 展示加载动画
     *
     * @param cancelable 是否允许点击外层消失
     */
    void showLoading(boolean cancelable);

    /**
     * 隐藏加载动画
     */
    void hideLoading();
}
