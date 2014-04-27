package com.example.bpmanager.Data;

import java.util.Map;

public class StressSurvey implements Survey {

	public final static String[] question = {
		"��ä�Һ��ٴ� ��ġ�� �� �����Ѵ�.",
		"���̹��̳� ���� ������ �� �����Ѵ�.",
		"����� �丮���� �߱���, �Ϻ��� �丮�� �� �����Ѵ�.",
		"���� �����̳� ���� �ڹ� ���� ���� �����Ѵ�.",
		"����� ���� �������� ��Ź�� ������ �����ϴ�.",
		"����(���� �Ǵ� �� ����)�� �̰ſ�� �ұ��̳� ������ �� �ִ´�.",
		"���̳� �, ���� ������ ������ ������� �Դ´�.",
		"Ƣ���̳� ��, ����ȸ � ������ ���(������ ��⵵��) ��� �Դ´�.",
		"�ܽ��� �ϰų� ����� ���� ���� �Դ´�.","�丮�� ������ �巹���� ���� ����Ѵ�.",
		"��鱹���� �����.",
	"���� ���� �� ���� �ʴ´�."};

	@Override
	public String[] getSurveyQuestion() {
		return question;
	}

	@Override
	public String getSurveyReport(Map<Integer,Object> q) {
		StringBuilder output = new StringBuilder();
		int totalScore = getTotalScore(q);

		if(totalScore < 5){
			output.append("�������̸� �� �����ϰ� �ֽ��ϴ�.\n�������̴� ���� ������ ������ ������ �����ϴ� ������ ������ �˴ϴ�. ����ó�� �������̸� �����ϼ���.");
			if(getAnswer(q, 5)  == 1 || getAnswer(q, 7) == 1 || 
					getAnswer(q, 6)  == 1 || getAnswer(q, 10) == 1 ||
					getAnswer(q, 0)  == 1 || getAnswer(q, 11) == 1) output.append("�ٸ�");
		}else 
			output.append("�� ���뱺�� �ش��մϴ�.\n�ұ� ���븦 ���̴� ���� ������ ����� ������ ���ҽ�Ű�� ���ÿ� �̹� ������ ������ ���ҽ�Ű�� ȿ���� �ֽ��ϴ�.");	
		if(getAnswer(q, 5)  == 1 || getAnswer(q, 7) == 1) output.append("�ұ��̳� ���� �߰��� �ﰡ����.");
		if(getAnswer(q, 6)  == 1 || getAnswer(q, 10) == 1) output.append("������ ����ô� �� �����ϴ�.");
		if(getAnswer(q, 0)  == 1 || getAnswer(q, 11) == 1) output.append("����, �������� �ٿ�������.");

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

	private int getAnswer( Map<Integer,Object> q, int i){
		Object o = q.get(i);
		if(o instanceof Integer) return (Integer)q.get(i);
		else return 0;
	}



}
