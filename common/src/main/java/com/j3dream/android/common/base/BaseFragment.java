package com.j3dream.android.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.j3dream.android.common.exception.InitContentViewException;

/**
 * <p>文件名称: BaseFragment </p>
 * <p>所属包名: com.bloodsport.lib.core.base.fragment</p>
 * <p>描述: 基础的碎片活动 </p>
 * <p>创建时间: 2020/3/16 11:14 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class BaseFragment extends Fragment {

    @Nullable
    protected View mContentView;
    @Nullable
    protected Context mContext;

    /**
     * 设置当前布局资源
     *
     * @return 设置的布局资源 只接受 'View, @LayoutRes int' 类型参数
     */
    protected abstract Object setLayoutRes();

    /**
     * 初始化活动相关数据
     */
    protected abstract void initData();

    /**
     * 初始化相关页面
     */
    protected abstract void initView(@NonNull View rootView);

    /**
     * 初始化额外配置
     */
    protected abstract void initConfigs(@NonNull View rootView);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = getContentViewRes(setLayoutRes(), inflater, container);
        initConfigs(mContentView);
        bindViews(mContentView);
        initData();
        initView(mContentView);
        return mContentView;
    }

    @SuppressWarnings("ConstantConditions")
    protected <T extends View> T $(@IdRes int viewId) {
        return this.getView().findViewById(viewId);
    }

    /**
     * 绑定视图控件
     */
    protected void bindViews(View rootView) {

    }

    /**
     * 设置当前活动的布局资源
     *
     * @param layoutRes 布局资源
     */
    private View getContentViewRes(Object layoutRes, LayoutInflater inflater, ViewGroup container) {
        if (layoutRes instanceof Integer) {
            return inflater.inflate((Integer) layoutRes, container, false);
        } else if (layoutRes instanceof View) {
            return (View) layoutRes;
        } else {
            throw new InitContentViewException("don't support u commit layout resource type!!!");
        }
    }
}
