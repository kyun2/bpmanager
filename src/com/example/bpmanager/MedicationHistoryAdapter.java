package com.example.bpmanager;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MedicationHistoryAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<Float> mList = null;
	
	
	public MedicationHistoryAdapter(Context c, ArrayList<Float> items)
	{
		mContext = c;
		mList = items;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Float getItem(int position) {
		return mList.get(mList.size() - 1 - position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		TextView tv;
		if (convertView == null)
		{
			tv = new TextView(mContext);
			tv.setLayoutParams(new GridView.LayoutParams(85, 85));
		}
		else
		{
			tv = (TextView) convertView;
		}
		
		if (getItem(position) >= 1f)
		{
			tv.setBackgroundColor(Color.GREEN);
		}
		else if (getItem(position) == -1)
		{
			tv.setBackgroundColor(Color.GRAY);
		}
		else
		{
			tv.setBackgroundColor(Color.RED);
		}
		
		return tv;		
	}

}
