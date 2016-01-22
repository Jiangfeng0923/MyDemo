package com.feng.demo.mydemos.http;

import android.content.Intent;
import android.net.Uri;
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

import com.feng.demo.data.MyIntentAction;
import com.feng.demo.mydemos.R;
import com.feng.demo.data.MyURLs;
import com.feng.demo.utils.DialogHelper;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.ResponseBody;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
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
            final Uri uri = Uri.parse(url);
            DialogHelper helper = new DialogHelper(WebViewActivity.this);
            helper.setOnDialogCilckListener(new DialogHelper.DialogListener() {
                @Override
                public void onDialogClick(int item) {
                    if (item == 0) {
                        Intent intent = new Intent(MyIntentAction.ACTION_DOWNLOAD, uri);
                        startActivity(intent);
                    } else if (item == 1) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                }
            });
            String[] items={"DownloaderTask","Browser"};
            helper.createAlertDialog(items);

            //Todo: Download by DownloaderTask!!!
            /*DownloaderTask task = new DownloaderTask();
            task.execute(url);*/
        }
    }


}
