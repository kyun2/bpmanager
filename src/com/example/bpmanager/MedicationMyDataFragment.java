package com.example.bpmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class MedicationMyDataFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_mine, container, false);
		
		// 의약품 추가
		Button add = (Button) view.findViewById(R.id.med_btn_add_medicine);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).changeFragment(new MedicationListFragment());
			}
		});
		
		return view;
	}
	
	

}
