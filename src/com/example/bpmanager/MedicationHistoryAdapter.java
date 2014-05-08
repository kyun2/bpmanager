package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class MedicationHistoryAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<Float> mList = null;
	private ArrayList<Integer> mScheduleData = null;
	
	
	public MedicationHistoryAdapter(Context c, ArrayList<Float> items, ArrayList<Integer> schedule)
	{
		mContext = c;
		mList = items;
		mScheduleData = schedule;
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
		
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view;
		TextView tv_date;
		TextView tv_rate;
		
		if (convertView == null)
		{
			view = inflater.inflate(R.layout.calendaritem, parent, false);
		}
		else
		{
			view = convertView;
		}
		tv_date = (TextView) view.findViewById(R.id.tv_medhist_date);
		tv_rate = (TextView) view.findViewById(R.id.tv_medhist_rate);
		
		// date
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1 * (mList.size() - 1 - position));
		tv_date.setText(String.format("%d/%d", c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH)));
		
		// rate
		int scheduleCount = mScheduleData.get(mList.size() - 1 - position);		
		if (scheduleCount > 0)
		{
			if (getItem(position) == 1f)
			{
				tv_rate.setBackgroundColor(Color.GREEN);
			}
			else
			{
				tv_rate.setBackgroundColor(Color.RED);
			}
			tv_rate.setText(Math.round(getItem(position) * 100) + "%");
		}
		else
		{
			tv_rate.setBackgroundColor(Color.GRAY);
			tv_rate.setText("-");
		}		
		
		return view;		
	}

}
