package com.feng.demo.mydemos.http;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.feng.demo.mydemos.R;
import com.feng.demo.data.MyURLs;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class TomcatActivity extends AppCompatActivity {


    Button submitBtnPost = null;
    Button submitBtnGet = null;
    TextView infoTextView = null;
    EditText nameEdit = null;
    EditText codeEdit = null;
    ScrollView scrollView = null;
    boolean isPost = true; //默认采取post登录方式

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomcat);
        scrollView = (ScrollView) findViewById(R.id.info_scroll_view);
        submitBtnPost = (Button) findViewById(R.id.btn_submit_post);
        submitBtnGet = (Button) findViewById(R.id.btn_submit_get);
        infoTextView = (TextView) findViewById(R.id.tv_info);
        nameEdit = (EditText) findViewById(R.id.edit_name);
        codeEdit = (EditText) findViewById(R.id.edit_code);
        submitBtnPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                isPost = true;
                new SubmitAsyncTask().execute(MyURLs.TOMCATURL);
            }
        });
        submitBtnGet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                isPost = false;
                new SubmitAsyncTask().execute(MyURLs.TOMCATURL);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public class SubmitAsyncTask extends AsyncTask<String, Void, String> {
        String info = "";

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url = params[0];
            String reps = "";
            if (isPost) {
                info = "HttpPost返回结果: ";
                //reps = doPost(url);
            } else {
                info = "HttpGet返回结果: ";
                reps = doGetNew(url);
            }
            return reps;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            infoTextView.append("\n" + info + result + "\n");
            String res = result.trim();
            if (res.equals("0")) {
                info = "验证通过.....";
            } else if (res.equals("1")) {
                info = "密码错误.....";
            } else if (res.equals("2")) {
                info = "用户名错误.....";
            } else if (res.equals("-1")) {
                info = "返回结果异常！";
            }
            infoTextView.append(info + "\n");
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            super.onPostExecute(result);
        }


        private String doGetNew(String url) {
            final String[] respon = {""};
            //创建okHttpClient对象
            OkHttpClient mOkHttpClient = new OkHttpClient();
            //创建一个Request
            final Request request = new Request.Builder()
                    .url(url)
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
                    JLog("onFailure request=" + request);
                }

                @Override
                public void onResponse(final Response response) throws IOException {
                    //String htmlStr =  response.body().string();
                    //respon[0] = response.body().string();
                    JLog("onResponse source: "+response.body().source());
                    JLog("onResponse type: "+response.body().contentType());
               /* runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //textView.setText(res);
                    }

                });*/
                }
            });
            return respon[0];
        }
    /*
    private String doGet(String url){
        String responseStr = "";
        try {
            String name = nameEdit.getText().toString().trim();
            String code = codeEdit.getText().toString().trim();
            String getUrl = URL + "?NAME=" + name+"&"+"CODE=" + code;

            HttpGet httpRequest = new HttpGet(getUrl);
            HttpParams params = new BasicHttpParams();
            ConnManagerParams.setTimeout(params, 1000);
            HttpConnectionParams.setConnectionTimeout(params, 3000);
            HttpConnectionParams.setSoTimeout(params, 5000);
            httpRequest.setParams(params);

            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
            final int ret = httpResponse.getStatusLine().getStatusCode();
            if(ret == HttpStatus.SC_OK){
                responseStr = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
            }else{
                responseStr = "-1";
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return responseStr;
    }
    */

        /**
         * 用Post方式跟服务器传递数据
         * @param url
         * @return
         */
    /*
    private String doPost(String url){
        String responseStr = "";
        try {
            HttpPost httpRequest = new HttpPost(url);
            HttpParams params = new BasicHttpParams();
            ConnManagerParams.setTimeout(params, 1000); //从连接池中获取连接的超时时间
            HttpConnectionParams.setConnectionTimeout(params, 3000);//通过网络与服务器建立连接的超时时间
            HttpConnectionParams.setSoTimeout(params, 5000);//读响应数据的超时时间
            httpRequest.setParams(params);
            //下面开始跟服务器传递数据，使用BasicNameValuePair
            List<BasicNameValuePair> paramsList = new ArrayList<BasicNameValuePair>();
            String name = nameEdit.getText().toString().trim();
            String code = codeEdit.getText().toString().trim();
            paramsList.add(new BasicNameValuePair("NAME", name));
            paramsList.add(new BasicNameValuePair("CODE", code));
            UrlEncodedFormEntity mUrlEncodeFormEntity = new UrlEncodedFormEntity(paramsList, HTTP.UTF_8);
            httpRequest.setEntity(mUrlEncodeFormEntity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            final int ret = httpResponse.getStatusLine().getStatusCode();
            if(ret == HttpStatus.SC_OK){
                responseStr = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
            }else{
                responseStr = "-1";
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return responseStr;
    }*/


    }
}
