package com.feng.demo.mydemos.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import static com.feng.demo.utils.MyLogUtils.JLog;



public class MyAIDLService extends Service {
    public static final int RESULT = 99;
    private Messenger messenger;
    public MyAIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        JLog("intent="+intent);
        messenger = intent.getParcelableExtra("messenger");
        JLog("messenger="+messenger);
        return stub;
    }

    IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int Compute(final int a, final int b) throws RemoteException {
            AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendBack("calculating...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int sum = 0;
                    sum = a + b;
                    sendBack(Integer.valueOf(sum));
                }
            });
            AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendBack("calculating2...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int sum = 0;
                    sum = a * b;
                    sendBack(Integer.valueOf(sum));
                }
            });
            return 0;
        }
    };

    private <E> void sendBack(E e){
        Message msg = Message.obtain();
        msg.obj = e;
        msg.what = RESULT;
        try {
            messenger.send(msg);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}
