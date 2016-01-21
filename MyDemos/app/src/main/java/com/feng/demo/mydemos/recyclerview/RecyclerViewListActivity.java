package com.feng.demo.mydemos.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.feng.demo.mydemos.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewListActivity extends AppCompatActivity {
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);
        initData();
        initRecyclerView();
    }

    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    private void initRecyclerView() {
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

        MyRecyclerAdapter mAdapter = new MyRecyclerAdapter(RecyclerViewListActivity.this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
    }
}
