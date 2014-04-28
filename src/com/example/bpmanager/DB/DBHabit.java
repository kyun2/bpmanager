package com.example.bpmanager.DB;

import android.provider.BaseColumns;

public final class DBHabit 
{
	public DBHabit() {}
	
	public static abstract class TABLE implements BaseColumns
	{
		public static final String NAME = "habit";
		public static final String COLUMN_TYPE = "habit_type";
		public static final String COLUMN_ANSWERS = "answers";
		public static final String COLUMN_RESULT = "result";
		public static final String COLUMN_UPDATE = "update_time";
	}
	
	public static final String SQL_CREATE = "create table " + TABLE.NAME +
			"(" + TABLE._ID + " integer primary key, " + 
			TABLE.COLUMN_TYPE + " integer, " + 
			TABLE.COLUMN_ANSWERS + " text, " +
			TABLE.COLUMN_RESULT + " integer, " +
			TABLE.COLUMN_UPDATE + " text " + 
			");";
		
	public static final String SQL_DROP = "drop table if exists " + TABLE.NAME;
}
