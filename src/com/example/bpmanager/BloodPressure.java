<<<<<<< HEAD
package com.example.bpmanager;
/**
 * 
 * @author Kyun
 *
 * * 혈압 정보
 */
public class BloodPressure {
	
	
}
=======
package com.example.bpmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bpmanager.DB.DBBloodPressure;
import com.example.bpmanager.DB.DBUser;
import com.example.bpmanager.DB.DBhandler;
/**
 * 
 * @author Kyun
 *
 * 제공 함수
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
	
	//원하는 날짜까지 디비에서 혈압들을 조회
	public static List<BloodPressure> getLastBPsList(int termday){
		List<BloodPressure> bps = new ArrayList<BloodPressure>();
		
		String strDay = "";
	
		String where = DBBloodPressure.BloodPressure.COLUMN_LAST_UPDATETIME+" > "+strDay;
		String strQry = "";
		SQLiteDatabase db = MainActivity.mDBHelper.getWritableDatabase();
		
		Cursor cursor = db.rawQuery(strQry, null);
		//if (cursor.getCount() > 0) return null;
		while(cursor.moveToNext()){
			int iSystolic = cursor.getInt(cursor.getColumnIndex(DBBloodPressure.BloodPressure.COLUMN_SYS));
			int iDiastolic= cursor.getInt(cursor.getColumnIndex(DBBloodPressure.BloodPressure.COLUMN_DIA));
			String strDate = cursor.getString(cursor.getColumnIndex(DBBloodPressure.BloodPressure.COLUMN_LAST_UPDATETIME));	
		
			if(iSystolic > 0 && iDiastolic > 0 && strDate != null)
				bps.add(new BloodPressure(iSystolic, iDiastolic, strDate));
		}
		
		return bps;
	}
	
	//목표 혈압 조회
	public static BloodPressure getRecommendBloodPressure(){
		//**if(!MainActivity.mUserData.IsLoaded()) return null;
		
		int age = MainActivity.mUserData.getAge(); 
		boolean isGlucose = MainActivity.mUserData.hasGlucoseDisease();
		boolean isKidney = MainActivity.mUserData.hasKidneyDisease(); 
		
		int recommendSystolic = 150;
		int recommendDiastolic = 90;
		if(age > 60 && !( isGlucose || isKidney )) recommendSystolic = 150;
		else recommendSystolic = 140;	
		
		return new BloodPressure(recommendSystolic,recommendDiastolic);
	}
	
	//최종 혈압 입력일 이 한달이 지났는지 확인
	public static boolean IsExpiredBPData(){
		if(getLastBPsList(30).size() > 0) return false;
		else return true;
	}
	
}
>>>>>>> e9584c0a1ababd62aa3ac58a8314200297d8a254
