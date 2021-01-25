package com.j3dream.android.net.rx;

import androidx.annotation.NonNull;

import io.reactivex.observers.DisposableMaybeObserver;

/**
 * <p>文件名称: BaseMaybeObserver </p>
 * <p>所属包名: cn.example.room.rx</p>
 * <p>描述: 基础的数据库Rxjava Maybe订阅者 </p>
 * <p>创建时间: 2020/5/19 11:01 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BaseDisposableMaybe<T> extends DisposableMaybeObserver<T> {

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
