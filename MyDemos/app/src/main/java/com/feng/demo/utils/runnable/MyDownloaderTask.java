package com.feng.demo.utils.runnable;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.feng.demo.mydemos.aidl.MyAIDLService;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import static com.feng.demo.utils.MyLogUtils.JLog;

/**
 * Created by feng.jiang on 2016/1/22.
 */

public class MyDownloaderTask extends AsyncTask<String, Integer, String> {
    private Context mContext;
    private Handler mHandler;

    public MyDownloaderTask(Context context,Handler handler) {
        mContext = context;
        mHandler = handler;
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        int progress = values[0];
        Message msg = Message.obtain();
        msg.what= MyAIDLService.RESULT_PROGRESS;
        msg.obj=progress;
        mHandler.sendMessage(msg);
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub

        String url = params[0];
        final String fileName = url.substring(url.lastIndexOf("/") + 1);
        JLog("fileName=" + fileName);
        //downloadByOkHttp(url, fileName);
        downloadByURLConnection(url, fileName);
        return fileName;
    }

    private void downloadByURLConnection(String _urlStr, String newFilename) {
        try {
            // 构造URL
            JLog("_urlStr=" + _urlStr + " newFilename=" + newFilename);
            URL url = new URL(_urlStr);
            // 打开连接
            URLConnection con = url.openConnection();
            //获得文件的长度
            int contentLength = con.getContentLength();
            JLog("长度 :" + contentLength);
            // 输入流
            InputStream is = con.getInputStream();

            writeToSDCard(newFilename, is,contentLength);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            JLog("e=" + e);
        }
    }

    public void writeToSDCard(String fileName, InputStream input,int total) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File directory = Environment.getExternalStorageDirectory();
            File root = new File(directory
                    + File.separator + "100" + File.separator);
            boolean result = root.mkdirs();
            File file=null;
            do{
                file= new File(root, fileName);
                fileName="A"+fileName;
            }while (file.exists());
            JLog("path=" + file.getAbsolutePath());
            JLog("fileName=" + fileName);
            try {
                FileOutputStream fos = new FileOutputStream(file);
                byte[] b = new byte[1024];
                int length = 0;
                JLog("write file start ----");
                int count = 0;
                while ((length = input.read(b)) != -1) {
                    fos.write(b, 0, length);
                    count += length;
                    publishProgress((int) ((count / (float) total) * 100));
                }
                fos.flush();
                fos.close();
                JLog("Download finish!~~~~~~~~~");
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Log.i("tag", "NO SDCard.");
        }
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

    public void downloadByOkHttp(String url, final String fileName) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        JLog("doInBackground:" + request);
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

                ResponseBody entity = response.body();
                InputStream input = entity.byteStream();
                //writeToSDCard(fileName, input);

                input.close();
//                  entity.consumeContent();
            }
        });
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

}

