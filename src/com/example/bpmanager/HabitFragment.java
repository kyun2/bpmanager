package com.example.bpmanager;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

public class HabitFragment extends Fragment{
	Button slat;
	Button weight;
	Button waist;
	Button exam;
	Button alchole;
	Button smoking;
	Button stress;
	Button notification;
	habit hpoint = new habit();
	UserData us = new UserData();
	int count = 0;
	LayoutInflater inf;
	int q1v, q2v,q3v,q4v,q5v;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		inf = inflater;
		View view = inflater.inflate(R.layout.fragment_habit, container, false);

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
				slatDialig();
			}else if(btnId == R.id.weight_bth){
				//Toast.makeText(getActivity(), "weight btn click", 500).show();
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
		private void stressDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			count = 0;
			builder.setTitle("5(�׻� �־��) ~ 1(���� ����)");
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
					}
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
					}
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
					}
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
					}
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
					}
					//Toast.makeText(getActivity(), "q5 : "+q2v, 300).show();
				}
			});
			
			
			
			builder.setView(stress);
			builder.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					hpoint.setStress(getStressPoint());
						dialog.dismiss();
						Toast.makeText(getActivity(), "stress is : "+ hpoint.getStress(), 500).show();
					
				}

				private double getStressPoint() {
					// TODO Auto-generated method stub
					int sum = q1v + q2v + q3v + q4v + q5v; 
					double retval = (double)sum/5L;
					return retval;
				}
			}
			);
			builder.show();
			
		}
		private void smokingDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("���� ��踦 �ǿ�ʴϱ�?");
//			final EditText wText = new EditText(getActivity());
//			wText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
//			builder.setView(wText);
			builder.setPositiveButton("��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
						dialog.dismiss();
						Toast.makeText(getActivity(), "weight is : ", 500).show();
					
				}
			}
			);
			builder.setNegativeButton("�ƴϿ�", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.show();
			
		}
		private void alcholeDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			TextView dayText = new TextView(getActivity());
			dayText.setText("�� ���ַ�(��) : ");
			TextView weekText = new TextView(getActivity());
			weekText.setText("�ְ� ���� Ƚ��");
			
			 LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			
			EditText day = new EditText(getActivity());
			EditText week = new EditText(getActivity());
			day.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			day.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			
			
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
			builder.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
						dialog.dismiss();
						Toast.makeText(getActivity(), "weight is : ".toString(), 500).show();
					
				}
			}
			);
			builder.show();
			
		}
		private void examDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//			builder.setMessage("���� �㸮�ѷ��� �Է��ϼ���.");
			TextView dayText = new TextView(getActivity());
			dayText.setText("�� ��ð�(��) : ");
			TextView weekText = new TextView(getActivity());
			weekText.setText("�ְ� � Ƚ�� : ");
			TextView hardText = new TextView(getActivity());
			hardText.setText("������� ���ٰ� ���Ͻʴϱ�?");
			
			 LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
			
			EditText day = new EditText(getActivity());
			EditText week = new EditText(getActivity());
			day.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			day.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			
			CheckBox hard = new CheckBox(getActivity());
			hard.setText("");
			
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
			
//			final EditText wText = new EditText(getActivity());
//			wText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
//			builder.setView(wText);
			builder.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					//us.setWaist(wText.getText().toString());
						dialog.dismiss();
						Toast.makeText(getActivity(), "waist is : ".toString(), 500).show();
					
				}
			}
			);
			builder.show();
			
		}
		private void waistDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("���� �㸮�ѷ��� �Է��ϼ���.");
			final EditText wText = new EditText(getActivity());
			wText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			builder.setView(wText);
			builder.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					us.setWaist(Float.parseFloat(wText.getText().toString()));
						dialog.dismiss();
						Toast.makeText(getActivity(), "waist is : "+wText.getText().toString(), 500).show();
					
				}
			}
			);
			builder.show();
			
		}

		private void weightDialog() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setMessage("���� ü���� �Է��ϼ���.");
			final EditText wText = new EditText(getActivity());
			wText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
			builder.setView(wText);
			builder.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					us.setWeight(Float.parseFloat(wText.getText().toString()));
						dialog.dismiss();
						Toast.makeText(getActivity(), "weight is : "+wText.getText().toString(), 500).show();
					
				}
			}
			);
			builder.show();
			
		}

		private void slatDialig() {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			String[] question = {"��ü�Һ��ٴ� ��ġ�� �� �����Ѵ�.",
					"���̹��̳� ���� ���� �� �����Ѵ�.",
					"����� �丮���� �߱���, �Ϻ��� �丮�� �� �����Ѵ�.",
					"���� ���̳� ���� �ڹ� ���� ���� �����Ѵ�.",
					"����� ���� ������ ��Ź�� ������ �����ϴ�.",
					"����(���� �Ǵ� �� ����)�� �̰ſ�� �ұ��̳� ������ �� �ִ´�.",
					"���̳� �, ���� ������ ������ ������� �Դ´�.",
					"Ƣ���̳� ��, ��ȸ � ������ ���(������ ��⵵��) ��� �Դ´�.",
					"�ܽ��� �ϰų� ����� ���� ���� �Դ´�.","�丮�� ������ �巹���� ���� ����Ѵ�.",
					"��鱹���� �����.",
					"���� ���� �� ���� �ʴ´�."};
			
				//showDialog(question[i]);
				//builder.setMessage(question[cset]);
				builder.setMultiChoiceItems(question, null, new DialogInterface.OnMultiChoiceClickListener() {
					
					int temp = 0;

					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						// TODO Auto-generated method stub
						if(which < 9 && isChecked){
							temp++; 
						}else if(which < 9 && !isChecked){
							temp--;
						}else if(which >= 9 && isChecked){
							temp--;
						}else if(which >= 9 && !isChecked){
							temp++;
						}
						//Toast.makeText(getActivity(), "count is : "+temp, 500).show();
						setTotal(temp);
					}

					
				});
				builder.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
							dialog.dismiss();
							hpoint.setSalt(count);
							count = 0;
							Toast.makeText(getActivity(), "Total count is : "+count, 500).show();
						
					}
				}
				);
				
				builder.show();
				
			
		}
		private void setTotal(int temp) {
			// TODO Auto-generated method stub
			count = temp;
			
		}
	};

	public void onClick(View v) {

		switch (v.getId()) {

//		case R.id.bt_ok:
//			Toast.makeText(getActivity(), "One Fragment", Toast.LENGTH_SHORT)
//					.show();
//			break;

		}
	}

}
