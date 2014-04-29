package com.example.bpmanager.DB;

import android.provider.BaseColumns;

public final class DBUser 
{
	public DBUser() {}
	
	public static abstract class User implements BaseColumns
	{
		public static final String TB_NAME = "user";
		public static final String COLUMN_NAME = "name";
		public static final String COLUMN_EMAIL = "email";
		public static final String COLUMN_SEX = "sex";
		public static final String COLUMN_BIRTH = "birthdate";
		public static final String COLUMN_HEIGHT = "height";
		public static final String COLUMN_WEIGHT = "weight";
		public static final String COLUMN_WAIST = "waist";
		public static final String COLUMN_HYPER = "hypertension";
		public static final String COLUMN_GLUCOSE = "glucose";
		public static final String COLUMN_KIDNEY = "kidney";
		public static final String COLUMN_CORONARY = "coronary";
		public static final String COLUMN_LASTVISIT = "last_visit_date";
		public static final String COLUMN_NEXTVISIT = "next_visit_date";
		public static final String COLUMN_NEXTVISIT_ALARMTIME = "next_visit_alarmtime";
	}
	
	public static final String SQL_CREATE = "create table " + User.TB_NAME +
		"(" + User._ID + " integer primary key, " + 
		User.COLUMN_NAME + " text, " + 
		User.COLUMN_EMAIL + " text, " + 
		User.COLUMN_SEX + " integer, " + 
		User.COLUMN_BIRTH + " text, " + 
		User.COLUMN_HEIGHT + " real, " +
		User.COLUMN_WEIGHT + " real, " + 
		User.COLUMN_WAIST + " real, " + 
		User.COLUMN_HYPER + " integer, " + 
		User.COLUMN_GLUCOSE + " integer, " +
		User.COLUMN_KIDNEY + " integer, " + 
		User.COLUMN_CORONARY + " integer, " +
		User.COLUMN_LASTVISIT + " text, " +
		User.COLUMN_NEXTVISIT + " text, " +
		User.COLUMN_NEXTVISIT_ALARMTIME + " integer " +
		");";
	
	public static final String SQL_DROP = "drop table if exists " + User.TB_NAME;
}
