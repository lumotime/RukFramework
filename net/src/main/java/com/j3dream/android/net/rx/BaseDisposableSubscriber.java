package com.j3dream.android.net.rx;

import android.util.Log;

import androidx.annotation.NonNull;

import com.j3dream.android.common.interf.IViewLoading;
import com.j3dream.android.common.util.AppUtils;
import com.j3dream.android.net.NetConstant;
import com.j3dream.core.util.ThrowableUtils;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * <p>文件名称: BaseDisposableSubscriber </p>
 * <p>所属包名: com.sdftgs.visitor.common.rx</p>
 * <p>描述: 基础的DisposableSubscriber </p>
 * <p>feature:
 *
 * </p>
 * <p>创建时间: 2020/7/2 10:57 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BaseDisposableSubscriber<T> extends DisposableSubscriber<T> {

    private IViewLoading mViewLoading;

    public BaseDisposableSubscriber() {
    }

    public BaseDisposableSubscriber(@NonNull IViewLoading mViewLoading) {
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
