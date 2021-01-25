package com.j3dream.android.net.rx;

import androidx.annotation.NonNull;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * <p>文件名称: BaseSingleObserver </p>
 * <p>所属包名: cn.example.room.rx</p>
 * <p>描述: 基础数据库RxJava Single订阅者 </p>
 * <p>创建时间: 2020/5/19 11:03 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BaseSingleObserver<T> implements SingleObserver<T> {

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

    protected abstract void onResult(@NonNull T t);

    protected abstract void onFailure(@NonNull Throwable ex);
}
