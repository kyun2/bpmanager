package com.example.bpmanager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class UserMenuFragment extends Fragment {

	ImageButton userInfo;
	ImageButton alarm;
	ImageButton appInfo;
	ImageButton dataTransfer;
	ImageButton password;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_menu, container, false);
		
		userInfo = (ImageButton) view.findViewById(R.id.btn_user_info);
		alarm = (ImageButton) view.findViewById(R.id.btn_alarm);
		appInfo = (ImageButton) view.findViewById(R.id.btn_app_info);
		dataTransfer = (ImageButton) view.findViewById(R.id.btn_data_transfer);
		password = (ImageButton) view.findViewById(R.id.btn_password);
		
		userInfo.setOnClickListener(onClickListener);
		alarm.setOnClickListener(onClickListener);
		appInfo.setOnClickListener(onClickListener);
		dataTransfer.setOnClickListener(onClickListener);
		password.setOnClickListener(onClickListener);
		
		return view;
	}
	
	View.OnClickListener onClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (v.getId())
			{
			case R.id.btn_user_info:
				((MainActivity)getActivity()).changeFragment(new UserInformationFragment());
				break;
				
			case R.id.btn_alarm:
				((MainActivity)getActivity()).changeFragment(new AlarmFragment());
				break;
				
			case R.id.btn_app_info:
				((MainActivity)getActivity()).changeFragment(new AppInformationFragment());
				break;
				
			case R.id.btn_data_transfer:
				((MainActivity)getActivity()).sendEmail();
				break;
				
			case R.id.btn_password:
				((MainActivity)getActivity()).changeFragment(new PasswordFragment());
				break;
			}
		}

		private void popupAlarmDialog() {
			FragmentActivity act = getActivity();
			//RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			// TODO Auto-generated method stub
			Builder alramDialog = new AlertDialog.Builder(act);
//			Button medNotiBtn =new Button(act);
//			Button clinicNotiBtn = new Button(act);
//			
//			medNotiBtn.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//			medNotiBtn.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//			medNotiBtn.setText("占쏙옙占쏙옙舡占�);
//			clinicNotiBtn.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//			clinicNotiBtn.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//			clinicNotiBtn.setText("占쏙옙占쏙옙疫�옙舡占�);
//			LinearLayout temp = new LinearLayout(act);
//			temp.addView(medNotiBtn);
//			temp.addView(clinicNotiBtn);
//			medNotiBtn.setOnClickListener(new OnClickListener());
//			clinicNotiBtn.setOnClickListener(bthClick);
			
			
//			alramDialog.setView(temp);
//			alramDialog.setView(medNotiBtn);
//			alramDialog.setView(clinicNotiBtn); 
			String[] temp = {"hello", "world"};
			
			alramDialog.setItems(temp, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "select item : "+which, 1000).show();
					switch(which){
					case 0:
					case 1:
						//Fragment f = new ClinicFragment();
						//changeFragment(f);
					break;
					}
					
				}
			});
			alramDialog.show();
//			
//			medNotiBtn.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//				}
//			});
//			
//			clinicNotiBtn.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Fragment f = new ClinicFragment();
//					changeFragment(f);
//					setDismiss(alramDialog);
//					
//				}
//
//				private void setDismiss(Dialog dialog) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
		}
	};
}
