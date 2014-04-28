package com.example.bpmanager;

import org.achartengine.GraphicalView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MyInfoFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_mydata, container, false);
		
		LinearLayout lLayoutBPChart = (LinearLayout) view.findViewById(R.id.ll_mydata_bp_chart);
		LinearLayout lLayoutInjectionChart = (LinearLayout) view.findViewById(R.id.ll_mydata_inject_chart);
		LinearLayout lLayoutHabitChart = (LinearLayout) view.findViewById(R.id.ll_mydata_habit_chart);
		
		// 혈압추이 그래프
		BloodPressureGraph bpgrph = new BloodPressureGraph();
		GraphicalView grphView = bpgrph.execute(getActivity());
		lLayoutBPChart.addView(grphView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		return view;
	}

	public void onClick(View v) {

		switch (v.getId()) {

//		case R.id.bt_ok:
//			Toast.makeText(getActivity(), "One Fragment", Toast.LENGTH_SHORT)
//					.show();
//			break;

		}
	}
}
