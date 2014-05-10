package com.example.bpmanager;

import java.util.Calendar;

import com.example.bpmanager.DB.DBUser;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.format.DateUtils;
import android.util.Log;

public class UserData {
	
	String mName;
	String mEmail;
	int mSex;
	String mBirth;
	float mHeight;
	//float mWeight;
	//float mWaist;
	int mHypertension;
	int mGlucose;
	int mKidney;
	int mCoronary;
	
	String mLastVisitDate;
	String mNextVisitDate;	
	long mNextVisitAlarmTime;
	public static final int ALARM_ID = 10000;
	
	String mPassword;
	
	boolean mLoaded;
	
	
	public UserData()
	{
		mName = "";
		mEmail = "";
		mSex = 0;
		mBirth = "";
		mHeight = 0f;
		//mWeight = 0f;
		//mWaist = 0f;
		mHypertension = 0;
		mGlucose = 0;
		mKidney = 0;
		mCoronary = 0;
		
		mLastVisitDate = "";
		mNextVisitDate = "";
		mNextVisitAlarmTime = 0;
		
		mPassword = "";
		
		mLoaded = false;
	}
	
	public boolean getData()
	{
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBUser.User.COLUMN_NAME,
				DBUser.User.COLUMN_EMAIL,
				DBUser.User.COLUMN_SEX,
				DBUser.User.COLUMN_BIRTH,
				DBUser.User.COLUMN_HEIGHT,
				//DBUser.User.COLUMN_WEIGHT,
				//DBUser.User.COLUMN_WAIST,
				DBUser.User.COLUMN_HYPER,
				DBUser.User.COLUMN_GLUCOSE,
				DBUser.User.COLUMN_KIDNEY,
				DBUser.User.COLUMN_CORONARY,
				DBUser.User.COLUMN_LASTVISIT,
				DBUser.User.COLUMN_NEXTVISIT,
				DBUser.User.COLUMN_NEXTVISIT_ALARMTIME,
				DBUser.User.COLUMN_PASSWORD
			};
			
			Cursor c1 = db.query(DBUser.User.TB_NAME, projection, null, null, null, null, null);
			
			if (c1.getCount() > 0)
				mLoaded = true;
			
