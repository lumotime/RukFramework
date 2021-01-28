package com.j3dream.android.viewbinding;

import androidx.viewbinding.ViewBinding;

import com.dylanc.viewbinding.ViewBindingUtil;
import com.j3dream.android.common.base.BaseParentActivity;

/**
 * <p>文件名称: VBBaseActivity </p>
 * <p>所属包名: com.j3dream.android.viewbinding</p>
 * <p>描述: 视图绑定基础服务 </p>
 * <p></p>
 * <p>创建时间: 2021/1/27 17:21 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class VBBaseActivity<VB extends ViewBinding> extends BaseParentActivity {

    /**
     * 页面视图绑定器
     */
    private VB mViewBinding;

    @Override
    protected Object setLayoutRes() {
        mViewBinding = ViewBindingUtil.inflateWithGeneric(this, getLayoutInflater());
        return mViewBinding.getRoot();
    }

    public VB getViewBinding() {
        return mViewBinding;
    }
}
