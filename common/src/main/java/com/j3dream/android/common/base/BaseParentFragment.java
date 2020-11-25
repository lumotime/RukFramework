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
import androidx.fragment.app.Fragment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.j3dream.android.common.R;
import com.j3dream.android.common.annotate.BindEventBus;
import com.j3dream.android.common.exception.InitContentViewException;
import com.j3dream.android.common.interf.IViewLoading;
import com.j3dream.android.common.interf.OnRequestPermissionsResultListener;
import com.j3dream.android.common.util.DisplayUtils;
import com.j3dream.android.common.util.IntentUtils;
import com.j3dream.android.common.util.ToastUtils;
import com.j3dream.core.util.ObjectUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * <p>文件名称: BaseFragment </p>
 * <p>所属包名: com.bloodsport.lib.core.base.fragment</p>
 * <p>描述: 基础的碎片活动 </p>
 * <p>创建时间: 2020/3/16 11:14 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public abstract class BaseParentFragment extends Fragment implements IViewLoading {

    private static final int DEFAULT_REQUEST_PERMISSION_CODE = 11722;

    @Nullable
    protected View mContentView;
    @Nullable
    protected Context mContext;

    /**
     * 是否需要注册EventBus
     */
    private boolean isReceiveEventBus = Boolean.FALSE;
    private final Map<Integer, OnRequestPermissionsResultListener> mRequestPermissionsResultListeners
            = Maps.newHashMap();

    /**
     * 信息提示的Dialog
     */
    private AlertDialog.Builder mBaseAlertDialog;

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
    protected abstract void initConfigs(@NonNull View rootView);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = getContentViewRes(setLayoutRes(inflater, container, savedInstanceState), inflater, container);
        initConfigs(mContentView);
        bindViews(mContentView);
        initData();
        initView(mContentView);
        return mContentView;
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

    /**
     * loading dialogs
     */
    protected List<Dialog> mLoadingDialogs = Lists.newArrayList();

    @Override
    public void showLoading() {
        Dialog targetDialog;
        if (ObjectUtils.isEmpty(mLoadingDialogs)) {
            targetDialog = new AlertDialog.Builder(getContext())
                    .create();
            if (mLoadingDialogs == null) {
                mLoadingDialogs = Lists.newArrayList();
            }
            mLoadingDialogs.add(targetDialog);
        } else {
            targetDialog = mLoadingDialogs.get(0);
        }

        if (targetDialog != null) {
            targetDialog.show();
            int dialogSideLength = DisplayUtils.getLongSideSize() / 8;
            Window window = targetDialog.getWindow();
            if (window != null) {
                window.setLayout(dialogSideLength, dialogSideLength);
            }
            targetDialog.setContentView(R.layout.widget_base_loading_activity);
        }
    }

    @Override
    public void hideLoading() {
        for (Dialog mLoadingDialog : mLoadingDialogs) {
            mLoadingDialog.cancel();
        }
        if (ObjectUtils.isNotEmpty(mLoadingDialogs)) {
            Dialog dialog = mLoadingDialogs.get(0);
            mLoadingDialogs.clear();
            mLoadingDialogs.add(dialog);
        }
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
        for (Dialog mLoadingDialog : mLoadingDialogs) {
            mLoadingDialog.cancel();
        }
        mLoadingDialogs.clear();
        if (mBaseAlertDialog != null) {
            mBaseAlertDialog = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        unBindEventBus();
        super.onDestroy();
    }

    /**
     * 获取基础的弹出层
     *
     * @return 基础的弹出层
     */
    protected androidx.appcompat.app.AlertDialog.Builder getBaseAlertDialog() {
        if (mBaseAlertDialog == null) {
            mBaseAlertDialog = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        }
        return mBaseAlertDialog;
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
        Class<? extends BaseParentFragment> clazz = this.getClass();
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

    private void bindEventBus() {
        // 获取事件总线绑定许可
        isReceiveEventBus = isAllBindEventBusRequire();
        boolean isRegistered = EventBus.getDefault().isRegistered(this);
        if (isReceiveEventBus && !isRegistered) {
            // 事件总线许可通过, 进行绑定
            EventBus.getDefault().register(this);
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
