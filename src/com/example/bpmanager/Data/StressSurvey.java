package com.example.bpmanager.Data;

import java.util.Map;

public class StressSurvey extends AbstractSurvey {

	@Override
	public String getSurveyReport() {
		Map<Integer, Object> q = getLastAnswer();
		
		int totalScore = getTotalScore(q);
		float  BEPSI = ((float)totalScore)/5;
		
		if(BEPSI >= 2.8 ){
			return "스트레스 지수가 "+ BEPSI +"점으로, 스트레스를 느끼는 정도가 고스트레스군에 해당합니다.\n 스트레스는 신체기능이나 건강결과에 직접적인 영향을 미칩니다."
					+ "스트레스 지수 1.6미만으로 스트레스를 관리하는 것이 꼭 필요합니다. 스트레스관리를 위한 상담이 필요합니다.";
		}else if(BEPSI >= 1.8){
			return "스트레스 지수가 "+ BEPSI +"점으로, 스트레스를 느끼는 정도가 중등도 스트레스군에 해당합니다.\n 스트레스는 신체기능이나 건강결과에 직접적인 영향을 미칩니다."; 
		}else {
			return "스트레스 지수가 "+ BEPSI +"점으로, 스트레스를 느끼는 정도가 저스트레스군에 해당합니다.\n 스트레스는 신체기능이나 건강결과에 직접적인 영향을 미칩니다."; 
		}
	
	}

	private int getTotalScore(Map<Integer,Object> q){
		int score = 0;

		for( Map.Entry<Integer, Object> entry : q.entrySet() ){
			Object value = entry.getValue();
			if(value instanceof Integer)  score += (Integer)value;
		}
		return score;
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

