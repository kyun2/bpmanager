package com.example.bpmanager;

import org.achartengine.GraphicalView;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyInfoFragment extends Fragment
{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_mydata, container, false);
		
		LinearLayout lLayoutBPChart = (LinearLayout) view.findViewById(R.id.ll_mydata_bp_chart);
		LinearLayout lLayoutInjectionChart = (LinearLayout) view.findViewById(R.id.ll_mydata_inject_chart);
		//LinearLayout lLayoutHabitChart = (LinearLayout) view.findViewById(R.id.ll_mydata_habit_chart);
		
		// 혈압추이 그래프
		BloodPressureGraph bpgrph = new BloodPressureGraph();
		GraphicalView grphView = bpgrph.execute(getActivity());
		if (grphView != null)
		{
			lLayoutBPChart.addView(grphView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		else
		{
			TextView tv = new TextView(getActivity());
			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			tv.setGravity(Gravity.CENTER);
			tv.setText("혈압측정 기록 없음");
			tv.setTextAppearance(getActivity(), android.R.attr.textAppearanceLarge);
			tv.setBackgroundColor(Color.WHITE);
			lLayoutBPChart.addView(tv);
		}
		
		// 투약성취율 그래프
		MedicationInjectionGraph medInjGrph = new MedicationInjectionGraph();
		GraphicalView medInjGrphView = medInjGrph.excute(getActivity());
		if (medInjGrphView != null)
		{
			lLayoutInjectionChart.addView(medInjGrphView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
		else
		{
			TextView tv = new TextView(getActivity());
			tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			tv.setGravity(Gravity.CENTER);
			tv.setText("복용 기록 없음");
			tv.setTextAppearance(getActivity(), android.R.attr.textAppearanceLarge);
			tv.setBackgroundColor(Color.WHITE);
			lLayoutInjectionChart.addView(tv);
		}
		
		return view;
	}

}
