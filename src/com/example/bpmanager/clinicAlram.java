package com.example.bpmanager;

public class clinicAlram {
//	public static final String CLINICALRAMDB_CREATE = "create table " + clinictable 
//			+ "(" + KEY + " integer primary key autoincrement, "
//			+ CLINICDATE + " text, " 
//			+ CLINICCHECK + " integer" + ");";
	private long id;
	private String clinicDate;
	private int clinicCheck;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClinicDate() {
		return clinicDate;
	}
	public void setClinicDate(String clinicDate) {
		this.clinicDate = clinicDate;
	}
	public int getClinicCheck() {
		return clinicCheck;
	}
	public void setClinicCheck(int clinicCheck) {
		this.clinicCheck = clinicCheck;
	}
	
}
