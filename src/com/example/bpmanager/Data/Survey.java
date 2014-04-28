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
	 * @return 설문조사 결과
	 */
	public String getSurveyReport();
	
	/**
	 * 설문조사 결과를 디비에 INSERT
	 * @param q 설문조사 답안
	 * @return 성공 실패 여부
	 */
	public long insertDatatoDB(Map<Integer,Object> q);
	
	/**
	 * 가장 최근 작성된 답안을 가져옴
	 * @return 결과
	 */
	public Map<Integer, Object> getLastAnswer(); 
	
	/**
	 * 가장 최근 작성된 답안의 결과를 가져옴
	 * @return 결과(1 :good, 0: bad)
	 */
	public int getLastResult();
	
	//최근 일주일치 데이타 가져오는건 필요할때 해야지
}
