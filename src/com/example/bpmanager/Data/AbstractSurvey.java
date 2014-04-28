package com.example.bpmanager.Data;

import java.util.Map;

public abstract class AbstractSurvey implements Survey {

	@Override
	public String[] getSurveyQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long insertDatatoDB(Map<Integer, Object> q) {
		// DB »ğÀÔ±¸Çö
		return -1;
	}
	
	protected int getAnswer( Map<Integer,Object> q, int i){
		Object o = q.get(i);
		if(o instanceof Integer) return (Integer)o;
		else return -1;
	}
	

}
