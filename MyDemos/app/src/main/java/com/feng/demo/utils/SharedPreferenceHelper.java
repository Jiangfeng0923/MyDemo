package com.feng.demo.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by feng.jiang on 2016/1/19.
 */
public class SharedPreferenceHelper {
    private SharedPreferenceHelper() {
    }

    public static String getFromSharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String ip = preferences.getString("ip", "");
        return ip;
    }

    public static void putIntoSharedPreferences(Context context, String ip) {
        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ip", ip);
        editor.apply();
    }
}
