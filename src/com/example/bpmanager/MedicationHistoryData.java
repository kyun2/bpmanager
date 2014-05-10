package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.util.Pair;

import com.example.bpmanager.MedicationScheduleData.MedicationSchedule;
import com.example.bpmanager.DB.DBMedication;
import com.example.bpmanager.DB.DBMedicationTook;
import com.example.bpmanager.DB.INFOMedication;

public class MedicationHistoryData {
	
	public static int mPeriod = 365;
	
	// 섭취율
	ArrayList<Float> mDataList;
	// 먹은 갯수
	ArrayList<Integer> mTookData;
	// 먹어야 하는 갯수
	ArrayList<Integer> mScheduledCount;
	
	boolean mLoaded;
	
	public MedicationHistoryData()
	{
		mDataList = new ArrayList<Float>();

		mTookData = new ArrayList<Integer>(mPeriod);
		mScheduledCount = new ArrayList<Integer>(mPeriod);
		
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
		period = Math.min(period, mPeriod);
		
		int tookCount = 0;
		int scheduledCount = 0;
		
		mDataList.clear();
		for (int i = 0; i < period; i++)
		{
			int t = mTookData.get(i);
			int s = mScheduledCount.get(i);
			
			if (s > 0)
			{
				mDataList.add((float)t / (float)s);
				
				tookCount += t;
				scheduledCount += s;
			}
			else
			{
				mDataList.add(0f);
			}
		}
		
		return Pair.create(tookCount, scheduledCount);
	}	

	void getTookData()
	{
		mTookData.clear();
		Calendar fromDay = Calendar.getInstance();
		Calendar toDay = Calendar.getInstance();
		
		for (int i = 0; i < mPeriod; i++)
		{
			mTookData.add(0);
		}
		
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
				
				mTookData.set(diffDay, mTookData.get(diffDay)+1);
			}
			
			c1.close();
		}
		catch (SQLiteException e)
		{
		}
	}
	
	void getScheduleData()
	{
		mScheduledCount.clear();
		//mScheduledCount = 0;
		
		ArrayList<MedicationSchedule> msData = MainActivity.mMedicationScheduleData.getDataList();
		
		for (int i = 0; i < mPeriod; i++)
		{
			mScheduledCount.add(0);
		}
		
		Calendar today = Calendar.getInstance();
		int t_year = today.get(Calendar.YEAR);
		int t_month = today.get(Calendar.MONTH);
		int t_day = today.get(Calendar.DAY_OF_MONTH);
		today.clear();
		today.set(t_year, t_month, t_day);		
		for (int i = 0; i < msData.size(); i++)
		{
			int firstIndex, lastIndex;
			
			// first index
			if (msData.get(i).mEndTime.equals("0000/00/00"))
			{
				firstIndex = 0;
			}
			else
			{
				String[] endtime = msData.get(i).mEndTime.split("/");
				int e_year = Integer.parseInt(endtime[0]);
				int e_month = Integer.parseInt(endtime[1]) - 1;
				int e_day = Integer.parseInt(endtime[2]);
				Calendar e_c = Calendar.getInstance();
				e_c.clear();
				e_c.set(e_year, e_month, e_day);
				
				long e_diffSec = (today.getTimeInMillis() - e_c.getTimeInMillis()) / 1000;
				int e_diffDay = (int)(e_diffSec / (60 * 60 * 24));
				
				// 종료일이 오늘이면 포함하지 않는다.
				firstIndex = e_diffDay + 1;
			}

			// last index
			String[] starttime = msData.get(i).mStartTime.split("/");
			int s_year = Integer.parseInt(starttime[0]);
			int s_month = Integer.parseInt(starttime[1]) - 1;
			int s_day = Integer.parseInt(starttime[2]);
			Calendar s_c = Calendar.getInstance();
			s_c.clear();
			s_c.set(s_year, s_month, s_day);
			
			long s_diffSec = (today.getTimeInMillis() - s_c.getTimeInMillis()) / 1000;
			int s_diffDay = (int)(s_diffSec / (60 * 60 * 24));
			
			lastIndex = Math.min(s_diffDay, mPeriod);
			
			for (int i2 = firstIndex; i2 <= lastIndex; i2++)
			{
				mScheduledCount.set(i2, mScheduledCount.get(i2) + 1);
			}
		}
	}
	
	public void resetData()
	{
		getScheduleData();
		getTookData();
	}	
	
	public String buildDataString()
	{
		String ret = "";
		
		ret += "약물ID/약물이름/복용시간\n";
		
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
				int id = c1.getInt(c1.getColumnIndex(DBMedicationTook.SCHEMA.COLUMN_MEDID));
				String time = c1.getString(c1.getColumnIndex(DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME));
				INFOMedication info = INFOMedication.getInfoMedicine(id);
				
				ret += id + "/" + info.mName + "/" + time + "\n";
			}
			
			c1.close();
		}
		catch(SQLiteException e)
		{
		}
		
		return ret;
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
	
	public ArrayList<Integer> getScheduledCount()
	{
		return mScheduledCount;
	}
}
