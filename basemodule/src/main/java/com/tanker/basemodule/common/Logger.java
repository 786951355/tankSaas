package com.tanker.basemodule.common;

import android.util.Log;

import com.tanker.basemodule.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Logger utils
 * <p>
 * Created by ronny on 2016/11/17.
 */
public class Logger {
    public static final int LEVEL_V = 0x00;
    public static final int LEVEL_D = 0x01;
    public static final int LEVEL_I = 0x02;
    public static final int LEVEL_W = 0x03;
    public static final int LEVEL_E = 0x04;
    private static final boolean isDebugable = BuildConfig.DEBUG;
    private static final String logfileName = "tanker_log";
    /**/
    public static String TAG = "TANKER_APP";

    public static final void setTag(String tag) {
        TAG = tag;
    }

    public static final void v(String message) {
        printLog(LEVEL_V, message);
    }

    public static final void d(String message) {
        printLog(LEVEL_D, message);
    }

    public static final void i(String message) {
        printLog(LEVEL_I, message);
    }

    public static final void w(String message) {
        printLog(LEVEL_W, message);
    }

    public static final void e(String message) {
        printLog(LEVEL_E, message);
    }

    public static final void v(String tag, String message) {
        TAG = tag;
        printLog(LEVEL_V, message);
    }

    public static final void d(String tag, String message) {
        TAG = tag;
        printLog(LEVEL_D, message);
    }

    public static final void i(String tag, String message) {
        TAG = tag;
        printLog(LEVEL_I, message);
    }

    public static final void w(String tag, String message) {
        TAG = tag;
        printLog(LEVEL_W, message);
    }

    public static final void e(String tag, String message) {
        TAG = tag;
        printLog(LEVEL_E, message);
    }

    private static final void printLog(int level, String message) {
        printLog(false, level, message);
    }

    private static final void printLog(boolean write2file, int level, String message) {
        if (!isDebugable && level != LEVEL_E) {
            return;
        }

        if (message == null) {
            Log.e(TAG, "message = null");
            return;
        }

        if (write2file) {
            write2logfile(message);
        }

        switch (level) {
            case LEVEL_V:
                Log.v(TAG, message);
                break;
            case LEVEL_D:
                Log.d(TAG, message);
                break;
            case LEVEL_I:
                Log.i(TAG, message);
                break;
            case LEVEL_W:
                Log.w(TAG, message);
                break;
            case LEVEL_E:
                Log.e(TAG, message);
                break;
            default:
                Log.d(TAG, message);
        }
    }

    /**
     * @param msg
     */
    private static void write2logfile(String msg) {
        try {
            File sdCardDir = android.os.Environment.getExternalStorageDirectory();
            File logfile = new File(sdCardDir.getAbsolutePath() + File.separator + logfileName);
            if (!logfile.exists()) {
                logfile.createNewFile();
            }

            //
            msg += "\n";

            FileOutputStream outputStream = new FileOutputStream(logfile, true);
            outputStream.write(msg.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
