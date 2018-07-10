package com.tanker.basemodule.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.net.Uri;

import com.tanker.basemodule.common.SaasApp;

import java.util.HashMap;
import java.util.Map;


public class SoundPoolUtil {
    private static SoundPool soundPool;
    //存声音的map
    private static Map<Integer, Integer> ringMap = new HashMap<Integer, Integer>();

    public static void initSoundPool() {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
    }

    public static SoundPool getSoundPool() {
        return soundPool;
    }

    /**
     * 铃声响应
     *
     * @param
     */
    public static void ringById(int resId) {
        if (soundPool == null) {
            soundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
            final Integer phonering = getSoundPoolSourceByid(resId);
            soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
                public void onLoadComplete(SoundPool soundPool, int sampleId,
                                           int status) {
                    float volume = 1;
                    soundPool.play(phonering, volume, volume, 0, 0, 1);
                }
            });
        } else {
            Integer phonering = getSoundPoolSourceByid(resId);
            float volume = 1;
            soundPool.play(phonering, volume, volume, 0, 0, 1);
        }

    }

    /*
        获取声音资源
     */
    private static Integer getSoundPoolSourceByid(int resId) {
        Integer phonering = ringMap.get(resId);
        if (phonering == null) {
            phonering = soundPool.load(SaasApp.getInstance(), resId, 0);
            ringMap.put(resId, phonering);
        }
        return phonering;
    }


    public static void releaseSoundPool() {
        if (soundPool != null)
            soundPool.release();
    }

    private static int getRingVolume() {
        int result = -1;
        AudioManager audioManager = (AudioManager) SaasApp.getInstance()
                .getSystemService(Context.AUDIO_SERVICE);
        result = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        return result;
    }


    public static Uri getDefaultRingtone() {
        return RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    }


}
