package com.feng.demo.data;

import android.content.Intent;

/**
 * Created by lenovo on 2016/1/3.
 */
public class DataMode {
    private String mName;
    private Intent mIntent;
    public DataMode(String name , Intent intent){
        mName =name;
        mIntent=intent;
    }
    public String getName(){
        return mName;
    }
    public Intent getIntent(){
        return mIntent;
    }
}
