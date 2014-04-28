package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, container, false);

		TextView sysMax = (TextView) view.findViewById(R.id.systolic_max);
		TextView diaMax = (TextView) view.findViewById(R.id.diastolic_max);
		
		BloodPressure recommendBloodPressure = BloodPressure.getRecommendBloodPressure();
		if(recommendBloodPressure != null)
		{
			sysMax.setText(String.valueOf(recommendBloodPressure.getSystolic())+ "/");
			diaMax.setText(String.valueOf(recommendBloodPressure.getDiastolic()));
		}

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
