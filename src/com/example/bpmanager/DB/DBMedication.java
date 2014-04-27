package com.example.bpmanager.DB;

import android.provider.BaseColumns;

public final class DBMedication 
{
	public DBMedication() {}
	
	public static abstract class Medication implements BaseColumns
	{
		public static final String TB_NAME = "medication";
		public static final String COLUMN_MEDIID = "medicine_id";
		public static final String COLUMN_AMOUNT = "amount";
		public static final String COLUMN_COUNT = "count";
	}
	
	public static final String SQL_CREATE = "create table " + Medication.TB_NAME +
			"(" + Medication._ID + " integer primary key, " + 
			Medication.COLUMN_MEDIID + " integer, " + 
			Medication.COLUMN_AMOUNT + " integer, " +
			Medication.COLUMN_COUNT + " integer " +
			");";
		
	public static final String SQL_DROP = "drop table if exists " + Medication.TB_NAME;
}
