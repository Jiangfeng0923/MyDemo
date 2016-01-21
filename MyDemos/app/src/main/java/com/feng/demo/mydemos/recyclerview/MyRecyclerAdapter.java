package com.feng.demo.mydemos.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.feng.demo.data.DataMode;

import java.util.List;

/**
 * Created by feng.jiang on 2016/1/19.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<?> mData;
    private OnRecyclerViewItemClickListener mOnItemClickListener;

    public MyRecyclerAdapter(Context context, List<?> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Button btn = new Button(mContext);
        MyViewHolder holder = new MyViewHolder(btn);
        holder.getView().setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (mData.get(position) instanceof DataMode) {
            DataMode data = (DataMode) mData.get(position);
            ((TextView) holder.getView()).setText(data.getName());
            holder.getView().setTag(data.getIntent());
        } else if (mData.get(position) instanceof String) {
            String str = (String) mData.get(position);
            ((TextView) holder.getView()).setText(str);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
        }

        public View getView() {
            return view;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Object data);
    }
}
