package com.example.bpmanager;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bpmanager.Data.*;

public class HabitFragment extends Fragment{
	Button slat;
	Button weight;
	Button waist;
	Button exam;
	Button alchole;
	Button smoking;
	Button stress;
	Button notification;
	Habit hpoint = new Habit();
	UserData us = new UserData();
	int count = 0;
	LayoutInflater inf;
	int q1v, q2v,q3v,q4v,q5v ;

	Survey saltSurvey = new SaltIntakeSurvey();
	Survey weightSurvey = new WeightSurvey();
	Survey waistSurvey = new WaistSurvey();
	Survey exerciseSurvey = new ExerciseSurvey();
	Survey drinkSurvey = new DrinkSurvey();
	Survey smokeSurvey = new SmokeSurvey();
	Survey stressSurvey = new StressSurvey();

	Map<Integer,Object> answer_salt;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_weight;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_waist;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_exer;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_drink;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_smoke;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_stress;// = new HashMap<Integer, Object>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		inf = inflater;
		View view = inflater.inflate(R.layout.fragment_habit, container, false);

		ScrollView scrollView;


		scrollView = (ScrollView) view.findViewById(R.id.scrollView1);
		scrollView.setVerticalScrollBarEnabled(true);


		//		Button button = (Button) view.findViewById(R.id.bt_ok);
		//		button.setOnClickListener(this);
		slat = (Button) view.findViewById(R.id.salt_btn);
		weight = (Button) view.findViewById(R.id.weight_bth);
		waist = (Button) view.findViewById(R.id.waist_bth);
		exam = (Button) view.findViewById(R.id.exam_bth);
		alchole = (Button) view.findViewById(R.id.alchole_bth);
		smoking = (Button) view.findViewById(R.id.smoking_bth);
		stress = (Button) view.findViewById(R.id.stress_btn);
		notification = (Button) view.findViewById(R.id.habit_noti_bth);

		slat.setOnClickListener(click);
		weight.setOnClickListener(click);
		waist.setOnClickListener(click);
		exam.setOnClickListener(click);
		alchole.setOnClickListener(click);
		smoking.setOnClickListener(click);
		stress.setOnClickListener(click);
		notification.setOnClickListener(click);

