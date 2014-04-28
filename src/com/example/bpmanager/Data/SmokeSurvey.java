package com.example.bpmanager.Data;

import java.util.Map;

public class SmokeSurvey extends AbstractSurvey {

	@Override
	public String getSurveyReport() {
		Map<Integer, Object> q = getLastAnswer();
		
		int unsmoking = getAnswer(q, 0);

		if(unsmoking > 0)
			return "금연을 잘 유지하고 있습니다.\n 금연은 심혈관계 질환 발생율을 줄이는 데 매우 중요한 요소입니다.";
		else return "금연을 지키지 못하고 흡연중입니다.\n 금연은 심혈관계 질환 발생율을 줄이는 데 매우 중요한 요소입니다. 금연해도 심혈관계질환 위험율이 50% 감소합니다. 꼭 금연하세요.";
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
