package com.example.bpmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bpmanager.DB.DBhandler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

public class HabitNotiFragment extends Fragment {

	private ArrayList<Map<String, String>> mGroupList = null;
	private ArrayList<ArrayList<Map<String, String>>> mChildList = null;
	private ArrayList<Map<String,String>> mChildListContent = null;
	private ExpandableListView mListView;
	DBhandler handle;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.habit_noti, container, false);

		// LinearLayout f = (LinearLayout)view.findViewById(R.id.bp_graph_view);
		// ImageView img = new ImageView(view.getContext());
		// BitmapDrawable dr = (BitmapDrawable)
		// view.getContext().getResources().getDrawable(R.drawable.bp_graph);
		// img.setImageDrawable(dr);
		// f.addView(img);

		mListView = (ExpandableListView) view
				.findViewById(R.id.habit_noti_view);
		;
		handle = new DBhandler(getActivity());
		handle.open();
		// Toast.makeText(getActivity(), "check3", 300).show();
		List<user> uslist = handle.getUsers();
		List<habit> hablist = handle.getHabits();
		user us = null;
		habit hab = null;
		// Toast.makeText(getActivity(), "check4", 300).show();
		handle.close();
		if (!uslist.isEmpty()) {
			// Toast.makeText(getActivity(), "check1", 300).show();
			us = uslist.get(uslist.size() - 1);
		}
		if (!hablist.isEmpty()) {
			// Toast.makeText(getActivity(), "check2", 300).show();
			hab = hablist.get(hablist.size() - 1);
		}
		mGroupList = new ArrayList<Map<String,String>>();
		mChildList = new ArrayList<ArrayList<Map<String,String>>>();

		ArrayList<Map<String,String>> saltNoti = new ArrayList<Map<String,String>>();
		ArrayList<Map<String,String>> weightNoti = new ArrayList<Map<String,String>>();
		ArrayList<Map<String,String>> waistNoti = new ArrayList<Map<String,String>>();
		ArrayList<Map<String,String>> examNoti = new ArrayList<Map<String,String>>();
		ArrayList<Map<String,String>> alcholeNoti = new ArrayList<Map<String,String>>();
		ArrayList<Map<String,String>> smokingNoti = new ArrayList<Map<String,String>>();
		ArrayList<Map<String,String>> stressNoti = new ArrayList<Map<String,String>>();

		Map<String, String> saltType = new HashMap<String, String>();
		saltType.put("Type", "�ұݼ����");
		mGroupList.add(saltType);
		Map<String, String> weightType = new HashMap<String, String>();
		weightType.put("Type", "ü�߰�");
		mGroupList.add(weightType);
		Map<String, String> waistType = new HashMap<String, String>();
		waistType.put("Type", "���εѷ���");
		mGroupList.add(waistType);
		Map<String, String> examType = new HashMap<String, String>();
		examType.put("Type", "���");
		mGroupList.add(examType);
		Map<String, String> alcholeType = new HashMap<String, String>();
		alcholeType.put("Type", "���ݼ����");
		mGroupList.add(alcholeType);
		Map<String, String> smokingType = new HashMap<String, String>();
		smokingType.put("Type", "�ݿ���");
		mGroupList.add(smokingType);
		Map<String, String> stressType = new HashMap<String, String>();
		stressType.put("Type", "��Ʈ������");
		mGroupList.add(stressType);
		

		if (hab == null) {
			Toast.makeText(getActivity(), "check1", 300).show();
			// errDialog("��Ȱ���� ������ ����ϴ�.");
		} else if (us == null) {
			// errDialog("����� ������ ����ϴ�.");
		} else {
			int salt = hab.getSalt();

			String eval;
			String recm;
			if (salt < 5) {
				 eval = EvaText.E6;
				 recm = RecomendText.R6;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", "");
				value.put("E",eval);
				value.put("R", recm);
				saltNoti.add(value);
			} else {
				 eval = EvaText.E7;
				 recm = RecomendText.R7;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", "");
				value.put("E",eval);
				value.put("R", recm);
				saltNoti.add(value);
			}

			double tweight = ((Double.valueOf(us.getHeight()) - 100) / 0.9);
			double gap = tweight - Double.valueOf(us.getHeight());
			String t;
			t = TargetText.T6;
			t = t.replace("TARGET", String.valueOf(tweight));
			if (gap > 0) {
				eval = EvaText.E18;
				eval = eval.replace("INPUT", us.getWeight())
						.replace("TARGET", String.valueOf(tweight))
						.replace("GAP", String.valueOf(gap));
				recm = RecomendText.R18;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", t);
				value.put("E",eval);
				value.put("R", recm);
				weightNoti.add(value);
			} else {
				eval = EvaText.E17;
				eval = eval.replace("INPUT", us.getWeight()).replace("TARGET",
						String.valueOf(tweight));
				recm = RecomendText.R17;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", t);
				value.put("E",eval);
				value.put("R", recm);
				weightNoti.add(value);
			}

			int twaist;
			if ((us.getGender() == 1)) {
				t = TargetText.T7;
				twaist = 102;
				if (Integer.valueOf(us.getWaist()) > twaist) {

					eval = EvaText.E19;
					eval = eval
							.replace("INPUT", us.getWaist())
							.replace("TARGET", "102")
							.replace(
									"GAP",
									String.valueOf(Integer.valueOf(us
											.getWaist()) - 102));
					recm = RecomendText.R19;
					Map<String, String> value = new HashMap<String,String>();
					value.put("T", t);
					value.put("E",eval);
					value.put("R", recm);
					waistNoti.add(value);
					
				} else {
					eval = EvaText.E20;

					eval = eval.replace("INPUT", us.getWaist()).replace(
							"TARGET", "102");
					recm = RecomendText.R20;
					Map<String, String> value = new HashMap<String,String>();
					value.put("T", t);
					value.put("E",eval);
					value.put("R", recm);
					waistNoti.add(value);
				}
			} else {
				t = TargetText.T7;
				twaist = 88;
				if (Integer.valueOf(us.getWaist()) > twaist) {

					eval = EvaText.E19;
					eval = eval
							.replace("INPUT", us.getWaist())
							.replace("TARGET", "102")
							.replace(
									"GAP",
									String.valueOf(Integer.valueOf(us
											.getWaist()) - 102));
					recm = RecomendText.R19;
					Map<String, String> value = new HashMap<String,String>();
					value.put("T", t);
					value.put("E",eval);
					value.put("R", recm);
					waistNoti.add(value);
				} else {
					eval = EvaText.E20;

					eval = eval.replace("INPUT", us.getWaist()).replace(
							"TARGET", "102");
					recm = RecomendText.R20;
					Map<String, String> value = new HashMap<String,String>();
					value.put("T", t);
					value.put("E",eval);
					value.put("R", recm);
					waistNoti.add(value);
				}
			}

			if ((hab.getExamNum() > 4) && (hab.getExamTime() > 60)
					&& (hab.getExamHard() == 1)) {
				t = TargetText.T3;

				eval = EvaText.E8;
				eval = eval.replace("COUNT", String.valueOf(hab.getExamNum()))
						.replace("MINITE", String.valueOf(hab.getExamTime()))
						.replace("HARDNESS", "��");
				recm = RecomendText.R8;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", t);
				value.put("E",eval);
				value.put("R", recm);
				examNoti.add(value);
				

			} else {
				t = TargetText.T3;
				String hard;
				if (hab.getExamHard() == 1) {
					hard = "��";
				} else {
					hard = "����";
				}
				eval = EvaText.E8;
				eval = eval.replace("COUNT", String.valueOf(hab.getExamNum()))
						.replace("MINITE", String.valueOf(hab.getExamTime()))
						.replace("HARDNESS", hard);
				recm = RecomendText.R8;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", t);
				value.put("E",eval);
				value.put("R", recm);
				examNoti.add(value);
			}

			if (us.getGender() == 1 && (Double.valueOf(us.getWeight()) > 60)) {
				t = TargetText.T4;
				int alDay = hab.getAlcholeDay();
				int alWeek = hab.getAlcholeWeek();
				if (alDay > 2 && (alDay * alWeek) > 14) {
					eval = EvaText.E11;
					eval = eval
							.replace("INPUT", String.valueOf(alDay * alWeek));
					recm = RecomendText.R11;
					Map<String, String> value = new HashMap<String,String>();
					value.put("T", t);
					value.put("E",eval);
					value.put("R", recm);
					alcholeNoti.add(value);
				} else {
					eval = EvaText.E10;
					eval = eval
							.replace("INPUT", String.valueOf(alDay * alWeek));
					recm = RecomendText.R10;
					Map<String, String> value = new HashMap<String,String>();
					value.put("T", t);
					value.put("E",eval);
					value.put("R", recm);
					alcholeNoti.add(value);
				}
			} else {
				t = TargetText.T5;
				int alDay = hab.getAlcholeDay();
				int alWeek = hab.getAlcholeWeek();
				if (alDay > 1 && (alDay * alWeek) > 9) {
					eval = EvaText.E11;
					eval = eval
							.replace("INPUT", String.valueOf(alDay * alWeek));
					recm = RecomendText.R11;
					Map<String, String> value = new HashMap<String,String>();
					value.put("T", t);
					value.put("E",eval);
					value.put("R", recm);
					alcholeNoti.add(value);
				} else {
					eval = EvaText.E10;
					eval = eval
							.replace("INPUT", String.valueOf(alDay * alWeek));
					recm = RecomendText.R10;
					Map<String, String> value = new HashMap<String,String>();
					value.put("T", t);
					value.put("E",eval);
					value.put("R", recm);
					alcholeNoti.add(value);
				}
			}

			if (hab.getSmoking() == 1) {
				eval = EvaText.E13;
				recm = RecomendText.R13;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", "");
				value.put("E",eval);
				value.put("R", recm);
				smokingNoti.add(value);
			} else {
				eval = EvaText.E12;
				recm = RecomendText.R12;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", "");
				value.put("E",eval);
				value.put("R", recm);
				smokingNoti.add(value);
			}

			if (hab.getStress() >= 2.8) {
				eval = EvaText.E14;
				eval = eval.replace("INPUT", String.valueOf(hab.getStress()));
				recm = RecomendText.R14;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", "");
				value.put("E",eval);
				value.put("R", recm);
				stressNoti.add(value);
			} else if (hab.getStress() < 1.7) {
				eval = EvaText.E16;
				eval = eval.replace("INPUT", String.valueOf(hab.getStress()));
				recm = RecomendText.R16;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", "");
				value.put("E",eval);
				value.put("R", recm);
				stressNoti.add(value);
			} else {
				eval = EvaText.E15;
				eval = eval.replace("INPUT", String.valueOf(hab.getStress()));
				recm = RecomendText.R15;
				Map<String, String> value = new HashMap<String,String>();
				value.put("T", "");
				value.put("E",eval);
				value.put("R", recm);
				stressNoti.add(value);
			}

			

		}
		
		mChildList.add(saltNoti);
		mChildList.add(weightNoti);
		mChildList.add(waistNoti);
		mChildList.add(examNoti);
		mChildList.add(alcholeNoti);
		mChildList.add(smokingNoti);
		mChildList.add(stressNoti);

		// mListView.setAdapter(new BaseExpandableAdapter(this, mGroupList,
		// mChildList));
		
