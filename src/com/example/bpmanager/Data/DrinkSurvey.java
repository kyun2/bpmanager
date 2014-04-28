package com.example.bpmanager.Data;

import java.util.Map;

import com.example.bpmanager.MainActivity;

public class DrinkSurvey extends AbstractSurvey {

	@Override
	public String getSurveyReport(Map<Integer, Object> q) {
		int dayconsum = getAnswer(q,0);
		int weekconsum =  getAnswer(q,1);
		
		if(dayconsum < 0 || weekconsum < 0) return "정확한 값을 입력하세요";
		
		if(!isExcessiveDrinking(dayconsum, weekconsum))
			return "당신의 알코올 섭취량은 하루 "+dayconsum+"잔 일주일에 "+ weekconsum +"잔으로 제한량을 잘 지키고 있습니다.\n 제한량 미만의 알코올 섭취는 혈압조절에 큰 영향이 없습니다. 지금처럼 알콜섭취 제한량을 지키세요.";
		else return "당신의 알코올 섭취량은 하루 "+dayconsum+"잔 일주일에 "+ weekconsum +"잔으로 제한량을 초과했습니다.\n 과도한 알코올 섭취는 혈압을 상승시킵니다. 당신의 알코올 섭취 제한량을 지키세요.";
	}
	
	

	private boolean isExcessiveDrinking(int day, int week){
		
		int sex =  MainActivity.mUserData.getSex(); 	
		float weight = MainActivity.mUserData.getWeight();
		
		if(sex == 1 && weight > 60){
			if(day > 2 || week > 14) return true;
			else return false;
		}else{
			if(day > 1 || week > 9) return true;
			else return false;
			
		}
	}
}

