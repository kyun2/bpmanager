package com.example.bpmanager;

public class habit {

//	public static final String HABITDB_CREATE = "create table " + habittable
//			+ "(" + KEY + " integer primary key autoincrement, " 
//			+ SALT + " integer, " 
//			+ EXAM_NUM + " integer, " 
//			+ EXAM_TIME + " integer, "
//			+ EXAM_HARD + " integer, " 
//			+ ALCHOLEDAY + " integer, "
//			+ ALCHOLEWEEK + " integer, " 
//			+ SMOKING + " integer, " 
//			+ STRESS + " string" + ");";
	private long id;
	private int salt;
	private int examTime;
	private int examNum;
	private int examHard;
	private int alcholeDay;
	private int alcholeWeek;
	private int smoking;
	private double stress;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSalt() {
		return salt;
	}
	public void setSalt(int salt) {
		this.salt = salt;
	}
	public int getExamTime() {
		return examTime;
	}
	public void setExamTime(int examTime) {
		this.examTime = examTime;
	}
	public int getExamNum() {
		return examNum;
	}
	public void setExamNum(int examNum) {
		this.examNum = examNum;
	}
	public int getExamHard() {
		return examHard;
	}
	public void setExamHard(int examHard) {
		this.examHard = examHard;
	}
	public int getAlcholeDay() {
		return alcholeDay;
	}
	public void setAlcholeDay(int alcholeDay) {
		this.alcholeDay = alcholeDay;
	}
	public int getAlcholeWeek() {
		return alcholeWeek;
	}
	public void setAlcholeWeek(int alcholeWeek) {
		this.alcholeWeek = alcholeWeek;
	}
	public int getSmoking() {
		return smoking;
	}
	public void setSmoking(int smoking) {
		this.smoking = smoking;
	}
	public double getStress() {
		return stress;
	}
	public void setStress(double stress) {
		this.stress = stress;
	}
	
	
}
