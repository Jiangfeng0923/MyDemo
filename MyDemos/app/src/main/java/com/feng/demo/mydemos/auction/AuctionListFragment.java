package com.feng.demo.mydemos.auction;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.feng.demo.mydemos.R;

public class AuctionListFragment extends Fragment
{	
	ListView auctionList;
	private Callbacks mCallbacks;
	@Override
	public View onCreateView(LayoutInflater inflater
		, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.auction_list,
			container, false);
		auctionList = (ListView) rootView.findViewById(
			R.id.auction_list);
		auctionList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				mCallbacks.onItemSelected(position , null);
			}
		});
		return rootView;
	}	

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		if (!(activity instanceof Callbacks))
		{
			throw new IllegalStateException(
				"AuctionListFragment Callbacks IllegalStateException!");
		}
		mCallbacks = (Callbacks) activity;
	}
	@Override
	public void onDetach()
	{
		super.onDetach();
		mCallbacks = null;
	}

	public void setActivateOnItemClick(boolean activateOnItemClick)
	{
		auctionList.setChoiceMode(activateOnItemClick 
			? ListView.CHOICE_MODE_SINGLE
			: ListView.CHOICE_MODE_NONE);
	}
}