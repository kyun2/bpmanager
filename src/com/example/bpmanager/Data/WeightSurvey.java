package com.example.bpmanager.Data;

import java.util.Map;

import com.example.bpmanager.MainActivity;

public class WeightSurvey extends AbstractSurvey {

	@Override
	public String getSurveyReport() {
		
		Map<Integer, Object> q = getLastAnswer();
		
		float weiht = getWeiht(q);
		if(weiht < 0) return "정확한 값을 입력하세요";
		float targetWeiht = getTargetWeiht(getHeight());
		
		if(weiht < targetWeiht) 
			return "당신의 몸무게는 "+ weiht+"kg로, 적정 체중 "+targetWeiht+"kg 미만을 잘 유지하고 있습니다.\n 고혈압 환자들은 혈압 감소를 위해 건강한 체중을 유지하는 것이 필요합니다. 지금처럼 적정 체중을 유지하세요.";
		else 
			return "당신의 몸무게는" +weiht+ "kg로, 적정 체중"+targetWeiht+"kg를  "+(weiht - targetWeiht)+"kg 초과하였습니다.\n 고혈압 환자들은 혈압 감소를 위해 건강한 체중을 유지하는 것이 필요합니다. 건강한 BMI유지를 위해 목표 체중까지 감량이 필요합니다.";
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

	@Override
	public String[] getSurveyQuestion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected String getAnswer(Map<Integer, Object> q) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getResult(Map<Integer, Object> q) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Map<Integer, Object> parseAnswer(String s) {
		// TODO Auto-generated method stub
		return null;
	}
}
