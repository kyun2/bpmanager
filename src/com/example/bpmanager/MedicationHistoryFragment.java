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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class MedicationHistoryFragment extends Fragment {
	
	public static enum PeriodType
	{
		WEEK,
		MONTH
	}
	
	RadioButton mWeeklyBtn;
	RadioButton mMonthlyBtn;
	RadioGroup mPeriod;
	
	TextView mAccomplish;
	RatingBar mAccomplishBar;
	
	GridView mData;
	
	LinearLayout mWeekTitle;
	
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
		
		mWeekTitle = (LinearLayout) view.findViewById(R.id.ll_week_title);
		
		// click bind
		mWeeklyBtn.setOnClickListener(clickListener);
		mMonthlyBtn.setOnClickListener(clickListener);
		
		// Init
		setPeriod(PeriodType.WEEK);

		return view;
	}
	
	public void setPeriod(PeriodType pt)
	{
		Pair<Integer, Integer> counts = null;
		Calendar c = Calendar.getInstance();
		int days = 0;
		switch (pt)
		{
		case WEEK:
			days = c.get(Calendar.DAY_OF_WEEK);			
			counts = MainActivity.mMediHistData.setTookRatioDataList(days);
			mWeekTitle.setVisibility(View.INVISIBLE);
			mData.setAdapter(new MedicationHistoryAdapter(getActivity(), pt, MainActivity.mMediHistData.getDataList(), MainActivity.mMediHistData.getScheduledCount()));
			break;
			
		case MONTH:
			days = c.get(Calendar.DAY_OF_MONTH);
			counts = MainActivity.mMediHistData.setTookRatioDataList(days);
			mWeekTitle.setVisibility(View.VISIBLE);
			mData.setAdapter(new MedicationHistoryAdapter(getActivity(), pt, MainActivity.mMediHistData.getDataList(), MainActivity.mMediHistData.getScheduledCount()));
			break;
		}
		
		// 퍼센트
		if (counts != null)
		{
			float percent = (float)counts.first / (float)counts.second;
			mAccomplish.setText(Integer.toString(Math.round(percent * 100f)));
			mAccomplishBar.setRating(percent * (float)mAccomplishBar.getNumStars());
		}
	}
	
	View.OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId())
			{
			case R.id.radio_period_weekly:
				setPeriod(PeriodType.WEEK);
				break;
				
			case R.id.radio_period_monthly:
				setPeriod(PeriodType.MONTH);
				break;
			}
		}
	};
}
