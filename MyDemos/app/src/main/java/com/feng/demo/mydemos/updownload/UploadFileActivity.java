package com.feng.demo.mydemos.updownload;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.feng.demo.data.FileInfo;
import com.feng.demo.data.MyURLs;
import com.feng.demo.mydemos.R;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class UploadFileActivity extends AppCompatActivity {
    private Uri fileUri;
    private String uploadUrl;
    private EditText editText;
    private static final int UPLOADHTTP = 0;
    private static final int UPLOADOKHTTP = 1;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_file);
        editText = (EditText) findViewById(R.id.ip_address);
    }

    private void initURL() {
        String ip = MyURLs.getIPAddress(this, editText);
        uploadUrl = MyURLs.BuildURL(ip, MyURLs.LOCAL_UPLOAD_ADDRESS);
        JLog("uploadUrl=" + uploadUrl);
    }

    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.getIP:
                initURL();
                break;
            case R.id.getFile:
                startChooser();
                break;
            case R.id.uploadFile:
                upload(UPLOADHTTP);
                break;
            case R.id.uploadFileByOkHttp:
                upload(UPLOADOKHTTP);
                break;
        }
    }

    private void startChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, " Select a file to upload"), 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            fileUri = uri;
            JLog("fileUri=" + fileUri);
        }
    }


    public void upload(final int mode) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                if (mode == UPLOADHTTP) {
                    uploadFileByHttp();
                } else if (mode == UPLOADOKHTTP) {
                    uploadFileByOkHttp();
                }

            }
        });
    }

    protected FileInfo getFileInfoByURI(Uri uri) {
        // can post image
        String mUriStr = String.valueOf(uri);
        if(mUriStr.startsWith("file://")){
            int indexStart = mUriStr.lastIndexOf("//")+1;
            int indexEnd = mUriStr.length();
            String filePath = mUriStr.substring(indexStart,indexEnd);
            String name ="file";
            FileInfo info = new FileInfo(name,filePath);
            return info;
        }
        String[] proj = {MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.TITLE
        };
        Cursor cursor = managedQuery(uri,
                proj,                 // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null);                 // Order-by clause (ascending by name)

        int data_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int name_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int title_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.TITLE);
        cursor.moveToFirst();
        String name = cursor.getString(name_index);
        String path = cursor.getString(data_index);
        String title = cursor.getString(title_index);
        FileInfo info = new FileInfo(name, path);
        return info;
    }

    private void uploadFileByHttp() {
        JLog("uploadFileByHttp");
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            /*获取文件信息*/
            FileInfo info = getFileInfoByURI(fileUri);

            URL url = new URL(uploadUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
          /* 允许Input、Output，不使用Cache */
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
          /* 设置传送的method=POST */
            con.setRequestMethod("POST");
          /* setRequestProperty */
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");
            con.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
          /* 设置DataOutputStream */
            DataOutputStream ds =
                    new DataOutputStream(con.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);
            ds.writeBytes("Content-Disposition: form-data; "
                    + "name=\"file1\";filename=\"" +
                    info.getName() + "\"" + end);
            ds.writeBytes(end);
          /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(info.getPath());
          /* 设置每次写入1024bytes */
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
          /* 从文件读取数据至缓冲区 */
            while ((length = fStream.read(buffer)) != -1) {
            /* 将资料写入DataOutputStream中 */
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
          /* close streams */
            fStream.close();
            ds.flush();
          /* 取得Response内容 */
            InputStream is = con.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
          /* 将Response显示于Dialog */
            JLog("ok~~~~");
          /* 关闭DataOutputStream */
            ds.close();
        } catch (Exception e) {
            JLog("fail~~~~ e=" + e);
            e.printStackTrace(System.out);
        }
    }

    public void writeFile() {
        try {
            FileOutputStream fos = openFileOutput("test.txt", MODE_APPEND);
            PrintStream ps = new PrintStream(fos);
            ps.println("this is my first file!");
            ps.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void uploadFileByOkHttp() {
        String end = "\r\n";//html中换行符
        MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");
        MediaType MEDIA_TYPE_PNG
                = MediaType.parse("image/png");

        FileInfo info = getFileInfoByURI(fileUri);
        JLog("path=" + info.getPath());
        File file = new File(info.getPath());
        JLog("232 name ="+file.getName());

        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        builder.addPart(
                Headers.of("Content-Disposition", "form-data; name=\"image\"; "
                        + "filename=\"" + file.getName() + "\"" + end),
                RequestBody.create(MEDIA_TYPE_PNG, file));
        RequestBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(uploadUrl)
                .post(requestBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        } catch (Exception e) {
            JLog("e=" + e);
            e.printStackTrace(System.out);
        }

    }
}
