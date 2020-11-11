package com.j3dream.android.common.util;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;

import androidx.annotation.Nullable;

import com.j3dream.android.common.data.AudioInfo;

/**
 * <p>文件名称: AndroidMediaUtils </p>
 * <p>所属包名: com.j3dream.android.common.util</p>
 * <p>描述:  </p>
 * <p></p>
 * <p>创建时间: 2020/11/11 11:57 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class AndroidMediaUtils {

    /**
     * 获取系统的音量信息
     *
     * @return 系统的音量信息
     */
    @Nullable
    public static AudioInfo getAudioInfo() {
        try {
            AudioInfo audioInfo = new AudioInfo();
            AudioManager audioManager = (AudioManager) Utils.getApp()
                    .getSystemService(Context.AUDIO_SERVICE);
            audioInfo.setMaxVoiceCall(audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL));
            audioInfo.setCurrentVoiceCall(audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));

            audioInfo.setMaxSystem(audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM));
            audioInfo.setCurrentSystem(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));

            audioInfo.setMaxRing(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
            audioInfo.setCurrentRing(audioManager.getStreamVolume(AudioManager.STREAM_RING));

            audioInfo.setMaxMusic(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            audioInfo.setCurrentMusic(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

            audioInfo.setMaxAlarm(audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));
            audioInfo.setCurrentAlarm(audioManager.getStreamVolume(AudioManager.STREAM_ALARM));

            audioInfo.setMaxNotifications(audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION));
            audioInfo.setCurrentNotifications(audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION));

            audioInfo.setMaxDTMF(audioManager.getStreamMaxVolume(AudioManager.STREAM_DTMF));
            audioInfo.setCurrentDTMF(audioManager.getStreamVolume(AudioManager.STREAM_DTMF));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                audioInfo.setMaxAccessibility(audioManager.getStreamMaxVolume(AudioManager.STREAM_ACCESSIBILITY));
                audioInfo.setCurrentAccessibility(audioManager.getStreamVolume(AudioManager.STREAM_ACCESSIBILITY));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                audioInfo.setMinDTMF(audioManager.getStreamMinVolume(AudioManager.STREAM_DTMF));
                audioInfo.setMinNotifications(audioManager.getStreamMinVolume(AudioManager.STREAM_NOTIFICATION));
                audioInfo.setMinAlarm(audioManager.getStreamMinVolume(AudioManager.STREAM_ALARM));
                audioInfo.setMinMusic(audioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC));
                audioInfo.setMinRing(audioManager.getStreamMinVolume(AudioManager.STREAM_RING));
                audioInfo.setMinSystem(audioManager.getStreamMinVolume(AudioManager.STREAM_SYSTEM));
                audioInfo.setMinVoiceCall(audioManager.getStreamMinVolume(AudioManager.STREAM_VOICE_CALL));
                audioInfo.setMinAccessibility(audioManager.getStreamMinVolume(AudioManager.STREAM_ACCESSIBILITY));
            }
            return audioInfo;
        } catch (Exception ex) {
            return null;
        }
    }
}