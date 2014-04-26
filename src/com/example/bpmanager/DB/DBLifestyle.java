package com.example.bpmanager.DB;

import android.provider.BaseColumns;

public final class DBLifestyle 
{
	public DBLifestyle() {}
	
	public static abstract class Lifestyle implements BaseColumns
	{
		public static final String TB_NAME = "lifestyle";
		public static final String COLUMN_SODIUM_SCORE = "sodium_score";
		public static final String COLUMN_EXERCISE_FREQUENCY = "exercise_frequency";
		public static final String COLUMN_EXERCISE_INTENSITY = "exercise_intensity";
		public static final String COLUMN_EXERCISE_TIME = "exercise_time";
		public static final String COLUMN_ALCOHOL_NAME = "alcohol_name";
		public static final String COLUMN_ALCOHOL_FREQUENCY = "alcohol_frequency";
		public static final String COLUMN_ALCOHOL_AMOUNT = "alcohol_amount";
		public static final String COLUMN_STRESS_SCORE = "stress_score";
		public static final String COLUMN_SMOKING_AMOUNT = "smoking_amount";
		public static final String COLUMN_CREATETIME = "create_time";
	}
	
	public static final String SQL_CREATE = "create table " + Lifestyle.TB_NAME +
			"(" + Lifestyle._ID + " integer primary key, " + 
			Lifestyle.COLUMN_SODIUM_SCORE + " integer, " + 
			Lifestyle.COLUMN_EXERCISE_FREQUENCY + " integer, " +
			Lifestyle.COLUMN_EXERCISE_INTENSITY + " integer, " +
			Lifestyle.COLUMN_EXERCISE_TIME + " integer, " +
			Lifestyle.COLUMN_ALCOHOL_NAME + " text, " +
			Lifestyle.COLUMN_ALCOHOL_FREQUENCY + " integer, " +
			Lifestyle.COLUMN_ALCOHOL_AMOUNT + " integer, " +
			Lifestyle.COLUMN_STRESS_SCORE + " integer, " +
			Lifestyle.COLUMN_SMOKING_AMOUNT + " integer " + 
			");";
		
	public static final String SQL_DROP = "drop table if exists " + Lifestyle.TB_NAME;
}
