package com.example.bpmanager.Data;
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
	
	/**
	 * 설문조사 결과를 디비에 INSERT
	 * @param q 설문조사 답안
	 * @return 성공 실패 여부
	 */
	public long insertDatatoDB(Map<Integer,Object> q);
}
