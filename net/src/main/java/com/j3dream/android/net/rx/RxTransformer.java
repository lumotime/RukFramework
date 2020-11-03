package com.j3dream.android.net.rx;

import androidx.annotation.Nullable;

import com.j3dream.android.common.interf.IViewLoading;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>文件名称: RxDbTransformer </p>
 * <p>所属包名: cn.example.room.rx</p>
 * <p>描述: 异步数据库变换器 </p>
 * <p>创建时间: 2020/5/19 13:47 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public final class RxTransformer {

    public static final long DEFAULT_OBSERVABLE_TIMEOUT_LIMIT = 8;
    public static final TimeUnit DEFAULT_OBSERVABLE_TIMEOUT_LIMIT_UNIT = TimeUnit.SECONDS;

    /**
     * rxJava 线程切换处理器
     *
     * @param <T> 来源订阅者类型
     * @return 订阅在Io线程, 接受到主线程
     */
    public static <T> ObservableTransformer<T, T> rxObservableTransformer() {
        return rxObservableTransformer(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * rxJava 线程切换处理器
     *
     * @param subscribeOn 订阅的线程
     * @param observeOn   接受的线程
     * @param <T>         来源订阅者类型
     * @return 线程切换工具
     */
    public static <T> ObservableTransformer<T, T> rxObservableTransformer(
            @Nullable final Scheduler subscribeOn,
            @Nullable final Scheduler observeOn
    ) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(subscribeOn)
                        .observeOn(observeOn);
            }
        };
    }

    /**
     * rxJava 线程切换处理器
     *
     * @param <T> 来源订阅者类型
     * @return 订阅在Io线程, 接受到主线程
     */
    public static <T> FlowableTransformer<T, T> rxFlowableTransformer() {
        return rxFlowableTransformer(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * rxJava 线程切换处理器
     *
     * @param subscribeOn 订阅的线程
     * @param observeOn   接受的线程
     * @param <T>         来源订阅者类型
     * @return 线程切换工具
     */
    public static <T> FlowableTransformer<T, T> rxFlowableTransformer(
            @Nullable final Scheduler subscribeOn,
            @Nullable final Scheduler observeOn
    ) {
        return new FlowableTransformer<T, T>() {

            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(subscribeOn)
                        .observeOn(observeOn);
            }
        };
    }

    /**
     * rxJava 线程切换处理器
     *
     * @return 订阅在Io线程, 接受到主线程
     */
    public static CompletableTransformer rxCompletableTransformer() {
        return rxCompletableTransformer(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * rxJava 线程切换处理器
     *
     * @param subscribeOn 订阅的线程
     * @param observeOn   接受的线程
     * @return 线程切换工具
     */
    public static CompletableTransformer rxCompletableTransformer(
            @Nullable final Scheduler subscribeOn,
            @Nullable final Scheduler observeOn
    ) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable upstream) {
                return upstream.subscribeOn(subscribeOn)
                        .observeOn(observeOn);
            }
        };
    }

    /**
     * rxJava 线程切换处理器
     *
     * @param <T> 来源订阅者类型
     * @return 订阅在Io线程, 接受到主线程
     */
    public static <T> SingleTransformer<T, T> rxSingleTransformer() {
        return rxSingleTransformer(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * rxJava 线程切换处理器
     *
     * @param subscribeOn 订阅的线程
     * @param observeOn   接受的线程
     * @param <T>         来源订阅者类型
     * @return 线程切换工具
     */
    public static <T> SingleTransformer<T, T> rxSingleTransformer(
            @Nullable final Scheduler subscribeOn,
            @Nullable final Scheduler observeOn
    ) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream.subscribeOn(subscribeOn)
                        .observeOn(observeOn);
            }
        };
    }

    /**
     * rxJava 线程切换处理器
     *
     * @param <T> 来源订阅者类型
     * @return 订阅在Io线程, 接受到主线程
     */
    public static <T> MaybeTransformer<T, T> rxMaybeTransformer() {
        return rxMaybeTransformer(Schedulers.io(), AndroidSchedulers.mainThread());
    }

    /**
     * rxJava 线程切换处理器
     *
     * @param subscribeOn 订阅的线程
     * @param observeOn   接受的线程
     * @param <T>         来源订阅者类型
     * @return 线程切换工具
     */
    public static <T> MaybeTransformer<T, T> rxMaybeTransformer(
            @Nullable final Scheduler subscribeOn,
            @Nullable final Scheduler observeOn
    ) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> upstream) {
                return upstream.subscribeOn(subscribeOn)
                        .observeOn(observeOn);
            }
        };
    }

    /**
     * rxJava 线程切换处理器 自动对loading进行显示隐藏
     *
     * @param viewLoading loading 操作接口
     * @param <T>         来源订阅者类型
     * @return 线程切换工具
     */
    public static <T> ObservableTransformer<T, T> rxLoadingObservableTransformer(final IViewLoading viewLoading) {
        return new ObservableTransformer<T, T>() {

            @Override
            public ObservableSource apply(Observable upstream) {
                return upstream
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.showLoading();
                                } else {
                                    disposable.dispose();
                                }
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.hideLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * rxJava 线程切换处理器 自动对loading进行显示隐藏
     *
     * @param viewLoading loading 操作接口
     * @param <T>         来源订阅者类型
     * @return 线程切换工具
     */
    public static <T> FlowableTransformer<T, T> rxLoadingFlowableTransformer(final IViewLoading viewLoading) {
        return new FlowableTransformer<T, T>() {

            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Subscription>() {
                            @Override
                            public void accept(Subscription subscription) throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.showLoading();
                                } else {
                                    subscription.cancel();
                                }
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.hideLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * rxJava 线程切换处理器 自动对loading进行显示隐藏（背压）
     *
     * @param viewLoading loading 操作接口
     * @return 线程切换工具
     */
    public static CompletableTransformer rxLoadingCompletableTransformer(final IViewLoading viewLoading) {
        return new CompletableTransformer() {

            @Override
            public CompletableSource apply(Completable upstream) {
                return upstream
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.showLoading();
                                } else {
                                    disposable.dispose();
                                }
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.hideLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * rxJava 线程切换处理器 自动对loading进行显示隐藏
     *
     * @param viewLoading loading 操作接口
     * @param <T>         来源订阅者类型
     * @return 线程切换工具
     */
    public static <T> SingleTransformer<T, T> rxLoadingSingleTransformer(final IViewLoading viewLoading) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.showLoading();
                                } else {
                                    disposable.dispose();
                                }
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.hideLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * rxJava 线程切换处理器 自动对loading进行显示隐藏（背压）
     *
     * @param viewLoading loading 操作接口
     * @param <T>         来源订阅者类型
     * @return 线程切换工具
     */
    public static <T> MaybeTransformer<T, T> rxLoadingMaybeTransformer(final IViewLoading viewLoading) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> upstream) {
                return upstream
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(Disposable disposable) throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.showLoading();
                                } else {
                                    disposable.dispose();
                                }
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                if (viewLoading != null) {
                                    viewLoading.hideLoading();
                                }
                            }
                        });
            }
        };
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param <T> 原始被订阅者类型
     * @return 变形后的被订阅者
     */
    public static <T> ObservableTransformer<T, T> transformerObservableOfTimeoutError() {
        return transformerObservableOfTimeoutError(DEFAULT_OBSERVABLE_TIMEOUT_LIMIT,
                DEFAULT_OBSERVABLE_TIMEOUT_LIMIT_UNIT);
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param timeout  超时时间数量
     * @param timeUnit 超时时间单位
     * @param <T>      原始被订阅者类型
     * @return 变形后的被订阅者
     */
    public static <T> ObservableTransformer<T, T>
    transformerObservableOfTimeoutError(final long timeout, final TimeUnit timeUnit) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .observeOn(Schedulers.newThread())
                        .timeout(timeout, timeUnit, Observable.<T>error(new TimeoutException()))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param <T> 原始被订阅者类型
     * @return 变形后的被订阅者
     */
    public static <T> FlowableTransformer<T, T> transformerFlowableOfTimeoutError() {
        return transformerFlowableOfTimeoutError(DEFAULT_OBSERVABLE_TIMEOUT_LIMIT,
                DEFAULT_OBSERVABLE_TIMEOUT_LIMIT_UNIT);
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param timeout  超时时间数量
     * @param timeUnit 超时时间单位
     * @param <T>      原始被订阅者类型
     * @return 变形后的被订阅者
     */
    public static <T> FlowableTransformer<T, T>
    transformerFlowableOfTimeoutError(final long timeout, final TimeUnit timeUnit) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .observeOn(Schedulers.newThread())
                        .timeout(timeout, timeUnit, Flowable.<T>error(new TimeoutException()))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param <T> 原始被订阅者类型
     * @return 变形后的被订阅者
     */
    public static <T> SingleTransformer<T, T> transformerSingleOfTimeoutError() {
        return transformerSingleOfTimeoutError(DEFAULT_OBSERVABLE_TIMEOUT_LIMIT,
                DEFAULT_OBSERVABLE_TIMEOUT_LIMIT_UNIT);
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param timeout  超时时间数量
     * @param timeUnit 超时时间单位
     * @param <T>      原始被订阅者类型
     * @return 变形后的被订阅者
     */
    public static <T> SingleTransformer<T, T>
    transformerSingleOfTimeoutError(final long timeout, final TimeUnit timeUnit) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream
                        .observeOn(Schedulers.newThread())
                        .timeout(timeout, timeUnit, Single.<T>error(new TimeoutException()))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param <T> 原始被订阅者类型
     * @return 变形后的被订阅者
     */
    public static <T> MaybeTransformer<T, T> transformerMaybeOfTimeoutError() {
        return transformerMaybeOfTimeoutError(DEFAULT_OBSERVABLE_TIMEOUT_LIMIT,
                DEFAULT_OBSERVABLE_TIMEOUT_LIMIT_UNIT);
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param timeout  超时时间数量
     * @param timeUnit 超时时间单位
     * @param <T>      原始被订阅者类型
     * @return 变形后的被订阅者
     */
    public static <T> MaybeTransformer<T, T>
    transformerMaybeOfTimeoutError(final long timeout, final TimeUnit timeUnit) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> upstream) {
                return upstream
                        .observeOn(Schedulers.newThread())
                        .timeout(timeout, timeUnit, Maybe.<T>error(new TimeoutException()))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @return 变形后的被订阅者
     */
    public static CompletableTransformer transformerCompletableOfTimeoutError() {
        return transformerCompletableOfTimeoutError(DEFAULT_OBSERVABLE_TIMEOUT_LIMIT,
                DEFAULT_OBSERVABLE_TIMEOUT_LIMIT_UNIT);
    }

    /**
     * RxJava订阅访问超时变形器
     *
     * @param timeout  超时时间数量
     * @param timeUnit 超时时间单位
     * @return 变形后的被订阅者
     */
    public static CompletableTransformer
    transformerCompletableOfTimeoutError(final long timeout, final TimeUnit timeUnit) {
        return new CompletableTransformer() {

            @Override
            public CompletableSource apply(Completable upstream) {
                return upstream
                        .observeOn(Schedulers.newThread())
                        .timeout(timeout, timeUnit, Completable.error(new TimeoutException()))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
