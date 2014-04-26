package com.example.bpmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;

public class ClinicFragment extends Fragment{
	DBhandler handle;
	RadioGroup last;
	RadioGroup next;
	Button inputclinic;
	DatePicker lastDate;
	DatePicker nextDate;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_mydata, container, false);

//		LinearLayout f = (LinearLayout)view.findViewById(R.id.bp_graph_view);
//		ImageView img = new ImageView(view.getContext());
//		BitmapDrawable dr = (BitmapDrawable) view.getContext().getResources().getDrawable(R.drawable.bp_graph);
//        img.setImageDrawable(dr);
//        f.addView(img);

		last = (RadioGroup) view.findViewById(R.id.last_clinic);
		next = (RadioGroup) view.findViewById(R.id.last_clinic);
		last.setOnCheckedChangeListener(checkListen);
		next.setOnCheckedChangeListener(checkListen);
		
		inputclinic = (Button) view.findViewById(R.id.clinic_btn);
		inputclinic.setOnClickListener(click);
		lastDate = (DatePicker) view.findViewById(R.id.last_clinic_date);
		nextDate = (DatePicker) view.findViewById(R.id.next_clinic_date);
		


		return view;
	}
	
	RadioGroup.OnCheckedChangeListener checkListen = new RadioGroup.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (group.getId()){
			case R.id.last_clinic:
				int checked = group.getCheckedRadioButtonId();
				if(checked == R.id.last_ok){
					lastDate.setVisibility(View.VISIBLE);
				}else if(checked == R.id.last_not){
					lastDate.setVisibility(View.INVISIBLE);
				}
				break;
			case R.id.next_clinic:
				int checkedNext = group.getCheckedRadioButtonId();
				if(checkedNext == R.id.next_ok){
					nextDate.setVisibility(View.VISIBLE);
				}else if(checkedNext == R.id.next_not){
					nextDate.setVisibility(View.INVISIBLE);
				}
				break;
			
			}
			
			
		}
	};
	View.OnClickListener click = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()){
			case R.id.clinic_btn:
				if(lastDate.getVisibility() == View.VISIBLE){
					inputDate(lastDate.getYear(), lastDate.getMonth(), lastDate.getDayOfMonth());
				}

				if(lastDate.getVisibility() == View.VISIBLE){
					inputDate(nextDate.getYear(), nextDate.getMonth(), nextDate.getDayOfMonth());
				}
				
				Fragment newFragment = new HomeFragment(); 

				// replace fragment
				final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
						.beginTransaction();

				transaction.replace(R.id.frag_viewer, newFragment);
				//transaction.addToBackStack(null);
				FragmentManager fm = getActivity().getSupportFragmentManager();
				for(int i = 0; i < fm.getBackStackEntryCount(); i++){
					fm.popBackStack();
				}
				 //Commit the transaction
				transaction.commit();
				break;
			
			}
			
		}

		private void inputDate(int year, int month, int dayOfMonth) {
			// TODO Auto-generated method stub
			handle = new DBhandler(getActivity());
			clinicAlram clinic = new clinicAlram();
			String date = String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(dayOfMonth);
			clinic.setClinicDate(date);
			clinic.setClinicCheck(1);
			handle.open();
			handle.insertClinicAlram(clinic);
			handle.close();
			
		}
	};
}
