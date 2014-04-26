package com.example.bpmanager.DB;

import android.provider.BaseColumns;

public final class DBMedication 
{
	public DBMedication() {}
	
	public static abstract class Medication implements BaseColumns
	{
		public static final String TB_NAME = "medication";
		public static final String COLUMN_MEDIID = "medicine_id";
		public static final String COLUMN_USERID = "user_id";
		public static final String COLUMN_AMOUNT = "amount";
		public static final String COLUMN_TIME = "time";
		public static final String COLUMN_FREQUENCY = "frequency";
	}
	
	public static final String SQL_CREATE = "create table " + Medication.TB_NAME +
			"(" + Medication._ID + " integer primary key, " + 
			Medication.COLUMN_MEDIID + " integer, " + 
			Medication.COLUMN_USERID + " integer, " +
			Medication.COLUMN_AMOUNT + " integer, " +
			Medication.COLUMN_TIME + " integer, " +
			Medication.COLUMN_FREQUENCY + " integer " + 
			");";
		
	public static final String SQL_DROP = "drop table if exists " + Medication.TB_NAME;
}
