package com.j3dream.android.common.arch.proxy;

import com.j3dream.android.common.arch.IBaseView;
import com.j3dream.android.common.arch.impl.BasePresenter;
import com.j3dream.android.common.arch.inject.InjectPresenter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>文件名称: ProxyImpl </p>
 * <p>所属包名: cn.novakj.j3.arch.mvp.proxy</p>
 * <p>描述: 代理实现 </p>
 * <p>创建时间: 2020/4/24 11:18 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ProxyImpl implements IProxy {

    private IBaseView mView;
    private List<BasePresenter> mInjectPresenters;

    public ProxyImpl(IBaseView view) {
        this.mView = view;
        mInjectPresenters = new ArrayList<>();
    }

    @Override
    public void bindPresenter() {
        //获得已经申明的变量，包括私有的
        Field[] fields = mView.getClass().getDeclaredFields();
        for (Field field : fields) {
            //获取变量上面的注解类型
            InjectPresenter injectPresenter = field.getAnnotation(InjectPresenter.class);
            if (injectPresenter != null) {
                try {
                    Class<? extends BasePresenter> type = (Class<? extends BasePresenter>) field.getType();
                    BasePresenter mInjectPresenter = type.newInstance();
                    mInjectPresenter.attach(mView);
                    field.setAccessible(true);
                    field.set(mView, mInjectPresenter);
                    mInjectPresenters.add(mInjectPresenter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    throw new RuntimeException("SubClass must extends Class:BasePresenter");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unBindPresenter() {
        // 代理解绑操作，避免内存泄漏
        if (mInjectPresenters != null) {
            for (BasePresenter presenter : mInjectPresenters) {
                presenter.detach();
            }
            mInjectPresenters.clear();
        }
        mInjectPresenters = null;
    }
}
