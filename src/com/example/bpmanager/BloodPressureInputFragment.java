package com.example.bpmanager;

import java.util.Calendar;
import java.util.InvalidPropertiesFormatException;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpmanager.Data.DrinkSurvey;
import com.example.bpmanager.Data.ExerciseSurvey;
import com.example.bpmanager.Data.SaltIntakeSurvey;
import com.example.bpmanager.Data.SmokeSurvey;
import com.example.bpmanager.Data.StressSurvey;
import com.example.bpmanager.Data.Survey;
import com.example.bpmanager.Data.WaistSurvey;
import com.example.bpmanager.Data.WeightSurvey;

/**
 * 
 * @author Kyun
 *
 *1. 목표 혈압 출력 //(완)
 *2. 최종 혈압 입력일이 한달 이상이면 알림  //(완)
 *3. 현재 혈압 저장(수축기,이완기, 날짜) //(완)
 * 3-1. 입력된 혈압을 목표 혈압과 비교하여 팝업 메시지 발생
 *4. 자료 화면(그래프, 표) 이동
 *5. 날짜 선택(측정일 눌렀을 때) //(완)
 */
public class BloodPressureInputFragment extends Fragment{

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
		TextView diaMax = (TextView) view.findViewById(R.id.diastolic_max);

		//목표 혈압 출력 -> 없으면 경고창후 정보 입력부분으로 이동
		BloodPressure recommendBloodPressure = BloodPressure.getRecommendBloodPressure();
		if(recommendBloodPressure != null)
		{
			sysMax.setText( String.valueOf(recommendBloodPressure.getSystolic())+ "/");
			diaMax.setText(String.valueOf(recommendBloodPressure.getDiastolic()));
		}else {
			//Toast.makeText(getActivity(), "목표혈압 계산을 위한 정보를 입력하세요", Toast.LENGTH_SHORT).show();
			new AlertDialog.Builder(getActivity())./*setTitle("Argh").*/setMessage("목표 혈압 계산을 위한 정보 입력 화면으로 이동합니다.").setNeutralButton("Close", null).show();
			Fragment next = new UserInformationFragment();
			replaceFragment(next);	
		}
		//		Button button = (Button) view.findViewById(R.id.bt_ok);
		//		button.setOnClickListener(this);

