package com.example.bpmanager;

import java.util.ArrayList;
import java.util.Calendar;

import com.example.bpmanager.DB.INFOMedication;
import com.example.bpmanager.MedicationScheduleData.MedicationSchedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MedicationMyDataFragment extends Fragment {
	
	private ListView mListView;
	MedicationBaseAdapter mListAdapter;
	ArrayList<MedicationScheduleData.MedicationSchedule> mMedicineList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_mine, container, false);
		
		mListView = (ListView) view.findViewById(R.id.list_medicine);
		mMedicineList = new ArrayList<MedicationScheduleData.MedicationSchedule>();
		ArrayList<MedicationScheduleData.MedicationSchedule> mData = MainActivity.mMedicationScheduleData.getDataList();
		for (int i = 0; i < mData.size(); i++)
		{
			MedicationScheduleData.MedicationSchedule ms = MainActivity.mMedicationScheduleData.getSchedule(mData.get(i).mMedicineId);
			if (ms != null)
				mMedicineList.add(ms);
		}

		setupList();		
		
		Button add = (Button) view.findViewById(R.id.med_btn_add_medicine);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).changeFragment(new MedicationAddFragment());
			}
		});
		
		return view;
	}
	
	public void setupList()
	{
		if (mListView == null)
			return;
		
		mListAdapter = new MedicationBaseAdapter(getActivity(), mMedicineList, R.layout.medicineitem_mine);
		
		mListView.setAdapter(mListAdapter);
		/*
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MedicationDetailFragment next = new MedicationDetailFragment();
				next.setMedicineId(mListAdapter.getItem(position).mId);
				Log.i("ID: ", Integer.toString(mListAdapter.getItem(position).mId));
				((MainActivity)getActivity()).changeFragment(next);
			}
		});
		*/
	}

}
