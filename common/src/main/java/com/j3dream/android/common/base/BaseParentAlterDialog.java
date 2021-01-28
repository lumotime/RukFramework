package com.j3dream.android.common.base;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.j3dream.android.common.exception.InitContentViewException;

/**
 * <p>文件名称: BaseParentDialog </p>
 * <p>所属包名: com.j3dream.android.common.base</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2021/1/28 09:21 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class BaseParentAlterDialog extends AlertDialog {

    public BaseParentAlterDialog(@NonNull Context context) {
        super(context);
    }

    public BaseParentAlterDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseParentAlterDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    /**
     * 设置当前布局资源
     * <p>
     * 原使用Object来接受当前的返回值，View类型使用概率太低，
     * 抛弃使用Object，使用int接收
     * </p>
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
    protected abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewRes(setLayoutRes());
        initData();
        initView();
    }

    /**
     * 设置当前活动的布局资源
     *
     * @param layoutRes 布局资源
     */
    private void setContentViewRes(Object layoutRes) {
        if (layoutRes instanceof Integer) {
            setContentView((Integer) layoutRes);
        } else if (layoutRes instanceof View) {
            setContentView((View) layoutRes);
        } else {
            throw new InitContentViewException("don't support u commit layout resource type!!!");
        }
    }
}
