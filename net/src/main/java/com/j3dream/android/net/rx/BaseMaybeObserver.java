package com.j3dream.android.net.rx;

import androidx.annotation.NonNull;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;

/**
 * <p>文件名称: BaseMaybeObserver </p>
 * <p>所属包名: cn.example.room.rx</p>
 * <p>描述: 基础的数据库Rxjava Maybe订阅者 </p>
 * <p>创建时间: 2020/5/19 11:01 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class BaseMaybeObserver<T> implements MaybeObserver<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public final void onSuccess(T t) {
        onResult(t);
    }

    @Override
    public final void onError(Throwable ex) {
        onFailure(ex);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 显示数据结果
     *
     * @param t 数据结果
     */
    protected abstract void onResult(@NonNull T t);

    /**
     * 存在失败情况
     *
     * @param ex 失败结果
     */
    protected abstract void onFailure(@NonNull Throwable ex);
}
