package com.feng.demo.mydemos.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feng.demo.mydemos.R;


import org.w3c.dom.Text;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class MyAIDLActivity extends AppCompatActivity {
    private TextView textView;
    private ViewGroup progressGroup;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MyAIDLService.RESULT) {
                textView.setText(String.valueOf(msg.obj));
            }else if(msg.what == MyAIDLService.RESULT_PROGRESS){
                /*TextView tx = new TextView(MyAIDLActivity.this);
                tx*/
                textView.setText(String.valueOf(msg.obj));
            }
        }
    };
    private String url;
    private IMyAidlInterface myServiceAIDL;


    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            JLog("onServiceConnected");
            myServiceAIDL = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            JLog("onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_aidl);
        textView = (TextView) findViewById(R.id.result);
        progressGroup = (ViewGroup)findViewById(R.id.progress);
        bindService();
    }

    private void download() {
        Uri uri = getIntent().getData();
        if(TextUtils.isEmpty(String.valueOf(uri))){
            return;
        }
        url = uri.toString();
        textView.setText(url);
        try {
            //通过AIDL远程调用
            myServiceAIDL.download(url);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void clear() {
        textView.setText("");
        unbindService(sc);
    }

    private void bindService() {
        //连接远程Service和Activity
        Intent binderIntent = new Intent(this, MyAIDLService.class);
        Messenger messenger = new Messenger(handler);
        binderIntent.putExtra("messenger", messenger);
        bindService(binderIntent, sc, BIND_AUTO_CREATE);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.compute:
                bindService();
                break;
            case R.id.clear:
                clear();
                break;
            case R.id.download:
                download();
                break;
        }

    }
}
