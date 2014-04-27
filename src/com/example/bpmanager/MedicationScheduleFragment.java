package com.example.bpmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MedicationScheduleFragment extends Fragment {
	
	EditText amount;
	EditText count;
	
	Button submitBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_user, container, false);
		
		amount = (EditText) view.findViewById(R.id.medischedule_amount);
		count = (EditText) view.findViewById(R.id.medischedule_count);
		
		submitBtn = (Button) view.findViewById(R.id.medischedule_submit);
		submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});

		return view;
	}
	
	private boolean checkInputData()
	{
		if (amount.length() == 0)
			return false;
		
		if (count.length() == 0)
			return false;
		
		return true;
	}
	
	private void updateData()
	{
		
	}
	
}
