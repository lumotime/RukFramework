package com.j3dream.android.idreader;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.Nullable;

import com.j3dream.android.common.util.AndroidSystemUtils;
import com.j3dream.android.common.util.IntentUtils;
import com.j3dream.android.idreader.util.DriverExtractHelper;
import com.j3dream.core.util.ObjectUtils;
import com.j3dream.core.util.StringUtils;
import com.j3dream.core.util.executor.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>文件名称: ReadCardDriver </p>
 * <p>所属包名: com.jnft.controller.driver.ams</p>
 * <p>描述: 读卡启动器, 主要用于开启读卡服务的相关应用 </p>
 * <p>创建时间: 2020-02-10 19:16 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ReadCardDriver implements IReadCardDriver {

    private static final String TAG = "ReadCardDriver";

    private static final long DELAY_START_READER_SEND_BROADCAST = 500;

    public static ReadCardDriver getInstance() {
        return ReadCardDriverHolder.HOLDER;
    }

    @Override
    public void startReadingCardInfo(final Context context, @Nullable String packagePath) {
        if (ObjectUtils.isEmpty(context)) {
            return;
        }

        try {
            // 开启读卡服务
            for (Intent intent : getSafetyPackageStartReaderIntent(packagePath)) {
                if (checkAppPackage(context, intent.getPackage())) {
                    context.getApplicationContext().startService(intent);
                }
            }

            // 开始读卡广播
            ThreadPoolUtils.runInBackground(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(DELAY_START_READER_SEND_BROADCAST);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Intent intent = new Intent(ReadCardConstant.BROADCAST_READ_CARD_START_READ);
                        context.getApplicationContext().sendBroadcast(intent);
                        Log.i(TAG, "开启读卡服务成功; info:" + intent.toString());
                    }
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "开启读卡服务失败", ex);
        }
    }

    @Override
    public void stopReadingCardInfo(Context mContext, @Nullable String packagePath) {
        Intent intent;
        if (ObjectUtils.isEmpty(mContext)) {
            return;
        }

        try {
            // 关闭读卡广播
            intent = new Intent(ReadCardConstant.BROADCAST_READ_CARD_STOP_READ);
            mContext.getApplicationContext().sendBroadcast(intent);
            for (Intent tmpIntent : getSafetyPackageStartReaderIntent(packagePath)) {
                if (checkAppPackage(mContext, intent.getPackage())) {
                    mContext.getApplicationContext().stopService(tmpIntent);
                }
            }
            Log.i(TAG, "关闭读卡服务成功; info:" + intent.toString());
        } catch (Exception ex) {
            Log.e(TAG, "关闭读卡服务失败;", ex);
        }
    }

    private List<Intent> getSafetyPackageStartReaderIntent(String packagePath) {
        List<Intent> startReaderIntent = new ArrayList<>();
        if (StringUtils.isNotEmpty(packagePath)) {
            Intent intent = IntentUtils.newIntent();
            intent.setAction(ReadCardConstant.PACKAGE_CYBERTECH_IDCARD);
            intent.setPackage(packagePath);
            startReaderIntent.add(intent);
        } else {
            String systemModel = AndroidSystemUtils.getSystemModel();
            Set<String> safetyPackage = DriverExtractHelper.extractSafetyPackage(systemModel);
            for (String packageStr : safetyPackage) {
                Intent intent = IntentUtils.newIntent();
                intent.setAction(ReadCardConstant.PACKAGE_CYBERTECH_IDCARD);
                intent.setPackage(packageStr);
                startReaderIntent.add(intent);
            }
        }
        return startReaderIntent;
    }

    private boolean checkAppPackage(Context context, String packageStr) {
        if (StringUtils.isEmpty(packageStr)) {
            return false;
        }
        try {
            context.getPackageManager()
                    .getApplicationInfo(packageStr, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private static final class ReadCardDriverHolder {
        static final ReadCardDriver HOLDER = new ReadCardDriver();
    }
}
