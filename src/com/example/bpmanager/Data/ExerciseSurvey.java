package com.example.bpmanager.Data;

import java.util.Map;

import com.example.bpmanager.MainActivity;

public class ExerciseSurvey extends AbstractSurvey {

	public final static String[] question = {""};
	
	@Override
	public String[] getSurveyQuestion() {
		return question;
	}

	@Override
	public String getSurveyReport(Map<Integer, Object> q) {
		int number	= getAnswer(q,0);
		int time	= getAnswer(q,1);
		int strength= getAnswer(q,2); //1:높음, 0 : 보통
		if(number < 0 ||time < 0 || strength < 0 ) return "정확한 값을 입력하세요";
		
		String strStrength ="미상";
		if(strength >= 3) strStrength = "높음";
		else if(strength == 2) strStrength = "보통"; 
		else if(strength == 1 ) strStrength = "가벼움"; 
		
		StringBuilder str = new StringBuilder();
		str.append("( ");
		if(number <= 3) str.append(" 운동횟수 ");
		if(time <= 60 ) str.append(" 운동시간 ");
		if(strength <= 3) str.append(" 운동 강도 ");
		str.append(")");
		
		if(number > 3 && time > 60 && strength >0)
			return "당신은 운동횟수 "+ number+"회, 시간 "+time+"분, 강도 "+strStrength+"으로, 목표 운동량을 잘 지키고 있습니다.\n 적절한 운동은 혈압을 유지하는데 도움이 됩니다. 지금처럼 목표 운동량을 지키세요.";
		else return "당신은 운동횟수 "+ number+"회, 시간 "+time+"분, 강도 "+strStrength+"으로, "+ str.toString() + "가 부족합니다.\n 고혈압 환자의 목표 운동량은 일주일에 4-7회, 한 번에 60분 이상입니다. 운동을 할 때는 보통 이상의 노력이 필요한 강도로 하세요. 고혈압 환자는 적절한 운동만으로 수축기 혈압과 이완기 혈압을 5-7mmHg까지 낮출 수 있습니다. 권고량에 따라 운동을 해보세요.";
	}
	
	private int getAnswer( Map<Integer,Object> q, int i){
		Object o = q.get(i);
		if(o instanceof Integer) return (Integer)o;
		else return -1;
	}
}
