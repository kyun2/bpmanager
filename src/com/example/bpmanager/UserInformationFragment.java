package com.example.bpmanager;

import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UserInformationFragment extends Fragment
{
	EditText name;
	EditText email;
	EditText height;
	EditText waist;
	EditText weight;
	EditText birth;
	RadioGroup gender;
	
	//CheckBox hyper;
	CheckBox glucose;
	CheckBox kidney;
	//CheckBox coronary;
	
	Calendar c = Calendar.getInstance();
    int cyear = c.get(Calendar.YEAR);
    int cmonth = c.get(Calendar.MONTH);
    int cday = c.get(Calendar.DAY_OF_MONTH);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_user, container, false);
		
		Button modify = (Button)view.findViewById(R.id.user_commit);
		modify.setOnClickListener(click);
		
		name = (EditText)view.findViewById(R.id.edit_name);
		email = (EditText)view.findViewById(R.id.edit_email);
		gender = (RadioGroup)view.findViewById(R.id.gender_radio);
		height = (EditText)view.findViewById(R.id.edit_bodyheight);
		weight = (EditText)view.findViewById(R.id.edit_bodyweight);
		waist = (EditText)view.findViewById(R.id.edit_waist);
		birth = (EditText)view.findViewById(R.id.edit_birth);
		birth.setOnFocusChangeListener(focus);
