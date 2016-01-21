package com.feng.demo.utils;

/**
 * Created by feng.jiang on 2016/1/4.
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feng.demo.mydemos.R;

/**
 * @TODO [一个简单的自定义 ListView Item]
 * @author XiaoMaGuo ^_^
 * @version [version-code, 2013-10-22]
 * @since [Product/module]
 */
public class MyListItem extends LinearLayout
{
    private TextView mTitle;

    private ProgressBar mProgress;

    public MyListItem(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyListItem(Context context)
    {
        super(context);
    }

    public void setTitle(String title)
    {
        if (mTitle == null)
        {
            mTitle = (TextView)findViewById(R.id.task_name);
        }
        mTitle.setText(title);
    }

    public void setProgress(int prog)
    {
        if (mProgress == null)
        {
            mProgress = (ProgressBar)findViewById(R.id.task_progress);
        }
        mProgress.setProgress(prog);
    }

}
