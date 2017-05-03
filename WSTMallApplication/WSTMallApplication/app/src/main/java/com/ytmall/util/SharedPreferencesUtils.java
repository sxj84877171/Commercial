package com.ytmall.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/4/21.
 */
public class SharedPreferencesUtils {
    /**
     * 描述：把数据存储到SharedPreferences
     *
     * @param context 上下文对象
     * @param key    键
     * @param value   值
     */
    public static void saveValue(Context context, String key, String value) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("tokenId",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    /**
     * 描述：读取保存在SharedPreferences中的值
     *
     * @param context 上下文对象
     * @param key    键
     * @return String类型
     */
    public static String getValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("tokenId",
                Activity.MODE_PRIVATE);
        String value = preferences.getString(key, "0");
        return value;
    }
}
