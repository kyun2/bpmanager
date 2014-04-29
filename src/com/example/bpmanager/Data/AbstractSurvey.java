package com.example.bpmanager.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.bpmanager.MainActivity;
import com.example.bpmanager.DB.DBHabit;

public abstract class AbstractSurvey implements Survey {
	
	public static SimpleDateFormat sm = new SimpleDateFormat("yyyy/MM/dd");
	
	protected int getAnswer( Map<Integer,Object> q, int i){
		Object o = q.get(i);
		if(o instanceof Integer) return (Integer)o;
		else return -1;
	}
	
	@Override
	public String[] getSurveyQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	final public long insertDatatoDB(Map<Integer, Object> q) {
		ContentValues values = new ContentValues();
		values.put(DBHabit.TABLE.COLUMN_TYPE, getType());
		values.put(DBHabit.TABLE.COLUMN_ANSWERS, getAnswer(q));
		values.put(DBHabit.TABLE.COLUMN_RESULT, getResult(q));
		values.put(DBHabit.TABLE.COLUMN_UPDATE, (new SimpleDateFormat("yyyy/MM/dd")).format(Calendar.getInstance().getTime()));
		
		return MainActivity.mDBHelper.insertData(DBHabit.TABLE.NAME, values);
		
//		if (IsTodayInputDone(bp.getDatetime()))
//		{
//			return MainActivity.mDBHelper.updateData(DBBloodPressure.BloodPressure.TB_NAME, values, " last_update_time = " + bp.getDatetime(), null);
//		}
//		else
//		{
//			return MainActivity.mDBHelper.insertData(DBBloodPressure.BloodPressure.TB_NAME, values);
//		}	
	}
	
	protected abstract int getType();
	protected abstract String getAnswer(Map<Integer,Object> q);
	protected abstract int getResult(Map<Integer,Object> q);
	protected abstract Map<Integer, Object> parseAnswer(String s);
	
	@Override
	public Map<Integer, Object> getLastAnswer() {
		
		String strQry = "Select * FROM " + DBHabit.TABLE.NAME + " WHERE "+DBHabit.TABLE.COLUMN_TYPE+"="+ getType() +" ORDER BY " +  DBHabit.TABLE._ID + " DESC LIMIT 1";
		String str ;
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
					
			Cursor cursor = db.rawQuery(strQry, null);
			if(!cursor.moveToNext()) return null;
			str = cursor.getString(cursor.getColumnIndex(DBHabit.TABLE.COLUMN_ANSWERS));
			cursor.close();
			
		}
		catch(SQLiteException e)
		{
			return null;
		}
		return  parseAnswer(str);	
	}
	
	@Override
	final public int getLastResult() {
		String strQry = "Select * FROM " + DBHabit.TABLE.NAME + " WHERE "+DBHabit.TABLE.COLUMN_TYPE+"="+ getType() +" ORDER BY " +  DBHabit.TABLE._ID + " DESC LIMIT 1";
		
		int rst = -1;
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			Cursor cursor = db.rawQuery(strQry, null);
			if(!cursor.moveToNext()) return -1;
			rst =  cursor.getInt(cursor.getColumnIndex(DBHabit.TABLE.COLUMN_RESULT));
			cursor.close();
		}
		catch(SQLiteException e)
		{
			return -1;
		}
		return rst;
	}
	
	
	
}
