package com.j3dream.android.viewbinding;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

import com.dylanc.viewbinding.ViewBindingUtil;
import com.j3dream.android.common.base.BaseParentDialog;

/**
 * <p>文件名称: VBBaseDialog </p>
 * <p>所属包名: com.j3dream.android.viewbinding</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2021/1/28 09:07 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class VBBaseDialog<VB extends ViewBinding> extends BaseParentDialog {

    private VB mViewBinding;

    public VBBaseDialog(@NonNull Context context) {
        this(context, 0);
    }

    public VBBaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected Object setLayoutRes() {
        mViewBinding = ViewBindingUtil.inflateWithGeneric(this, getLayoutInflater());
        return mViewBinding.getRoot();
    }

    public VB getViewBinding() {
        return mViewBinding;
    }
}
