package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.bpmanager.DB.DBMedication;
import com.example.bpmanager.DB.DBMedicationTook;
import com.example.bpmanager.MedicationScheduleData.MedicationSchedule;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class MedicationHistoryFragment extends Fragment {
	
	public enum PeriodType
	{
		week,
		month
	}
	
	RadioButton mWeeklyBtn;
	RadioButton mMonthlyBtn;
	RadioGroup mPeriod;
	
	TextView mAccomplish;
	RatingBar mAccomplishBar;
	
	GridView mData;
	ArrayList<Float> mDataList;
	
	ArrayList<Integer> mTookData;
	int mScheduledCount;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_history, container, false);
		
		mWeeklyBtn = (RadioButton) view.findViewById(R.id.radio_period_weekly);
		mMonthlyBtn = (RadioButton) view.findViewById(R.id.radio_period_monthly);
		mPeriod = (RadioGroup) view.findViewById(R.id.radio_period);
		
		mAccomplish = (TextView) view.findViewById(R.id.text_accomplish);
		mAccomplishBar = (RatingBar) view.findViewById(R.id.ratingbar_accomplish);
		
		mData = (GridView) view.findViewById(R.id.grid_history_data);
		mDataList = new ArrayList<Float>();
		
		// click bind
		mWeeklyBtn.setOnClickListener(clickListener);
		mMonthlyBtn.setOnClickListener(clickListener);
		
		mTookData = new ArrayList<Integer>();
		mScheduledCount = 0;
		
		// get data
		getScheduleData();
		getTookData();
		
		// Init
		setPeriod(PeriodType.week);

		return view;
	}
	
	public void setPeriod(PeriodType pt)
	{
		int tookCount = 0;
		int scheduledCount = 0;
		switch (pt)
		{
		case week:
			mDataList.clear();
			for (int i = 0; i < 7; i++)
			{
				if (i < mTookData.size())
				{
					mDataList.add((float)mTookData.get(i) / (float)mScheduledCount);
					
					tookCount += mTookData.get(i);
					scheduledCount += mScheduledCount;
				}
				else
				{
					mDataList.add(-1f);
				}
			}
			mData.setAdapter(new MedicationHistoryAdapter(getActivity(), mDataList));
			break;
			
		case month:
			mDataList.clear();
			for (int i = 0; i < 28; i++)
			{
				if (i < mTookData.size())
				{
					mDataList.add((float)mTookData.get(i) / (float)mScheduledCount);
					
					tookCount += mTookData.get(i);
					scheduledCount += mScheduledCount;
				}
				else
				{
					mDataList.add(-1f);
				}
			}
			mData.setAdapter(new MedicationHistoryAdapter(getActivity(), mDataList));
			break;
		}
		
		// 퍼센트
		float percent = (float)tookCount / (float)scheduledCount;
		mAccomplish.setText(Integer.toString(Math.round(percent * 100f)));
		mAccomplishBar.setRating(percent * (float)mAccomplishBar.getNumStars());
	}
	
	View.OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId())
			{
			case R.id.radio_period_weekly:
				setPeriod(PeriodType.week);
				break;
				
			case R.id.radio_period_monthly:
				setPeriod(PeriodType.month);
				break;
			}
		}
	};
	
	void getTookData()
	{
		mTookData.clear();
		Calendar fromDay = Calendar.getInstance();
		Calendar toDay = Calendar.getInstance();
		
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedicationTook.SCHEMA.COLUMN_MEDID,
				DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME
			};
			
			Cursor c1 = db.query(DBMedicationTook.SCHEMA.TB_NAME, projection, null, null, null, null, null);
			
			while (c1.moveToNext())
			{
				//int id = c1.getInt(c1.getColumnIndex(DBMedicationTook.SCHEMA.COLUMN_MEDID));
				String[] times = c1.getString(c1.getColumnIndex(DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME)).split("/");
				int timeYear = Integer.parseInt(times[0]);
				int timeMonth = Integer.parseInt(times[1]) - 1;
				int timeDay = Integer.parseInt(times[2]);
				
				fromDay.set(timeYear, timeMonth, timeDay);
				
				long diffSec = (toDay.getTimeInMillis() - fromDay.getTimeInMillis()) / 1000;
				int diffDay = (int)(diffSec / (60 * 60 * 24));
				
				if (diffDay < mTookData.size())
				{
					mTookData.set(diffDay, mTookData.get(diffDay)+1);
				}
				else
				{
					mTookData.add(1);
				}
			}
			
			c1.close();
		}
		catch (SQLiteException e)
		{
		}
	}
	
	void getScheduleData()
	{
		mScheduledCount = 0;
		
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedication.Medication.COLUMN_MEDID
			};
			
			Cursor c1 = db.query(DBMedication.Medication.TB_NAME, projection, null, null, null, null, null);
			mScheduledCount = c1.getCount();
			
			c1.close();			
		}
		catch (SQLiteException e)
		{			
		}
	}
}
