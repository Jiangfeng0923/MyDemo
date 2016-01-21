package com.feng.demo.mydemos.http;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.feng.demo.mydemos.R;
import com.feng.demo.data.MyURLs;
import com.feng.demo.utils.SharedPreferenceHelper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class OkHttpActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private String mUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        textView=(TextView)findViewById(R.id.response);
        editText =(EditText)findViewById(R.id.ip_address);
    }

    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.getURLs){
            initURLs();
        }else{
            getResponse();
        }
    }
    private void initURLs() {
        String ip = MyURLs.getIPAddress(this,editText);
        mUrl= MyURLs.BuildURL(ip, MyURLs.LOCAL_LOGIN_ADDRESS);
        JLog("mUrl:" + mUrl);
    }

    private void getResponse(){
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(mUrl)
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
        //立即执行
        /*try {
            call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        //请求加入调度
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                JLog("onFailure request="+request);
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                //String htmlStr =  response.body().string();
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(res);
                    }

                });
            }
        });
    }

}
