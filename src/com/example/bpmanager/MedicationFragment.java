package com.example.bpmanager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MedicationFragment extends Fragment{
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_med, container, false);

		Button btn_mymed = (Button) view.findViewById(R.id.med_btn_mymed);
		Button btn_conf = (Button) view.findViewById(R.id.med_btn_configure);
		Button btn_history = (Button) view.findViewById(R.id.med_btn_history);
		btn_mymed.setOnClickListener(clickListener);
		btn_conf.setOnClickListener(clickListener);
		btn_history.setOnClickListener(clickListener);
		
		return view;
	}
	
	View.OnClickListener clickListener = new View.OnClickListener() {
		
		private OnItemSelectedListener selecListener;

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.med_btn_mymed) {
				changeFragment(new MedicationTopFragment());
			} else if (id == R.id.med_btn_configure) {
				
			} else if (id == R.id.med_btn_history) {
				
			}
		}
		
		private void changeFragment(Fragment next) {
			final FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

			transaction.replace(R.id.frag_viewer, next);
			transaction.addToBackStack(null);
			 //Commit the transaction
			transaction.commit();
		}
		
	};


}
