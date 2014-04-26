package com.example.bpmanager;

public class medalram {
	
//	public static final String MEDALRAMDB_CREATE = "create table " + medalramtable 
//			+ "(" + KEY + " integer primary key autoincrement, " 
//			+ IMG + " text, " 
//			+ LABEL + " text, " 
//			+ CHECK + " integer" + ");";
	private long id;
	private String imglocation;
	private String medlabel;
	private String medtime;
	private int check;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImglocation() {
		return imglocation;
	}
	public void setImglocation(String imglocation) {
		this.imglocation = imglocation;
	}
	public String getMedlabel() {
		return medlabel;
	}
	public void setMedlabel(String medlabel) {
		this.medlabel = medlabel;
	}
	public String getMedtime() {
		return medtime;
	}
	public void setMedtime(String medtime) {
		this.medtime = medtime;
	}
	public int getCheck() {
		return check;
	}
	public void setCheck(int check) {
		this.check = check;
	}

}