//		mListView.setOnGroupClickListener(new OnGroupClickListener() {
//			@Override
//			public boolean onGroupClick(ExpandableListView parent, View v,
//					int groupPosition, long id) {
//				Toast.makeText(getActivity(), "g click = " + groupPosition,
//						Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
		
		ExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				getActivity(),
				this.mGroupList, //ȭ�鿡 �ѷ��� �����͸� ȣ��
				//����� ����Ʈ�並 ȣ��
				android.R.layout.simple_expandable_list_item_1,
				new String[] { "Type" }, //�ѷ��� ���� Hash�� key�� ����ش�.
				new int[] { android.R.id.text1 }, //�ѷ��� TextView�� �ҷ��´�.
				//����� ���� �����͸� ȣ���Ѵ�.
				this.mChildList,
				android.R.layout.simple_expandable_list_item_2,
				//�迭�� ����Ͽ� ȣ�� �� �� �ִ�. �� ��� View�� ���� �� �°� ����ؾ� �Ѵ�.
				new String[] { "T", "E", "R" },
				//String�� ���� View�� ���� 1:1�̾�� �Ѵ�.
				new int[] { android.R.id.text1, android.R.id.text2, android.R.id.text2 }
			);
		this.mListView.setAdapter(adapter);
//		// ���ϵ� Ŭ�� ���� ��� �̺�Ʈ
//		mListView.setOnChildClickListener(new OnChildClickListener() {
//			@Override
//			public boolean onChildClick(ExpandableListView parent, View v,
//					int groupPosition, int childPosition, long id) {
//				Toast.makeText(getActivity(), "c click = " + childPosition,
//						Toast.LENGTH_SHORT).show();
//				return false;
//			}
//		});
//
//		// �׷��� ���� ��� �̺�Ʈ
//		mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
//			@Override
//			public void onGroupCollapse(int groupPosition) {
//				Toast.makeText(getActivity(),
//						"g Collapse = " + groupPosition, Toast.LENGTH_SHORT)
//						.show();
//			}
//		});
//
//		// �׷��� ���� ��� �̺�Ʈ
//		mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
//			@Override
//			public void onGroupExpand(int groupPosition) {
//				Toast.makeText(getActivity(),
//						"g Expand = " + groupPosition, Toast.LENGTH_SHORT)
//						.show();
//			}
//		});

		return view;
	}

	private void errDialog(String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(string);
		// final EditText wText = new EditText(getActivity());
		// wText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// builder.setView(wText);
		builder.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Toast.makeText(getActivity(), "weight is : ", 500).show();

			}
		});
		builder.show();
	}
}
