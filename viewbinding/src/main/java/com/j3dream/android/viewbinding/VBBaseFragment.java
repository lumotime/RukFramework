package com.j3dream.android.viewbinding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.dylanc.viewbinding.ViewBindingUtil;
import com.j3dream.android.common.base.BaseParentFragment;

/**
 * <p>文件名称: VBBaseFragment </p>
 * <p>所属包名: com.j3dream.android.viewbinding</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2021/1/27 17:37 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class VBBaseFragment<VB extends ViewBinding> extends BaseParentFragment {

    /**
     * 页面视图绑定器
     */
    private VB mViewBinding;

    @Override
    protected Object setLayoutRes(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewBinding = ViewBindingUtil.inflateWithGeneric(this, getLayoutInflater());
        return mViewBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewBinding = null;
    }

    public VB getViewBinding() {
        return mViewBinding;
    }
}