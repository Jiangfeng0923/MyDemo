package com.feng.demo.mydemos.auction;

import android.app.Fragment;
import android.os.Bundle;

import com.feng.demo.mydemos.auction.FragmentActivity;

public class ViewItem extends FragmentActivity
{
	@Override
	protected Fragment getFragment()
	{
		ViewItemFragment fragment = new ViewItemFragment();
		Bundle arguments = new Bundle();
		arguments.putString("action"
			, getIntent().getStringExtra("action"));
		fragment.setArguments(arguments);
		return fragment;
	}
}
