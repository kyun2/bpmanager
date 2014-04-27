package com.example.bpmanager.Data;

import java.util.Map;

import com.example.bpmanager.MainActivity;

public class WeightSurvey implements Survey {

	public final static String[] question = {""};
	
	@Override
	public String[] getSurveyQuestion() {
		return question;
	}

	@Override
	public String getSurveyReport(Map<Integer, Object> q) {
		
		float weiht = getWeiht(q);
		if(weiht < 0) return "��Ȯ�� ���� �Է��ϼ���";
		float targetWeiht = getTargetWeiht(getHeight());
		
		if(weiht < targetWeiht) 
			return "����� �����Դ� "+ weiht+"kg��, ���� ü�� "+targetWeiht+"kg �̸��� �� �����ϰ� �ֽ��ϴ�.\n ������ ȯ�ڵ��� ���� ���Ҹ� ���� �ǰ��� ü���� �����ϴ� ���� �ʿ��մϴ�.����ó�� ���� ü���� �����ϼ���.";
		else 
			return "����� �����Դ�" +weiht+ "kg��, ���� ü��"+targetWeiht+"kg��  "+(weiht - targetWeiht)+"kg �ʰ��Ͽ����ϴ�.\n ������ ȯ�ڵ��� ���� ���Ҹ� ���� �ǰ��� ü���� �����ϴ� ���� �ʿ��մϴ�. �ǰ��� BMI������ ���� ��ǥ ü�߱��� ������ �ʿ��մϴ�.";
	}
	
	private float getWeiht(Map<Integer, Object> q){
		Object o = q.get(0);
		if(o instanceof Float) return (Float)o;
		else return -1;
	}
	
	private float getTargetWeiht(float height){	
		height /= 100;
		return height * height * 25;
	}
	
	private float getHeight(){
		return MainActivity.mUserData.getHeight(); 	
	}
}
