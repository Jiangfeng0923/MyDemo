package com.feng.demo.mydemos.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.feng.demo.mydemos.R;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class MyAIDLActivity extends AppCompatActivity {
    private TextView textView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MyAIDLService.RESULT) {
                textView.setText(String.valueOf(msg.obj));
            }
        }
    };


    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            JLog("onServiceConnected");
            IMyAidlInterface myServiceAIDL = IMyAidlInterface.Stub.asInterface(service);
            try {
                //通过AIDL远程调用
                myServiceAIDL.Compute(5, 6);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.compute:
                compute();
                break;
            case R.id.clear:
                clear();
                break;
        }

    }

    private void clear() {
        textView.setText("");
        unbindService(sc);
    }

    private void compute() {
        //开启服务
        //Intent intent = new Intent(this, MyAIDLService.class);
        //startService(intent);

        //连接远程Service和Activity
        Intent binderIntent = new Intent(this, MyAIDLService.class);
        Messenger messenger = new Messenger(handler);
        binderIntent.putExtra("messenger", messenger);
        //Bundle bundle = new Bundle();
        //bundle.putBoolean("flag",create_flag);
        //binderIntent.putExtras(bundle);
        bindService(binderIntent, sc, BIND_AUTO_CREATE);
    }
}
