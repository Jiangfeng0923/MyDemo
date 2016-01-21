package com.feng.demo.mydemos.updownload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.feng.demo.mydemos.R;
import com.feng.demo.data.MyURLs;
import com.feng.demo.utils.SharedPreferenceHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class ImageLoaderActivity extends AppCompatActivity {
    private ImageView imageView;
    private int index = 0;
    private List<String> URLS = new ArrayList<>();
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_loader);
        imageView = (ImageView) findViewById(R.id.image);
        editText = (EditText) findViewById(R.id.ip_address);
    }

    private void initURLs() {
        String ip = MyURLs.getIPAddress(this ,editText);
        URLS.clear();
        for (int i = 0; i < MyURLs.IMAGE_NAMES.length; i++) {
            URLS.add(MyURLs.BuildURL(ip, MyURLs.LOCAL_IMAGES_ADDRESS, MyURLs.IMAGE_NAMES[i]));
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
