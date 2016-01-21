package com.feng.demo.mydemos.bitmap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.feng.demo.mydemos.R;

public class BitmapParcelDestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_parcel_dest);
        Intent intent = getIntent();
        Bitmap bitmap = intent.getParcelableExtra("bitmap");
        ImageView imageView = (ImageView)findViewById(R.id.bitmap);
        imageView.setImageBitmap(bitmap);
    }
}
