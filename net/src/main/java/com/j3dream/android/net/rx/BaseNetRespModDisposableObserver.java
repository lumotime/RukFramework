package com.j3dream.android.net.rx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.j3dream.android.common.interf.IViewLoading;
import com.j3dream.android.net.NetConfigurator;
import com.j3dream.android.net.exception.NetHandleException;
import com.j3dream.android.net.model.NetRespMod;
import com.j3dream.core.util.ObjectUtils;

/**
 * <p>文件名称: BaseNetRespModDisposableObserver </p>
 * <p>所属包名: cn.novakj.j3.net.rx</p>
 * <p>描述: 基础网络响应数据订阅者外层剥离 </p>
 * <p>创建时间: 2020/4/21 09:24 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class BaseNetRespModDisposableObserver<R, T extends NetRespMod<R>> extends BaseDisposableObserver<T> {

    public BaseNetRespModDisposableObserver() {
    }

    public BaseNetRespModDisposableObserver(@NonNull IViewLoading mViewLoading) {
        super(mViewLoading);
    }

    @Override
    protected final void onResult(@Nullable T resp) {
        if (ObjectUtils.isEmpty(resp)) {
            return;
        }
        if (resp.getCode() != NetConfigurator.getInstance().getNetConfig().getRespSuccessCode()) {
            onError(new NetHandleException(resp.getMessage(), resp.getCode()));
            return;
        }
        onNetResult(resp.getData());
    }

    public abstract void onNetResult(R resp);
}
