package com.example.bpmanager.DB;

import android.provider.BaseColumns;

public final class DBBloodPressure 
{
	public DBBloodPressure() {}
	
	public static abstract class BloodPressure implements BaseColumns
	{
		public static final String TB_NAME = "bloodpressure";
		public static final String COLUMN_SYS = "systolic";
		public static final String COLUMN_DIA = "diastolic";
		public static final String COLUMN_LAST_UPDATETIME = "last_update_time";
	}
	
	public static final String SQL_CREATE = "create table " + BloodPressure.TB_NAME +
			"(" + BloodPressure._ID + " integer primary key, " + 
			BloodPressure.COLUMN_SYS + " integer, " +
			BloodPressure.COLUMN_DIA + " integer, " +
			BloodPressure.COLUMN_LAST_UPDATETIME + " text " + 
			");";
		
	public static final String SQL_DROP = "drop table if exists " + BloodPressure.TB_NAME;
}
