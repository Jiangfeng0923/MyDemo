package com.feng.demo.mydemos.http;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.feng.demo.mydemos.R;
import com.feng.demo.data.MyURLs;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Callback;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class WebViewActivity extends AppCompatActivity {
    private WebView webView;
    private EditText editText;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        editText = (EditText) findViewById(R.id.ip_address);
        webView = (WebView) findViewById(R.id.webview);

    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.getURLs) {
            initURLs();
        } else {
            loadWebview(mUrl);
        }
    }

    private void initURLs() {
        String ip = MyURLs.getIPAddress(this, editText);
        mUrl = MyURLs.BuildURL(ip, MyURLs.LOCAL_RESOURCE_ADDRESS);
        JLog("mUrl:" + mUrl);
    }

    private void loadWebview(String url) {
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                return true;
            } else {
                // System.exit(0);
                finish();//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
            JLog("down load url=" + url);
            DownloaderTask task = new DownloaderTask();
            task.execute(url);
        }
    }

    private class DownloaderTask extends AsyncTask<String, Void, String> {

        public DownloaderTask() {
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(params[0])
                    .build();
                JLog("doInBackground:"+request);
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        JLog(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    JLog(response.body().string());
                }
            });
            return null;
        }

        private String doByHttpClient(String... params) {
            String url = params[0];
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            fileName = URLDecoder.decode(fileName);
            Log.i("tag", "fileName=" + fileName);

            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, fileName);
            if (file.exists()) {
                Log.i("tag", "The file has already exists.");
                return fileName;
            }
            try {
                /*HttpClient client = new DefaultHttpClient();
//                client.getParams().setIntParameter("http.socket.timeout",3000);//设置超时
                HttpGet get = new HttpGet(url);
                HttpResponse response = client.execute(get);
                if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
                    HttpEntity entity = response.getEntity();
                    InputStream input = entity.getContent();

                    writeToSDCard(fileName,input);

                    input.close();
                    return fileName;
                }else{
                    return null;
                }*/
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            //closeProgressDialog();
            if (result == null) {
                return;
            }

            File directory = Environment.getExternalStorageDirectory();
            File file = new File(directory, result);
            Log.i("tag", "Path=" + file.getAbsolutePath());


        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            //showProgressDialog();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }
    }
}
