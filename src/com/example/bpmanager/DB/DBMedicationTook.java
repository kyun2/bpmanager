package com.example.bpmanager.DB;

import android.provider.BaseColumns;

public class DBMedicationTook 
{
	public DBMedicationTook() {}
	
	public static abstract class SCHEMA implements BaseColumns
	{
		public static final String TB_NAME = "medication_took";
		public static final String COLUMN_MEDID = "medicine_id";
		public static final String COLUMN_INJECT_TIME = "inject_time";
	}
	
	public static final String SQL_CREATE = "create table " + SCHEMA.TB_NAME +
			"(" + SCHEMA._ID + " integer primary key, " +
			SCHEMA.COLUMN_MEDID + " integer, " +
			SCHEMA.COLUMN_INJECT_TIME + " text " +
			");";
	
	public static final String SQL_DROP = "drop table if exists " + SCHEMA.TB_NAME;
}
