package com.feng.demo.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

/**
 * Created by feng.jiang on 2016/1/22.
 */
public class DialogHelper {
    private DialogListener mListener;
    private Context mContext;
    public DialogHelper(Context context){
        mContext = context;
    }
    public void setOnDialogCilckListener(DialogListener dialogCilckListener){
        mListener = dialogCilckListener;
    }
    public interface DialogListener{
        public void onDialogClick(int item);
    }
    public  void createAlertDialog(String[] items){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        //xinli.chen for PL-5122 at 2015-10-15
        builder.setTitle("Dialog");
        //builder.setMessage(message);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                mListener.onDialogClick(which);
            }
        });
        /*builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });*/
        builder.show();
    }
}
