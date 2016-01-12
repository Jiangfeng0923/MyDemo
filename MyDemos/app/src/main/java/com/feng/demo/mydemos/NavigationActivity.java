package com.feng.demo.mydemos;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.feng.demo.mydemos.aidl.MyAIDLActivity;
import com.feng.demo.mydemos.bitmap.BitmapToParcelActivity;
import com.feng.demo.mydemos.http.OkHttpActivity;
import com.feng.demo.mydemos.http.TomcatActivity;
import com.feng.demo.mydemos.http.VolleyActivity;
import com.feng.demo.mydemos.http.WebViewActivity;
import com.feng.demo.mydemos.threadpool.ThreadPoolActivity;
import com.feng.demo.mydemos.threadpool.ThreadPool_2_Activity;
import com.feng.demo.utils.DataMode;
import com.feng.demo.utils.ListAdapter;

import java.util.ArrayList;

public class NavigationActivity extends ListActivity {

    Class[] cls = {
            CursorLoaderActivity.class,
            RecyclerViewListActivity.class,
            TestActivity.class,
            ToastDemoActivity.class,
            VolleyActivity.class,
            WebViewActivity.class,
            ThreadPoolActivity.class,
            ThreadPool_2_Activity.class,
            BitmapToParcelActivity.class,
            OkHttpActivity.class,
            MyAIDLActivity.class,
            TomcatActivity.class,
            ImageLoaderActivity.class
    };

    private ArrayList<DataMode> mData = new ArrayList<DataMode>();
    String[] ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ss = getResources().getStringArray(R.array.activity_name);
        initData();
        ListAdapter<DataMode> adapter = new ListAdapter<DataMode>(this, mData);
        getListView().setAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = (Intent) v.getTag();
        startActivity(intent);
    }

    private String getStringKey(int id) {
        return getResources().getString(id);
    }

    private void initData() {
        for (int index = 0; index < cls.length; index++) {
            mData.add(new DataMode(ss[index], new Intent(NavigationActivity.this, cls[index])));
        }
    }
}
