package com.feng.demo.mydemos.bitmap;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.feng.demo.mydemos.R;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class BitmapToParcelActivity extends AppCompatActivity {
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JLog("onCreate");
        if (savedInstanceState != null) {
            int save = savedInstanceState.getInt("KEY");
        }

        setContentView(R.layout.activity_bitmap_to_parcel);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_sim1);
    }

    /**
     * 测试Activity生命周期
     **/
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("KEY", 998);
        JLog("onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int save = savedInstanceState.getInt("KEY");
        JLog("onRestoreInstanceState");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        JLog("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        JLog("onNewIntent");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        JLog("onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        JLog("onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        JLog("onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        JLog("onResume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        JLog("onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JLog("onDestroy");
    }

    public void onClick(View v) {
        Intent intent = new Intent(BitmapToParcelActivity.this, BitmapParcelDestActivity.class);
        intent.putExtra("bitmap", bitmap);
        startActivity(intent);
    }
}
