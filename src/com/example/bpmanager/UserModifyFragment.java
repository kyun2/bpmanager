package com.example.bpmanager;

import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserModifyFragment extends Fragment{
	DBhandler handle;
	user u;
	
	EditText name;
	EditText email;
	EditText height;
	EditText waist;
	EditText weight;
	EditText birth;
	RadioGroup gender;
	
	CheckBox hyper;
	CheckBox glucose;
	CheckBox kidney;
	CheckBox coronary;
	
	Calendar c = Calendar.getInstance();
    int cyear = c.get(Calendar.YEAR);
    int cmonth = c.get(Calendar.MONTH);
    int cday = c.get(Calendar.DAY_OF_MONTH);
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_user, container, false);
		
//		Button button = (Button) view.findViewById(R.id.bt_ok);
//		button.setOnClickListener(this);
		Button modify = (Button)view.findViewById(R.id.user_commit);
		modify.setOnClickListener(click);
		name = (EditText)view.findViewById(R.id.edit_name);
		email = (EditText)view.findViewById(R.id.edit_email);
		height = (EditText)view.findViewById(R.id.edit_bodyheight);
		waist = (EditText)view.findViewById(R.id.edit_waist);
		weight = (EditText)view.findViewById(R.id.edit_bodyweight);
		birth = (EditText)view.findViewById(R.id.edit_birth);
		birth.setOnFocusChangeListener(focus);
//		birth.setOnClickListener(click);
		gender = (RadioGroup)view.findViewById(R.id.gender_radio);
		
		hyper = (CheckBox)view.findViewById(R.id.high_check);
		glucose = (CheckBox)view.findViewById(R.id.glucos_check);
		kidney = (CheckBox)view.findViewById(R.id.kidney_check);
		coronary = (CheckBox)view.findViewById(R.id.coronary_check);
		
		setVeiew();
		

		return view;
	}
	private void setVeiew() {
		// TODO Auto-generated method stub
		handle = new DBhandler(getActivity());
		handle = handle.readOpen();
		List<user> retval = handle.getUsers();
		if(!retval.isEmpty()){
		u = retval.get(retval.size() - 1);

		name.setText(u.getName());
		email.setText(u.getEmail());
		if(u.getGender() == 1){
			gender.check(R.id.radio_male);
		}else{
			gender.check(R.id.radio_female);
		}
		birth.setText(u.getBirth());
		height.setText(u.getHeight());
		weight.setText(u.getWeight());
		waist.setText(u.getWaist());
		if(u.getHypertension() == 1){
			hyper.setChecked(true);
		}
		if(u.getGlucose() == 1){
			glucose.setChecked(true);
		}
		if(u.getKidney() == 1){
			kidney.setChecked(true);
		}
		if(u.getCoronary() == 1){
			coronary.setChecked(true);
		}
		}
		handle.close();
		
		
		
	}
	private user setUser(Cursor cu) {
		// TODO Auto-generated method stub
		user retval = new user();
		retval.setKey(cu.getInt(0));
		retval.setName(cu.getString(1));
		retval.setEmail(cu.getString(2));
		retval.setGender(cu.getInt(3));
		retval.setBirth(cu.getString(4));
		retval.setHeight(cu.getString(5));
		retval.setWeight(cu.getString(6));
		retval.setWaist(cu.getString(7));
		retval.setHypertension(cu.getInt(8));
		retval.setGlucose(cu.getInt(9));
		retval.setKidney(cu.getInt(10));
		retval.setCoronary(cu.getInt(11));

		return retval;
		
	}
	private void viewToast(String text) {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
		
	}
	View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.edit_birth && hasFocus == true){
				String[] arr = birth.getText().toString().split("/");
				
				DialogDatePicker(Integer.valueOf(arr[0]), Integer.valueOf(arr[1]) - 1, Integer.valueOf(arr[2]));
			}
			
		}
	};
	View.OnClickListener click = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.user_commit :
				insertDB();
				//updateDB();
				FragmentManager fm = getActivity().getSupportFragmentManager();
				fm.popBackStack();
				break;
//			case R.id.edit_birth:
//				DialogDatePicker();
//				break;
			}
			
		}
		
		public void updateDB(){
			handle = new DBhandler(getActivity());
			handle = handle.open();
			//user u = new user();
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
			
//			long retval =handle.insertUser(u);
			long retval = handle.updateUser(u.getKey(), u);
			
//			Cursor cu = handle.getUsers();
//			if(cu.getCount() > 0){
//				cu.moveToPosition(cu.getCount() - 1);
//				String text = "modifed user :\n"
//						+handle.KEY+"="+cu.getString(0)+"\n"
//						+handle.NAME+"="+cu.getString(1)+"\n"
//						+handle.EMAIL+"="+cu.getString(2)+"\n"
//						+handle.GENDER+"="+cu.getString(3)+"\n"
//						+handle.BIRTH+"="+cu.getString(4)+"\n"
//						+handle.HEIGHT+"="+cu.getString(5)+"\n"
//						+handle.WEIGHT+"="+cu.getString(6)+"\n"
//						+handle.WAIST+"="+cu.getString(7)+"\n"
//						+handle.HYPER+"="+cu.getString(8)+"\n"
//						+handle.GLUCOSE+"="+cu.getString(9)+"\n"
//						+handle.KIDNEY+"="+cu.getString(10)+"\n"
//						+handle.CORONARY+"="+cu.getString(11)+"\n";
//				Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
//				
//			}
			
			handle.close();
			
			viewUpdate();
			
		}

		private void viewUpdate() {
			// TODO Auto-generated method stub
			handle = new DBhandler(getActivity());
			handle = handle.open();
			List<user> retval = handle.getUsers();
			if(!retval.isEmpty()){
				user us = retval.get(retval.size());
				String text = "modifed user :\n"
						+DBhandler.KEY+"="+us.getKey()+"\n"
						+DBhandler.NAME+"="+us.getName()+"\n"
						+DBhandler.EMAIL+"="+us.getEmail()+"\n"
						+DBhandler.GENDER+"="+us.getGender()+"\n"
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
			user u = new user();
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
			List<user> value = handle.getUsers();
			if(!value.isEmpty()){
				user us =value.get(value.size() - 1);
				String text = "modifed user :\n"
						+DBhandler.KEY+"="+us.getKey()+"\n"
						+DBhandler.NAME+"="+us.getName()+"\n"
						+DBhandler.EMAIL+"="+us.getEmail()+"\n"
						+DBhandler.GENDER+"="+us.getGender()+"\n"
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
	         Toast.makeText(getActivity(), 
	        "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
	         String date = String.valueOf(year) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(dayOfMonth);
	         birth.setText(date);
	    	}

	    };
	     DatePickerDialog alert = new DatePickerDialog(getActivity(),  mDateSetListener,  
	     cyear, cmonth, cday);
	     alert.show();
	}


//	public void onClick(View v) {
//
//		switch (v.getId()) {
//
////		case R.id.bt_ok:
////			Toast.makeText(getActivity(), "One Fragment", Toast.LENGTH_SHORT)
////					.show();
////			break;
//
//		}
//	}

}
