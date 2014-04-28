package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.bpmanager.DB.DBMedication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class MedicationScheduleData {

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
	
	public MedicationScheduleData()
	{
		mData = new ArrayList<MedicationScheduleData.MedicationSchedule>();		
		
		mLoaded = false;
		mAdded = false;
	}
	
	public boolean getData()
	{
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
		mData.clear();
		this.getData();
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
}
