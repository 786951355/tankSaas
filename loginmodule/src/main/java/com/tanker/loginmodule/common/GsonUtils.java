package com.tanker.loginmodule.common;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27.
 */

public class GsonUtils {
    /**
     * @param <T>
     * @param <T>
     * @param string json 字符串
     * @param T      转为对象
     * @return 对象
     * @Description Json字符串转为List 对象
     */
    public static <T> List<T> jsonStringToList(String string, Class<T> T) {

        try {
            Gson gson = new Gson();
            List<T> lst = new ArrayList<>();
            JsonArray array = new JsonParser().parse(string).getAsJsonArray();
            for (final JsonElement element : array) {
                lst.add(gson.fromJson(element, T));
            }
            return lst;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T jsonStringToModel(String jsonString, Class<?> model) {
        try {
            Gson gson = new Gson();
            T statusModel = (T) gson.fromJson(jsonString, model);
            return statusModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
