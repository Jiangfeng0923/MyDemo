package com.feng.demo.mydemos.aidl;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.feng.demo.mydemos.R;
import com.feng.demo.utils.runnable.MyDownloaderTask;

import static com.feng.demo.utils.MyLogUtils.JLog;


public class MyAIDLService extends Service {
    public static final int RESULT = 99;
    public static final int RESULT_PROGRESS = 89;
    private Messenger messenger;
    private Handler mHandler;
    private NotificationManager mNotificationManager;

    public MyAIDLService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        JLog("intent=" + intent);
        messenger = intent.getParcelableExtra("messenger");
        mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (mHandler == null) {
            mHandler = new MyHandler(MyAIDLService.this, mNotificationManager);
        }
        return stub;
    }

    IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int download(String url) throws RemoteException {
            executeDownload(url);
            return 0;
        }
        @Override
        public int notifyResult(String str){
            Message msg = Message.obtain();
            msg.what= MyAIDLService.RESULT_PROGRESS;
            msg.arg1 = str.hashCode();
            msg.obj=str;
            mHandler.sendMessage(msg);
            return 0;
        }
    };

    private static class MyHandler extends Handler {
        private Context mContext;
        Bitmap largeIcon;
        Notification.Builder notificationBuilder;
        private NotificationManager mNotificationManager;

        public MyHandler(Context context, NotificationManager notificationManager) {
            mContext = context;
            largeIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.contact);
            mNotificationManager = notificationManager;
            notificationBuilder = new Notification.Builder(mContext);
        }

        @Override
        public void handleMessage(Message msg) {
            String progress = String.valueOf(msg.obj);
            int id = msg.arg1;
            Notification notifi =
                    notificationBuilder.setContentTitle("Progress")
                            .setContentText(progress)
                            .setSmallIcon(R.drawable.ic_sim1)
                            .setLargeIcon(largeIcon)
                            .build();
            mNotificationManager.notify(id, notifi);
        }
    }

    private void executeDownload(String url) {
        JLog("MyAIDLService executeDownload");
        MyDownloaderTask task = new MyDownloaderTask(MyAIDLService.this, mHandler);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
    }

    private <E> void sendBack(E e, int what) {
        Message msg = Message.obtain();
        msg.obj = e;
        msg.what = what;
        try {
            messenger.send(msg);
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }

    private void compute(final int a, final int b) {
        AsyncTask.SERIAL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //sendBack("calculating...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int sum = 0;
                sum = a + b;
                //sendBack(Integer.valueOf(sum));
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
                //sendBack("calculating2...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int sum = 0;
                sum = a * b;
                //sendBack(Integer.valueOf(sum));
            }
        });
    }
}
