package com.j3dream.android.common.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.AnimRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.j3dream.android.common.util.AndroidThreadPoolUtils;
import com.j3dream.android.common.util.Utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.j3dream.android.common.util.AppUtils.isAppForeground;

/**
 * <p>文件名称: ActivityManager </p>
 * <p>所属包名: com.jnft.controller.utils</p>
 * <p>描述: 应用的活动控制 </p>
 * <p>创建时间: 2020-02-18 11:44 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ActivityStackManager {

    /**
     * Return the activity by view.
     *
     * @param view The view.
     * @return the activity by view.
     */
    public static Activity getActivityByView(@NonNull View view) {
        return getActivityByContext(view.getContext());
    }

    /**
     * Return the activity by context.
     *
     * @param context The context.
     * @return the activity by context.
     */
    public static Activity getActivityByContext(Context context) {
        if (context instanceof Activity) return (Activity) context;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /**
     * Return whether the activity exists.
     *
     * @param pkg The name of the package.
     * @param cls The name of the class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExists(@NonNull final String pkg,
                                           @NonNull final String cls) {
        Intent intent = new Intent();
        intent.setClassName(pkg, cls);
        return !(Utils.getApp().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(Utils.getApp().getPackageManager()) == null ||
                Utils.getApp().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * Start the activity.
     *
     * @param clz The activity class.
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz) {
        Context context = Utils.getTopActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(), null);
    }

    /**
     * Start the activity.
     *
     * @param clz     The activity class.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz,
                                     @Nullable final Bundle options) {
        Context context = Utils.getTopActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(), options);
    }

    /**
     * Start the activity.
     *
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Class<? extends Activity> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = Utils.getTopActivityOrApp();
        startActivity(context, null, context.getPackageName(), clz.getName(),
                getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param clz      The activity class.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param clz      The activity class.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     @Nullable final Bundle options) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(), options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     final View... sharedElements) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity  The activity.
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param extras The Bundle of extras to add to this intent.
     * @param clz    The activity class.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<? extends Activity> clz) {
        Context context = Utils.getTopActivityOrApp();
        startActivity(context, extras, context.getPackageName(), clz.getName(), null);
    }

    /**
     * Start the activity.
     *
     * @param extras  The Bundle of extras to add to this intent.
     * @param clz     The activity class.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<? extends Activity> clz,
                                     @Nullable final Bundle options) {
        Context context = Utils.getTopActivityOrApp();
        startActivity(context, extras, context.getPackageName(), clz.getName(), options);
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Class<? extends Activity> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = Utils.getTopActivityOrApp();
        startActivity(context, extras, context.getPackageName(), clz.getName(),
                getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity The activity.
     * @param clz      The activity class.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(), null);
    }

    /**
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity The activity.
     * @param clz      The activity class.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     @Nullable final Bundle options) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(), options);
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     final View... sharedElements) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param activity  The activity.
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final Class<? extends Activity> clz,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, extras, activity.getPackageName(), clz.getName(),
                getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param pkg The name of the package.
     * @param cls The name of the class.
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(Utils.getTopActivityOrApp(), null, pkg, cls, null);
    }

    /**
     * Start the activity.
     *
     * @param pkg     The name of the package.
     * @param cls     The name of the class.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(Utils.getTopActivityOrApp(), null, pkg, cls, options);
    }

    /**
     * Start the activity.
     *
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = Utils.getTopActivityOrApp();
        startActivity(context, null, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, null, pkg, cls, null);
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(activity, null, pkg, cls, options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     final View... sharedElements) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity  The activity.
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, null, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param extras The Bundle of extras to add to this intent.
     * @param pkg    The name of the package.
     * @param cls    The name of the class.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(Utils.getTopActivityOrApp(), extras, pkg, cls, null);
    }

    /**
     * Start the activity.
     *
     * @param extras  The Bundle of extras to add to this intent.
     * @param pkg     The name of the package.
     * @param cls     The name of the class.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(Utils.getTopActivityOrApp(), extras, pkg, cls, options);
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        Context context = Utils.getTopActivityOrApp();
        startActivity(context, extras, pkg, cls, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param extras   The Bundle of extras to add to this intent.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls) {
        startActivity(activity, extras, pkg, cls, null);
    }

    /**
     * Start the activity.
     *
     * @param extras   The Bundle of extras to add to this intent.
     * @param activity The activity.
     * @param pkg      The name of the package.
     * @param cls      The name of the class.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @Nullable final Bundle options) {
        startActivity(activity, extras, pkg, cls, options);
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     final View... sharedElements) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param extras    The Bundle of extras to add to this intent.
     * @param pkg       The name of the package.
     * @param cls       The name of the class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     * @param activity  activity
     */
    public static void startActivity(@NonNull final Bundle extras,
                                     @NonNull final Activity activity,
                                     @NonNull final String pkg,
                                     @NonNull final String cls,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(activity, extras, pkg, cls, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param intent The description of the activity to start.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean startActivity(@NonNull final Intent intent) {
        return startActivity(intent, Utils.getTopActivityOrApp(), null);
    }

    /**
     * Start the activity.
     *
     * @param intent  The description of the activity to start.
     * @param options Additional options for how the Activity should be started.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean startActivity(@NonNull final Intent intent,
                                        @Nullable final Bundle options) {
        return startActivity(intent, Utils.getTopActivityOrApp(), options);
    }

    /**
     * Start the activity.
     *
     * @param intent    The description of the activity to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean startActivity(@NonNull final Intent intent,
                                        @AnimRes final int enterAnim,
                                        @AnimRes final int exitAnim) {
        Context context = Utils.getTopActivityOrApp();
        boolean isSuccess = startActivity(intent, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (isSuccess) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
                ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
            }
        }
        return isSuccess;
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param intent   The description of the activity to start.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent) {
        startActivity(intent, activity, null);
    }

    /**
     * Start the activity.
     *
     * @param activity The activity.
     * @param intent   The description of the activity to start.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @Nullable final Bundle options) {
        startActivity(intent, activity, options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param intent         The description of the activity to start.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     final View... sharedElements) {
        startActivity(intent, activity, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity  The activity.
     * @param intent    The description of the activity to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivity(@NonNull final Activity activity,
                                     @NonNull final Intent intent,
                                     @AnimRes final int enterAnim,
                                     @AnimRes final int exitAnim) {
        startActivity(intent, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * Start the activity.
     *
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, null, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, null);
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, options);
    }

    /**
     * Start the activity.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param clz            The activity class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param clz         The activity class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final Class<? extends Activity> clz,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, extras, activity.getPackageName(), clz.getName(),
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param extras      The Bundle of extras to add to this intent.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode) {
        startActivityForResult(activity, extras, pkg, cls, requestCode, null);
    }

    /**
     * Start the activity for result.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param activity    The activity.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(activity, extras, pkg, cls, requestCode, options);
    }

    /**
     * Start the activity for result.
     *
     * @param extras         The Bundle of extras to add to this intent.
     * @param activity       The activity.
     * @param pkg            The name of the package.
     * @param cls            The name of the class.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(activity, extras, pkg, cls,
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity for result.
     *
     * @param extras      The Bundle of extras to add to this intent.
     * @param pkg         The name of the package.
     * @param cls         The name of the class.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Bundle extras,
                                              @NonNull final Activity activity,
                                              @NonNull final String pkg,
                                              @NonNull final String cls,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(activity, extras, pkg, cls,
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode) {
        startActivityForResult(intent, activity, requestCode, null);
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param options     Additional options for how the Activity should be started.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @Nullable final Bundle options) {
        startActivityForResult(intent, activity, requestCode, options);
    }

    /**
     * Start the activity for result.
     *
     * @param activity       The activity.
     * @param intent         The description of the activity to start.
     * @param requestCode    if &gt;= 0, this code will be returned in
     *                       onActivityResult() when the activity exits.
     * @param sharedElements The names of the shared elements to transfer to the called
     *                       Activity and their associated Views.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              final View... sharedElements) {
        startActivityForResult(intent, activity,
                requestCode, getOptionsBundle(activity, sharedElements));
    }

    /**
     * Start the activity for result.
     *
     * @param activity    The activity.
     * @param intent      The description of the activity to start.
     * @param requestCode if &gt;= 0, this code will be returned in
     *                    onActivityResult() when the activity exits.
     * @param enterAnim   A resource ID of the animation resource to use for the
     *                    incoming activity.
     * @param exitAnim    A resource ID of the animation resource to use for the
     *                    outgoing activity.
     */
    public static void startActivityForResult(@NonNull final Activity activity,
                                              @NonNull final Intent intent,
                                              final int requestCode,
                                              @AnimRes final int enterAnim,
                                              @AnimRes final int exitAnim) {
        startActivityForResult(intent, activity,
                requestCode, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start activities.
     *
     * @param intents The descriptions of the activities to start.
     */
    public static void startActivities(@NonNull final Intent[] intents) {
        startActivities(intents, Utils.getTopActivityOrApp(), null);
    }

    /**
     * Start activities.
     *
     * @param intents The descriptions of the activities to start.
     * @param options Additional options for how the Activity should be started.
     */
    public static void startActivities(@NonNull final Intent[] intents,
                                       @Nullable final Bundle options) {
        startActivities(intents, Utils.getTopActivityOrApp(), options);
    }

    /**
     * Start activities.
     *
     * @param intents   The descriptions of the activities to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivities(@NonNull final Intent[] intents,
                                       @AnimRes final int enterAnim,
                                       @AnimRes final int exitAnim) {
        Context context = Utils.getTopActivityOrApp();
        startActivities(intents, context, getOptionsBundle(context, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN && context instanceof Activity) {
            ((Activity) context).overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start activities.
     *
     * @param activity The activity.
     * @param intents  The descriptions of the activities to start.
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents) {
        startActivities(intents, activity, null);
    }

    /**
     * Start activities.
     *
     * @param activity The activity.
     * @param intents  The descriptions of the activities to start.
     * @param options  Additional options for how the Activity should be started.
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents,
                                       @Nullable final Bundle options) {
        startActivities(intents, activity, options);
    }

    /**
     * Start activities.
     *
     * @param activity  The activity.
     * @param intents   The descriptions of the activities to start.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void startActivities(@NonNull final Activity activity,
                                       @NonNull final Intent[] intents,
                                       @AnimRes final int enterAnim,
                                       @AnimRes final int exitAnim) {
        startActivities(intents, activity, getOptionsBundle(activity, enterAnim, exitAnim));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Start home activity.
     */
    public static void startHomeActivity() throws SecurityException {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(homeIntent);
    }

    /**
     * Start the launcher activity.
     */
    public static void startLauncherActivity() {
        startLauncherActivity(Utils.getApp().getPackageName());
    }

    /**
     * Start the launcher activity.
     *
     * @param pkg The name of the package.
     */
    public static void startLauncherActivity(@NonNull final String pkg) {
        startActivity(pkg, getLauncherActivity(pkg));
    }

    /**
     * Return the list of activity.
     *
     * @return the list of activity
     */
    public static List<Activity> getActivityList() {
        return Utils.getActivityList();
    }

    /**
     * Return the name of launcher activity.
     *
     * @return the name of launcher activity
     */
    public static String getLauncherActivity() {
        return getLauncherActivity(Utils.getApp().getPackageName());
    }

    /**
     * Return the name of launcher activity.
     *
     * @param pkg The name of the package.
     * @return the name of launcher activity
     */
    public static String getLauncherActivity(@NonNull final String pkg) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(pkg);
        PackageManager pm = Utils.getApp().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        ResolveInfo next = info.iterator().next();
        if (next != null) {
            return next.activityInfo.name;
        }
        return "no launcher activity of " + pkg;
    }

    /**
     * Return the top activity in activity's stack.
     *
     * @return the top activity in activity's stack
     */
    public static Activity getTopActivity() {
        return Utils.getTopActivity();
    }

    /**
     * Return whether the activity is alive.
     *
     * @param context The context.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityAlive(final Context context) {
        return isActivityAlive(getActivityByContext(context));
    }

    /**
     * Return whether the activity is alive.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityAlive(final Activity activity) {
        return activity != null && !activity.isFinishing()
                && (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1 || !activity.isDestroyed());
    }

    /**
     * Return whether the activity exists in activity's stack.
     *
     * @param activity The activity.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExistsInStack(@NonNull final Activity activity) {
        List<Activity> activities = Utils.getActivityList();
        for (Activity aActivity : activities) {
            if (aActivity.equals(activity)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return whether the activity exists in activity's stack.
     *
     * @param clz The activity class.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isActivityExistsInStack(@NonNull final Class<? extends Activity> clz) {
        List<Activity> activities = Utils.getActivityList();
        for (Activity aActivity : activities) {
            if (aActivity.getClass().equals(clz)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finish the activity.
     *
     * @param activity The activity.
     */
    public static void finishActivity(@NonNull final Activity activity) {
        finishActivity(activity, false);
    }

    /**
     * Finish the activity.
     *
     * @param activity   The activity.
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishActivity(@NonNull final Activity activity, final boolean isLoadAnim) {
        activity.finish();
        if (!isLoadAnim) {
            activity.overridePendingTransition(0, 0);
        }
    }

    /**
     * Finish the activity.
     *
     * @param activity  The activity.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void finishActivity(@NonNull final Activity activity,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        activity.finish();
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * Finish the activity.
     *
     * @param clz The activity class.
     */
    public static void finishActivity(@NonNull final Class<? extends Activity> clz) {
        finishActivity(clz, false);
    }

    /**
     * Finish the activity.
     *
     * @param clz        The activity class.
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishActivity(@NonNull final Class<? extends Activity> clz,
                                      final boolean isLoadAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                if (!isLoadAnim) {
                    activity.overridePendingTransition(0, 0);
                }
            }
        }
    }

    /**
     * Finish the activity.
     *
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void finishActivity(@NonNull final Class<? extends Activity> clz,
                                      @AnimRes final int enterAnim,
                                      @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (Activity activity : activities) {
            if (activity.getClass().equals(clz)) {
                activity.finish();
                activity.overridePendingTransition(enterAnim, exitAnim);
            }
        }
    }

    /**
     * Finish to the activity.
     *
     * @param activity      The activity.
     * @param isIncludeSelf True to include the activity, false otherwise.
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf) {
        return finishToActivity(activity, isIncludeSelf, false);
    }

    /**
     * Finish to the activity.
     *
     * @param activity      The activity.
     * @param isIncludeSelf True to include the activity, false otherwise.
     * @param isLoadAnim    True to use animation for the outgoing activity, false otherwise.
     * @return result
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, isLoadAnim);
                }
                return true;
            }
            finishActivity(aActivity, isLoadAnim);
        }
        return false;
    }

    /**
     * Finish to the activity.
     *
     * @param activity      The activity.
     * @param isIncludeSelf True to include the activity, false otherwise.
     * @param enterAnim     A resource ID of the animation resource to use for the
     *                      incoming activity.
     * @param exitAnim      A resource ID of the animation resource to use for the
     *                      outgoing activity.
     * @return execute result
     */
    public static boolean finishToActivity(@NonNull final Activity activity,
                                           final boolean isIncludeSelf,
                                           @AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.equals(activity)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, enterAnim, exitAnim);
                }
                return true;
            }
            finishActivity(aActivity, enterAnim, exitAnim);
        }
        return false;
    }

    /**
     * Finish to the activity.
     *
     * @param clz           The activity class.
     * @param isIncludeSelf True to include the activity, false otherwise.
     * @return execute result
     */
    public static boolean finishToActivity(@NonNull final Class<? extends Activity> clz,
                                           final boolean isIncludeSelf) {
        return finishToActivity(clz, isIncludeSelf, false);
    }

    /**
     * Finish to the activity.
     *
     * @param clz           The activity class.
     * @param isIncludeSelf True to include the activity, false otherwise.
     * @param isLoadAnim    True to use animation for the outgoing activity, false otherwise.
     * @return execute result
     */
    public static boolean finishToActivity(@NonNull final Class<? extends Activity> clz,
                                           final boolean isIncludeSelf,
                                           final boolean isLoadAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, isLoadAnim);
                }
                return true;
            }
            finishActivity(aActivity, isLoadAnim);
        }
        return false;
    }

    /**
     * Finish to the activity.
     *
     * @param clz           The activity class.
     * @param isIncludeSelf True to include the activity, false otherwise.
     * @param enterAnim     A resource ID of the animation resource to use for the
     *                      incoming activity.
     * @param exitAnim      A resource ID of the animation resource to use for the
     *                      outgoing activity.
     * @return execute result
     */
    public static boolean finishToActivity(@NonNull final Class<? extends Activity> clz,
                                           final boolean isIncludeSelf,
                                           @AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 1; i >= 0; --i) {
            Activity aActivity = activities.get(i);
            if (aActivity.getClass().equals(clz)) {
                if (isIncludeSelf) {
                    finishActivity(aActivity, enterAnim, exitAnim);
                }
                return true;
            }
            finishActivity(aActivity, enterAnim, exitAnim);
        }
        return false;
    }

    /**
     * Finish the activities whose type not equals the activity class.
     *
     * @param clz The activity class.
     */
    public static void finishOtherActivities(@NonNull final Class<? extends Activity> clz) {
        finishOtherActivities(clz, false);
    }


    /**
     * Finish the activities whose type not equals the activity class.
     *
     * @param clz        The activity class.
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishOtherActivities(@NonNull final Class<? extends Activity> clz,
                                             final boolean isLoadAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (!activity.getClass().equals(clz)) {
                finishActivity(activity, isLoadAnim);
            }
        }
    }

    /**
     * Finish the activities whose type not equals the activity class.
     *
     * @param clz       The activity class.
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void finishOtherActivities(@NonNull final Class<? extends Activity> clz,
                                             @AnimRes final int enterAnim,
                                             @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (!activity.getClass().equals(clz)) {
                finishActivity(activity, enterAnim, exitAnim);
            }
        }
    }

    /**
     * Finish all of activities.
     */
    public static void finishAllActivities() {
        finishAllActivities(false);
    }

    /**
     * Finish all of activities.
     *
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishAllActivities(final boolean isLoadAnim) {
        List<Activity> activityList = Utils.getActivityList();
        for (int i = activityList.size() - 1; i >= 0; --i) {// remove from top
            Activity activity = activityList.get(i);
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish();
            if (!isLoadAnim) {
                activity.overridePendingTransition(0, 0);
            }
        }
    }

    /**
     * Finish all of activities.
     *
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void finishAllActivities(@AnimRes final int enterAnim,
                                           @AnimRes final int exitAnim) {
        List<Activity> activityList = Utils.getActivityList();
        for (int i = activityList.size() - 1; i >= 0; --i) {// remove from top
            Activity activity = activityList.get(i);
            // sActivityList remove the index activity at onActivityDestroyed
            activity.finish();
            activity.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * Finish all of activities except the newest activity.
     */
    public static void finishAllActivitiesExceptNewest() {
        finishAllActivitiesExceptNewest(false);
    }

    /**
     * Finish all of activities except the newest activity.
     *
     * @param isLoadAnim True to use animation for the outgoing activity, false otherwise.
     */
    public static void finishAllActivitiesExceptNewest(final boolean isLoadAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 2; i >= 0; i--) {
            finishActivity(activities.get(i), isLoadAnim);
        }
    }

    /**
     * Finish all of activities except the newest activity.
     *
     * @param enterAnim A resource ID of the animation resource to use for the
     *                  incoming activity.
     * @param exitAnim  A resource ID of the animation resource to use for the
     *                  outgoing activity.
     */
    public static void finishAllActivitiesExceptNewest(@AnimRes final int enterAnim,
                                                       @AnimRes final int exitAnim) {
        List<Activity> activities = Utils.getActivityList();
        for (int i = activities.size() - 2; i >= 0; i--) {
            finishActivity(activities.get(i), enterAnim, exitAnim);
        }
    }

    /**
     * Return the icon of activity.
     *
     * @param activity The activity.
     * @return the icon of activity
     */
    public static Drawable getActivityIcon(@NonNull final Activity activity) {
        return getActivityIcon(activity.getComponentName());
    }

    /**
     * Return the icon of activity.
     *
     * @param clz The activity class.
     * @return the icon of activity
     */
    public static Drawable getActivityIcon(@NonNull final Class<? extends Activity> clz) {
        return getActivityIcon(new ComponentName(Utils.getApp(), clz));
    }

    /**
     * Return the icon of activity.
     *
     * @param activityName The name of activity.
     * @return the icon of activity
     */
    public static Drawable getActivityIcon(@NonNull final ComponentName activityName) {
        PackageManager pm = Utils.getApp().getPackageManager();
        try {
            return pm.getActivityIcon(activityName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return the logo of activity.
     *
     * @param activity The activity.
     * @return the logo of activity
     */
    public static Drawable getActivityLogo(@NonNull final Activity activity) {
        return getActivityLogo(activity.getComponentName());
    }

    /**
     * Return the logo of activity.
     *
     * @param clz The activity class.
     * @return the logo of activity
     */
    public static Drawable getActivityLogo(@NonNull final Class<? extends Activity> clz) {
        return getActivityLogo(new ComponentName(Utils.getApp(), clz));
    }

    /**
     * Return the logo of activity.
     *
     * @param activityName The name of activity.
     * @return the logo of activity
     */
    public static Drawable getActivityLogo(@NonNull final ComponentName activityName) {
        PackageManager pm = Utils.getApp().getPackageManager();
        try {
            return pm.getActivityLogo(activityName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void startActivity(final Context context,
                                      final Bundle extras,
                                      final String pkg,
                                      final String cls,
                                      @Nullable final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        startActivity(intent, context, options);
    }

    private static boolean startActivity(final Intent intent,
                                         final Context context,
                                         final Bundle options) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent, options);
        } else {
            context.startActivity(intent);
        }
        return true;
    }

    private static boolean isIntentAvailable(final Intent intent) {
        return Utils.getApp()
                .getPackageManager()
                .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                .size() > 0;
    }

    private static boolean startActivityForResult(final Activity activity,
                                                  final Bundle extras,
                                                  final String pkg,
                                                  final String cls,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        return startActivityForResult(intent, activity, requestCode, options);
    }

    private static boolean startActivityForResult(final Intent intent,
                                                  final Activity activity,
                                                  final int requestCode,
                                                  @Nullable final Bundle options) {
        if (!isIntentAvailable(intent)) {
            Log.e("ActivityUtils", "intent is unavailable");
            return false;
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            activity.startActivityForResult(intent, requestCode, options);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
        return true;
    }

    private static void startActivities(final Intent[] intents,
                                        final Context context,
                                        @Nullable final Bundle options) {
        if (!(context instanceof Activity)) {
            for (Intent intent : intents) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        }
        if (options != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivities(intents, options);
        } else {
            context.startActivities(intents);
        }
    }

    private static Bundle getOptionsBundle(final Context context,
                                           final int enterAnim,
                                           final int exitAnim) {
        return ActivityOptionsCompat.makeCustomAnimation(context, enterAnim, exitAnim).toBundle();
    }

    private static Bundle getOptionsBundle(final Activity activity,
                                           final View[] sharedElements) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return null;
        if (sharedElements == null) return null;
        int len = sharedElements.length;
        if (len <= 0) return null;
        @SuppressWarnings("unchecked")
        Pair<View, String>[] pairs = new Pair[len];
        for (int i = 0; i < len; i++) {
            pairs[i] = Pair.create(sharedElements[i], sharedElements[i].getTransitionName());
        }
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs).toBundle();
    }

    public static class ActivityCallbacks implements Application.ActivityLifecycleCallbacks {

        final LinkedList<Activity> mActivityList = new LinkedList<>();

        private int mForegroundCount = 0;
        private int mConfigCount = 0;
        private boolean mIsBackground = false;

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (!mIsBackground) {
                setTopActivity(activity);
            }
            if (mConfigCount < 0) {
                ++mConfigCount;
            } else {
                ++mForegroundCount;
            }
        }

        @Override
        public void onActivityResumed(final Activity activity) {
            setTopActivity(activity);
            if (mIsBackground) {
                mIsBackground = false;
            }
            processHideSoftInputOnActivityDestroy(activity, false);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (activity.isChangingConfigurations()) {
                --mConfigCount;
            } else {
                --mForegroundCount;
                if (mForegroundCount <= 0) {
                    mIsBackground = true;
                }
            }
            processHideSoftInputOnActivityDestroy(activity, true);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {/**/}

        @Override
        public void onActivityDestroyed(Activity activity) {
            mActivityList.remove(activity);
        }

        public Activity getTopActivity() {
            if (!mActivityList.isEmpty()) {
                for (int i = mActivityList.size() - 1; i >= 0; i--) {
                    Activity activity = mActivityList.get(i);
                    if (activity == null
                            || activity.isFinishing()
                            || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())) {
                        continue;
                    }
                    return activity;
                }
            }
            Activity topActivityByReflect = getTopActivityByReflect();
            if (topActivityByReflect != null) {
                setTopActivity(topActivityByReflect);
            }
            return topActivityByReflect;
        }

        private void setTopActivity(final Activity activity) {
            if (mActivityList.contains(activity)) {
                if (!mActivityList.getLast().equals(activity)) {
                    mActivityList.remove(activity);
                    mActivityList.addLast(activity);
                }
            } else {
                mActivityList.addLast(activity);
            }
        }

        /**
         * To solve close keyboard when activity onDestroy.
         * The preActivity set windowSoftInputMode will prevent
         * the keyboard from closing when curActivity onDestroy.
         */
        private void processHideSoftInputOnActivityDestroy(final Activity activity, boolean isSave) {
            if (isSave) {
                final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
                final int softInputMode = attrs.softInputMode;
                activity.getWindow().getDecorView().setTag(-123, softInputMode);
                activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            } else {
                final Object tag = activity.getWindow().getDecorView().getTag(-123);
                if (!(tag instanceof Integer)) return;
                AndroidThreadPoolUtils.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.getWindow().setSoftInputMode(((Integer) tag));
                    }
                }, 100);
            }
        }

        private Activity getTopActivityByReflect() {
            try {
                @SuppressLint("PrivateApi")
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Object currentActivityThreadMethod = activityThreadClass.getMethod("currentActivityThread").invoke(null);
                Field mActivityListField = activityThreadClass.getDeclaredField("mActivityList");
                mActivityListField.setAccessible(true);
                Map activities = (Map) mActivityListField.get(currentActivityThreadMethod);
                if (activities == null) return null;
                for (Object activityRecord : activities.values()) {
                    Class activityRecordClass = activityRecord.getClass();
                    Field pausedField = activityRecordClass.getDeclaredField("paused");
                    pausedField.setAccessible(true);
                    if (!pausedField.getBoolean(activityRecord)) {
                        Field activityField = activityRecordClass.getDeclaredField("activity");
                        activityField.setAccessible(true);
                        return (Activity) activityField.get(activityRecord);
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        }

        public Context getTopActivityOrApp() {
            if (isAppForeground()) {
                Activity topActivity = mActivityList.getFirst();
                return topActivity == null ? Utils.getApp() : topActivity;
            } else {
                return Utils.getApp();
            }
        }

        public List<Activity> getActivityList() {
            return mActivityList;
        }
    }

    private static final class ActivityManagerHolder {
        static final ActivityStackManager HOLDER = new ActivityStackManager();
    }
}
