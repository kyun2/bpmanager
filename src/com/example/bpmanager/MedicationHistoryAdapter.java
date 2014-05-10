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
import android.widget.LinearLayout;
import android.widget.TextView;

public class MedicationHistoryAdapter extends BaseAdapter {
	
	private Context mContext;
	private ArrayList<Float> mList = null;
	private ArrayList<Integer> mScheduleData = null;
	private Calendar mCalendar = null;
	private int mDays = 0;
	private int mDummys = 0;
		
	public MedicationHistoryAdapter(Context c, ArrayList<Float> items, ArrayList<Integer> schedule)
	{
		mContext = c;
		
		mCalendar = Calendar.getInstance();
		mDays = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DAY_OF_MONTH, 1);
		mDummys = firstDate.get(Calendar.DAY_OF_WEEK) - 1;		
		
		int today = mCalendar.get(Calendar.DAY_OF_MONTH);
		mList = new ArrayList<Float>(mDays + mDummys);
		mScheduleData = new ArrayList<Integer>(mDays + mDummys);
		for (int i = 1; i <= mDummys; i++)
		{
			mList.add(0f);
			mScheduleData.add(-2);
		}		
		for (int i = 1; i <= mDays; i++)
		{
			if (i <= today - items.size() || i > today)
			{
				mList.add(0f);
				mScheduleData.add(-1);
			}
			else
			{
				int base_idx_1 = i - today + items.size();
				int base_idx_2 = i - today + schedule.size();
				mList.add(items.get(items.size() - base_idx_1));
				mScheduleData.add(schedule.get(schedule.size() - base_idx_2));
			}
		}
	}

	@Override
	public int getCount() {
		return mDays + mDummys;
	}

	@Override
	public Float getItem(int position) {
		return mList.get(position);
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
		
		if (position < mDummys)
		{
			view = new LinearLayout(mContext);
		}
		else
		{
			if (convertView == null || convertView.getId() != R.layout.calendaritem)
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
			//c.add(Calendar.DAY_OF_MONTH, -1 * (mList.size() - 1 - position));
			tv_date.setText(String.format("%d/%d", c.get(Calendar.MONTH) + 1, position + 1 - mDummys));
			
			// rate
			//int scheduleCount = mScheduleData.get(mList.size() - 1 - position);		
			int scheduleCount = mScheduleData.get(position);
			if (scheduleCount == -1)
			{
				tv_rate.setBackgroundColor(Color.GRAY);
				tv_rate.setText("-");
			}
			else if (scheduleCount == 0)
			{
				tv_date.setBackgroundResource(R.color.btn_indigo);
				tv_date.setTextColor(Color.WHITE);
				
				tv_rate.setBackgroundColor(Color.WHITE);
				tv_rate.setText("");
			}
			else
			{
				tv_date.setBackgroundResource(R.color.btn_indigo);
				tv_date.setTextColor(Color.WHITE);
				
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
		}
		
		return view;		
	}

}
