package com.j3dream.android.common.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.common.collect.Maps;
import com.j3dream.android.common.R;
import com.j3dream.android.common.annotate.BindEventBus;
import com.j3dream.android.common.exception.InitContentViewException;
import com.j3dream.android.common.interf.IViewLoading;
import com.j3dream.android.common.interf.OnRequestPermissionsResultListener;
import com.j3dream.android.common.log.Logger;
import com.j3dream.android.common.util.DisplayUtils;
import com.j3dream.android.common.util.IntentUtils;
import com.j3dream.android.common.util.ToastUtils;

import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * <p>文件名称: BaseFragment </p>
 * <p>所属包名: com.bloodsport.lib.core.base.fragment</p>
 * <p>描述: 基础的碎片活动 </p>
 * <p>创建时间: 2020/3/16 11:14 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public abstract class BaseParentDialogFragment extends DialogFragment implements IViewLoading {

    private static final int DEFAULT_REQUEST_PERMISSION_CODE = 11722;
    private final Map<Integer, OnRequestPermissionsResultListener> mRequestPermissionsResultListeners
            = Maps.newHashMap();
    @Nullable
    protected View mContentView;
    @Nullable
    protected Context mContext;
    private int mLoadingViewLevel = 0;
    private Dialog mPagerLoadingDialog;

    /**
     * 设置当前布局资源
     *
     * @return 设置的布局资源 只接受 'View, @LayoutRes int' 类型参数
     */
    protected abstract Object setLayoutRes(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化活动相关数据
     */
    protected abstract void initData();

    /**
     * 初始化相关页面
     */
    protected abstract void initView(@NonNull View rootView);

    /**
     * 初始化额外配置
     */
    protected void initConfigs(@NonNull View rootView) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            mContentView = getContentViewRes(setLayoutRes(inflater, container, savedInstanceState), inflater, container);
        } catch (InitContentViewException ex) {
            Logger.e(ex.getMessage());
            // 自身无法进行处理, 交给上层进行处理
            mContentView = super.onCreateView(inflater, container, savedInstanceState);
        }
        initConfigs(mContentView);
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化数据, 加载数据
        bindViews(mContentView);
        initData();
        initView(view);
    }

    @SuppressWarnings("ConstantConditions")
    protected <T extends View> T $(@IdRes int viewId) {
        return this.getView().findViewById(viewId);
    }

    /**
     * 绑定视图控件
     */
    protected void bindViews(View rootView) {

    }

    /**
     * 设置当前活动的布局资源
     *
     * @param layoutRes 布局资源
     */
    private View getContentViewRes(Object layoutRes, LayoutInflater inflater, ViewGroup container) {
        if (layoutRes instanceof Integer) {
            return inflater.inflate((Integer) layoutRes, container, false);
        } else if (layoutRes instanceof View) {
            return (View) layoutRes;
        } else {
            throw new InitContentViewException("don't support u commit layout resource type!!!");
        }
    }

    @Override
    public void showLoading() {
        this.showLoading(false);
    }

    @Override
    public void showLoading(boolean cancelable) {
        if (mPagerLoadingDialog == null) {
            mPagerLoadingDialog = createLoadingDialog();
        }
        if (mPagerLoadingDialog.isShowing()) {
            mLoadingViewLevel++;
            return;
        }
        mPagerLoadingDialog.show();
        mPagerLoadingDialog.setCancelable(cancelable);
    }

    @Override
    public void hideLoading() {
        if (mPagerLoadingDialog == null) {
            return;
        }
        if (mLoadingViewLevel > 0) {
            mLoadingViewLevel--;
            return;
        }
        mPagerLoadingDialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        if (mContentView != null) {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
            mContentView = null;
        }
        super.onDestroyView();
        if (mPagerLoadingDialog != null && mPagerLoadingDialog.isShowing()) {
            mPagerLoadingDialog.dismiss();
        }
    }

    /**
     * 创建 Loading Dialog
     *
     * @return loading Dialog
     */
    protected Dialog createLoadingDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
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
            requestPermissions(permissions, requestCode);
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

    /**
     * 获取内容View
     *
     * @param layoutRes   内容view
     * @param container   容器
     * @param attach2Root 是否绑定主视图
     * @return 内容View
     */
    private View getFragmentContentView(
            Object layoutRes,
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            boolean attach2Root
    ) {
        if (layoutRes instanceof View) {
            return (View) layoutRes;
        } else if (layoutRes instanceof Integer) {
            return inflater.inflate((Integer) layoutRes, container, attach2Root);
        } else {
            throw new InitContentViewException("don't support u commit layout resource type!!!");
        }
    }

    /**
     * 判断是否符合EventBus注册要求
     *
     * @return {true: 符合注册要求, false: 不符合注册要求}
     */
    private boolean isAllBindEventBusRequire() {
        Class<? extends BaseParentDialogFragment> clazz = this.getClass();
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
}
