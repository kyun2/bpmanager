package com.example.bpmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbOpenHelper extends SQLiteOpenHelper{
	public static final String dbName = "bpmanager.db";
	private String tableModel;
	private String tableName;
	private Context con;
	private int version;
	

	public dbOpenHelper(Context context, String name,
			CursorFactory factory, int version, String dbModel) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.tableName = name;
		con = context;
		this.version = version;
		this.tableModel = dbModel;
	}

	@Override
	public void onCreate(SQLiteDatabase sqlitedb) {
		// TODO Auto-generated method stub
		sqlitedb.execSQL(tableModel);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqlitedb, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w("DBhandler", oldVersion + "will be upgraded to "+ newVersion+", all of old data will be destroyed.");
		sqlitedb.execSQL("DROP TABLE IF EXIST"+ tableName);
		onCreate(sqlitedb);
	}
}
