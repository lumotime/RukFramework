package com.j3dream.android.net.rx;

import android.util.Log;

import androidx.annotation.NonNull;

import com.j3dream.android.common.interf.IViewLoading;
import com.j3dream.android.common.util.AppUtils;
import com.j3dream.android.net.NetConstant;
import com.j3dream.core.util.ThrowableUtils;

import io.reactivex.observers.DisposableObserver;

/**
 * <p>文件名称: BaseNetRespModDisposableObserver </p>
 * <p>所属包名: cn.novakj.j3.net.rx</p>
 * <p>描述: 基础网络响应数据订阅者外层剥离 </p>
 * <p>创建时间: 2020/4/21 09:24 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class BaseDisposableObserver<T> extends DisposableObserver<T> {

    private IViewLoading mViewLoading;

    public BaseDisposableObserver() {
    }

    public BaseDisposableObserver(@NonNull IViewLoading mViewLoading) {
        this.mViewLoading = mViewLoading;
    }

    @Override
    public final void onNext(T t) {
        onResult(t);
    }

    @Override
    public void onComplete() {
        if (mViewLoading != null) {
            mViewLoading.hideLoading();
        }
        onUnifyHandle();
    }

    @Override
    public void onError(Throwable ex) {
        if (mViewLoading != null) {
            mViewLoading.hideLoading();
        }
        if (AppUtils.isAppDebug()) {
            Log.w(NetConstant.TAG, "observer subject error; " + ThrowableUtils.getStackTrace(ex));
        } else {
            Log.e(NetConstant.TAG, "observer subject error", ex);
        }
        onFailure(ex);
        onUnifyHandle();
    }

    protected void onUnifyHandle() {

    }

    protected abstract void onResult(@NonNull T t);

    protected abstract void onFailure(@NonNull Throwable ex);
}
