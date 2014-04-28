package com.example.bpmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.annotation.SuppressLint;
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
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;
import com.example.bpmanager.DB.DBhandler;
import com.example.bpmanager.Data.SaltIntakeSurvey;

public class HabitNotiFragment extends Fragment {

	private ArrayList<Map<String, String>> mGroupList = null;
	private ArrayList<ArrayList<Map<String, String>>> mChildList = null;
	private ArrayList<Map<String,String>> mChildListContent = null;
	private ExpandableListView mListView;
	DBhandler handle;

	public Map<Integer,Object> answer_salt;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_weight;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_waist;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_exer;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_drink;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_smoke;// = new HashMap<Integer, Object>();
	Map<Integer,Object> answer_stress;// = new HashMap<Integer, Object>();
	
	public HabitNotiFragment(){}
//	
//	public void setAnswer(Map<Integer,Object> answer_salt){
//		this.answer_salt = answer_salt;
//	}
//	
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
		//handle = new DBhandler(getActivity());
		//handle.open();
		// Toast.makeText(getActivity(), "check3", 300).show();
		//List<UserData> uslist = handle.getUsers();
		//List<Habit> hablist = handle.getHabits();
		UserData us = null;
		Habit hab = null;
		// Toast.makeText(getActivity(), "check4", 300).show();
		//handle.close();
		//if (!uslist.isEmpty()) {
			// Toast.makeText(getActivity(), "check1", 300).show();
			//us = uslist.get(uslist.size() - 1);
		//}
		//if (!hablist.isEmpty()) {
			// Toast.makeText(getActivity(), "check2", 300).show();
			//hab = hablist.get(hablist.size() - 1);
		//}
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
		saltType.put("Type", "소금섭취관리");
		mGroupList.add(saltType);
		Map<String, String> weightType = new HashMap<String, String>();
		weightType.put("Type", "체중관리");
		mGroupList.add(weightType);
		Map<String, String> waistType = new HashMap<String, String>();
		waistType.put("Type", "복부둘레관리");
		mGroupList.add(waistType);
		Map<String, String> examType = new HashMap<String, String>();
		examType.put("Type", "운동관리");
		mGroupList.add(examType);
		Map<String, String> alcholeType = new HashMap<String, String>();
		alcholeType.put("Type", "알콜섭취관리");
		mGroupList.add(alcholeType);
		Map<String, String> smokingType = new HashMap<String, String>();
		smokingType.put("Type", "금연관리");
		mGroupList.add(smokingType);
		Map<String, String> stressType = new HashMap<String, String>();
		stressType.put("Type", "스트레스관리");
		mGroupList.add(stressType);
		 
		// 여기 참고
		Map<String, String> val2 = new HashMap<String, String>();
		
		SaltIntakeSurvey sv = new SaltIntakeSurvey();
		String aw = sv.getSurveyReport();
		val2.put("T", aw); 
		val2.put("E", ""); 
		val2.put("R", "");
		
		saltNoti.add(val2);
		
		
		
		
		
		// saltNoti, weightNoti, waistNoti, examNoti, alcholeNoti, smokingNoti, stressNoti 에 값을 넣어주면 됨.
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
				this.mGroupList, //화면에 뿌려줄 데이터를 호출
				android.R.layout.simple_expandable_list_item_1, 				//사용할 리스트뷰를 호출
				new String[] { "Type" }, //뿌려줄 값의 Hash의 key를 적어준다.
				new int[] { android.R.id.text1 }, //뿌려줄 TextView를 불러온다.
				this.mChildList, 				//사용할 보조 데이터를 호출한다.
				android.R.layout.simple_expandable_list_item_2,	//배열을 사용하여 호출 할 수 있다. 이 경우 View의 수와 꼭 맞게 적용해야 한다.
				new String[] { "T", "E", "R" },	//String의 수와 View의 수가 1:1이어야 한다.
				new int[] { android.R.id.text1, android.R.id.text2, android.R.id.text2 }
			);
		this.mListView.setAdapter(adapter);
		// 차일드 클릭 했을 경우 이벤트
		this.mListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Toast.makeText(getActivity(), "c click = " + childPosition,
						Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// 그룹 닫기
		this.mListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getActivity(),
						"g Collapse = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
			}
		});

		// 그룹 펼치기
		this.mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getActivity(),
						"g Expand = " + groupPosition, Toast.LENGTH_SHORT)
						.show();
			}
		});

		return view;
	}

	private void errDialog(String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(string);
		// final EditText wText = new EditText(getActivity());
		// wText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
		// builder.setView(wText);
		builder.setPositiveButton("확 인", new DialogInterface.OnClickListener() {

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
