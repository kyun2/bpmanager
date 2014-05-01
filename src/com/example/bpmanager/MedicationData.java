package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.bpmanager.DB.DBMedication;
import com.example.bpmanager.DB.DBMedicationTook;

public class MedicationData 
{
	public static boolean hasTookToday(int medicine_id)
	{
		boolean ret = false;
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedicationTook.SCHEMA.COLUMN_MEDID,
				DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME
			};
			
			Cursor c1 = db.query(DBMedicationTook.SCHEMA.TB_NAME, projection, " medicine_id = " + medicine_id, null, null, null, " inject_time desc");
			
			if (c1.moveToFirst())
			{
				String[] times = c1.getString(c1.getColumnIndex(DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME)).split("/");
				
				try
				{
					int day = Integer.parseInt(times[2]);
					int cday = Integer.parseInt((new SimpleDateFormat("dd")).format(Calendar.getInstance().getTime()));
					
					if (Math.abs(day - cday) == 0)
						ret = true;
					else
						ret = false;
				}
				catch (NumberFormatException e)
				{
				}
			}
			else
			{
				ret = false;
			}
			
			c1.close();
		}
		catch (SQLiteException e)
		{
			ret = false;
		}
		return ret;
	}
	
	public static String loadInjectionTime(int medicine_id)
	{
		String time = "";
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedication.Medication.COLUMN_MEDID,
				DBMedication.Medication.COLUMN_INJECT_TIME
			};
			
			Cursor c1 = db.query(DBMedication.Medication.TB_NAME, projection, " medicine_id = " + medicine_id, null, null, null, null);
			
			if (c1.moveToFirst())
			{
				time = c1.getString(c1.getColumnIndex(DBMedication.Medication.COLUMN_INJECT_TIME));
			}
			
			c1.close();
		}
		catch (SQLiteException e)
		{
		}
		return time;
	}
	
	public static void saveInjectionTime(int medicine_id, String time)
	{
		if (!time.matches("[0-9]{2}:[0-9]{2}"))
			return;
		
		ContentValues values = new ContentValues();
		values.put(DBMedication.Medication.COLUMN_INJECT_TIME, time);
		
		MainActivity.mDBHelper.updateData(DBMedication.Medication.TB_NAME, values, " medicine_id = " + medicine_id, null);
	}	
}
