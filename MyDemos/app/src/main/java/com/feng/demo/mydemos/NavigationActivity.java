package com.feng.demo.mydemos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.feng.demo.mydemos.recyclerview.MyRecyclerAdapter;
import com.feng.demo.data.DataMode;
import com.feng.demo.mydemos.recyclerview.DividerGridItemDecoration;
import com.feng.demo.data.MyActivities;

import java.util.ArrayList;
import java.util.List;

import static com.feng.demo.utils.MyLogUtils.JLog;

public class NavigationActivity extends Activity {
    private List<DataMode> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initData();
        initRecyclerView();
    }
    private void initData() {
        Class[] cls = MyActivities.ACTIVITIES;
        for (int index = 0; index < cls.length; index++) {
            mData.add(new DataMode(cls[index].getSimpleName(),
                    new Intent(NavigationActivity.this, cls[index])));
        }
    }

    private void initRecyclerView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        MyRecyclerAdapter mAdapter = new MyRecyclerAdapter(NavigationActivity.this, mData);
        mAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Object data) {
                startActivity((Intent) data);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }
}
