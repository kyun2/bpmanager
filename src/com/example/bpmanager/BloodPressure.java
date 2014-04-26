package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;

import com.example.bpmanager.DB.DBBloodPressure;

public class BloodPressure {
	private int systolic;
	private int diastolic;
	
	public BloodPressure(){
		this.systolic = 0;
		this.diastolic = 0;
	}
	
	public BloodPressure(int sys, int rel){
		this.systolic = sys;
		this.diastolic = rel;
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
	
	public static void insertToDB(BloodPressure bp){
		ContentValues values = new ContentValues();
		values.put(DBBloodPressure.BloodPressure.COLUMN_SYS, bp.getSystolic());
		values.put(DBBloodPressure.BloodPressure.COLUMN_DIA, bp.getDiastolic());
		SimpleDateFormat sm = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS", Locale.KOREA);
		values.put(DBBloodPressure.BloodPressure.COLUMN_LAST_UPDATETIME, sm.format(new Date()));
		MainActivity.mDBHelper.insertData(DBBloodPressure.BloodPressure.TB_NAME, values);
		//dd
	}
	
}
