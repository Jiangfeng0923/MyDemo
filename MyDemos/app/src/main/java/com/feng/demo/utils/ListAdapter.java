
package com.feng.demo.utils;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/1/3.
 */
public class ListAdapter<E> extends BaseAdapter {
    private ArrayList<E> mArray;
    private Context mContext;

    public ListAdapter(Context context, ArrayList<E> array) {
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
        E e = mArray.get(position);
        if (e instanceof String) {
            v.setText((String) e);
        } else if (e instanceof DataMode) {
            v.setText(((DataMode) e).mName);
            v.setTag(((DataMode) e).mIntent);
        }

        return v;
    }
}

