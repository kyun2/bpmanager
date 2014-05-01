package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.bpmanager.DB.DBMedication;
import com.example.bpmanager.DB.DBMedicationTook;
import com.example.bpmanager.DB.INFOMedication;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MedicationDetailFragment extends Fragment {
	
	private int medicine_id;
	private String medicine_inject_time = "";
	
	//ImageView img;
	TextView name;
	TextView ingredient;
	
	EditText alarmTime;
	
	ImageButton btnOpenWebInfo;
	Button btnTook;
	Button btnDelete;
	
	RadioGroup alarm;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_detail, container, false);
		final INFOMedication info = INFOMedication.getInfoMedicine(medicine_id);
		
		medicine_inject_time = loadInjectionTime();
		
		// 컴포넌트들
		//img = (ImageView) view.findViewById(R.id.medicine_img);
		name = (TextView) view.findViewById(R.id.medicine_name);
		ingredient = (TextView) view.findViewById(R.id.medicine_ingredient);
		alarmTime = (EditText) view.findViewById(R.id.edit_alarm_time);
		btnOpenWebInfo = (ImageButton) view.findViewById(R.id.medicine_web_open);
		btnTook = (Button) view.findViewById(R.id.med_btn_took_it);
		btnDelete = (Button) view.findViewById(R.id.med_btn_delete_it);
		alarm = (RadioGroup) view.findViewById(R.id.radiogrp_med_alarm);
		
		// Image
		
		// name
		name.setText(info.mName);
		
		// ingredient
		ingredient.setText(info.mIngredient);
		
		// 자세히 보기 버튼		
		btnOpenWebInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).OpenBrowser(info.mWebLink);
			}
		});
		
		// 복용하기 버튼		
		if (hasTookToday())
		{
			btnTook.setEnabled(false);
			btnTook.setClickable(false);
			btnTook.setBackgroundResource(R.drawable.basic_button_dis);
		}
		else
		{
			btnTook.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// DB
					insert();
					// Disable button
					btnTook.setEnabled(false);
					btnTook.setClickable(false);
					btnTook.setBackgroundResource(R.drawable.basic_button_dis);
					// 알림
					AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
					alert.setMessage("복용 완료").setTitle("복용완료");
					alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					alert.show();
				}
				
				private void insert()
				{
					ContentValues values = new ContentValues();
					values.put(DBMedicationTook.SCHEMA.COLUMN_MEDID, medicine_id);
					values.put(DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME, (new SimpleDateFormat("yyyy/MM/dd")).format(Calendar.getInstance().getTime()));
					
					MainActivity.mDBHelper.insertData(DBMedicationTook.SCHEMA.TB_NAME, values);
					MainActivity.mMediHistData.resetData();
				}
			});
		}
		
		btnDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 삭제 확인팝업
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setMessage("복용을 중단하시겠습니까?").setTitle("복용중단");
				alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						delete();
					}
				});
				alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {						
					}
				});
				
				alert.show();
			}
			
			private void delete()
			{
				MainActivity.mDBHelper.deleteData(DBMedication.Medication.TB_NAME, " medicine_id = " + medicine_id, null);
				MainActivity.mMedicationScheduleData.getData();				
				// 뒤로 가기
				FragmentManager fm = getActivity().getSupportFragmentManager();
				fm.popBackStack();
			}
		});
		
		// 알람
		boolean isAlarmed = false;
		Intent alarmIntent = new Intent(getActivity(), AlarmReciever.class);
		PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getActivity(), info.mId, alarmIntent, PendingIntent.FLAG_NO_CREATE);
		if (alarmPendingIntent != null)
			isAlarmed = true;
		
		if (isAlarmed)
			alarm.check(R.id.radio_med_set);
		else
			alarm.check(R.id.radio_med_unset);		
		alarm.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				Intent alarmIntent = new Intent(getActivity(), AlarmReciever.class);
				alarmIntent.putExtra("type", "MEDICINE");
				alarmIntent.putExtra("medicineId", info.mId);
				alarmIntent.putExtra("medicineName", info.mName);
								
				AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
				
				if (checkedId == R.id.radio_med_set)
				{
					if (checkValidTime())
					{
						// 호출 등록
						String time = alarmTime.getText().toString();
						String[] times = time.split(":");
						int hour = Integer.parseInt(times[0]);
						int minute = Integer.parseInt(times[1]);
						Calendar c = Calendar.getInstance();
						c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
						
						PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getActivity(), info.mId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), 1000 * 60 * 60 * 24, alarmPendingIntent);
						
						// DB에 저장
						saveInjectionTime(time);
						
						Toast.makeText(getActivity(), "알림설정이 완료되었습니다.", Toast.LENGTH_LONG).show();
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
						group.check(R.id.radio_med_unset);
					}				
				}
				else
				{
					// 알림해제
					PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(getActivity(), info.mId, alarmIntent, PendingIntent.FLAG_NO_CREATE);
					if (alarmPendingIntent != null)
					{
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
			alarmTime.setText(medicine_inject_time);
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
					timePickerDialog.show();
				}
			}
		});
		
		return view;
	}
	
	public void setMedicineId(int id)
	{
		this.medicine_id = id;
	}
	
	public boolean hasTookToday()
	{
		boolean ret = false;
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedicationTook.SCHEMA.COLUMN_MEDID,
				DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME
			};
			
			Cursor c1 = db.query(DBMedicationTook.SCHEMA.TB_NAME, projection, " medicine_id = " + this.medicine_id, null, null, null, " inject_time desc");
			
			if (c1.moveToFirst())
			{
				String[] times = c1.getString(c1.getColumnIndex(DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME)).split("/");
				
				try
				{
					int day = Integer.parseInt(times[2]);
					int cday = Integer.parseInt((new SimpleDateFormat("dd")).format(Calendar.getInstance().getTime()));
					
					if (Math.abs(day - cday) == 0)
						ret = true;
					else
						ret = false;
				}
				catch (NumberFormatException e)
				{
				}
			}
			else
			{
				ret = false;
			}
			
			c1.close();
		}
		catch (SQLiteException e)
		{
			ret = false;
		}
		return ret;
	}
	
	public String loadInjectionTime()
	{
		String time = "";
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedication.Medication.COLUMN_MEDID,
				DBMedication.Medication.COLUMN_INJECT_TIME
			};
			
			Cursor c1 = db.query(DBMedication.Medication.TB_NAME, projection, " medicine_id = " + this.medicine_id, null, null, null, null);
			
			if (c1.moveToFirst())
			{
				time = c1.getString(c1.getColumnIndex(DBMedication.Medication.COLUMN_INJECT_TIME));
			}
			
			c1.close();
		}
		catch (SQLiteException e)
		{
		}
		return time;
	}
	
	public void saveInjectionTime(String time)
	{
		if (!time.matches("[0-9]{2}:[0-9]{2}"))
			return;
		
		ContentValues values = new ContentValues();
		values.put(DBMedication.Medication.COLUMN_INJECT_TIME, time);
		
		MainActivity.mDBHelper.updateData(DBMedication.Medication.TB_NAME, values, " medicine_id = " + medicine_id, null);
	}
}
