package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.bpmanager.DB.DBMedication;
import com.example.bpmanager.DB.INFOMedication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class MedicationScheduleData {

	private Context mContext;
	ArrayList<MedicationSchedule> mData;
	
	public class MedicationSchedule
	{
		public int mId;
		public int mAmount;
		public int mCount;
		public String mInjectTime;
	}		
	
	boolean mLoaded;
	boolean mAdded;
	
	public MedicationScheduleData(Context c)
	{
		mContext = c;
		mData = new ArrayList<MedicationScheduleData.MedicationSchedule>();		
		
		mLoaded = false;
		mAdded = false;
	}
	
	public boolean getData()
	{
		mData.clear();
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedication.Medication.COLUMN_MEDID,
				DBMedication.Medication.COLUMN_AMOUNT,
				DBMedication.Medication.COLUMN_COUNT,
				DBMedication.Medication.COLUMN_INJECT_TIME
			};
			
			Cursor c1 = db.query(DBMedication.Medication.TB_NAME, projection, null, null, null, null, null);
			
			while (c1.moveToNext())
			{
				MedicationSchedule ms = new MedicationSchedule();
				ms.mId = c1.getInt(c1.getColumnIndex(DBMedication.Medication.COLUMN_MEDID));
				ms.mAmount = c1.getInt(c1.getColumnIndex(DBMedication.Medication.COLUMN_AMOUNT));
				ms.mCount = c1.getInt(c1.getColumnIndex(DBMedication.Medication.COLUMN_COUNT));
				ms.mInjectTime = c1.getString(c1.getColumnIndex(DBMedication.Medication.COLUMN_INJECT_TIME));
				
				mData.add(ms);
			}
			
			c1.close();
			
			mLoaded = true;
		}
		catch (SQLiteException e)
		{
			mLoaded = false;
		}
		return mLoaded;
	}
	
	public void submitData(int medicineID)
	{
		MedicationSchedule ms = getSchedule(medicineID);
		if (ms == null)
			return;
		
		ContentValues values = new ContentValues();
		values.put(DBMedication.Medication.COLUMN_MEDID, ms.mId);
		values.put(DBMedication.Medication.COLUMN_AMOUNT, ms.mAmount);
		values.put(DBMedication.Medication.COLUMN_COUNT, ms.mCount);
		values.put(DBMedication.Medication.COLUMN_INJECT_TIME, ms.mInjectTime);
		
		if (mAdded)
		{
			mAdded = false;
			MainActivity.mDBHelper.insertData(DBMedication.Medication.TB_NAME, values);
		}
		else
		{
			MainActivity.mDBHelper.updateData(DBMedication.Medication.TB_NAME, values, " medicine_id = " + ms.mId, null);
		}
		// 알림설정
		INFOMedication info = INFOMedication.getInfoMedicine(medicineID);
		Intent alarmIntent = new Intent(mContext, AlarmReciever.class);
		alarmIntent.putExtra("type", "MEDICINE");
		alarmIntent.putExtra("medicineId", info.mId);
		alarmIntent.putExtra("medicineName", info.mName);
		PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(mContext, info.mId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		String[] times = ms.mInjectTime.split(":");
		int hour = Integer.parseInt(times[0]);
		int minute = Integer.parseInt(times[1]);
		Calendar c = Calendar.getInstance();
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
		
		if (Calendar.getInstance().getTimeInMillis() < c.getTimeInMillis())
		{
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 60 * 24, alarmPendingIntent);
		}
		else
		{
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 1000 * 60 * 60 * 24, 1000 * 60 * 60 * 24, alarmPendingIntent);
		}
				
		mData.clear();
		this.getData();
		
		// history data
		MainActivity.mMediHistData.resetData();
	}
	
	public boolean addSchedule(int medicineID, int amount, int count, String time)
	{
		MedicationSchedule ms = getSchedule(medicineID);
		if (ms != null)
		{
			updateSchedule(medicineID, amount, count, time);
			return true;
		}
		
		ms = new MedicationSchedule();
		ms.mId = medicineID;
		ms.mAmount = amount;
		ms.mCount = count;
		ms.mInjectTime = time;
		
		mData.add(ms);
		mAdded = true;
		
		return true;
	}
	
	public boolean updateSchedule(int medicineID, int amount, int count, String time)
	{
		MedicationSchedule ms = getSchedule(medicineID);
		if (ms == null)
			return false;
		
		ms.mAmount = amount;
		ms.mCount = count;
		ms.mInjectTime = time;
		
		return true;
	}
	
	public MedicationSchedule getSchedule(int medicineID)
	{
		MedicationSchedule ms = null;
		for (int i = 0; i < mData.size(); i++)
		{
			if (mData.get(i).mId == medicineID)
			{
				ms = mData.get(i);
				break;
			}
		}
		return ms;
	}
	
	public ArrayList<MedicationSchedule> getDataList()
	{
		return mData;
	}
	
	public String buildDataString()
	{
		String ret = "";
		
		ret += "ID/Amount/Count/InjectionTime\n";
		for (int i = 0; i < mData.size(); i++)
		{
			MedicationSchedule ms = mData.get(i);
			ret += ms.mId + "/" + ms.mAmount + "/" + ms.mCount + "/" + ms.mInjectTime + "\n";
		}		
		
		return ret;
	}
}
