package com.example.bpmanager;

import com.example.bpmanager.DB.DBhandler;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class UserMenuFragment extends Fragment {
	DBhandler handle;
	
	Button basic;
	Button bp;
	Button medication;
	Button habit;
	Button alramMenu;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_user_menu, container, false);
		
		basic = (Button) view.findViewById(R.id.user_modi_bth);
		bp = (Button) view.findViewById(R.id.bp_view_bth);
		habit = (Button) view.findViewById(R.id.habit_menu_btn);
		medication = (Button) view.findViewById(R.id.medical_bth);
		alramMenu = (Button) view.findViewById(R.id.alram_bth);
		
		basic.setOnClickListener(clickListen);
		bp.setOnClickListener(clickListen);
		medication.setOnClickListener(clickListen);
		habit.setOnClickListener(clickListen);
		alramMenu.setOnClickListener(clickListen);
		
		return view;
	}
	
	View.OnClickListener clickListen = new View.OnClickListener() {
		
		private OnItemSelectedListener selecListen;

		@Override
		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.user_modi_bth) {
				changeFragment(new UserModifyFragment());
			} else if (id == R.id.bp_view_bth) {
				changeFragment(new InputBPFragment());
			} else if (id == R.id.medical_bth) {
				changeFragment(new UserModifyFragment());
			} else if (id == R.id.habit_menu_btn) {
				changeFragment(new HabitFragment());
			} else if (id == R.id.alram_bth) {
				popupAlarmDialog();
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
//			medNotiBtn.setText("����˸�");
//			clinicNotiBtn.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//			clinicNotiBtn.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//			clinicNotiBtn.setText("����湮�˸�");
//			LinearLayout temp = new LinearLayout(act);
//			temp.addView(medNotiBtn);
//			temp.addView(clinicNotiBtn);
//			medNotiBtn.setOnClickListener(new OnClickListener());
//			clinicNotiBtn.setOnClickListener(bthClick);
			
			
//			alramDialog.setView(temp);
//			alramDialog.setView(medNotiBtn);
//			alramDialog.setView(clinicNotiBtn);
			String[] temp ={"����˸�","����湮�˸�"}; 
			
			alramDialog.setItems(temp, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "select item : "+which, 1000).show();
					switch(which){
					case 0:
					case 1:
						Fragment f = new ClinicFragment();
						changeFragment(f);
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
		
		

		private void changeFragment(Fragment next) {
			// TODO Auto-generated method stub
			final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
					.beginTransaction();

			transaction.replace(R.id.frag_viewer, next);
			transaction.addToBackStack(null);
//			FragmentManager fm = getActivity().getSupportFragmentManager();
//			for(int i = 0; i < fm.getBackStackEntryCount(); i++){
//				fm.popBackStack();
//			}
			 //Commit the transaction
			transaction.commit();
		}
	};
}
