package com.tanker.loginmodule.common;

import android.app.Activity;
import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/3/29.
 */

public class TimeUtils {
    //
    private static Timer timer = new Timer();
    private static TimerTask task = null;

    /**
     * 时间任务监听
     *
     * @author xm
     */
    public interface ItimeTaskListener {
        void timeTask();
    }

    /**
     * 计时器
     *
     * @param context
     * @param timeTaskListener
     */
    public static void timeTask(final Context context,
                                final TimeUtils.ItimeTaskListener timeTaskListener) {
        task = new TimerTask() {
            @Override
            public void run() {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timeTaskListener.timeTask();
                    }
                });
            }
        };
        if (timer == null)
            timer = new Timer();
        //delay多少秒后执行task，period时间间隔
        timer.schedule(task, 0, 1000);
    }

    public static void timeCancel() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
