package com.feng.demo.mydemos.auction;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.feng.demo.mydemos.R;

public abstract class FragmentActivity extends Activity
{
	//private static final int ROOT_CONTAINER_ID = 0x90001;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		LinearLayout layout = new LinearLayout(this);
		setContentView(layout);
		layout.setId(R.id.ROOT_CONTAINER_ID);
		getFragmentManager().beginTransaction()
			.replace(R.id.ROOT_CONTAINER_ID , getFragment())
			.commit();
	}
	protected abstract Fragment getFragment();
}
