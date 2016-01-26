package com.feng.demo.data;

import com.feng.demo.mydemos.CursorLoaderActivity;
import com.feng.demo.mydemos.EventBusActivity;
import com.feng.demo.mydemos.TestActivity;
import com.feng.demo.mydemos.ToastDemoActivity;
import com.feng.demo.mydemos.aidl.MyAIDLActivity;
import com.feng.demo.mydemos.auction.LoginActivity;
import com.feng.demo.mydemos.bitmap.BitmapToParcelActivity;
import com.feng.demo.mydemos.http.OkHttpActivity;
import com.feng.demo.mydemos.http.TomcatActivity;
import com.feng.demo.mydemos.http.VolleyActivity;
import com.feng.demo.mydemos.http.WebViewActivity;
import com.feng.demo.mydemos.layout.SymmetricalLayoutActivity;
import com.feng.demo.mydemos.recyclerview.RecyclerViewListActivity;
import com.feng.demo.mydemos.threadpool.ThreadPoolActivity;
import com.feng.demo.mydemos.threadpool.ThreadPool_2_Activity;
import com.feng.demo.mydemos.updownload.ImageLoaderActivity;
import com.feng.demo.mydemos.updownload.UploadFileActivity;

/**
 * Created by lenovo on 2016/1/19.
 */
public class MyActivities {

    public static final Class[] ACTIVITIES = {
            CursorLoaderActivity.class,
            RecyclerViewListActivity.class,
            TestActivity.class,
            ToastDemoActivity.class,
            VolleyActivity.class,
            WebViewActivity.class,
            ThreadPoolActivity.class,
            ThreadPool_2_Activity.class,
            BitmapToParcelActivity.class,
            OkHttpActivity.class,
            MyAIDLActivity.class,
            TomcatActivity.class,
            ImageLoaderActivity.class,
            EventBusActivity.class,
            UploadFileActivity.class,
            SymmetricalLayoutActivity.class,
            LoginActivity.class
    };

    private MyActivities() {
    }
}
