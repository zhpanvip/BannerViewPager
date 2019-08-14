package com.zhpan.idea.utils;

/**
 * Created by zhpan on 2017/5/27.
 * Description:Json Sting和Object相互转换工具类
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class JsonHelper {
    public JsonHelper() {
    }

    public static <T> T parserJson2Object(String json, Class<T> classOfT) {
        T bean = null;
        if (!"".equals(json) && classOfT != null) {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();

            try {
                bean = gson.fromJson(json, classOfT);
            } catch (Exception var6) {
                bean = null;
            }
        }

        return bean;
    }

    public static <T> String parserObject2Json(T entity) {
        String result = "";
        if (entity != null) {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();

            try {
                result = gson.toJson(entity);
            } catch (Exception var5) {

            }
        }

        return result;
    }

    public static <T> ArrayList<T> parserJson2List(String json, Type typeOfT) {
        ArrayList beans = null;
        if (!"".equals(json) && typeOfT != null) {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();

            try {
                beans = (ArrayList) gson.fromJson(json, typeOfT);
            } catch (Exception var6) {
                System.out.println("==================parserJson2List:Exception" + var6.toString());
                beans = null;
            }
        }

        return beans;
    }

    public static <T> HashMap<String, T> parserJson2Map(String json, Type typeOfT) {
        HashMap beans = null;
        if (!"".equals(json) && typeOfT != null) {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();

            try {
                beans = (HashMap) gson.fromJson(json, typeOfT);
            } catch (Exception var6) {
                System.out.println("==================parserJson2Map:Exception" + var6.toString());
                beans = null;
            }
        }

        return beans;
    }

    public static <T> String parserList2Json(ArrayList<T> list, Type typeOfT) {
        String result = "";
        if (list != null && typeOfT != null) {
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();

            try {
                result = gson.toJson(list, typeOfT);
            } catch (Exception var6) {

            }
        }

        return result;
    }

    public static JSONObject getJSONObject(String json) {
        JSONObject object = null;

        try {
            JSONTokener jsonParser = new JSONTokener(json.toString());
            object = (JSONObject) jsonParser.nextValue();
        } catch (Throwable var3) {

        }

        return object;
    }

    public static String getJSONValueByKey(String json, String key) {
        String value = "";
        JSONObject obj = getJSONObject(json);
        if (obj != null) {
            try {
                value = obj.getString(key);
            } catch (Throwable var5) {

            }
        }

        return value;
    }
}
