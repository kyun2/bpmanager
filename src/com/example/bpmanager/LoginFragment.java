package com.example.bpmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment 
{
	EditText inputVal;
	Button loginBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_login, container, false);
		
		inputVal = (EditText) view.findViewById(R.id.edit_input_password);
		loginBtn = (Button) view.findViewById(R.id.btn_login);
		
		loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				if (isCorrectPassword())
				{
					((MainActivity)getActivity()).changeFragment(new HomeFragment());
					/*
					((MainActivity)getActivity()).getSupportFragmentManager()
						.beginTransaction()
						.add(R.id.frag_viewer, new HomeFragment())
						.commit();
					*/
					MainActivity.showFooter();
				}
				else
				{
					AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
					dialog.setTitle("오류").setMessage("비밀번호가 올바르지 않습니다.");
					dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
			
			private boolean isCorrectPassword()
			{
				return MainActivity.mUserData.getPassword().equals(inputVal.getText().toString());
			}
		});
		
		return view;
	}

}
