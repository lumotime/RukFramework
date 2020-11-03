package com.j3dream.android.net.rx;

import androidx.annotation.NonNull;

import io.reactivex.observers.DisposableCompletableObserver;

/**
 * <p>文件名称: BaseCompletable </p>
 * <p>所属包名: cn.example.room.rx</p>
 * <p>描述: 基础数据库RxJava Completable订阅者 </p>
 * <p>创建时间: 2020/5/19 11:28 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class BaseDisposableCompletable extends DisposableCompletableObserver {

    @Override
    public final void onError(Throwable ex) {
        onFailure(ex);
    }

    protected abstract void onFailure(@NonNull Throwable ex);
}
