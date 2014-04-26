package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;

import com.example.bpmanager.DB.DBBloodPressure;
/**
 * 
 * @author Kyun
 *
 * 제공 함수
 * 1. 혈압을 디비에 삽입
 * 2. 원하는 날짜까지 디비에서 혈압들을 조회
 * 3. 목표 혈압 출력
 * 
 */
public class BloodPressure {
	private int systolic;
	private int diastolic;
	private String datetime;
	
//	SimpleDateFormat sm = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS", Locale.KOREA);
	
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
	
	//DB에 혈압을 추가
	public static long insertToDB(BloodPressure bp){
		ContentValues values = new ContentValues();
		values.put(DBBloodPressure.BloodPressure.COLUMN_SYS, bp.getSystolic());
		values.put(DBBloodPressure.BloodPressure.COLUMN_DIA, bp.getDiastolic());
		values.put(DBBloodPressure.BloodPressure.COLUMN_LAST_UPDATETIME, bp.getDatetime());
		
		return MainActivity.mDBHelper.insertData(DBBloodPressure.BloodPressure.TB_NAME, values);
	}
	
	//추천 혈압 조회
	public static BloodPressure getRecommendBloodPressure(){
		
		int age = MainActivity.mUserData.getAge(); 
		boolean isGlucose = MainActivity.mUserData.hasGlucoseDisease();
		boolean isKidney = MainActivity.mUserData.hasKidneyDisease(); 
		
		int recommendSystolic = 0;
		int recommendDiastolic = 90;
		if(age > 60 && !( isGlucose || isKidney )) recommendSystolic = 150;
		else recommendSystolic = 140;	
		
		return new BloodPressure(recommendSystolic,recommendDiastolic);
	
	}
	
	//최종 혈압 조회
	
}
