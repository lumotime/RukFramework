package com.j3dream.android.common.arch.impl;

import com.j3dream.android.common.arch.BaseModel;
import com.j3dream.android.common.arch.IBasePresenter;
import com.j3dream.android.common.arch.IBaseView;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * <p>文件名称: BasePresenter </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp.impl</p>
 * <p>描述: 基础的控制层, 供其他实现的引用 </p>
 * <p>创建时间: 2020/4/24 11:10 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> implements IBasePresenter {

    private SoftReference<IBaseView> mReferenceView;
    private V mProxyView;
    private M mModel;

    @SuppressWarnings({"unchecked", "TryWithIdenticalCatches", "ConstantConditions"})
    @Override
    public void attach(IBaseView view) {

        //使用软引用创建对象
        mReferenceView = new SoftReference<>(view);

        //使用动态代理做统一的逻辑判断 aop 思想
        mProxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                if (mReferenceView == null || mReferenceView.get() == null) {
                    return null;
                }
                return method.invoke(mReferenceView.get(), objects);
            }
        });

        //通过获得泛型类的父类，拿到泛型的接口类实例，通过反射来实例化 model
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        if (type != null) {
            Type[] types = type.getActualTypeArguments();
            try {
                mModel = (M) ((Class<?>) types[1]).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    public V getView() {
        return mProxyView;
    }

    protected M getModel() {
        return mModel;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void detach() {
        mReferenceView.clear();
        mReferenceView = null;
    }
}