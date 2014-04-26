package com.example.bpmanager;

import java.util.Calendar;
import java.util.List;




import com.example.bpmanager.DB.DBhandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * 
 * @author Kyun
 *
 */
public class BPViewFragment extends Fragment{
	DBhandler handle;
//	RadioGroup last;
//	RadioGroup next;
//	Button inputclinic;
//	DatePicker lastDate;
//	DatePicker nextDate;
	SeekBar duration;
	ListView bplist;
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_bpview, container, false);

//		LinearLayout f = (LinearLayout)view.findViewById(R.id.bp_graph_view);
//		ImageView img = new ImageView(view.getContext());
//		BitmapDrawable dr = (BitmapDrawable) view.getContext().getResources().getDrawable(R.drawable.bp_graph);
//        img.setImageDrawable(dr);
//        f.addView(img);

		duration = (SeekBar) view.findViewById(R.id.bp_duration);
		duration.setOnSeekBarChangeListener(seekChange);
		
		bplist = (ListView) view.findViewById(R.id.bplist);
		listPrint(0);

		return view;
	}
	private void listPrint(int i) {
		
		List<BloodPressure> bps = BloodPressure.getLastBPsList(i);
		
		FragmentManager fm = getActivity().getSupportFragmentManager();
		bpAdapter medAdap = new bpAdapter(view.getContext(), R.layout.bpitem, bps, fm);
		bplist.setAdapter(medAdap);
		
		
		
		
		
		/////////////////////////////////////////////////////
		
		// TODO Auto-generated method stub
		int due = (i+1);
		handle = new DBhandler(getActivity());
		handle.open();
//		List<bp> retval = handle.getBPs();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+ 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		String now = String.valueOf(year)+String.valueOf(month)+String.valueOf(day)+String.valueOf(hour)+String.valueOf(minute);
		String befor = String.valueOf(year)+String.valueOf(month-due)+String.valueOf(day)+"0001";
		
		List<bp> retval = handle.getBPsByTime(befor, now);
		
		
		
		handle.close();
		
		if(retval.isEmpty()){
        	bp t = new bp();
        	t.setBpdatetime("dermy");
        	t.setSystolic(123);
        	t.setDiastolic(88);
        	retval.add(t);
        }
		
		FragmentManager fm = getActivity().getSupportFragmentManager();
		bpAdapter medAdap = new bpAdapter(view.getContext(), R.layout.bpitem, retval, fm);
		bplist.setAdapter(medAdap);
		
	}
	SeekBar.OnSeekBarChangeListener seekChange = new SeekBar.OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
			//Toast.makeText(getActivity(), String.valueOf(progress), 2000).show();
			
		}
	};
}
