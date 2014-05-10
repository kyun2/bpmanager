package com.example.bpmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PasswordFragment extends Fragment 
{
	EditText passwordText;
	RadioGroup password;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_password, container, false);
		
		// 패스워드
		final UserData uData = MainActivity.mUserData;
		passwordText = (EditText) view.findViewById(R.id.edit_password);
		password = (RadioGroup) view.findViewById(R.id.radiogrp_password);
		
		boolean isSet = false;
		if (uData.getPassword().length() > 0)
			isSet = true;
		
		if (isSet)
		{
			password.check(R.id.radio_pw_set);
		}
		else
		{
			password.check(R.id.radio_pw_unset);
		}		
		password.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if (checkedId == R.id.radio_pw_set)
				{
					if (checkValid())
					{
						uData.setPassword(passwordText.getText().toString());
						uData.submitData();
						
						passwordText.setEnabled(false);
					}
					else
					{
						password.check(R.id.radio_pw_unset);
						
						Toast.makeText(getActivity(), "암호를 입력해주세요.", Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					uData.setPassword("");
					uData.submitData();
					
					passwordText.setEnabled(true);
					passwordText.setText("");
					passwordText.clearFocus();
				}				
			}
			
			private boolean checkValid()
			{
				String pw = passwordText.getText().toString();
				return pw.length() > 0;
			}
		});
		
		if (isSet)
		{
			passwordText.setText(uData.getPassword());
			passwordText.setEnabled(false);
		}
		
		return view;
	}
}
