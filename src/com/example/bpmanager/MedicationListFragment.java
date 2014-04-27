package com.example.bpmanager;

import java.util.ArrayList;

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

public class MedicationListFragment extends Fragment {
	
	private ListView mListView;
	BaseAdapter mListAdapter;
	//ArrayAdapter<String> mListAdapter;
	ArrayList<LinearLayout> mMedicineList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_medlist, container, false);
		
		mListView = (ListView) view.findViewById(R.id.list_medicine);
		mMedicineList = new ArrayList<LinearLayout>();
		
		getData(inflater, container);
		setupList();
		
		return view;
	}
	
	public void setupList()
	{
		if (mListView == null)
			return;
		
		// พ๎ด๐ลอ
		//mListAdapter = new ArrayAdapter<String>(getActivity(), R.layout.medicineitem_add, mMedicineList);
		mListAdapter = new MedicationBaseAdapter(getActivity(), mMedicineList);
		
		mListView.setAdapter(mListAdapter);
		//mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		/*
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//Toast.makeText(getActivity(), "POS: " + position + "", Toast.LENGTH_SHORT).show();
				Log.i("POS", "" + position + ":" + id);
			}
		});
		*/
	}
	
	public void getData(LayoutInflater inflater, ViewGroup container)
	{
		if (mMedicineList == null)
			return;
		
		for (int i = 0; i < 20; i++)
		{
			LinearLayout llayout = (LinearLayout)inflater.inflate(R.layout.medicineitem_add, container, false);
			TextView tv = (TextView)llayout.findViewById(R.id.medicine_name);
			tv.setText(Integer.toString(i));
			mMedicineList.add(llayout);
		}
	}
}
