package com.example.bpmanager;

import java.util.ArrayList;

import com.example.bpmanager.MedicationBaseAdapter.MedicineInfoHolder;
import com.example.bpmanager.MedicationScheduleData.MedicationSchedule;
import com.example.bpmanager.DB.INFOMedication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MedicationAddFragment extends Fragment {
	
	private ListView mListView;
	MedicationBaseAdapter mListAdapter;
	ArrayList<MedicationScheduleData.MedicationSchedule> mMedicineList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_add, container, false);
		
		mListView = (ListView) view.findViewById(R.id.list_medicine);
		mMedicineList = new ArrayList<MedicationScheduleData.MedicationSchedule>();
		
		getData();
		setupList();
		
		return view;
	}
	
	public void setupList()
	{
		if (mListView == null)
			return;
		
		// 어댑터
		mListAdapter = new MedicationBaseAdapter(getActivity(), mMedicineList, R.layout.medicineitem_add);
		
		mListView.setAdapter(mListAdapter);
		//mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				MedicationScheduleFragment next = new MedicationScheduleFragment();
				next.setMedicineId(mListAdapter.getItem(position).mId);
				Log.i("ID: ", Integer.toString(next.getMedicineId()));
				((MainActivity)getActivity()).changeFragment(next);
			}
		});
	}
	
	public void getData()
	{
		if (mMedicineList == null)
			return;
		
		for (int i = 0; i < INFOMedication.InfoData.size(); i++)
		{
			INFOMedication info = INFOMedication.getInfoMedicine(i);
			MedicationScheduleData.MedicationSchedule data = MainActivity.mMedicationScheduleData.getSchedule(info.mId);
			if (data != null)
				continue;
			
			data = MainActivity.mMedicationScheduleData.new MedicationSchedule();
			data.mMedicineId = info.mId;
			
			mMedicineList.add(data);
		}
	}
}
