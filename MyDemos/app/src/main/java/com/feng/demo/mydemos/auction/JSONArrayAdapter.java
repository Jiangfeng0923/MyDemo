/**
 *
 */
package com.feng.demo.mydemos.auction;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feng.demo.mydemos.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONArrayAdapter extends BaseAdapter
{
	private Context ctx;
	private JSONArray jsonArray;
	private String property;
	private boolean hasIcon;
	public JSONArrayAdapter(Context ctx
			, JSONArray jsonArray, String property
			, boolean hasIcon)
	{
		this.ctx = ctx;
		this.jsonArray = jsonArray;
		this.property = property;
		this.hasIcon = hasIcon;
	}

	@Override
	public int getCount()
	{
		return jsonArray.length();
	}

	@Override
	public Object getItem(int position)
	{
		return jsonArray.optJSONObject(position);
	}

	@Override
	public long getItemId(int position)
	{
		try
		{
			return ((JSONObject)getItem(position)).getInt("id");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LinearLayout linear = new LinearLayout(ctx);
		linear.setOrientation(0);
		ImageView iv = new ImageView(ctx);
		iv.setPadding(10, 0, 20, 0);
		iv.setImageResource(R.drawable.item);
		linear.addView(iv);
		TextView tv = new TextView(ctx);
		try
		{
			String itemName = ((JSONObject)getItem(position))
				.getString(property);
			tv.setText(itemName);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		tv.setTextSize(20);
		if (hasIcon)
		{
			linear.addView(tv);
			return linear;
		}
		else
		{
			return tv;
		}
	}
}