		return view;
	}

	View.OnClickListener click = new View.OnClickListener() {

		private Handler mHandler;

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int btnId = v.getId();
			if(btnId == R.id.salt_btn){
				saltDialig();
			}else if(btnId == R.id.weight_bth){
				weightDialog();
			}else if(btnId == R.id.waist_bth){
				waistDialog();
			}else if(btnId == R.id.exam_bth){
				examDialog();
			}else if(btnId == R.id.alchole_bth){
				alcholeDialog();
			}else if(btnId == R.id.smoking_bth){
				smokingDialog();
			}else if(btnId == R.id.stress_btn){
				stressDialog();
			}else if(btnId == R.id.habit_noti_bth){
				//adviceDialog();
				final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
						.beginTransaction();

				transaction.replace(R.id.frag_viewer, new HabitNotiFragment());
				transaction.addToBackStack(null);
				//				FragmentManager fm = getActivity().getSupportFragmentManager();
				//				for(int i = 0; i < fm.getBackStackEntryCount(); i++){
				//					fm.popBackStack();
				//				}
				//Commit the transaction
				transaction.commit();
			}
		}
		
		private void AdviceReportDialog(Survey sv , Map<Integer,Object> anwer, String strTitle ) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			builder.setTitle(strTitle);
			builder.setMessage( sv.getSurveyReport(anwer));

			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			builder.show();
		}
		
		private void saltDialig() {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			answer_salt = new HashMap<Integer, Object>();

			builder.setMultiChoiceItems(saltSurvey.getSurveyQuestion(), null, new DialogInterface.OnMultiChoiceClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					if(isChecked) answer_salt.put(which, 1);
					else answer_salt.put(which, 0);
				}
			});

			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					AdviceReportDialog(saltSurvey ,answer_salt, "소급 섭취량 평가");
				}
			});
			builder.show();
		}
		
		private void weightDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			answer_weight = new HashMap<Integer, Object>();

			builder.setMessage("현재 체중을 입력하세요.");
			final EditText wText = new EditText(getActivity());
			wText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			builder.setView(wText);
			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

					String inputText = wText.getText().toString();
					try{
						answer_weight.put(0,Float.parseFloat(inputText));
					}catch(NumberFormatException e){
					}
					dialog.dismiss();
					AdviceReportDialog(weightSurvey ,answer_weight, "체중 관리 평가");
				}
			}
					);
			builder.show();
		}

		private void waistDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			answer_waist= new HashMap<Integer, Object>();
			
			builder.setMessage("현재 허리둘레를 입력하세요.");
			final EditText wText = new EditText(getActivity());
			wText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			builder.setView(wText);
			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String inputText = wText.getText().toString();
					try{
						answer_waist.put(0,Float.parseFloat(inputText));
					}catch(NumberFormatException e){
						
					}
					dialog.dismiss();
					AdviceReportDialog(waistSurvey ,answer_waist, "복부 둘레 관리 평가");
				}
			});
			builder.show();
		}
		
		private void examDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			answer_exer= new HashMap<Integer, Object>();
			
			TextView dayText = new TextView(getActivity());
			dayText.setText("일 운동시간(분) : ");
			TextView weekText = new TextView(getActivity());
			weekText.setText("주간 운동 횟수 : ");
			TextView hardText = new TextView(getActivity());
			hardText.setText("운동강도(가벼운 노력:1, 보통 노력2, 극도 노력3) : ");

			LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

			final EditText day = new EditText(getActivity());
			final EditText week = new EditText(getActivity());
			final EditText hard = new EditText(getActivity());
			day.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			week.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			hard.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			
			LinearLayout dayItem = new LinearLayout(getActivity());
			dayItem.setOrientation(LinearLayout.HORIZONTAL);
			dayItem.setLayoutParams(lparam);
			dayItem.addView(dayText);
			dayItem.addView(day);
			LinearLayout weekItem = new LinearLayout(getActivity());
			weekItem.setOrientation(LinearLayout.HORIZONTAL);
			weekItem.setLayoutParams(lparam);
			weekItem.addView(weekText);
			weekItem.addView(week);

			LinearLayout hardItem = new LinearLayout(getActivity());
			hardItem.setOrientation(LinearLayout.HORIZONTAL);
			hardItem.setLayoutParams(lparam);
			hardItem.addView(hardText);
			hardItem.addView(hard);

			LinearLayout Items =  new LinearLayout(getActivity());
			Items.setOrientation(LinearLayout.VERTICAL);
			Items.setLayoutParams(lparam);
			Items.addView(dayItem);
			Items.addView(weekItem);
			Items.addView(hardItem);

			builder.setView(Items);
		
			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					String number = day.getText().toString();
					String time = week.getText().toString();
					String strength = hard.getText().toString();
					
					try{
						answer_exer.put(0,Integer.parseInt(number));
						answer_exer.put(1,Integer.parseInt(time));
						answer_exer.put(2,Integer.parseInt(strength));
						
					}catch(NumberFormatException e){
						
					}
					dialog.dismiss();
					AdviceReportDialog(exerciseSurvey ,answer_exer, "운동 관리 평가");
				}
			}
					);
			builder.show();

		}
		
		private void alcholeDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			answer_drink= new HashMap<Integer, Object>();
			
			TextView dayText = new TextView(getActivity());
			dayText.setText("일 음주량(잔) : ");
			TextView weekText = new TextView(getActivity());
			weekText.setText("주간 음주 횟수");

			LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

			final EditText day = new EditText(getActivity());
			final EditText week = new EditText(getActivity());
			day.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			week.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

			LinearLayout dayItem = new LinearLayout(getActivity());
			dayItem.setOrientation(LinearLayout.HORIZONTAL);
			dayItem.setLayoutParams(lparam);
			dayItem.addView(dayText);
			dayItem.addView(day);
			LinearLayout weekItem = new LinearLayout(getActivity());
			weekItem.setOrientation(LinearLayout.HORIZONTAL);
			weekItem.setLayoutParams(lparam);
			weekItem.addView(weekText);
			weekItem.addView(week);

			LinearLayout Items =  new LinearLayout(getActivity());
			Items.setOrientation(LinearLayout.VERTICAL);
			Items.setLayoutParams(lparam);
			Items.addView(dayItem);
			Items.addView(weekItem);

			builder.setView(Items);
			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					String number = day.getText().toString();
					String time = week.getText().toString();
					
					try{
						answer_drink.put(0,Integer.parseInt(number));
						answer_drink.put(1,Integer.parseInt(time));
					}catch(NumberFormatException e){
						
					}
					dialog.dismiss();
					AdviceReportDialog(drinkSurvey ,answer_drink, "음주 관리 평가");

				}
			}
					);
			builder.show();

		}
	
		private void smokingDialog() {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			answer_smoke= new HashMap<Integer, Object>();
			
			builder.setMessage("현재 담배를 피우십니까?");
			builder.setPositiveButton("예", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					answer_smoke.put(0,0);
					dialog.dismiss();
					AdviceReportDialog(smokeSurvey ,answer_smoke, "흡연 관리 평가");
				}
			}
					);
			builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					answer_smoke.put(0,1);
					dialog.dismiss();
					AdviceReportDialog(smokeSurvey ,answer_smoke, "흡연 관리 평가");
				}
			});
			builder.show();

		}
		
		private void stressDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			answer_stress= new HashMap<Integer, Object>();
			
			answer_stress.put(0,3);
			answer_stress.put(1,3);
			answer_stress.put(2,3);
			answer_stress.put(3,3);
			answer_stress.put(4,3);
			
			count = 0;
			builder.setTitle("5(항상 있었다) ~ 1(전혀 없었다)");
			ScrollView stress = (ScrollView) inf.inflate(R.layout.stress_content, null);
			RadioGroup q1Value = (RadioGroup) stress.findViewById(R.id.q1_value);
			RadioGroup q2Value = (RadioGroup) stress.findViewById(R.id.q2_value);
			RadioGroup q3Value = (RadioGroup) stress.findViewById(R.id.q3_value);
			RadioGroup q4Value = (RadioGroup) stress.findViewById(R.id.q4_value);
			RadioGroup q5Value = (RadioGroup) stress.findViewById(R.id.q5_value);

			q1Value.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					int id = group.getCheckedRadioButtonId();
					if(id == R.id.q1_1){
						q1v = 1;
					}else if(id == R.id.q1_2){
						q1v = 2;
					}else if(id == R.id.q1_3){
						q1v = 3;
					}else if(id == R.id.q1_4){
						q1v = 4;
					}else if(id == R.id.q1_5){
						q1v = 5;
					}else q1v =3;
					answer_stress.put(0,q1v);
					//Toast.makeText(getActivity(), "q1 : "+q1v, 300).show();
				}
			});

			q2Value.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					int id = group.getCheckedRadioButtonId();
					if(id == R.id.q2_1){
						q2v = 1;
					}else if(id == R.id.q2_2){
						q2v = 2;
					}else if(id == R.id.q2_3){
						q2v = 3;
					}else if(id == R.id.q2_4){
						q2v = 4;
					}else if(id == R.id.q2_5){
						q2v = 5;
					}else q2v =3;
					answer_stress.put(1,q2v);
					//Toast.makeText(getActivity(), "q2 : "+q2v, 300).show();
				}
			});

			q3Value.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					int id = group.getCheckedRadioButtonId();
					if(id == R.id.q3_1){
						q3v = 1;
					}else if(id == R.id.q3_2){
						q3v = 2;
					}else if(id == R.id.q3_3){
						q3v = 3;
					}else if(id == R.id.q3_4){
						q3v = 4;
					}else if(id == R.id.q3_5){
						q3v = 5;
					}else q3v =3;
					answer_stress.put(2,q3v);
					//Toast.makeText(getActivity(), "q3 : "+q3v, 300).show();
				}
			});

			q4Value.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					int id = group.getCheckedRadioButtonId();
					if(id == R.id.q4_1){
						q1v = 1;
					}else if(id == R.id.q4_2){
						q4v = 2;
					}else if(id == R.id.q4_3){
						q4v = 3;
					}else if(id == R.id.q4_4){
						q4v = 4;
					}else if(id == R.id.q4_5){
						q4v = 5;
					}else q4v =3;
					answer_stress.put(3,q4v);
					//Toast.makeText(getActivity(), "q4 : "+q4v, 300).show();
				}
			});

			q5Value.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					int id = group.getCheckedRadioButtonId();
					if(id == R.id.q5_1){
						q5v = 1;
					}else if(id == R.id.q5_2){
						q5v = 2;
					}else if(id == R.id.q5_3){
						q5v = 3;
					}else if(id == R.id.q5_4){
						q5v = 4;
					}else if(id == R.id.q5_5){
						q5v = 5;
					}else q5v =3;
					answer_stress.put(4,q5v);
					//Toast.makeText(getActivity(), "q5 : "+q2v, 300).show();
				}
			});

			builder.setView(stress);

			builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					AdviceReportDialog(stressSurvey ,answer_stress, "스트레스 관리 평가");
				}
			}
					);
			builder.show();

		}	
	};


//	public void onClick(View v) {
//
//		switch (v.getId()) {
//
//		//		case R.id.bt_ok:
//		//			Toast.makeText(getActivity(), "One Fragment", Toast.LENGTH_SHORT)
//		//					.show();
//		//			break;
//
//		}
//	}

}
