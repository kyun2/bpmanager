package com.example.bpmanager;


/**
 * 
 * @author Kyun
 *
 * 혈압에 관한 정보
 * 현재 혈압 수축기 
 */
public class bp {
	private long id;
	private int systolic;
	private int diastolic;
	private String bpdatetime;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSystolic() {
		return systolic;
	}
	public void setSystolic(int systolic) {
		this.systolic = systolic;
	}
	public int getDiastolic() {
		return diastolic;
	}
	public void setDiastolic(int diastolic) {
		this.diastolic = diastolic;
	}
	public String getBpdatetime() {
		return bpdatetime;
	}
	public void setBpdatetime(String bpdatetime) {
		this.bpdatetime = bpdatetime;
	}
	
	public void setBP(){
		
	}

}
