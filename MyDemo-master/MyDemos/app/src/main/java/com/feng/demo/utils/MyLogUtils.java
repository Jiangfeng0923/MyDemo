package com.feng.demo.utils;

import android.util.Log;

/**
 * Created by feng.jiang on 2016/1/4.
 */
public class MyLogUtils {
    public static <E> void JLog(E msg){
        Log.v("JIANG",msg.toString());
    }
}
