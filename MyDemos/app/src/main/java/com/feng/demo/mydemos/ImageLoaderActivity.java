package com.feng.demo.mydemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.feng.demo.utils.MyURLs;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class ImageLoaderActivity extends AppCompatActivity {
    private ImageView imageView;
    private int index = 0;
    private ArrayList<String> URLS = new ArrayList<>();
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        imageView = (ImageView) findViewById(R.id.image);
        editText = (EditText) findViewById(R.id.ip_address);
    }

    private void initURLs() {
        String ip = String.valueOf(editText.getText());
        JLog(ip);
        URLS.clear();
        for (int i = 0; i < MyURLs.IMAGE_NAMES.length; i++) {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(MyURLs.HTTP);
            urlBuilder.append(ip);
            urlBuilder.append(MyURLs.PORT);
            urlBuilder.append(MyURLs.LOCAL_IMAGES_ADDRESS);
            urlBuilder.append(MyURLs.IMAGE_NAMES[i]);
            JLog(urlBuilder);
            URLS.add(urlBuilder.toString());
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.getURLs) {
            initURLs();
        } else {
            ImageLoader.getInstance().displayImage(URLS.get(index), imageView);
            if (index < URLS.size() - 1) {
                index++;
            } else {
                index = 0;
            }
        }
    }
}
