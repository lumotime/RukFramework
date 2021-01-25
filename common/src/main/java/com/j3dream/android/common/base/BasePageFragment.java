package com.j3dream.android.common.base;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * <p>文件名称: BasePageFragment </p>
 * <p>所属包名: com.j3dream.android.common.base</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/25 09:06 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BasePageFragment extends BaseParentFragment implements BasePageOwner {

    private BasePageProxy mBasePageProxy = new BasePageProxy(this);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mBasePageProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasePageProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBasePageProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mBasePageProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBasePageProxy.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBasePageProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mBasePageProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mBasePageProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLazyBeforeView() {

    }

    @Override
    public void onLazyAfterView() {

    }

    @Override
    public void onVisible() {

    }

    @Override
    public void onInvisible() {

    }
}
