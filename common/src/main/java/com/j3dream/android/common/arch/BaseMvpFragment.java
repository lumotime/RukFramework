package com.j3dream.android.common.arch;

import android.view.View;

import androidx.annotation.NonNull;

import com.j3dream.android.common.arch.proxy.ProxyFragment;
import com.j3dream.android.common.base.BaseParentFragment;
import com.j3dream.android.common.log.Logger;
import com.j3dream.android.common.util.ToastUtils;
import com.j3dream.core.exception.BaseException;
import com.j3dream.core.util.ObjectUtils;

/**
 * <p>文件名称: BaseMvpActivity </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp</p>
 * <p>描述: 基础的MVP架构活动 </p>
 * <p>创建时间: 2020/4/24 09:57 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BaseMvpFragment extends BaseParentFragment implements IBaseView {
    /**
     * 代理Presenter的绑定解绑操作的活动
     */
    private ProxyFragment mProxyFragment;

    @Override
    protected void initConfigs(@NonNull View rootView) {
        super.initConfigs(rootView);
        mProxyFragment = createProxyFragment();
        mProxyFragment.bindPresenter();
    }

    @Override
    public void onDestroy() {
        if (mProxyFragment != null) {
            mProxyFragment.unBindPresenter();
        }
        super.onDestroy();
    }

    @SuppressWarnings("unchecked")
    private ProxyFragment createProxyFragment() {
        if (mProxyFragment == null) {
            return new ProxyFragment(this);
        }
        return mProxyFragment;
    }

    @Override
    public void showBasicException(Throwable ex) {
        if (ex instanceof BaseException) {
            Logger.e(ex, "Arch Global Exception:");
        } else {
            Logger.e(ex, "App Exception:");
        }
        ToastUtils.show(ObjectUtils.toString(ex.getMessage()));
    }
}