//		birth.setOnClickListener(click);
		//hyper = (CheckBox)view.findViewById(R.id.high_check);
		glucose = (CheckBox)view.findViewById(R.id.glucos_check);
		kidney = (CheckBox)view.findViewById(R.id.kidney_check);
		//coronary = (CheckBox)view.findViewById(R.id.coronary_check);
		
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
		weight.setText(Float.toString(uData.getWeight()));
		waist.setText(Float.toString(uData.getWaist()));
		//hyper.setChecked(uData.getHypertension() == 1);
		glucose.setChecked(uData.getGlucose() == 1);
		kidney.setChecked(uData.getKidney() == 1);
		//coronary.setChecked(uData.getCoronary() == 1);
	}
	
	private void viewToast(String text) {
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
	}
	
	View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.edit_birth && hasFocus == true){
				String[] arr = birth.getText().toString().split("/");
				
				try
				{
					int year = Integer.parseInt(arr[0]);
					int month = Integer.parseInt(arr[1]) - 1;
					int day = Integer.parseInt(arr[2]);
					
					DialogDatePicker(year, month, day);
				}
				catch (NumberFormatException e)
				{
					DialogDatePicker(0, 0, 0);
				}
			}
			
		}
	};
	
	View.OnClickListener click = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.user_commit:
				// Hide keyboard
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (getActivity().getCurrentFocus() != null)
					imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
				if (checkInputData())
				{
					UserData uData = MainActivity.mUserData;
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
				}
				else
				{
					AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
					alert.setMessage("사용자정보를 모두 입력하여 주세요.").setTitle("오류");
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
			if (birth.length() == 0 || !birth.getText().toString().matches("[0-9]{4}/[0-9]+/[0-9]+"))
				return false;
			
			// height
			if (height.length() == 0)
				return false;
			
			// weight
			if (weight.length() == 0)
				return false;
			
			// waist
			if (waist.length() == 0)
				return false;
			
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
			try
			{
				uData.setWeight(Float.parseFloat(weight.getText().toString()));
			}
			catch (NumberFormatException e)
			{
				uData.setWeight(0f);
			}
			try
			{
				uData.setWaist(Float.parseFloat(waist.getText().toString()));
			}
			catch (NumberFormatException e)
			{
				uData.setWaist(0f);
			}
			//uData.setHypertension(hyper.isChecked() ? 1 : 0);
			uData.setGlucose(glucose.isChecked() ? 1 : 0);
			uData.setKidney(kidney.isChecked() ? 1 : 0);
			//uData.setCoronary(coronary.isChecked() ? 1 : 0);
		}
		
		/*

		private void viewUpdate() {
			// TODO Auto-generated method stub
			handle = new DBhandler(getActivity());
			handle = handle.open();
			List<UserData> retval = handle.getUsers();
			if(!retval.isEmpty()){
				UserData us = retval.get(retval.size());
				String text = "modifed user :\n"
						//+DBhandler.KEY+"="+us.getKey()+"\n"
						+DBhandler.NAME+"="+us.getName()+"\n"
						+DBhandler.EMAIL+"="+us.getEmail()+"\n"
						+DBhandler.GENDER+"="+us.getSex()+"\n"
						+DBhandler.BIRTH+"="+us.getBirth()+"\n"
						+DBhandler.HEIGHT+"="+us.getHeight()+"\n"
						+DBhandler.WEIGHT+"="+us.getWeight()+"\n"
						+DBhandler.WAIST+"="+us.getWaist()+"\n"
						+DBhandler.HYPER+"="+us.getHypertension()+"\n"
						+DBhandler.GLUCOSE+"="+us.getGlucose()+"\n"
						+DBhandler.KIDNEY+"="+us.getKidney()+"\n"
						+DBhandler.CORONARY+"="+us.getCoronary()+"\n";
				Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
				
			}
			
			handle.close();
			
		}

		private void insertDB() {
			// TODO Auto-generated method stub
			handle = new DBhandler(getActivity());
			handle = handle.open();
			UserData u = new UserData();
			u.name = name.getText().toString();
			u.email = email.getText().toString();
			if(gender.getCheckedRadioButtonId() == R.id.radio_male){
				u.gender = 1;
			}else if(gender.getCheckedRadioButtonId() == R.id.radio_female){
				u.gender = 0;
			}
			u.birth = birth.getText().toString();
			u.height = height.getText().toString();
			u.waist = waist.getText().toString();
			u.weight = weight.getText().toString();
			
			if(hyper.isChecked()){
				u.hypertension = 1;
			}else{
				u.hypertension = 0;
			}
			if(glucose.isChecked()){
				u.glucose = 1;
			}else{
				u.glucose = 0;
			}
			if(kidney.isChecked()){
				u.kidney = 1;
			}else{
				u.kidney = 0;
			}
			if(coronary.isChecked()){
				u.coronary = 1;
			}else{
				u.coronary = 0;
			}
			
			long retval =handle.insertUser(u);
			List<UserData> value = handle.getUsers();
			if(!value.isEmpty()){
				UserData us =value.get(value.size() - 1);
				String text = "modifed user :\n"
						//+DBhandler.KEY+"="+us.getKey()+"\n"
						+DBhandler.NAME+"="+us.getName()+"\n"
						+DBhandler.EMAIL+"="+us.getEmail()+"\n"
						+DBhandler.GENDER+"="+us.getSex()+"\n"
						+DBhandler.BIRTH+"="+us.getBirth()+"\n"
						+DBhandler.HEIGHT+"="+us.getHeight()+"\n"
						+DBhandler.WEIGHT+"="+us.getWeight()+"\n"
						+DBhandler.WAIST+"="+us.getWaist()+"\n"
						+DBhandler.HYPER+"="+us.getHypertension()+"\n"
						+DBhandler.GLUCOSE+"="+us.getGlucose()+"\n"
						+DBhandler.KIDNEY+"="+us.getKidney()+"\n"
						+DBhandler.CORONARY+"="+us.getCoronary()+"\n";
				Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
				
			}
			
			handle.close();
			
		}
		*/
	};
	
	private void DialogDatePicker(int cyear, int cmonth, int cday){
//	    Calendar c = Calendar.getInstance();
//	    int cyear = c.get(Calendar.YEAR);
//	    int cmonth = c.get(Calendar.MONTH);
//	    int cday = c.get(Calendar.DAY_OF_MONTH);
	     
	    DatePickerDialog.OnDateSetListener mDateSetListener = 
	    new DatePickerDialog.OnDateSetListener() {
	    	// onDateSet method
	    	@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	    		String date_selected = String.valueOf(monthOfYear+1)+
	                " /"+String.valueOf(dayOfMonth)+" /"+String.valueOf(year);
	    		Toast.makeText(getActivity(), "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
	    		String date = String.valueOf(year) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(dayOfMonth);
	    		birth.setText(date);
	    	}
	    };
	    DatePickerDialog dialog = new DatePickerDialog(getActivity(),  mDateSetListener,  
	    		cyear > 0 ? cyear : 2014, cmonth > 0 ? cmonth : 0, cday > 0 ? cday : 1);
	    dialog.show();
	}

}
