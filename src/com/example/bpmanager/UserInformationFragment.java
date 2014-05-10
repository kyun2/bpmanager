package com.example.bpmanager;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UserInformationFragment extends Fragment
{
	EditText name;
	EditText email;
	EditText height;
	//EditText waist;
	//EditText weight;
	EditText birth;
	RadioGroup gender;
	
	//CheckBox hyper;
	CheckBox glucose;
	CheckBox kidney;
	//CheckBox coronary;
	
	EditText lastVisitDate;
	EditText nextVisitDate;
	
	Calendar c = Calendar.getInstance();
    int cyear = c.get(Calendar.YEAR);
    int cmonth = c.get(Calendar.MONTH);
    int cday = c.get(Calendar.DAY_OF_MONTH);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_user, container, false);
		
		ImageButton modify = (ImageButton)view.findViewById(R.id.user_commit);
		modify.setOnClickListener(click);
		
		name = (EditText)view.findViewById(R.id.edit_name);
		email = (EditText)view.findViewById(R.id.edit_email);
		gender = (RadioGroup)view.findViewById(R.id.gender_radio);
		height = (EditText)view.findViewById(R.id.edit_bodyheight);
		//weight = (EditText)view.findViewById(R.id.edit_bodyweight);
		//waist = (EditText)view.findViewById(R.id.edit_waist);
		birth = (EditText)view.findViewById(R.id.edit_birth);
		
		//hyper = (CheckBox)view.findViewById(R.id.high_check);
		glucose = (CheckBox)view.findViewById(R.id.glucos_check);
		kidney = (CheckBox)view.findViewById(R.id.kidney_check);
		//coronary = (CheckBox)view.findViewById(R.id.coronary_check);
		
		lastVisitDate = (EditText)view.findViewById(R.id.edit_lastvisitdate);
		nextVisitDate = (EditText)view.findViewById(R.id.edit_nextvisitdate);
		
		// listener
		birth.setOnFocusChangeListener(focus);
		lastVisitDate.setOnFocusChangeListener(focus);
		nextVisitDate.setOnFocusChangeListener(focus);
		
		setView();

		return view;
	}
	
	private void setView() {
		// TODO Auto-generated method stub
		UserData uData = MainActivity.mUserData;
		if (!uData.IsLoaded())
			return;
		
		name.setText(uData.getName());
		email.setText(uData.getEmail());
		if (uData.getSex() == 1)
			gender.check(R.id.radio_male);
		else
			gender.check(R.id.radio_female);
		birth.setText(uData.getBirth());
		height.setText(Float.toString(uData.getHeight()));
		//weight.setText(Float.toString(uData.getWeight()));
		//waist.setText(Float.toString(uData.getWaist()));
		//hyper.setChecked(uData.getHypertension() == 1);
		glucose.setChecked(uData.getGlucose() == 1);
		kidney.setChecked(uData.getKidney() == 1);
		//coronary.setChecked(uData.getCoronary() == 1);		
		lastVisitDate.setText(uData.getLastVisitDate());
		nextVisitDate.setText(uData.getNextVisitDate());
	}
	
	View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(hasFocus == true && (v.getId() == R.id.edit_birth || v.getId() == R.id.edit_lastvisitdate || v.getId() == R.id.edit_nextvisitdate)){
				EditText text = null;
				switch (v.getId())
				{
				case R.id.edit_birth:
					text = birth;
					break;
				case R.id.edit_lastvisitdate:
					text = lastVisitDate;
					break;
				case R.id.edit_nextvisitdate:
					text = nextVisitDate;
					break;
				}
				
				if (text != null)
				{
					String[] arr = text.getText().toString().split("/");
					
					try
					{
						int year = Integer.parseInt(arr[0]);
						int month = Integer.parseInt(arr[1]) - 1;
						int day = Integer.parseInt(arr[2]);
						
						DialogDatePicker(year, month, day, text);
					}
					catch (NumberFormatException e)
					{
						DialogDatePicker(0, 0, 0, text);
					}
				}
			}
		}
	};
	
	View.OnClickListener click = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			final Context context = getActivity();
			
			switch(v.getId())
			{
			case R.id.user_commit:
				// Hide keyboard
				InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (getActivity().getCurrentFocus() != null)
					imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
				if (checkInputData())
				{
					final UserData uData = MainActivity.mUserData;
					// modify
					if (uData.IsLoaded())
					{
						// Go back
						FragmentManager fm = getActivity().getSupportFragmentManager();
						fm.popBackStack();
					}
					else // regist
					{
						// Go Home
						MainActivity activity = (MainActivity) getActivity();
						activity.fragmentReplace(MainActivity.FRAGMENT_HOME);
						// Footer show
						MainActivity.showFooter();
					}
					// Store Data to DB				
					updateUserData();
					uData.submitData();
					uData.setAlarm(context);
					// Set Alarm
					/*
					AlertDialog.Builder confirm = new AlertDialog.Builder(getActivity());
					confirm.setMessage("병원방문 전날 19:00시 알림을 설정할까요?").setTitle("알림설정확인");
					confirm.setPositiveButton("예", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							uData.setAlarm(context);
						}
					});
					confirm.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							uData.unsetAlarm(context);
						}
					});
					confirm.show();
					*/
				}
				else
				{
					AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
					alert.setMessage("입력하지 않은 정보가 있습니다.").setTitle("오류");
					alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					alert.show();
				}							
				break;
			}			
		}
		
		private boolean checkInputData()
		{
			// name
			if (name.length() == 0)
				return false;
			
			// email
			if (email.length() == 0 || !email.getText().toString().contains("@"))
				return false;
			
			// birth date
			if (birth.length() == 0 || !birth.getText().toString().matches("[0-9]{4}/[0-9]{2}/[0-9]{2}"))
				return false;
			
			// height
			if (height.length() == 0)
				return false;
			
			// weight
			//if (weight.length() == 0)
			//	return false;
			
			// waist
			//if (waist.length() == 0)
			//	return false;
			
			// last
			//if (lastVisitDate.length() == 0)
			//	return false;
			
			// next
			//if (nextVisitDate.length() == 0)
			//	return false;
			
			return true;
		}
			
		public void updateUserData(){
			UserData uData = MainActivity.mUserData;			
			
			uData.setName(name.getText().toString());
			uData.setEmail(email.getText().toString());
			if (gender.getCheckedRadioButtonId() == R.id.radio_male)
				uData.setSex(1);
			else
				uData.setSex(2);
			uData.setBirth(birth.getText().toString());
			try
			{
				uData.setHeight(Float.parseFloat(height.getText().toString()));
			}
			catch (NumberFormatException e)
			{
				uData.setHeight(0f);
			}
			//try
			//{
			//	uData.setWeight(Float.parseFloat(weight.getText().toString()));
			//}
			//catch (NumberFormatException e)
			//{
			//	uData.setWeight(0f);
			//}
			//try
			//{
			//	uData.setWaist(Float.parseFloat(waist.getText().toString()));
			//}
			//catch (NumberFormatException e)
			//{
			//	uData.setWaist(0f);
			//}
			//uData.setHypertension(hyper.isChecked() ? 1 : 0);
			uData.setGlucose(glucose.isChecked() ? 1 : 0);
			uData.setKidney(kidney.isChecked() ? 1 : 0);
			//uData.setCoronary(coronary.isChecked() ? 1 : 0);
			uData.setLastVisitDate(lastVisitDate.getText().toString());
			uData.setNextVisitDate(nextVisitDate.getText().toString());
			if (nextVisitDate.getText().toString().matches("[0-9]{4}/[0-9]{2}/[0-9]{2}"))
			{
				uData.setNextAlarmTime(19, 0);
			}
		}
	};
	
	private void DialogDatePicker(int cyear, int cmonth, int cday, final EditText target){

	    DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				String date = String.format("%04d/%02d/%02d", year, monthOfYear+1, dayOfMonth);
	    		target.setText(date);
			}
		}, cyear > 0 ? cyear : 2014, cmonth > 0 ? cmonth : 0, cday > 0 ? cday : 1);
	    dialog.setOnDismissListener(new DatePickerDialog.OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				View nextFocusView = null;
				switch (target.getId())
				{
				case R.id.edit_birth:
					nextFocusView = height;
					break;
				case R.id.edit_lastvisitdate:
					nextFocusView = nextVisitDate;
					break;
				case R.id.edit_nextvisitdate:
					nextFocusView = name;
					break;
				}
				if (nextFocusView != null)
					nextFocusView.requestFocus();				
			}
		});
	    dialog.show();
	}

}
