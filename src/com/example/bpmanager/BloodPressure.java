package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.bpmanager.DB.DBBloodPressure;
import com.example.bpmanager.DB.DBUser;
import com.example.bpmanager.DB.DBhandler;
/**
 * 
 * @author Kyun
 *
 * 1. 혈압을 디비에 삽입
 * 2. 원하는 날짜까지 디비에서 혈압들을 조회
 * 3. 목표 혈압 출력
 * 4. 마지막 입력값 출력
 * 
 */
public class BloodPressure {
	private int systolic;
	private int diastolic;
	private String datetime;
	
//	SimpleDateFormat sm = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS", Locale.KOREA);
	public static SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd");
	
	public BloodPressure(int sys, int rel){
		this.systolic = sys;
		this.diastolic = rel;
		this.datetime = null;
	}
	
	public BloodPressure(int sys, int rel, String date){
		this.systolic = sys;
		this.diastolic = rel;
		this.datetime = date;
	}
	
	public void setSystolic(int systolic){
		this.systolic = systolic;
	}

	public int getSystolic(){
		return this.systolic;
	}
	
	public void setDiastolic(int relaxation){
		this.diastolic = relaxation;
	}
	
	public int getDiastolic(){
		return this.diastolic;
	}
	
	public String getDatetime(){
		return this.datetime;
	}
	
	public void setDatetime(String datetime){
		this.datetime = datetime;
	}
	
	// DB에 혈압을 추가
	public static long insertToDB(BloodPressure bp){
		ContentValues values = new ContentValues();
		values.put(DBBloodPressure.BloodPressure.COLUMN_SYS, bp.getSystolic());
		values.put(DBBloodPressure.BloodPressure.COLUMN_DIA, bp.getDiastolic());
		values.put(DBBloodPressure.BloodPressure.COLUMN_LAST_UPDATETIME, bp.getDatetime());
		
		if (IsTodayInputDone(bp.getDatetime()))
		{
			return MainActivity.mDBHelper.updateData(DBBloodPressure.BloodPressure.TB_NAME, values, " last_update_time = " + bp.getDatetime(), null);
		}
		else
		{
			return MainActivity.mDBHelper.insertData(DBBloodPressure.BloodPressure.TB_NAME, values);
		}	
	}
	
	// where문으로 혈압 조회
	public static List<BloodPressure> getBPsList(String where){
		List<BloodPressure> bps = new ArrayList<BloodPressure>();
		
		String strQry = "Select * FROM " + DBBloodPressure.BloodPressure.TB_NAME + " where " + where;
		//Log.i("log: ", strQry);
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery(strQry, null);
			//Log.i("log: ", "" + cursor.getCount());
			
			while (cursor.moveToNext())
			{
				int iSystolic = cursor.getInt(cursor.getColumnIndex(DBBloodPressure.BloodPressure.COLUMN_SYS));
				int iDiastolic= cursor.getInt(cursor.getColumnIndex(DBBloodPressure.BloodPressure.COLUMN_DIA));
				String strDate = cursor.getString(cursor.getColumnIndex(DBBloodPressure.BloodPressure.COLUMN_LAST_UPDATETIME));	
			
				if (iSystolic > 0 && iDiastolic > 0 && strDate != null)
					bps.add(new BloodPressure(iSystolic, iDiastolic, strDate));
			}
			
			cursor.close();
		}
		catch(SQLiteException e)
		{
		}
		return bps;
	}
	
	// 원하는 날짜까지 디비에서 혈압들을 조회
	public static List<BloodPressure> getLastBPsList(int termday){
		
		String whereClause;
		Calendar c = Calendar.getInstance();
		
		c.add(Calendar.DATE, -1 * termday);
		whereClause = "last_update_time > '" + sm.format(c.getTime()) + "'";
		Log.i("log: ", whereClause);
		return getBPsList(whereClause);
	}
	
	// 오늘 혈압 입력값이 있는지?
	public static boolean IsTodayInputDone(String date)
	{
		boolean ret = false;
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			Cursor c1 = db.rawQuery("select * from " + DBBloodPressure.BloodPressure.TB_NAME + " where last_update_time = '" + date + "'", null);
			
			ret = c1.getCount() > 0;
			
			c1.close();
		}
		catch (SQLiteException e)
		{
			ret = false;
		}
		return ret;
	}
	
	// 목표 혈압 조회
	public static BloodPressure getRecommendBloodPressure(){
		if(!MainActivity.mUserData.IsLoaded()) return null;
		
		int age = MainActivity.mUserData.getAge(); 
		boolean isGlucose = MainActivity.mUserData.hasGlucoseDisease();
		boolean isKidney = MainActivity.mUserData.hasKidneyDisease(); 
		
		int recommendSystolic = 150;
		int recommendDiastolic = 90;
		if(age > 60 && !( isGlucose || isKidney )) recommendSystolic = 150;
		else recommendSystolic = 140;	
		
		return new BloodPressure(recommendSystolic,recommendDiastolic);
	}
	
	// 최종혈압 입력일이 한 달이 지났는지 확인
	public static boolean IsExpiredBPData(){
		if(getLastBPsList(30).size() > 0) return false;
		else return true;
	}
	
}
