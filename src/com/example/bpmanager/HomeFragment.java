package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class HomeFragment extends Fragment
{	
	EditText alarmTime;	
	RadioGroup alarm;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_home, container, false);

		// 목표혈압 표시
		TextView sysMax = (TextView) view.findViewById(R.id.systolic_max);
		TextView diaMax = (TextView) view.findViewById(R.id.diastolic_max);
		
		BloodPressure recommendBloodPressure = BloodPressure.getRecommendBloodPressure();
		if(recommendBloodPressure != null)
		{
			sysMax.setText(String.valueOf(recommendBloodPressure.getSystolic())+ "/");
			diaMax.setText(String.valueOf(recommendBloodPressure.getDiastolic()));
		}
		
		// 알람
		final UserData uData = MainActivity.mUserData;
		alarmTime = (EditText) view.findViewById(R.id.edit_home_alarm_time);
		alarm = (RadioGroup) view.findViewById(R.id.radiogrp_home_alarm);
		
		boolean isAlarmed = false;
		Intent alarmIntent = new Intent(getActivity(), AlarmReciever.class);
		PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getActivity(), UserData.ALARM_ID, alarmIntent, PendingIntent.FLAG_NO_CREATE);
		if (alarmPendingIntent != null)
			isAlarmed = true;
		
		if (isAlarmed)
		{
			alarm.check(R.id.radio_home_set);
			Log.i("", "alarm ok");
		}
		else
		{
			alarm.check(R.id.radio_home_unset);
			Log.i("", "alarm no");
		}
		
		alarm.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
								
				if (checkedId == R.id.radio_home_set)
				{
					if (checkValidTime())
					{
						// 호출 등록
						String time = alarmTime.getText().toString();
						String[] times = time.split(":");
						int hour = Integer.parseInt(times[0]);
						int minute = Integer.parseInt(times[1]);
						
						uData.setNextAlarmTime(hour, minute);
						uData.setAlarm(getActivity());
						
						AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
						alert.setMessage("알림설정이 완료되었습니다.\n*방문 전날 정해진 시간에 울립니다.").setTitle("");
						alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();								
							}
						});
						alert.show();
					}
					else
					{
						// 시간입력 안내
						AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
						alert.setMessage("알림 시간을 설정해 주세요.").setTitle("오류");
						alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						alert.show();
						
						// 버튼 원래대로
						group.check(R.id.radio_home_unset);
					}				
				}
				else
				{
					// 알림해제
					Intent alarmIntent = new Intent(getActivity(), AlarmReciever.class);
					PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getActivity(), UserData.ALARM_ID, alarmIntent, PendingIntent.FLAG_NO_CREATE);
					if (alarmPendingIntent != null)
					{
						AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
						alarmManager.cancel(alarmPendingIntent);
						alarmPendingIntent.cancel();
						
						alarmTime.setText("");
						
						Toast.makeText(getActivity(), "알림설정이 취소되었습니다.", Toast.LENGTH_LONG).show();
					}					
				}
			}
			
			private boolean checkValidTime()
			{
				String time = alarmTime.getText().toString();
				return time.matches("[0-9]{2}:[0-9]{2}");
			}
		});
		
		// 알람시간
		if (isAlarmed)
			alarmTime.setText(uData.getAlarmData());
		alarmTime.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus)
				{
					TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							alarmTime.setText(String.format("%02d:%02d", hourOfDay, minute));
						}

					}, 0, 0, true);
					timePickerDialog.setOnDismissListener(new OnDismissListener() {
						
						@Override
						public void onDismiss(DialogInterface dialog) {
							alarmTime.clearFocus();
						}
					});
					timePickerDialog.show();
				}
			}
			
		});

		return view;
	}
	
}
