package com.example.bpmanager.Data;

import java.util.HashMap;
import java.util.Map;

import com.example.bpmanager.MainActivity;

public class WeightSurvey extends AbstractSurvey {

	private final static int TYPE = 1;
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
		int tw = Math.round((height * height * 25)/100);
		return tw/100;
	}
	
	private float getHeight(){
		return MainActivity.mUserData.getHeight(); 	
	}

	@Override
	protected int getType() {
		return TYPE;
	}
	
	@Override
	protected int getResult(Map<Integer, Object> q) {
		if(getWeiht(q) <= getTargetWeiht(getHeight())) 
			return 1;
		else
			return 0;
	}

	@Override
	protected String getAnswer(Map<Integer, Object> q) {
		return getWeiht(q)+"";
	}

	@Override
	protected Map<Integer, Object> parseAnswer(String s) {
		try {
			Map<Integer, Object> map = new HashMap<Integer, Object>();
			map.put(0, Float.parseFloat(s));
			return  map;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
