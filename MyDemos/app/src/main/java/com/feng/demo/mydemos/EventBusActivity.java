package com.feng.demo.mydemos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import de.greenrobot.event.EventBus;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class EventBusActivity extends AppCompatActivity {
    private EventBus controlBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        controlBus = new EventBus();
        controlBus.register(this);
    }
    public void OnClick(View v){
        AsyncTask.SERIAL_EXECUTOR.execute(new MyRunnable(controlBus));
        //controlBus.post(new MyRunnable());
    }
    public void onEventMainThread(MyEvent MyEvent) {
            JLog("onEventMainThread");
        int data = MyEvent.getData();
        JLog("data=" + data);
    }
    /*public void  onEventBackground(MyEvent MyEvent){
        JLog("onEventBackground");
    }*/
    public void onEvent(MyEvent MyEvent){
        JLog("onEvent");
    }
    /*public void onEventAsync(MyEvent MyEvent){
        JLog("onEventAsync");
    }*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        controlBus.unregister(this);
    }

    private  class MyRunnable implements Runnable{
        private EventBus mEventBus;
        public MyRunnable(EventBus eventBus){
            mEventBus = eventBus;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JLog("event bus runnable");
            mEventBus.post(new MyEvent(2));
        }
    }

    public class MyEvent{
        int data;
        public MyEvent(int data){
            this.data = data;
        }
        public int getData(){
            return data;
        }
    }
}
