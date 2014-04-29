package com.example.bpmanager.Data;

import java.util.HashMap;
import java.util.Map;

import com.example.bpmanager.MainActivity;

public class DrinkSurvey extends AbstractSurvey {

	private final static int TYPE = 4;

	@Override
	public String getSurveyReport() {

		Map<Integer, Object> q = getLastAnswer();
		if(q == null) return null;

		int dayconsum = getAnswer(q,0);
		int weekconsum =  getAnswer(q,1);

		if(dayconsum < 0 || weekconsum < 0) return null;

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

	@Override
	protected int getType() {
		return TYPE;
	}

	@Override
	protected int getResult(Map<Integer, Object> q) {

		int dayconsum = getAnswer(q,0);
		int weekconsum =  getAnswer(q,1);

		if(!isExcessiveDrinking(dayconsum, weekconsum)) 
			return 1;
		else
			return 0;
	}

	@Override
	protected String getAnswer(Map<Integer, Object> q) {
		StringBuilder str = new StringBuilder();

		str.append(getAnswer(q,0));
		str.append(":#:");
		str.append(getAnswer(q,1));

		return str.toString();
	}

	@Override
	protected Map<Integer, Object> parseAnswer(String s) {
		Map<Integer, Object> map = new HashMap<Integer, Object> ();
		try {
			map.put(0, Integer.parseInt(s.split(":#:")[0]));
			map.put(1, Integer.parseInt(s.split(":#:")[1]));
			return map;
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

