package com.example.bpmanager.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper 
{
	public static final int DATABASE_VERSION = 1;
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
		
		// Refresh
		onCreate(db);
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		onUpgrade(db, oldVersion, newVersion);
	}
	
	// Custom Methods ----------------------------------------
	public void insertData(String dbName, ContentValues values)
	{
		try
		{
			SQLiteDatabase db = getWritableDatabase();
			
			db.insert(dbName, null, values);
		}
		catch (SQLiteException e)
		{
			
		}
	}
}