			while (c1.moveToNext())
			{
				mName = c1.getString(c1.getColumnIndex(DBUser.User.COLUMN_NAME));
				mEmail = c1.getString(c1.getColumnIndex(DBUser.User.COLUMN_EMAIL));
				mSex = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_SEX));
				mBirth = c1.getString(c1.getColumnIndex(DBUser.User.COLUMN_BIRTH));
				mHeight = c1.getFloat(c1.getColumnIndex(DBUser.User.COLUMN_HEIGHT));
				//mWeight = c1.getFloat(c1.getColumnIndex(DBUser.User.COLUMN_WEIGHT));
				//mWaist = c1.getFloat(c1.getColumnIndex(DBUser.User.COLUMN_WAIST));
				mHypertension = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_HYPER));
				mGlucose = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_GLUCOSE));
				mKidney = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_KIDNEY));
				mCoronary = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_CORONARY));
				mLastVisitDate = c1.getString(c1.getColumnIndex(DBUser.User.COLUMN_LASTVISIT));
				mNextVisitDate = c1.getString(c1.getColumnIndex(DBUser.User.COLUMN_NEXTVISIT));
				mNextVisitAlarmTime = c1.getLong(c1.getColumnIndex(DBUser.User.COLUMN_NEXTVISIT_ALARMTIME));
				mPassword = c1.getString(c1.getColumnIndex(DBUser.User.COLUMN_PASSWORD));
			}
			
			c1.close();
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
		values.put(DBUser.User.COLUMN_NAME, mName);
		values.put(DBUser.User.COLUMN_EMAIL, mEmail);
		values.put(DBUser.User.COLUMN_SEX, mSex);
		values.put(DBUser.User.COLUMN_BIRTH, mBirth);
		values.put(DBUser.User.COLUMN_HEIGHT, mHeight);
		//values.put(DBUser.User.COLUMN_WEIGHT, mWeight);
		//values.put(DBUser.User.COLUMN_WAIST, mWaist);
		values.put(DBUser.User.COLUMN_HYPER, mHypertension);
		values.put(DBUser.User.COLUMN_GLUCOSE, mGlucose);
		values.put(DBUser.User.COLUMN_KIDNEY, mKidney);
		values.put(DBUser.User.COLUMN_CORONARY, mCoronary);
		values.put(DBUser.User.COLUMN_LASTVISIT, mLastVisitDate);
		values.put(DBUser.User.COLUMN_NEXTVISIT, mNextVisitDate);
		values.put(DBUser.User.COLUMN_NEXTVISIT_ALARMTIME, mNextVisitAlarmTime);
		values.put(DBUser.User.COLUMN_PASSWORD, mPassword);

		if (IsLoaded())
			MainActivity.mDBHelper.updateData(DBUser.User.TB_NAME, values, null, null);
		else
			MainActivity.mDBHelper.insertData(DBUser.User.TB_NAME, values);
		this.getData();
	}
	
	public boolean IsLoaded()
	{
		return mLoaded;
	}
	
	public boolean hasPassword()
	{
		return mPassword.length() > 0;
	}
	
	public int getAge()
	{
		int age = 0;
		
		if (mBirth != "")
		{
			String[] strs = mBirth.split("/");
			if (strs.length > 0)
			{
				Calendar currentDate = Calendar.getInstance();
				int currYear = currentDate.get(Calendar.YEAR);
				age = currYear - Integer.parseInt(strs[0]);
			}
		}
		return age;
	}
	
	public boolean hasGlucoseDisease()
	{
		return (mGlucose == 1);
	}
	
	public boolean hasKidneyDisease()
	{
		return (mKidney == 1);
	}
	
	public void setNextAlarmTime(int hour, int minute)
	{
		mNextVisitAlarmTime = getNextAlarmTime(hour, minute);
	}
	
	public long getNextAlarmTime(int hour, int minute)
	{
		if (mNextVisitDate == "")
			return 0;
		
		String[] date = mNextVisitDate.split("/");
		int year = Integer.parseInt(date[0]);
		int month = Integer.parseInt(date[1]) - 1;
		int day = Integer.parseInt(date[2]);		
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, minute, 0);
		// 전날
		c.add(Calendar.DAY_OF_MONTH, -1);
		
		return c.getTimeInMillis();
	}
	
	public String getAlarmData()
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(mNextVisitAlarmTime);
		
		return String.format("%02d:%02d", c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
	}
	
	public void setAlarm(Context context)
	{
		// 앞으로 시간이 남은 경우
		if (mNextVisitAlarmTime - Calendar.getInstance().getTimeInMillis() > 0)
		{
			Intent alarmIntent = new Intent(context, AlarmReciever.class);
			alarmIntent.putExtra("type", "HOSPITAL");
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			
			PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			alarmManager.set(AlarmManager.RTC_WAKEUP, mNextVisitAlarmTime, alarmPendingIntent);
		}
	}
	
	public void unsetAlarm(Context context)
	{
		Intent alarmIntent = new Intent(context, AlarmReciever.class);
		PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, alarmIntent, PendingIntent.FLAG_NO_CREATE);
		if (alarmPendingIntent != null)
		{
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(alarmPendingIntent);
			alarmPendingIntent.cancel();
		}
	}
	
	public String buildDataString()
	{
		String ret = "";
		
		ret += "Name: " + mName + "\n";
		ret += "Email: " + mEmail + "\n";
		ret += "Sex: " + mSex + "\n";
		ret += "Birthdate: " + mBirth + "\n";
		ret += "Height: " + mHeight + "\n";
		//ret += "Weight: " + mWeight + "\n";
		//ret += "Waist: " + mWaist + "\n";
		ret += "Glucose: " + mGlucose + "\n";
		ret += "Kidney: " + mKidney + "\n";
		ret += "Last Visit Date: " + mLastVisitDate + "\n";
		ret += "Next Visit Date: " + mNextVisitDate + "\n";
		
		return ret;
	}	
	
	// getter/setter
	public String getName() 
	{
		return mName;
	}

	public void setName(String name) 
	{
		this.mName = name;
	}

	public String getEmail() 
	{
		return mEmail;
	}

	public void setEmail(String email) 
	{
		this.mEmail = email;
	}

	public int getSex() 
	{
		return mSex;
	}

	public void setSex(int gender) {
		this.mSex = gender;
	}

	public String getBirth() {
		return mBirth;
	}

	public void setBirth(String birth) {
		this.mBirth = birth;
	}

	public float getHeight() {
		return mHeight;
	}

	public void setHeight(float height) {
		this.mHeight = height;
	}

	//public float getWeight() {
	//	return mWeight;
	//}

	//public void setWeight(float weight) {
	//	this.mWeight = weight;
	//}

	//public float getWaist() {
	//	return mWaist;
	//}

	//public void setWaist(float waist) {
	//	this.mWaist = waist;
	//}

	public int getHypertension() {
		return mHypertension;
	}

	public void setHypertension(int hypertension) {
		this.mHypertension = hypertension;
	}

	public int getGlucose() {
		return mGlucose;
	}

	public void setGlucose(int glucose) {
		this.mGlucose = glucose;
	}

	public int getKidney() {
		return mKidney;
	}

	public void setKidney(int kidney) {
		this.mKidney = kidney;
	}

	public int getCoronary() {
		return mCoronary;
	}

	public void setCoronary(int coronary) {
		this.mCoronary = coronary;
	}
	
	public String getLastVisitDate() {
		return this.mLastVisitDate;
	}
	
	public void setLastVisitDate(String date) {
		this.mLastVisitDate = date;
	}
	
	public String getNextVisitDate() {
		return this.mNextVisitDate;
	}
	
	public void setNextVisitDate(String date) {
		this.mNextVisitDate = date;
	}
	
	public String getPassword() {
		return this.mPassword;
	}
	
	public void setPassword(String password) {
		this.mPassword = password;
	}
}
