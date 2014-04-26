package com.example.bpmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bpmanager.DB.DBhandler;

/**
 * 
 * @author Kyun
 *
 *1. ��ǥ ���� ���
 *2. ���� ���� ����(�����,�̿ϱ�, ��¥)
 *3. �ڷ� ȭ��(�׷���, ǥ) �̵�
 *4. ��¥ ����( ������ ������ ��)
 */
public class InputBPFragment extends Fragment{
	
	DBhandler handle;
	Button inputbth; 
	Button bpviewbth;
	
	EditText systolic;
	EditText diastolic;
	EditText bptime;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_inputbp, container, false);
		inputbth = (Button) view.findViewById(R.id.inputbp_btn);
		bpviewbth = (Button) view.findViewById(R.id.bpview_bth);
		
		systolic = (EditText) view.findViewById(R.id.edit_sys);
		diastolic= (EditText) view.findViewById(R.id.edit_dia);
		bptime= (EditText) view.findViewById(R.id.edit_bpinput_time);
		
		inputbth.setOnClickListener(click);
		bpviewbth.setOnClickListener(click);
		
		bptime.setOnFocusChangeListener(focus);
		
		TextView sysMax = (TextView) view.findViewById(R.id.systolic_max);
		
		//��ǥ ���� ��� -> ������ ���â�� ���� �Էºκ����� �̵�
		BloodPressure recommendBloodPressure = BloodPressure.getRecommendBloodPressure();
		if(recommendBloodPressure != null)
		{
			sysMax.setText( String.valueOf(recommendBloodPressure.getSystolic())+ "/" +  String.valueOf(recommendBloodPressure.getDiastolic()));
		}else {
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setMessage("");
			alert.show();
			Fragment next = new BPViewFragment();
			replaceFragment(next);	
		}
//		Button button = (Button) view.findViewById(R.id.bt_ok);
//		button.setOnClickListener(this);

		return view;
	}
	
	View.OnClickListener click = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.inputbp_btn:
				insertBloodPressure();
				break;
			case R.id.bpview_bth:
				Fragment next = new BPViewFragment();
				replaceFragment(next);
				break;
			}
		}
		
		private void insertBloodPressure() {
			BloodPressure.insertToDB(new BloodPressure(
					Integer.valueOf(diastolic.getText().toString()), 
					Integer.valueOf(systolic.getText().toString()), 
					bptime.getText().toString()
					)
			);
		}
	};
	
	private void replaceFragment(Fragment next) {	
		// TODO Auto-generated method stub
		final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.frag_viewer, next);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();
	}

	View.OnFocusChangeListener focus = new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.edit_bpinput_time && hasFocus){
				AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
				alert.setTitle("���� �������� �����ϼ���.");
				final DateTimePicker dt = new DateTimePicker(getActivity());
				
				alert.setView(dt);
				alert.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						int year = dt.getYear();
						int month = dt.getMonth() + 1;
						int day = dt.getDayOfMonth();
						int hour = dt.getCurrentHour();
						int minute = dt.getCurrentMinute();
						String time =String.valueOf(year)+String.valueOf(month)+String.valueOf(day)+String.valueOf(hour)+String.valueOf(minute);
						bptime.setText(time);
						dialog.dismiss();
						// TODO Auto-generated method stub
					}
				});
				alert.show();
			}
			
		}
	};

}
