package com.example.bpmanager.Data;
import java.util.List;
import java.util.Map;

public interface Survey {

	/**
	 * �������翡 ���� �������� ��ȯ�Ѵ�.
	 * @return ���������� �迭
	 */
	public String[] getSurveyQuestion();
	
	/**
	 * �������� ����� ���� ����Ʈ�� �ۼ��Ѵ�.
	 * @param q �������� �����
	 * @return �������� ���
	 */
	public String getSurveyReport(Map<Integer,Object> q);
	
}
