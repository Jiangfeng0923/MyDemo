/**
 *
 */
package com.feng.demo.mydemos.auction.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;

import com.feng.demo.mydemos.auction.AuctionClientActivity;

public class DialogUtil
{
	public static void showDialog(final Context ctx
		, String msg , boolean goHome)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx)
			.setMessage(msg).setCancelable(false);
		if(goHome)
		{
			builder.setPositiveButton("on", new OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					Intent i = new Intent(ctx , AuctionClientActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ctx.startActivity(i);
				}
			});
		}
		else
		{
			builder.setPositiveButton("cancel", null);
		}
		builder.create().show();
	}
	// ����һ����ʾָ������ĶԻ���
	public static void showDialog(Context ctx , View view)
	{
		new AlertDialog.Builder(ctx)
			.setView(view).setCancelable(false)
			.setPositiveButton("ȷ��", null)
			.create()
			.show();
	}
}
