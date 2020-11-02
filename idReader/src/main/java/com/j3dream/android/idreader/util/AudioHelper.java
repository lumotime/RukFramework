package com.j3dream.android.idreader.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.SparseIntArray;

import com.j3dream.android.common.util.Utils;
import com.j3dream.android.idreader.R;

import java.util.Collections;
import java.util.List;

/**
 * <p>文件名称: AudioHelper </p>
 * <p>所属包名: com.lumotime.tools.ft.util</p>
 * <p>描述:  </p>
 * <p>创建时间: 2020/6/4 14:48 </p>
 *
 * @author <a href="mail to: cnrivkaer@outlook.com" rel="nofollow">lumo</a>
 * @version v1.0
 */
public class AudioHelper {

    private static final List<Integer> HINT_MESSAGE_RES_LIST
            = Collections.singletonList(R.raw.beep);

    /**
     * 提示声音储藏器
     */
    private SparseIntArray mAudioArrayWare = new SparseIntArray();

    /**
     * 声音播放器
     */
    private SoundPool mSoundPool;

    private AudioHelper() {
        init();
    }

    public static synchronized AudioHelper getInstance() {
        return AudioHolder.HOLDER;
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                    .build();
            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(1)
                    .build();
        } else {
            mSoundPool = new SoundPool(1, 0, 5);
        }
        // 初始化播放的提示音列表
        for (int i = 0; i < HINT_MESSAGE_RES_LIST.size(); i++) {
            mAudioArrayWare.put(
                    i,
                    mSoundPool.load(Utils.getApp(), HINT_MESSAGE_RES_LIST.get(i), 1)
            );
        }
    }

    /**
     * 播放音频
     */
    public void playAudio() {

        if (mSoundPool == null) {
            return;
        }

        AudioManager audioManager = (AudioManager) Utils.getApp().getSystemService(Context.AUDIO_SERVICE);
        float audioCurrentVolume = audioManager == null ? 0 : audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        mSoundPool.play(
                mAudioArrayWare.get(0),
                audioCurrentVolume,
                audioCurrentVolume,
                1, 0, 1F
        );
    }

    private void closeAudio() {

        if (mSoundPool == null) {
            return;
        }

        mSoundPool.pause(0);
        mSoundPool.release();
    }

    private static class AudioHolder {
        private static final AudioHelper HOLDER = new AudioHelper();
    }
}
