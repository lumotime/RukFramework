package com.j3dream.android.idreader;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * <p>文件名称: ReadIdCardServiceTools </p>
 * <p>所属包名: cn.ftkj.visitor.tool.ams</p>
 * <p>描述: 读卡驱动服务工具类 </p>
 * <p>创建时间: 2020/5/9 10:24 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ReadIdCardServiceTools {

    /**
     * 在Activity OnStart时进行注册活动
     *
     * @param context              存在上下文
     * @param readerResultReceiver 读卡信息广播接收器
     */
    public static void registerActivityOnStart(
            @NonNull Context context,
            ReadIdCardResultReceiver readerResultReceiver
    ) {
        // 开启读卡相关
        context.registerReceiver(
                readerResultReceiver,
                ReadIdCardResultReceiver.fetchReadCardReceiverIntentFilter()
        );
        ReadCardDriver.getInstance().startReadingCardInfo(context, null);
    }

    /**
     * 在Activity OnStart时进行注册活动
     *
     * @param context              存在上下文
     * @param readerResultReceiver 读卡信息广播接收器
     */
    public static void registerActivityOnStop(
            @NonNull Context context,
            ReadIdCardResultReceiver readerResultReceiver
    ) {
        // 关闭读卡相关
        ReadCardDriver.getInstance().stopReadingCardInfo(context, null);
        context.unregisterReceiver(readerResultReceiver);
    }
}
