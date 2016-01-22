package com.feng.demo.utils.runnable;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.CursorAdapter;

/**
 * Created by lenovo on 2016/1/3.
 */
public class MyLoaderCallBacks implements LoaderManager.LoaderCallbacks<Cursor> {
    private CursorAdapter mAdapter;
    private String[] PROJECTION;
    private String SELECTION;
    private Context mContext;

    public MyLoaderCallBacks(Context context, CursorAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    public void setProjection(String[] s) {
        PROJECTION = s;
    }

    public void setSelection(String s) {
        SELECTION = s;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext, ContactsContract.Data.CONTENT_URI,
                PROJECTION, SELECTION, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }
}
