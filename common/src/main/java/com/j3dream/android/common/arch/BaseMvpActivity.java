package com.j3dream.android.common.arch;

import com.j3dream.android.common.arch.proxy.ProxyActivity;
import com.j3dream.android.common.base.BaseParentActivity;
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
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class BaseMvpActivity extends BaseParentActivity implements IBaseView {
    /**
     * 代理Presenter的绑定解绑操作的活动
     */
    private ProxyActivity mProxyActivity;

    @Override
    protected void initConfigs() {
        super.initConfigs();
        mProxyActivity = createProxyActivity();
        mProxyActivity.bindPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProxyActivity != null) {
            mProxyActivity.unBindPresenter();
        }
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

    @SuppressWarnings("unchecked")
    private ProxyActivity createProxyActivity() {
        if (mProxyActivity == null) {
            return new ProxyActivity(this);
        }
        return mProxyActivity;
    }
}