package com.example.bpmanager;

import java.util.Calendar;
import java.util.List;

import com.example.bpmanager.DB.DBhandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

public class BloodPressureViewFragment extends Fragment{

	private SeekBar mTerm;
	private ListView mList;
	private View mView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_bpview, container, false);
		
		mTerm = (SeekBar) view.findViewById(R.id.bp_term);
		mTerm.setOnSeekBarChangeListener(onChangeSeekBar);
		
		mList = (ListView) view.findViewById(R.id.bplist);
		
		// 처음엔 1주일치
		setList(7);

		return view;
	}
	
	private void setList(int i)
	{		
		List<BloodPressure> bps = BloodPressure.getLastBPsList(i);
		Log.i("count: ", ""+bps.size());
		BloodPressureAdapter adapter = new BloodPressureAdapter(getActivity(), R.layout.bpitem, bps, getActivity().getSupportFragmentManager());
		
		mList.setAdapter(adapter);
	}
	
	SeekBar.OnSeekBarChangeListener onChangeSeekBar = new SeekBar.OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (progress == 0)
			{
				setList(7);
			}
			else if (progress == 1)
			{
				setList(30);
			}
			else
			{
				setList(60);
			}
		}
	};
}
