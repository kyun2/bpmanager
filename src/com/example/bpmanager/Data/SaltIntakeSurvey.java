package com.example.bpmanager.Data;

import java.util.HashMap;
import java.util.Map;

public class SaltIntakeSurvey extends AbstractSurvey {

	private final static int TYPE = 0;
	public final static String[] question = {"생채소보다는 김치를 더 좋아한다.",
			"별미밥이나 덮밥 종류를 더 좋아한다.",
			"서양식 요리보다 중국식, 일본식 요리를 더 좋아한다.",
			"말린 생선이나 고등어 자반 같은 것을 좋아한다.",
			"명란젓 같은 젓갈류가 식탁에 없으면 섭섭하다.",
			"음식(나물 또는 탕 종류)이 싱거우면 소금이나 간장을 더 넣는다.",
			"국이나 찌개, 국수 종류의 국물을 남김없이 먹는다.",
			"튀김이나 전, 생선회 등에 간장을 듬뿍(음식이 잠기도록) 찍어 먹는다.",
			"외식을 하거나 배달을 자주 시켜 먹는다.","요리에 마요네즈나 드레싱을 곧잘 사용한다.",
			"라면국물은 남긴다.",
	"젓갈 장아찌를 잘 먹지 않는다."};
	
	@Override
	public String[] getSurveyQuestion() {
		return question;
	}

	@Override
	public String getSurveyReport() {
		Map<Integer, Object> q = getLastAnswer();
		StringBuilder output = new StringBuilder();
	
		if(q == null) return "입력된 정보가 없습니다. 설문조사에 응해주세요";
		
		int totalScore = getTotalScore(q);
		
		if(totalScore < 5){
			output.append("저염식이를 잘 유지하고 있습니다.\n저염식이는 정상 범위로 낮아진 혈압을 유지하는 데에도 도움이 됩니다. 지금처럼 저염식이를 유지하세요.");
			if(getAnswer(q, 5)  == 1 || getAnswer(q, 7) == 1 || 
					getAnswer(q, 6)  == 1 || getAnswer(q, 10) == 1 ||
					getAnswer(q, 0)  == 1 || getAnswer(q, 11) == 1) output.append("다만");
		}else 
			output.append("고염 섭취군에 해당합니다.\n소금 섭취를 줄이는 것은 혈압의 상승할 위험을 감소시키는 동시에 이미 높아진 혈압을 감소시키는 효과가 있습니다.");	
		
		if(getAnswer(q, 5)  == 1 || getAnswer(q, 7) == 1) output.append("소금이나 간장 추가는 삼가세요.");
		if(getAnswer(q, 6)  == 1 || getAnswer(q, 10) == 1) output.append("국물은 남기시는 게 좋습니다.");
		if(getAnswer(q, 0)  == 1 || getAnswer(q, 11) == 1) output.append("젓갈, 장아찌류는 줄여보세요.");
		
		return output.toString();
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
	protected int getType() {
		return TYPE;
	}
	
	@Override
	protected int getResult(Map<Integer,Object> q) {
		int totalScore = getTotalScore(q);
		if(totalScore < 5)
			return 1;
		else 
			return 0;
	}

	@Override
	protected String getAnswer(Map<Integer,Object> q) {
		StringBuilder answer = new StringBuilder();

		for(int i = 0; i < q.size(); i++)
			answer.append(q.get(i));
		return answer.toString();
	}

	@Override
	protected Map<Integer, Object> parseAnswer(String s) {
		Map<Integer, Object> answer = new HashMap<Integer, Object>();
		
		for(int i = 0; i< s.length(); i++)
			if(s.charAt(i) == '1')
				answer.put(i,1);
			else 
				answer.put(i, 0);
		
		return answer;
	}



	
}
