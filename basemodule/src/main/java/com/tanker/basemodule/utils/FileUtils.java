package com.tanker.basemodule.utils;

import android.content.Context;
import android.os.Environment;

import com.google.gson.reflect.TypeToken;
import com.tanker.basemodule.common.Logger;
import com.tanker.basemodule.common.SaasApp;
import com.tanker.basemodule.model.mine_model.ProvinceModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.ResponseBody;

public class FileUtils {


    public static List<ProvinceModel> getCities(Context context) {
        File address = SaasApp.getInstance().getApplicationContext().getFileStreamPath("province.json");
        if (!address.exists()) {
            return readJsonFromAssets(context);
        }
        return getCitiesFromSDCard(address);
    }

    private static List<ProvinceModel> readJsonFromAssets(Context context) {
        InputStream is = null;
        String result = "";
        try {
            is = context.getAssets().open("province.json");
            int length = is.available();
            byte[] buffer = new byte[length];
            is.read(buffer);
            result = new String(buffer, "utf8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<List<ProvinceModel>>() {
        }.getType();
        return GsonUtils.GsonToBean(result, type);
    }

    public static List<ProvinceModel> getCitiesFromSDCard(File file) {
        StringBuilder sb = new StringBuilder();
        BufferedReader bufferedReader = null;
        if (file == null) {
            return null;
        }
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String next = "";
            while (null != (next = bufferedReader.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            e.printStackTrace();
            sb.delete(0, sb.length());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Type type = new TypeToken<List<ProvinceModel>>() {
        }.getType();
        return GsonUtils.GsonToBean(sb.toString(), type);
    }


    public static boolean mkdir(File file) {
        while (!file.getParentFile().exists()) {
            mkdir(file.getParentFile());
        }
        return file.mkdir();
    }

    public static File getTempPicFile() {
        return SaasApp.getInstance().getExternalFilesDir("TempPic");
    }


    public static final int TYPE_JSON = 0;
    public static final int TYPE_PNG = 1;
    public static final int TYPE_JPG = 2;

    public static String writeResponseBodyToDisk(String fileName, ResponseBody body, int type) {
        String fileSuffix = "";
        Logger.d("contentType:>>>>" + body.contentType().toString());

        String path = null;
        switch (type) {
            case TYPE_JSON:
                fileSuffix = ".json";
                break;
            case TYPE_JPG:
                fileSuffix = ".jpeg";
                break;
            case TYPE_PNG:
                fileSuffix = ".png";
                break;
        }
        File file;
        if (type != TYPE_JSON) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TankerDownload" + File.separator + fileName + fileSuffix;
            file = new File(path);
        } else {
            file = SaasApp.getInstance().getApplicationContext().getFileStreamPath(fileName + fileSuffix);
        }
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (path == null) {
            return "";
        }
        Logger.d("path:>>>>" + path);

        try {

            File templateJsonFile = new File(path);
            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(templateJsonFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    Logger.d("file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                return path;
            } catch (IOException e) {
                Logger.e(e.getMessage());
                return null;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            Logger.e(e.getMessage());
            return null;
        }
    }

    public static boolean fileIsExists(String path) {
        if (path == null || path.trim().length() <= 0) {
            return false;
        }
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
