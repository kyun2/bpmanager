package com.example.bpmanager;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhandler {

	public static final String dbName = "bpmanager_1.db"; // name of Database;
	public static final String usertable = "user";
	public static final String bptable = "bloodpressure";// name of Table;
	// public static final String medicintable = "medicin";
	public static final String medhistory = "medhistory";
	public static final String medalramtable = "medalram";
	public static final String habittable = "habit";
	public static final String clinictable = "clinic";
	public static final String KEY = "_id";

	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String GENDER = "gender";
	public static final String BIRTH = "birthdate";
	public static final String HEIGHT = "bodyheight";
	public static final String WEIGHT = "bodyweight";
	public static final String WAIST = "waist";
	public static final String HYPER = "hypertension";
	public static final String GLUCOSE = "glucose";
	public static final String KIDNEY = "KIDNEY";
	public static final String CORONARY = "coronary";

	public static final String SYS = "systolic";
	public static final String DIA = "diastolic";
	public static final String BPTIME = "bptime";

	public static final String IMG = "imglocation";
	public static final String LABEL = "label";
	public static final String MEDTIME = "medtime";
	public static final String MEDCHECK = "medcheck";

	public static final String SALT = "imglocation";
	public static final String EXAM_NUM = "examnum";
	public static final String EXAM_TIME = "examtime";
	public static final String EXAM_HARD = "examhard";
	public static final String ALCHOLEDAY = "alcholeday";
	public static final String ALCHOLEWEEK = "alcholeweek";
	public static final String SMOKING = "smoking";
	public static final String STRESS = "stress";

	public static final String CLINICDATE = "clinictime";
	public static final String CLINICCHECK = "cliniccheck";

	public static final int dbVersion = 2;

	public static final String USERDB_CREATE = "create table " + usertable
			+ "(" + KEY + " integer primary key autoincrement, " 
			+ NAME + " text, " 
			+ EMAIL + " text, " 
			+ GENDER + " integer, " 
			+ BIRTH + " text, " 
			+ HEIGHT + " text, " 
			+ WEIGHT + " text, " 
			+ WAIST + " text, " 
			+ HYPER + " integer, " 
			+ GLUCOSE + " integer, "
			+ KIDNEY + " integer, " 
			+ CORONARY + " integer " + ");";

	public static final String BPDB_CREATE = "create table " + bptable 
			+ "(" + KEY + " integer primary key autoincrement, " 
			+ SYS + " integer, "
			+ DIA + " integer, " 
			+ BPTIME + " text " + ");";

	public static final String MEDALRAMDB_CREATE = "create table " + medalramtable 
			+ "(" + KEY + " integer primary key autoincrement, " 
			+ IMG + " text, " 
			+ LABEL + " text, " 
			+ MEDTIME + " text, " 
			+ MEDCHECK + " integer " + ");";

	public static final String HABITDB_CREATE = "create table " + habittable
			+ "(" + KEY + " integer primary key autoincrement, " 
			+ SALT + " integer, " 
			+ EXAM_NUM + " integer, " 
			+ EXAM_TIME + " integer, "
			+ EXAM_HARD + " integer, " 
			+ ALCHOLEDAY + " integer, "
			+ ALCHOLEWEEK + " integer, " 
			+ SMOKING + " integer, " 
			+ STRESS + " string " + ");";

	public static final String CLINICALRAMDB_CREATE = "create table " + clinictable 
			+ "(" + KEY + " integer primary key autoincrement, "
			+ CLINICDATE + " text, " 
			+ CLINICCHECK + " integer " + ");";

	int dbMode = Context.MODE_PRIVATE;
	public SQLiteDatabase db;
	private SQLiteOpenHelper openHelper; // DB opener

	private Context context;

	// private void createDatabase(){
	// db = openOrCreateDatabase(dbName, dbMode, null);
	// }

	public static class dbOpenHelper extends SQLiteOpenHelper {

		public dbOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase sqlitedb) {
			// TODO Auto-generated method stub
			sqlitedb.execSQL(USERDB_CREATE);
			sqlitedb.execSQL(BPDB_CREATE);
			sqlitedb.execSQL(MEDALRAMDB_CREATE);
			sqlitedb.execSQL(HABITDB_CREATE);
			sqlitedb.execSQL(CLINICALRAMDB_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase sqlitedb, int oldVersion,
				int newVersion) {
			// TODO Auto-generated method stub
			Log.w("DBhandler", oldVersion + "will be upgraded to " + newVersion
					+ ", all of old data will be destroyed.");
			sqlitedb.execSQL("DROP TABLE IF EXISTS " + usertable);
			sqlitedb.execSQL("DROP TABLE IF EXISTS " + bptable);
			sqlitedb.execSQL("DROP TABLE IF EXISTS " + medalramtable);
			sqlitedb.execSQL("DROP TABLE IF EXISTS " + habittable);
			sqlitedb.execSQL("DROP TABLE IF EXISTS " + clinictable);
			onCreate(sqlitedb);
		}
	}

	public DBhandler(Context ctx) {
		context = ctx;
		openHelper = new dbOpenHelper(ctx, dbName, null, dbVersion);

	}

	public DBhandler open() throws SQLException {
		try {
			db = openHelper.getWritableDatabase();
		} catch (SQLException e) {
			db = openHelper.getReadableDatabase();
		}
		return this;
	}

	public DBhandler readOpen() throws SQLException {
		try {
			db = openHelper.getReadableDatabase();
		} catch (SQLException e) {

		}
		return this;
	}

	public void close() {
		db.close();
	}

	public long insertUser(user u) {
		ContentValues value = new ContentValues();

		value.put(NAME, u.getName());
		value.put(DBhandler.EMAIL, u.getEmail());
		value.put(DBhandler.GENDER, u.getGender());
		value.put(DBhandler.BIRTH, u.getBirth());
		value.put(DBhandler.HEIGHT, u.getHeight());
		value.put(DBhandler.WEIGHT, u.getWeight());
		value.put(DBhandler.WAIST, u.getWaist());
		value.put(DBhandler.HYPER, u.getHypertension());
		value.put(DBhandler.GLUCOSE, u.getGlucose());
		value.put(DBhandler.KIDNEY, u.getKidney());
		value.put(DBhandler.CORONARY, u.getCoronary());

		return db.insert(usertable, null, value);

	}

	public boolean removeUser(long index) {
		return (db.delete(usertable, KEY + " = " + index, null) > 0);
	}

	public List<user> getUsers() {
		List<user> retval = new ArrayList<user>();
		Cursor cu = db.query(usertable, new String[] { KEY, NAME, EMAIL,
				GENDER, BIRTH, HEIGHT, WEIGHT, WAIST, HYPER, GLUCOSE, KIDNEY,
				CORONARY }, null, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			if (cu.moveToFirst()) {
				for (int i = 0; i < cu.getCount(); i++) {
					retval.add(setUser(cu));
					cu.moveToNext();
				}
			}
		}
		cu.close();
		return retval;
	}

	public user getUser(long index) {
		user u = new user();
		Cursor cu = db.query(usertable, new String[] { KEY, NAME, EMAIL,
				GENDER, BIRTH, HEIGHT, WEIGHT, WAIST, HYPER, GLUCOSE, KIDNEY,
				CORONARY }, KEY + " = " + index, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			cu.moveToPosition(cu.getCount() - 1);
			u = setUser(cu);
		}
		cu.close();
		return u;

	}

	private user setUser(Cursor cu) {
		// TODO Auto-generated method stub
		user retval = new user();
		retval.setKey(cu.getInt(0));
		retval.setName(cu.getString(1));
		retval.setEmail(cu.getString(2));
		retval.setGender(cu.getInt(3));
		retval.setBirth(cu.getString(4));
		retval.setHeight(cu.getString(5));
		retval.setWeight(cu.getString(6));
		retval.setWaist(cu.getString(7));
		retval.setHypertension(cu.getInt(8));
		retval.setGlucose(cu.getInt(9));
		retval.setKidney(cu.getInt(10));
		retval.setCoronary(cu.getInt(11));

		return retval;
	}

	public int updateUser(long index, user u) {
		String strWhere = KEY + " = " + index;
		ContentValues value = new ContentValues();

		value.put(NAME, u.getName());
		value.put(DBhandler.EMAIL, u.getEmail());
		value.put(DBhandler.GENDER, u.getGender());
		value.put(DBhandler.BIRTH, u.getBirth());
		value.put(DBhandler.HEIGHT, u.getHeight());
		value.put(DBhandler.WEIGHT, u.getWeight());
		value.put(DBhandler.WAIST, u.getWaist());
		value.put(DBhandler.HYPER, u.getHypertension());
		value.put(DBhandler.GLUCOSE, u.getGlucose());
		value.put(DBhandler.KIDNEY, u.getKidney());
		value.put(DBhandler.CORONARY, u.getCoronary());

		return db.update(usertable, value, strWhere, null);
	}

	public long insertBP(bp args) {
		ContentValues value = new ContentValues();

		value.put(DBhandler.SYS, args.getSystolic());
		value.put(DBhandler.DIA, args.getDiastolic());
		value.put(DBhandler.BPTIME, args.getBpdatetime());

		return db.insert(bptable, null, value);

	}

	public boolean removeBP(long index) {
		return (db.delete(bptable, KEY + " = " + index, null) > 0);
	}

	public List<bp> getBPs() {
		List<bp> retval = new ArrayList<bp>();
		Cursor cu = db.query(bptable, new String[] { KEY, DBhandler.SYS, DBhandler.DIA,
				DBhandler.BPTIME }, null, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			if (cu.moveToFirst()) {
				for (int i = 0; i < cu.getCount(); i++) {
					retval.add(setBP(cu));
					cu.moveToNext();
				}
			}
		}
		return retval;

	}

	private bp setBP(Cursor cu) {
		bp retval = new bp();
		retval.setId(cu.getLong(0));
		retval.setSystolic(cu.getInt(1));
		retval.setDiastolic(cu.getInt(2));
		retval.setBpdatetime(cu.getString(3));
		// TODO Auto-generated method stub
		return retval;
	}

	public bp getBP(long index) {
		bp retval = new bp();
		Cursor cu = db.query(bptable, new String[] { KEY, DBhandler.SYS, DBhandler.DIA,
				DBhandler.BPTIME }, DBhandler.KEY+" = "+index, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			cu.moveToPosition(cu.getCount() - 1);
			retval = setBP(cu);
		}
		cu.close();
		return retval;

	}

	public int updateBP(long index, bp args) {
		String strWhere = KEY + " = " + index;
		ContentValues value = new ContentValues();

		value.put(DBhandler.SYS, args.getSystolic());
		value.put(DBhandler.DIA, args.getDiastolic());
		value.put(DBhandler.BPTIME, args.getBpdatetime());

		return db.update(bptable, value, strWhere, null);
	}

	
	public long insertMedAlram(medalram args) {
		ContentValues value = new ContentValues();

		value.put(DBhandler.IMG, args.getImglocation());
		value.put(DBhandler.LABEL, args.getMedlabel());
		value.put(DBhandler.MEDTIME, args.getMedtime());
		value.put(DBhandler.MEDCHECK, args.getCheck());

		return db.insert(medalramtable, null, value);

	}

	public boolean removeMedalram(long index) {
		return (db.delete(medalramtable, KEY + " = " + index, null) > 0);
	}

	public List<medalram> getMedalrams() {
		List<medalram> retval = new ArrayList<medalram>();
		Cursor cu = db.query(medalramtable, new String[] { 
				DBhandler.KEY, 
				DBhandler.IMG, 
				DBhandler.LABEL,
				DBhandler.MEDTIME,
				DBhandler.MEDCHECK }, null, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			if (cu.moveToFirst()) {
				for (int i = 0; i < cu.getCount(); i++) {
					retval.add(setMedalram(cu));
					cu.moveToNext();
				}
			}
		}
		return retval;

	}

	private medalram setMedalram(Cursor cu) {
		medalram retval = new medalram();
		retval.setId(cu.getLong(0));
		retval.setImglocation(cu.getString(1));
		retval.setMedlabel(cu.getString(2));
		retval.setMedtime(cu.getString(3));
		retval.setCheck(cu.getInt(4));
		// TODO Auto-generated method stub
		return retval;
	}

	public medalram getMedalram(long index) {
		medalram retval = new medalram();
		Cursor cu = db.query(medalramtable, new String[] { 
				DBhandler.KEY, 
				DBhandler.IMG, 
				DBhandler.LABEL,
				DBhandler.MEDTIME,
				DBhandler.MEDCHECK }, DBhandler.KEY+" = "+index, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			cu.moveToPosition(cu.getCount() - 1);
			retval = setMedalram(cu);
		}
		return retval;

	}

	public int updateMedalram(long index, medalram args) {
		String strWhere = KEY + " = " + index;
		ContentValues value = new ContentValues();

		value.put(DBhandler.IMG, args.getImglocation());
		value.put(DBhandler.LABEL, args.getMedlabel());
		value.put(DBhandler.MEDTIME, args.getMedtime());
		value.put(DBhandler.MEDCHECK, args.getCheck());

		return db.update(medalramtable, value, strWhere, null);
	}
	
	///////////////////////////////////////////////////////////////////////

	public long insertHabit(habit args) {
		ContentValues value = new ContentValues();

		value.put(DBhandler.SALT, args.getSalt());
		value.put(DBhandler.EXAM_NUM, args.getExamNum());
		value.put(DBhandler.EXAM_TIME, args.getExamTime());
		value.put(DBhandler.EXAM_HARD, args.getExamHard());
		value.put(DBhandler.ALCHOLEDAY, args.getAlcholeDay());
		value.put(DBhandler.ALCHOLEWEEK, args.getAlcholeWeek());
		value.put(DBhandler.SMOKING, args.getSmoking());
		value.put(DBhandler.STRESS, String.valueOf(args.getStress()));

		return db.insert(habittable, null, value);

	}

	public boolean removeHabit(long index) {
		return (db.delete(habittable, KEY + " = " + index, null) > 0);
	}

	public List<habit> getHabits() {
		List<habit> retval = new ArrayList<habit>();
		Cursor cu = db.query(habittable, new String[] { 
				DBhandler.KEY, 
				DBhandler.EXAM_NUM, 
				DBhandler.EXAM_TIME,
				DBhandler.EXAM_HARD,
				DBhandler.ALCHOLEDAY,
				DBhandler.ALCHOLEWEEK,
				DBhandler.SMOKING,
				DBhandler.STRESS}, null, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
//			throw new SQLException("No items found for rows ");
		} else {
			if (cu.moveToFirst()) {
				for (int i = 0; i < cu.getCount(); i++) {
					retval.add(setHabit(cu));
					cu.moveToNext();
				}
			}
		}
		return retval;

	}

	private habit setHabit(Cursor cu) {
		habit retval = new habit();
		retval.setId(cu.getLong(0));
		retval.setExamNum(cu.getInt(1));
		retval.setExamTime(cu.getInt(2));
		retval.setExamHard(cu.getInt(3));
		retval.setAlcholeDay(cu.getInt(4));
		retval.setAlcholeWeek(cu.getInt(5));
		retval.setSmoking(cu.getInt(6));
		retval.setStress(Double.valueOf(cu.getString(7)));
		// TODO Auto-generated method stub
		return retval;
	}

	public habit getHabit(long index) {
		habit retval = new habit();
		Cursor cu = db.query(habittable, new String[] { 
				DBhandler.KEY, 
				DBhandler.EXAM_NUM, 
				DBhandler.EXAM_TIME,
				DBhandler.EXAM_HARD,
				DBhandler.ALCHOLEDAY,
				DBhandler.ALCHOLEWEEK,
				DBhandler.SMOKING,
				DBhandler.STRESS}, DBhandler.KEY+" = "+index, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			cu.moveToPosition(cu.getCount() - 1);
			retval = setHabit(cu);
		}
		return retval;

	}

	public int updateHabit(long index, habit args) {
		String strWhere = KEY + " = " + index;
		ContentValues value = new ContentValues();

		value.put(DBhandler.SALT, args.getSalt());
		value.put(DBhandler.EXAM_NUM, args.getExamNum());
		value.put(DBhandler.EXAM_TIME, args.getExamTime());
		value.put(DBhandler.EXAM_HARD, args.getExamHard());
		value.put(DBhandler.ALCHOLEDAY, args.getAlcholeDay());
		value.put(DBhandler.ALCHOLEWEEK, args.getAlcholeWeek());
		value.put(DBhandler.SMOKING, args.getSmoking());
		value.put(DBhandler.STRESS, String.valueOf(args.getStress()));

		return db.update(habittable, value, strWhere, null);
	}
	
	////////////////////////////////////////////////////////////////////
	
	public long insertClinicAlram(clinicAlram args) {
		ContentValues value = new ContentValues();

		value.put(DBhandler.CLINICDATE, args.getClinicDate());
		value.put(DBhandler.CLINICCHECK, args.getClinicCheck());

		return db.insert(DBhandler.clinictable, null, value);

	}

	public boolean removeClinicAlram(long index) {
		return (db.delete(DBhandler.clinictable, KEY + " = " + index, null) > 0);
	}

	public List<clinicAlram> getClinicAlrams() {
		List<clinicAlram> retval = new ArrayList<clinicAlram>();
		Cursor cu = db.query(DBhandler.clinictable, new String[] { 
				DBhandler.KEY,
				DBhandler.CLINICDATE, 
				DBhandler.CLINICCHECK}, null, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			if (cu.moveToFirst()) {
				for (int i = 0; i < cu.getCount(); i++) {
					retval.add(setClinicAlram(cu));
					cu.moveToNext();
				}
			}
		}
		return retval;

	}

	private clinicAlram setClinicAlram(Cursor cu) {
		clinicAlram retval = new clinicAlram();
		retval.setId(cu.getLong(0));
		retval.setClinicDate(cu.getString(1));
		retval.setClinicCheck(cu.getInt(2));
		// TODO Auto-generated method stub
		return retval;
	}

	public clinicAlram getClinicAlram(long index) {
		clinicAlram retval = new clinicAlram();
		Cursor cu = db.query(DBhandler.clinictable, new String[] { 
				DBhandler.KEY,
				DBhandler.CLINICDATE, 
				DBhandler.CLINICCHECK}, DBhandler.KEY+" = "+index, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			cu.moveToPosition(cu.getCount() - 1);
			retval = setClinicAlram(cu);
		}
		return retval;

	}

	public int updateClinicAlram(long index, clinicAlram args) {
		String strWhere = KEY + " = " + index;
		ContentValues value = new ContentValues();

		value.put(DBhandler.CLINICDATE, args.getClinicDate());
		value.put(DBhandler.CLINICCHECK, args.getClinicCheck());

		return db.update(DBhandler.clinictable, value, strWhere, null);
	}

	public List<bp> getBPsByTime(String befor, String now) {
		List<bp> retval = new ArrayList<bp>();
		String where = this.BPTIME+" >= "+befor+" and "+ this.BPTIME+" <= "+now;
		Cursor cu = db.query(bptable, new String[] { KEY, DBhandler.SYS, DBhandler.DIA,
				DBhandler.BPTIME }, where, null, null, null, null);
		if (cu.getCount() == 0 || (!cu.moveToFirst())) {
			Log.w("mybpapp", "No items found for rows ");
		} else {
			if (cu.moveToFirst()) {
				for (int i = 0; i < cu.getCount(); i++) {
					retval.add(setBP(cu));
					cu.moveToNext();
				}
			}
		}
		return retval;
	}
	

}
