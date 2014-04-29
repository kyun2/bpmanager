package com.example.bpmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper 
{
	public static final int DATABASE_VERSION = 4;
	public static final String DATABASE_NAME = "BPManager.db";
	
	public DBHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		// User
		db.execSQL(DBUser.SQL_CREATE);
		// BloodPressure
		db.execSQL(DBBloodPressure.SQL_CREATE);
		// Medication
		db.execSQL(DBMedication.SQL_CREATE);
		// Lifestyle
		db.execSQL(DBLifestyle.SQL_CREATE);
		// MedicationTook
		db.execSQL(DBMedicationTook.SQL_CREATE);
		//Habit
		db.execSQL(DBHabit.SQL_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
		// User
		db.execSQL(DBUser.SQL_DROP);
		// BloodPressure
		db.execSQL(DBBloodPressure.SQL_DROP);
		// Medication
		db.execSQL(DBMedication.SQL_DROP);
		// Lifestyle
		db.execSQL(DBLifestyle.SQL_DROP);
		// MedicationTook
		db.execSQL(DBMedicationTook.SQL_DROP);
		//Habit
		db.execSQL(DBHabit.SQL_DROP);
		
		// Refresh
		onCreate(db);
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		onUpgrade(db, oldVersion, newVersion);
	}
	
	// Custom Methods ----------------------------------------
	public long insertData(String tableName, ContentValues values)
	{
		long rID = -1;
		try
		{
			SQLiteDatabase db = getWritableDatabase();
			
			rID = db.insert(tableName, null, values);
			
		}
		catch (SQLiteException e)
		{
			
		}
		return rID;
	}
	
	public long updateData(String tableName, ContentValues values, String whereClause, String[] whereArgs)
	{
		long rID = -1;
		try
		{
			SQLiteDatabase db = getWritableDatabase();
			
			db.update(tableName, values, whereClause, whereArgs);
		}
		catch (SQLiteException e)
		{
			rID = -1;
		}
		return rID;
	}
	
	public void deleteData(String tableName, String whereClause, String[] whereArgs)
	{
		try
		{
			SQLiteDatabase db = getWritableDatabase();
			
			db.delete(tableName, whereClause, whereArgs);
		}
		catch (SQLiteException e)
		{
			
		}
	}
}
