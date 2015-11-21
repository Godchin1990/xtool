package com.godchin.codelife.json;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**解析帮助类
 * wei2015/10/28
 */
public class GsonT {

    private static Gson mGson = null;

    public static <T> ArrayList<T> fromJsonList(String json, Class<T> cls) {
        if (mGson == null) {
            mGson = new Gson();
        }
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            mList.add(mGson.fromJson(elem, cls));
        }
        return mList;
    }

    public static <T> T parseJson(String json, Class<T> t) {
        if (mGson == null) {
            mGson = new Gson();
        }
        T cT = mGson.fromJson(json, t);
        return cT;
    }

    public static String toJson(Object obj) {
        if (mGson == null) {
            mGson = new Gson();
        }
        return mGson.toJson(obj);
    }

}
