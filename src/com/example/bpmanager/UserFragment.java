package com.example.bpmanager;

import java.util.Calendar;
import java.util.List;

import com.example.bpmanager.DB.DBhandler;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class UserFragment extends Fragment{
	DBhandler handle;
	
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
		handle = new DBhandler(container.getContext());
//		Button button = (Button) view.findViewById(R.id.bt_ok);
//		button.setOnClickListener(this);
		Button commit = (Button)view.findViewById(R.id.user_commit);
		commit.setOnClickListener(click);
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
		

		return view;
	}
	View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.edit_birth && hasFocus == true){
				DialogDatePicker();
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

//				Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

				Fragment newFragment = new ClinicFragment(); 

				// replace fragment
				final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
						.beginTransaction();

				transaction.replace(R.id.frag_viewer, newFragment);
				transaction.addToBackStack(null);
//				FragmentManager fm = getActivity().getSupportFragmentManager();
//				for(int i = 0; i < fm.getBackStackEntryCount(); i++){
//					fm.popBackStack();
//				}
				 //Commit the transaction
				transaction.commit();
				break;
//			case R.id.edit_birth:
//				DialogDatePicker();
//				break;
			}
			
		}

		private void insertDB() {
			// TODO Auto-generated method stub
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
	
	private void DialogDatePicker(){
	    Calendar c = Calendar.getInstance();
	    int cyear = c.get(Calendar.YEAR);
	    int cmonth = c.get(Calendar.MONTH);
	    int cday = c.get(Calendar.DAY_OF_MONTH);
	     
	    DatePickerDialog.OnDateSetListener mDateSetListener = 
	    new DatePickerDialog.OnDateSetListener() {
	    // onDateSet method
	    	@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
	         String date_selected = String.valueOf(monthOfYear+1)+
	                " /"+String.valueOf(dayOfMonth)+" /"+String.valueOf(year);
	         Toast.makeText(getActivity(), 
	        "Selected Date is ="+date_selected, Toast.LENGTH_SHORT).show();
	         String date = String.valueOf(year) + String.valueOf(monthOfYear+1) + String.valueOf(dayOfMonth);
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
