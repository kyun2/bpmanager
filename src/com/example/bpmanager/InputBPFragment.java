package com.example.bpmanager;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputBPFragment extends Fragment{
	
	DBhandler handle;
	Button inputbth;
	Button bpviewbth;
	
	EditText systolic;
	EditText diastolic;
	EditText bptime;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_inputbp, container, false);
		inputbth = (Button) view.findViewById(R.id.inputbp_btn);
		bpviewbth = (Button) view.findViewById(R.id.bpview_bth);
		
		systolic = (EditText) view.findViewById(R.id.edit_sys);
		diastolic= (EditText) view.findViewById(R.id.edit_dia);
		bptime= (EditText) view.findViewById(R.id.edit_bpinput_time);
		
		inputbth.setOnClickListener(click);
		bpviewbth.setOnClickListener(click);
		
		bptime.setOnFocusChangeListener(focus);
		
		TextView sysMax = (TextView) view.findViewById(R.id.systolic_max);
		handle = new DBhandler(getActivity());
		handle.open();
		List<user> us = handle.getUsers();
		handle.close();
		if(!us.isEmpty()){
			user temp = us.get(us.size()-1);
			String[] arr = temp.getBirth().split("/");
			int year = Integer.valueOf(arr[0]);
			Date today = new Date();
			if((today.getYear() - year) < 79 && 
					(temp.getCoronary() == 1 || 
					temp.getHypertension() == 1 || 
					temp.getGlucose() == 1 || 
					temp.getKidney() == 1)){
				sysMax.setText("140/");
			}
		}
		
		

//		Button button = (Button) view.findViewById(R.id.bt_ok);
//		button.setOnClickListener(this);

		return view;
	}
	View.OnClickListener click = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.inputbp_btn:
				inputBP();
				break;
			case R.id.bpview_bth:
				Fragment next = new BPViewFragment();
				changeFragment(next);
				break;
			}
			
		}

		private void changeFragment(Fragment next) {
			// TODO Auto-generated method stub
			final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
					.beginTransaction();

			transaction.replace(R.id.frag_viewer, next);
			transaction.addToBackStack(null);
			// Commit the transaction
			transaction.commit();
			
		}

		
	};
	private void inputBP() {
		// TODO Auto-generated method stub
		bp value = new bp();
		value.setBpdatetime(bptime.getText().toString());
		value.setDiastolic(Integer.valueOf(diastolic.getText().toString()));
		value.setSystolic(Integer.valueOf(systolic.getText().toString()));
		handle = new DBhandler(getActivity());
		handle.open();
		long retval = handle.insertBP(value);
		if(retval > 0){
			Toast.makeText(getActivity(), "insert bp success", 2000).show();
		}
		handle.close();
		
	}
	
	View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.edit_bpinput_time && hasFocus){
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle("혈압측정시간");
				final DateTimePicker dt = new DateTimePicker(getActivity());
				
				
				alert.setView(dt);
				alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int year = dt.getYear();
						int month = dt.getMonth() + 1;
						int day = dt.getDayOfMonth();
						int hour = dt.getCurrentHour();
						int minute = dt.getCurrentMinute();
						String time =String.valueOf(year)+String.valueOf(month)+String.valueOf(day)+String.valueOf(hour)+String.valueOf(minute);
						bptime.setText(time);
						dialog.dismiss();
						
						// TODO Auto-generated method stub
						
					}
				});
				alert.show();
			}
			
		}
	};

}
