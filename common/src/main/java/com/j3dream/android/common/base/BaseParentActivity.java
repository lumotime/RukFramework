package com.j3dream.android.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.collect.Maps;
import com.j3dream.android.common.R;
import com.j3dream.android.common.annotate.BindEventBus;
import com.j3dream.android.common.exception.InitContentViewException;
import com.j3dream.android.common.interf.IViewLoading;
import com.j3dream.android.common.interf.OnRequestPermissionsResultListener;
import com.j3dream.android.common.util.AndroidThreadPoolUtils;
import com.j3dream.android.common.util.DisplayUtils;
import com.j3dream.android.common.util.IntentUtils;
import com.j3dream.android.common.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>文件名称: BaseActivity </p>
 * <p>所属包名: com.bloodsport.lib.core.basic.activity</p>
 * <p>描述: 基础的活动 </p>
 * <p>创建时间: 2020/3/16 11:08 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BaseParentActivity extends AppCompatActivity implements IViewLoading {

    private static final int DEFAULT_REQUEST_PERMISSION_CODE = 11721;

    /**
     * 视图上下文持有
     */
    protected Context mContext;

    protected Activity mActivity;

    private final Map<Integer, OnRequestPermissionsResultListener> mRequestPermissionsResultListeners
            = Maps.newHashMap();

    /**
     * 是否需要注册EventBus
     */
    private boolean isReceiveEventBus = Boolean.FALSE;

    private int mLoadingViewLevel = 0;
    private Dialog mPagerLoadingDialog;

    /**
     * 设置当前布局资源
     * <p>
     * 原使用Object来接受当前的返回值，View类型使用概率太低，
     * 抛弃使用Object，使用int接收
     * </p>
     *
     * @return 设置的布局资源 只接受 'View, @LayoutRes int' 类型参数
     */
    protected abstract Object setLayoutRes();

    /**
     * 初始化活动相关数据
     */
    protected abstract void initData();

    /**
     * 初始化相关页面
     */
    protected abstract void initView();

    protected void initConfigs() {
        // 获取事件总线绑定许可
        isReceiveEventBus = isAllBindEventBusRequire();
        if (isReceiveEventBus) {
            // 事件总线许可通过, 进行绑定
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewRes(setLayoutRes());
        mContext = this;
        mActivity = this;
        initConfigs();
        bindViews();
        initData();
        initView();
    }

    /**
     * 绑定视图控件
     */
    protected void bindViews() {

    }

    @Override
    public void showLoading() {
        this.showLoading(false);
    }

    @Override
    public void showLoading(boolean cancelable) {
        if (AndroidThreadPoolUtils.isOnMainThread()) {
            if (mPagerLoadingDialog == null) {
                mPagerLoadingDialog = createLoadingDialog();
            }
            if (mPagerLoadingDialog.isShowing()) {
                mLoadingViewLevel++;
                return;
            }
            mPagerLoadingDialog.show();
            mPagerLoadingDialog.setCancelable(cancelable);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showLoading();
                }
            });
        }
    }

    @Override
    public void hideLoading() {
        if (AndroidThreadPoolUtils.isOnMainThread()) {
            if (mPagerLoadingDialog == null) {
                return;
            }
            if (mLoadingViewLevel > 0) {
                mLoadingViewLevel--;
                return;
            }
            mPagerLoadingDialog.dismiss();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideLoading();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {

        if (mPagerLoadingDialog != null && mPagerLoadingDialog.isShowing())
            mPagerLoadingDialog.dismiss();

        unBindEventBus();
        super.onDestroy();
    }

    /**
     * 创建 Loading Dialog
     *
     * @return loading Dialog
     */
    protected Dialog createLoadingDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .create();
        int dialogSideLength = DisplayUtils.getLongSideSize() / 8;
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setLayout(dialogSideLength, dialogSideLength);
        }
        alertDialog.setContentView(R.layout.widget_base_loading_activity);
        return alertDialog;
    }

    /**
     * 设置当前活动的布局资源
     *
     * @param layoutRes 布局资源
     */
    private void setContentViewRes(Object layoutRes) {
        if (layoutRes instanceof Integer) {
            setContentView((Integer) layoutRes);
        } else if (layoutRes instanceof View) {
            setContentView((View) layoutRes);
        } else {
            throw new InitContentViewException("don't support u commit layout resource type!!!");
        }
    }

    @SuppressWarnings("SameParameterValue")
    protected <T extends View> T $(@IdRes int viewId) {
        return findViewById(viewId);
    }

    /*
     * 判断是否符合EventBus注册要求
     * @return {true: 符合注册要求, false: 不符合注册要求}
     */
    private boolean isAllBindEventBusRequire() {
        Class<? extends BaseParentActivity> clazz = this.getClass();
        Method[] declaredMethods = clazz.getDeclaredMethods();
        if (!clazz.isAnnotationPresent(BindEventBus.class)) {
            return Boolean.FALSE;
        }
        for (Method declaredMethod : declaredMethods) {
            if (declaredMethod.isAnnotationPresent(Subscribe.class)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 检查是否拥有了相关权限
     *
     * @param permissions 检测的权限列表
     * @return 是否已经拥有了相关权限
     */
    protected boolean checkSelfPermission(String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(mContext, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    protected void requestPermissions(OnRequestPermissionsResultListener requestPermissionsResultListener,
                                      String... permissions) {
        requestPermissions(requestPermissionsResultListener, DEFAULT_REQUEST_PERMISSION_CODE, permissions);
    }

    protected void requestPermissions(OnRequestPermissionsResultListener requestPermissionsResultListener,
                                      int requestCode, String... permissions) {
        mRequestPermissionsResultListeners.put(requestCode, requestPermissionsResultListener);
        if (checkSelfPermission(permissions)) {
            requestPermissionsResultListener.onResult(requestCode, permissions, Boolean.TRUE);
        } else {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }

    /**
     * 跳转到应用的详情信息设置页面
     */
    protected void launchAppDetailsSettings() {
        Intent launchAppDetailsSettingsIntent = IntentUtils.getLaunchAppDetailsSettingsIntent();
        startActivity(launchAppDetailsSettingsIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean grant = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (mRequestPermissionsResultListeners.containsKey(requestCode)) {
            mRequestPermissionsResultListeners.get(requestCode)
                    .onResult(requestCode, permissions, grant);
        } else {
            if (grant)
                ToastUtils.show(R.string.grant_permission_message);
            else
                ToastUtils.show(R.string.not_grant_permission_message);
        }
    }

    private void unBindEventBus() {
        // 事件总线许可通过, 事件总线解绑
        boolean isRegistered = EventBus.getDefault().isRegistered(this);
        if (isRegistered) {
            EventBus.getDefault().removeAllStickyEvents();
            EventBus.getDefault().unregister(this);
        }
    }
}
