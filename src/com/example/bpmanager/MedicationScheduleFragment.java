package com.example.bpmanager;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MedicationScheduleFragment extends Fragment {
	
	int medicine_id;
	
	EditText amount;
	EditText count;
	EditText time;
	
	Button submitBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_schedule, container, false);
		
		amount = (EditText) view.findViewById(R.id.medischedule_amount);
		count = (EditText) view.findViewById(R.id.medischedule_count);
		time = (EditText) view.findViewById(R.id.medischedule_time);
		
		submitBtn = (Button) view.findViewById(R.id.medischedule_submit);
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Hide keyboard
				InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				if (getActivity().getCurrentFocus() != null)
					imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
				if (checkInputData())
				{
					MedicationScheduleData msData = MainActivity.mMedicationScheduleData;
					
					// Go back
					FragmentManager fm = getActivity().getSupportFragmentManager();
					fm.popBackStack();
					((MainActivity)getActivity()).changeFragment(new MedicationTopFragment());
					// Store Data to DB				
					updateData();
					msData.submitData(medicine_id);
				}
				else
				{
					AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
					alert.setMessage("모든 값을 입력하여 주시기 바랍니다.").setTitle("오류");
					alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					alert.show();
				}
			}
		});
		
		time.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus)
				{
					TimePickerDialog dialog = new TimePickerDialog(getActivity(), new OnTimeSetListener() {
						
						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							time.setText(String.format("%02d:%02d", hourOfDay, minute));
						}
					}, 0, 0, true);
					dialog.show();
				}
			}
		});

		return view;
	}
	
	public void setMedicineId(int id)
	{
		medicine_id = id;
	}
	
	public int getMedicineId()
	{
		return medicine_id;
	}
	
	private boolean checkInputData()
	{
		if (amount.length() == 0)
			return false;
		
		if (count.length() == 0)
			return false;
		
		if (time.length() == 0)
			return false;
		
		return true;
	}
	
	private void updateData()
	{
		MedicationScheduleData msData = MainActivity.mMedicationScheduleData;
		int amount = 0;
		int count = 0;
		String time = "";
		
		try 
		{
			amount = Integer.parseInt(this.amount.getText().toString());
		}
		catch (NumberFormatException e)
		{
			amount = 0;
		}
		try
		{
			count = Integer.parseInt(this.count.getText().toString());
		}
		catch (NumberFormatException e)
		{
			count = 0;
		}
		try
		{
			time = this.time.getText().toString();
		}
		catch (NumberFormatException e)
		{
			time = "";
		}
		
		if (amount > 0 && count > 0 && time != "")
		{
			msData.addSchedule(medicine_id, amount, count, time);
		}
	}
	
}
