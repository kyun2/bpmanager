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
	public void insertUserData(String name, String email, int sex, String birthdate, float height, float weight, float waist, int hypertension, int glucose, int kidney, int coronary)
	{
		try
		{
			SQLiteDatabase db = getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(DBUser.User.COLUMN_NAME, name);
			values.put(DBUser.User.COLUMN_SEX, sex);
			values.put(DBUser.User.COLUMN_BIRTH, birthdate);
			values.put(DBUser.User.COLUMN_HEIGHT, height);
			values.put(DBUser.User.COLUMN_WEIGHT, weight);
			values.put(DBUser.User.COLUMN_WAIST, waist);
			values.put(DBUser.User.COLUMN_HYPER, hypertension);
			values.put(DBUser.User.COLUMN_GLUCOSE, glucose);
			values.put(DBUser.User.COLUMN_KIDNEY, kidney);
			values.put(DBUser.User.COLUMN_CORONARY, coronary);
			
			long rowId = db.insert(DBUser.User.TB_NAME, null, values);
		}
		catch (SQLiteException e)
		{
		
		}
	}
	
	public void isUserRegistered()
	{
		try
		{
			SQLiteDatabase db = getReadableDatabase();
			
			
		}
		catch (SQLiteException e)
		{
			Log.e("DB: ", "Open Failed");
		}
	}
}
