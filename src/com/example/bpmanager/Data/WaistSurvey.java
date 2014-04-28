package com.example.bpmanager.Data;

import java.util.Map;

import com.example.bpmanager.MainActivity;

public class WaistSurvey extends AbstractSurvey {

	@Override
	public String getSurveyReport(Map<Integer, Object> q) {
		float waist = getWaist(q);
		if(waist < 0) return "정확한 값을 입력하세요";
		float targetWaist = getTargetWaist(MainActivity.mUserData.getSex());
		
		if(waist < targetWaist) 
			return "당신의 복부둘레는 "+ waist+"cm로, 적정 복부둘레 "+targetWaist+"cm미만을 잘 유지하고 있습니다.\n 고혈압 환자들은 혈압 감소를 위해 적절한 복부둘레를 유지하는 것이 필요합니다. 지금처럼 적정 복부둘레를 유지하세요.";
		else 
			return "당신의 복부둘레는" +waist+ "cm로, 적정 복부둘레"+targetWaist+"cm를  "+(waist - targetWaist)+"cm 초과하였습니다.\n 고혈압 환자들은 혈압 감소를 위해 적절한 복부둘레를 유지하는 것이 필요합니다. "
					+ "복부둘레를 적정 복부둘레" + targetWaist +"cm까지 줄이세요.";
	}
	
	private float getWaist(Map<Integer, Object> q){
		Object o = q.get(0);
		if(o instanceof Float) return (Float)o;
		else return -1;
	}
	
	private float getTargetWaist(int sex){	
		if(sex == 2) return 88;
		else return 102;
	}
}
