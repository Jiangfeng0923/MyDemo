package com.feng.demo.mydemos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class ToastDemoActivity extends AppCompatActivity {
    private static final String TOASTTEXT = "oom test";
    private Toast mToast;
    //private /*static*/ ArrayList<Bitmap> arrayList = new ArrayList<>();
    private WeakHashMap<Integer, Bitmap> weakHashMap = new WeakHashMap<>();
    private /*volatile*/ int result = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linearlayout_vertical);
    }

    public void OnClick(View v) {

    }

    //测试Volatile关键字
    private void testVolatile() {
        for (int i = 0; i < 50; i++) {
            AsyncTask.THREAD_POOL_EXECUTOR.execute(new MyVolatileRunable(i));
        }
    }

    //测试内存泄漏和内存溢出
    private void outOfMemory() {
        //ExecutorService executorService = Executors.newSingleThreadExecutor();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 100; i > 0; i--) {
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.contact);
                    weakHashMap.put(Integer.valueOf(i), bitmap);
                    //arrayList.add(bitmap);
                    JLog("i=" + i);
                }
                JLog("map size=" + weakHashMap.size());
            }
        });
    }


    private void showToast() {
        String toast = getResources().getString(R.string.app_name);

        if (mToast == null) {
            mToast = Toast.makeText(this, toast, Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog("onDestroy result =" + result);
        result = 0;
    }

    private class MyVolatileRunable implements Runnable {
        int id;

        public MyVolatileRunable(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (result < 30) {
                result++;
            }
            JLog("MyVolatileRunable:" + id + " result=" + result);
        }
    }
}
