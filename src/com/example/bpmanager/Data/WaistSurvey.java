package com.example.bpmanager.Data;

import java.util.HashMap;
import java.util.Map;

import com.example.bpmanager.MainActivity;

public class WaistSurvey extends AbstractSurvey {

	private final static int TYPE = 2;
	@Override
	public String getSurveyReport() {
		
		Map<Integer, Object> q = getLastAnswer();
		if(q == null) return null;
		float waist = getWaist(q);
		if(waist < 0) return null;
		float targetWaist = getTargetWaist(MainActivity.mUserData.getSex());
		
		if(waist < targetWaist) 
			return "당신의 복부둘레는 "+ waist+"inch로, 적정 복부둘레 "+targetWaist+"inch미만을 잘 유지하고 있습니다.\n 고혈압 환자들은 혈압 감소를 위해 적절한 복부둘레를 유지하는 것이 필요합니다. 지금처럼 적정 복부둘레를 유지하세요.";
		else 
			return "당신의 복부둘레는" +waist+ "inch로, 적정 복부둘레"+targetWaist+"inch를  "+ (float)(Math.round((waist - targetWaist) * 10))/10+"inch 초과하였습니다.\n 고혈압 환자들은 혈압 감소를 위해 적절한 복부둘레를 유지하는 것이 필요합니다. "
					+ "복부둘레를 적정 복부둘레" + targetWaist +"inch까지 줄이세요.";
	}
	
	
	 
	private float getWaist(Map<Integer, Object> q){
		Object o = q.get(0);
		if(o instanceof Float) return (Float)o;
		else return -1;
	}
	
	private float getTargetWaist(int sex){	
		if(sex == 2) return (float)34.6;
		else return (float)40.1;
	}

	@Override
	protected int getType() {
		return TYPE;
	}

	@Override
	protected int getResult(Map<Integer, Object> q) {
		if(getWaist(q) <= getTargetWaist(MainActivity.mUserData.getSex())) 
			return 1;
		else
			return 0;
	}

	@Override
	protected String getAnswer(Map<Integer, Object> q) {
		return getWaist(q)+"";
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

	@Override
	public String[] getSurveyQuestion() {
		// TODO Auto-generated method stub
		return null;
	}
}
