package com.example.bpmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MedicationTopFragment extends Fragment {
	
	FragmentTabHost mTabHost;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_top, container, false);

		mTabHost = (FragmentTabHost)view.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);

		mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("나의 약물관리"), MedicationMyDataFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("나의 복약기록"), MedicationHistoryFragment.class, null);
		
		return view;
	}

}
