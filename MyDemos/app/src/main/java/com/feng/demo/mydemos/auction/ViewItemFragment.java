package com.feng.demo.mydemos.auction;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.feng.demo.mydemos.R;

import com.feng.demo.mydemos.auction.util.DialogUtil;
import com.feng.demo.mydemos.auction.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewItemFragment extends Fragment
{
	Button bnHome;
	ListView succList;
	TextView viewTitle;
	@Override
	public View onCreateView(LayoutInflater inflater
		, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.view_item
			, container , false);
		bnHome = (Button) rootView.findViewById(R.id.bn_home);
		succList = (ListView) rootView.findViewById(R.id.succList);
		viewTitle = (TextView) rootView.findViewById(R.id.view_titile);
		bnHome.setOnClickListener(new HomeListener(getActivity()));
		String action = getArguments().getString("action");
		String url = HttpUtil.BASE_URL + action;
		if (action.equals("viewFail.jsp"))
		{
			viewTitle.setText(R.string.view_fail);
		}
		try
		{
			JSONArray jsonArray = new JSONArray(HttpUtil
				.getRequest(url));
			JSONArrayAdapter adapter = new JSONArrayAdapter(getActivity()
				, jsonArray, "name", true);
			succList.setAdapter(adapter);
		}
		catch (Exception e)
		{
			DialogUtil.showDialog(getActivity(), "Exception", false);
			e.printStackTrace();
		}
		succList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				viewItemDetail(position);
			}
		});
		return rootView;
	}
	private void viewItemDetail(int position)
	{
		View detailView = getActivity().getLayoutInflater()
			.inflate(R.layout.detail, null);
		TextView itemName = (TextView) detailView
			.findViewById(R.id.itemName);
		TextView itemKind = (TextView) detailView
			.findViewById(R.id.itemKind);
		TextView maxPrice = (TextView) detailView
			.findViewById(R.id.maxPrice);
		TextView itemRemark = (TextView) detailView
			.findViewById(R.id.itemRemark);
		JSONObject jsonObj = (JSONObject) succList.getAdapter().getItem(
			position);
		try
		{
			itemName.setText(jsonObj.getString("name"));
			itemKind.setText(jsonObj.getString("kind"));
			maxPrice.setText(jsonObj.getString("maxPrice"));
			itemRemark.setText(jsonObj.getString("desc"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		DialogUtil.showDialog(getActivity(), detailView);
	}
}