package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Pair;

import com.example.bpmanager.DB.DBMedication;
import com.example.bpmanager.DB.DBMedicationTook;

public class MedicationHistoryData {
	
	ArrayList<Float> mDataList;	
	ArrayList<Integer> mTookData;
	int mScheduledCount;
	
	boolean mLoaded;
	
	public MedicationHistoryData()
	{
		mDataList = new ArrayList<Float>();
		mTookData = new ArrayList<Integer>();
		mScheduledCount = 0;
		
		// get data
		getScheduleData();
		getTookData();
	}
	
	public boolean isLoaded()
	{
		return mLoaded;
	}
	
	public Pair<Integer, Integer> setTookRatioDataList(int period)
	{
		int tookCount = 0;
		int scheduledCount = 0;
		
		mDataList.clear();
		for (int i = 0; i < period; i++)
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
		
		return Pair.create(tookCount, scheduledCount);
	}	

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
	
	public void resetData()
	{
		getDataList();
		getScheduleData();
	}	
	
	// getter & setter
	public ArrayList<Float> getDataList()
	{
		return mDataList;
	}
	
	public ArrayList<Integer> getTookDataList()
	{
		return mTookData;
	}
	
	public int getScheduledCount()
	{
		return mScheduledCount;
	}
}
