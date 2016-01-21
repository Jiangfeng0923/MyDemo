
package com.feng.demo.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.feng.demo.data.DataMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/1/3.
 */
public class ListAdapter extends BaseAdapter {
    private List<?> mArray;
    private Context mContext;

    public ListAdapter(Context context, List<?> array) {
        mArray = array;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mArray.size();
    }

    @Override
    public Object getItem(int position) {
        return mArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button v;
        if (convertView == null) {
            v = new Button(mContext);
        } else {
            v = (Button) convertView;
        }
        v.setFocusable(false);
        v.setClickable(false);
        if (mArray.get(position) instanceof String) {
            String str = (String)mArray.get(position);
            v.setText(str);
        } else if (mArray.get(position) instanceof DataMode) {
            DataMode data = (DataMode)mArray.get(position);
            v.setText(data.getName());
            v.setTag(data.getIntent());
        }

        return v;
    }
}

