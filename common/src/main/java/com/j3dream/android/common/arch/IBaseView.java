package com.j3dream.android.common.arch;

import com.j3dream.android.common.interf.IViewLoading;

/**
 * <p>文件名称: IBaseView </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp</p>
 * <p>描述: MVP View层接口 </p>
 * <p>创建时间: 2020/4/24 11:06 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public interface IBaseView extends IViewLoading {

    /**
     * 展示处理基础的错误
     *
     * @param ex 错误信息对象
     */
    void showBasicException(Throwable ex);
}