		//최종 혈압 입력일로 부터 30일이 지났으면 혈압 입력 메시지 발생
		if(BloodPressure.IsExpiredBPData())
			new AlertDialog.Builder(getActivity())./*setTitle("Argh").*/setMessage("마지막으로 혈압을 입력한지 한달이 지났습니다.").setNeutralButton("Close", null).show();
		
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
				Fragment next = new BloodPressureViewFragment();
				replaceFragment(next);
				break;
			}
		}

		private void insertBloodPressure() {
			try{
				int isystolic = Integer.valueOf(systolic.getText().toString());
				int idiastolic = Integer.valueOf(diastolic.getText().toString());
				String date = bptime.getText().toString();

				if(isystolic > 300 || isystolic < 30) throw new NumberFormatException(); 
				if(idiastolic > 300 || idiastolic < 30) throw new NumberFormatException();
				if(!date.matches("[0-9]{4}/[0-9]{2}/[0-9]{2}"))
				{
					throw new InvalidPropertiesFormatException("Invalid Format.");
				}
				
				if(BloodPressure.insertToDB(new BloodPressure(isystolic, idiastolic, bptime.getText().toString())) > 0)
				{
					Toast.makeText(getActivity(), "입력 완료", Toast.LENGTH_SHORT).show();
					
					systolic.setText("");
					diastolic.setText("");
					bptime.setText("");
					
					bloodPressureAdvice(isystolic);
				}
			}catch(NumberFormatException e){
				Toast.makeText(getActivity(), "입력 값이 허용 범위를 벗어났습니다.", Toast.LENGTH_SHORT).show();
			}catch(InvalidPropertiesFormatException e){
				Toast.makeText(getActivity(), "2001/01/01 형태로 입력하여 주세요.", Toast.LENGTH_LONG).show();
			}catch(Exception e){}
		}
	};
	
	private String getAdvice(boolean isGood){
		
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true; //처음 bad인지 확인
		boolean isNull = true; //모두 null이면 true
		
		Pair<Integer, Integer>  i = MainActivity.mMediHistData.setTookRatioDataList(30) ;
		
		if(i.first != i.second || i.second <= 0){
			if(isFirst) sb.append(" 복용");
			else sb.append("/복용");
			isFirst = false;
			if(i.second > 0) isNull = false;
		}
		
		Survey sv = new SaltIntakeSurvey();
		if(sv.getLastResult() != 1) {
			if(isFirst) sb.append(" 소금섭취");
			else sb.append("/소금섭취");
			isFirst = false;
			if(sv.getLastResult() != -1) isNull = false;
		}
		
		sv = new ExerciseSurvey();
		if(sv.getLastResult() != 1) {
			if(isFirst) sb.append(" 운동");
			else sb.append("/운동");
			isFirst = false;
			if(sv.getLastResult() != -1) isNull = false;
		}
		
		sv = new DrinkSurvey();
		if(sv.getLastResult() != 1) {
			if(isFirst) sb.append(" 음주");
			else sb.append("/음주");
			isFirst = false;
			if(sv.getLastResult() != -1) isNull = false;
		}
		
		sv = new SmokeSurvey();
		if(sv.getLastResult() != 1) {
			if(isFirst) sb.append(" 흡연");
			else sb.append("/흡연");
			isFirst = false;
			if(sv.getLastResult() != -1) isNull = false;
		}
		
		sv = new StressSurvey();
		if(sv.getLastResult() != 1) {
			if(isFirst) sb.append(" 스트레스");
			else sb.append("/스트레스");
			isFirst = false;
			if(sv.getLastResult() != -1) isNull = false;
		}
		
		sv = new WeightSurvey();
		if(sv.getLastResult() != 1) {
			if(isFirst) sb.append(" 체중");
			else sb.append("/체중");
			isFirst = false;
			if(sv.getLastResult() != -1) isNull = false;
		}
		
		sv = new WaistSurvey();
		if(sv.getLastResult() != 1) {
			if(isFirst) sb.append(" 복부둘레");
			else sb.append("/복부둘레");
			isFirst = false;
			if(sv.getLastResult() != -1) isNull = false;
		}
		
		if(isNull || isFirst) 
			return "복약관리와 생활습관 관리는 장기적인 혈압관리에 매우 중요한 요인입니다. 복약관리 및 생활습관 관리를 위한 메뉴가 있으니 꾸준히 이용해보세요.";
		else if(isGood) return "지금처럼 관리를 유지하되 " + sb.toString() + " 관리에 더 유의하세요. 해당 메뉴를 이용하면 상세 권고를 볼 수 있습니다.";
		else return sb.toString() + " 관리에 더 유의하세요. 해당 메뉴를 이용하면 상세 권고를 볼 수 있습니다.";
	}
	
	private void bloodPressureAdvice(int isystolic) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		int tSystolic = BloodPressure.getRecommendBloodPressure().getSystolic();
		StringBuilder str = new StringBuilder();
		
		boolean isGood = false;
		
		str.append("당신의 평균 수축기 혈압은 "+isystolic+"mmHg로, 목표 수축기 혈압 "+tSystolic+"mmHg");
		if( isystolic <= tSystolic){
			str.append("이 잘 유지되고 있습니다." + getAdvice(isGood));
			isGood = true;
		}else{ 
			str.append("보다 혈압이 높습니다." + getAdvice(isGood));	
			isGood = false;
		}
		
		builder.setMessage(str.toString());
		builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {				
				dialog.dismiss();
			}
		}
				);
//		builder.setNegativeButton("생활습관이동", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				answer.put(0,1);
//				if(sv.insertDatatoDB(answer) == -1) {
//					Toast.makeText(getActivity(), "저장 실패에 실패했습니다. 다시 시도하세요 .", 1000).show();;
//					return;
//				}
//				dialog.dismiss();
//				AdviceReportDialog(sv , "흡연 관리 평가");
//			}
//		});
		builder.show();

	}
	

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
			
			final EditText et = (EditText)v;
			
			if (hasFocus)
			{
				Calendar c = Calendar.getInstance();
				DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
					
					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						String time = String.format("%04d/%02d/%02d", year, monthOfYear+1, dayOfMonth);
						et.setText(time);
					}
				}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
				dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface di) {
						et.clearFocus();
					}
				});
				dialog.show();
			}

		}
	};

}