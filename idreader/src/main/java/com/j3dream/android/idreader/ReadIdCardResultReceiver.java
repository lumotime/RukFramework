package com.j3dream.android.idreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.j3dream.android.common.util.ToastUtils;
import com.j3dream.android.idreader.exception.IDCardReaderException;
import com.j3dream.core.util.ObjectUtils;
import com.j3dream.core.util.StringUtils;
import com.j3dream.core.util.TimeDateUtils;

/**
 * <p>文件名称: ReaderResultReceiver </p>
 * <p>所属包名: com.example.dell.checkproject.receiver</p>
 * <p>描述: 身份证读卡服务读取结果的广播接收器, 用于接受读卡的相关广播 </p>
 * <p>创建时间: 2020-02-04 11:56 </p>
 *
 * @author 贾军舰(lumo) cnrivkaer@outlook.com
 * @version v1.0
 */
public class ReadIdCardResultReceiver extends BroadcastReceiver implements ReadCardConstant {

    private final int readWay = 1;

    /**
     * 最后一次的读卡信息
     */
    private String lastCardNo;
    private long lastReceiverTime = 0L;

    private OnReadIdCardListener listener;

    public ReadIdCardResultReceiver(OnReadIdCardListener listener) {
        this.listener = listener;
    }

    /**
     * 获取读卡服务中用到的广播过滤器
     *
     * @return 广播过滤器
     */
    public static IntentFilter fetchReadCardReceiverIntentFilter() {
        IntentFilter tmpFilter = new IntentFilter();
        tmpFilter.addAction(ACTION_READ_PERSON_CARD_RESULT_SUCCESS);
        tmpFilter.addAction(ACTION_READ_PERSON_CARD_RESULT_WARNING);
        tmpFilter.addAction(ACTION_START_FINGERPRINT_SENSOR_SUCCESS);
        tmpFilter.addAction(ACTION_COMPAIR_RESULT);
        return tmpFilter;
    }

    /**
     * base64字符串转位图
     *
     * @param string base64字符串
     * @return 位图
     */
    private static Bitmap base64Str2Bitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0,
                    bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ObjectUtils.isEmpty(intent))
            return;

        if (ACTION_READ_PERSON_CARD_RESULT_SUCCESS.equals(StringUtils.null2Length0(intent.getAction()))) {// 身份证号码
            String curCardNumber = intent.getStringExtra("cardno");
            // 地址
            String curAddress = intent.getStringExtra("address");
            // 姓名
            String curName = intent.getStringExtra("name");
            // 性别
            String curSex = intent.getStringExtra("sex");
            // 民族
            String curNation = intent.getStringExtra("nation");
            // 生日
            String curBirthday = intent.getStringExtra("birthday");
            // 图像
            String curPhoto = intent.getStringExtra("photo");
            // 指纹
            String curFingerPrint = intent.getStringExtra("fingerprint");

            if (StringUtils.isEmpty(curCardNumber) || StringUtils.isEmpty(curName)) {
                ToastUtils.show(TIP_CUR_READ_PERSON_CARD_INFO_EMPTY_ERROR);
                return;
            }

            IDCardInfo IDCardInfo = new IDCardInfo(curCardNumber, curAddress,
                    curName, curSex, curNation, curBirthday, base64Str2Bitmap(curPhoto), curFingerPrint);

            if (StringUtils.isNotEquals(curCardNumber, getLastCardNo())) {
                listener.onIDCardReadResult(IDCardInfo);
                setLastCardNo(curCardNumber);
            } else {
                long curReceiverTime = TimeDateUtils.getCurTimestamp();
                if (curReceiverTime - lastReceiverTime >= LIMIT_LAST_RECEIVER_READ_CARD_INFO_VALID_TIME_INTERVAL) {
                    listener.onIDCardReadResult(IDCardInfo);
                    setLastCardNo(curCardNumber);
                } else {
                    listener.onIDCardReadFailure(new IDCardReaderException("不能重复刷卡"));
                }
            }
            lastReceiverTime = TimeDateUtils.getCurTimestamp();
        }
    }

    public String getLastCardNo() {
        return lastCardNo;
    }

    public void setLastCardNo(String lastCardNo) {
        this.lastCardNo = lastCardNo;
    }
}
