package com.example.bpmanager;

import java.util.ArrayList;

import com.example.bpmanager.DB.DBMedication;
import com.example.bpmanager.DB.DBUser;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class MedicationScheduleData {

	ArrayList<MedicationSchedule> mData;
	
	private class MedicationSchedule
	{
		public int mId;
		public int mAmount;
		public int mCount;
	}		
	
	boolean mLoaded;
	
	public MedicationScheduleData()
	{
		mData = new ArrayList<MedicationScheduleData.MedicationSchedule>();		
		
		mLoaded = false;
	}
	
	public boolean getData()
	{
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedication.Medication.COLUMN_MEDIID,
				DBMedication.Medication.COLUMN_AMOUNT,
				DBMedication.Medication.COLUMN_COUNT
			};
			
			Cursor c1 = db.query(DBMedication.Medication.TB_NAME, projection, null, null, null, null, null);
			
			while (c1.moveToNext())
			{
				MedicationSchedule ms = new MedicationSchedule();
				ms.mId = c1.getInt(c1.getColumnIndex(DBMedication.Medication.COLUMN_MEDIID));
				ms.mAmount = c1.getInt(c1.getColumnIndex(DBMedication.Medication.COLUMN_AMOUNT));
				ms.mCount = c1.getInt(c1.getColumnIndex(DBMedication.Medication.COLUMN_COUNT));
				
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
	
	public void submitData()
	{
		ContentValues values = new ContentValues();
	
	}
}
