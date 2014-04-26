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
 * ���� �Լ�
 * 1. ������ ��� ����
 * 2. ���ϴ� ��¥���� ��񿡼� ���е��� ��ȸ
 * 3. ��ǥ ���� ���
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
	
	
	public static long insertToDB(BloodPressure bp){
		ContentValues values = new ContentValues();
		values.put(DBBloodPressure.BloodPressure.COLUMN_SYS, bp.getSystolic());
		values.put(DBBloodPressure.BloodPressure.COLUMN_DIA, bp.getDiastolic());
		values.put(DBBloodPressure.BloodPressure.COLUMN_LAST_UPDATETIME, bp.getDatetime());
		
		return MainActivity.mDBHelper.insertData(DBBloodPressure.BloodPressure.TB_NAME, values);
	}
	
	public static BloodPressure getRecommendBloodPressure(){
		return new BloodPressure(150,90);
	}
	
	
	
}
