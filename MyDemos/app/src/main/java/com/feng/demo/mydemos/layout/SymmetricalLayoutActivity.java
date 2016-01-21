package com.feng.demo.mydemos.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.feng.demo.mydemos.R;
import com.feng.demo.mydemos.layout.SymmetricalLayout;

public class SymmetricalLayoutActivity extends AppCompatActivity {
    private SymmetricalLayout symmetricalLayout;
    private LinearLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symmetrical_layout);
        frameLayout=(LinearLayout)findViewById(R.id.frame);
    }
    public void onClick(View v) {
        testSymmetricalLayout(v);
    }
    private void testSymmetricalLayout(View v) {
        int id = v.getId();
        if(id == R.id.add1){
            if(frameLayout.getChildCount()==0){
                symmetricalLayout =new SymmetricalLayout(this);
                int width = (int)getResources().getDimension(R.dimen.width);
                int height = (int)getResources().getDimension(R.dimen.height);
                symmetricalLayout.setLayoutParams(new SymmetricalLayout.LayoutParams(width, height));
                int green = getResources().getColor(android.R.color.holo_green_dark);
                symmetricalLayout.setBackgroundColor(green);
                frameLayout.addView(symmetricalLayout);
            }
        }else if(id == R.id.add2){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.ic_sim1);
            symmetricalLayout.addView(imageView);
        }else{
            symmetricalLayout.removeAllViews();
        }
    }
}
