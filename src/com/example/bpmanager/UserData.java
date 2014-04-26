package com.example.bpmanager;

import java.util.Calendar;

import com.example.bpmanager.DB.DBUser;

import android.content.ContentValues;
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
	float mWeight;
	float mWaist;
	int mHypertension;
	int mGlucose;
	int mKidney;
	int mCoronary;
	
	Boolean mLoaded;
	
	
	public UserData()
	{
		mLoaded = false;
	}
	
	public Boolean getData()
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
				DBUser.User.COLUMN_WEIGHT,
				DBUser.User.COLUMN_WAIST,
				DBUser.User.COLUMN_HYPER,
				DBUser.User.COLUMN_GLUCOSE,
				DBUser.User.COLUMN_KIDNEY,
				DBUser.User.COLUMN_CORONARY
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
				mWeight = c1.getFloat(c1.getColumnIndex(DBUser.User.COLUMN_WEIGHT));
				mWaist = c1.getFloat(c1.getColumnIndex(DBUser.User.COLUMN_WAIST));
				mHypertension = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_HYPER));
				mGlucose = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_GLUCOSE));
				mKidney = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_KIDNEY));
				mCoronary = c1.getInt(c1.getColumnIndex(DBUser.User.COLUMN_CORONARY));
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
		values.put(DBUser.User.COLUMN_SEX, mSex);
		values.put(DBUser.User.COLUMN_BIRTH, mBirth);
		values.put(DBUser.User.COLUMN_HEIGHT, mHeight);
		values.put(DBUser.User.COLUMN_WEIGHT, mWeight);
		values.put(DBUser.User.COLUMN_WAIST, mWaist);
		values.put(DBUser.User.COLUMN_HYPER, mHypertension);
		values.put(DBUser.User.COLUMN_GLUCOSE, mGlucose);
		values.put(DBUser.User.COLUMN_KIDNEY, mKidney);
		values.put(DBUser.User.COLUMN_CORONARY, mCoronary);

		if (IsLoaded())
			MainActivity.mDBHelper.updateData(DBUser.User.TB_NAME, values, null, null);
		else
			MainActivity.mDBHelper.insertData(DBUser.User.TB_NAME, values);
	}
	
	public Boolean IsLoaded()
	{
		return mLoaded;
	}
	
	public int getAge()
	{
		int age = 0;
		
		String[] strs = mBirth.split("/");
		if (mBirth.length() > 0 && strs.length > 0)
		{
			Calendar currentDate = Calendar.getInstance();
			int currYear = currentDate.get(Calendar.YEAR);
			age = currYear - Integer.parseInt(strs[0]);
		}
		return age;
	}
	
	public Boolean hasGlucoseDisease()
	{
		return (mGlucose == 1);
	}
	
	public Boolean hasKidneyDisease()
	{
		return (mKidney == 1);
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

	public float getWeight() {
		return mWeight;
	}

	public void setWeight(float weight) {
		this.mWeight = weight;
	}

	public float getWaist() {
		return mWaist;
	}

	public void setWaist(float waist) {
		this.mWaist = waist;
	}

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

}
