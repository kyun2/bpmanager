package com.example.bpmanager.Data;
import java.util.List;
import java.util.Map;

public interface Survey {

	/**
	 * 설문조사에 쓰일 질문지를 반환한다.
	 * @return 질문지들의 배열
	 */
	public String[] getSurveyQuestion();
	
	/**
	 * 설문조사 결과로 부터 레포트를 작성한다.
	 * @param q 설문조사 답안지
	 * @return 설문조사 결과
	 */
	public String getSurveyReport(Map<Integer,Object> q);
	
}
