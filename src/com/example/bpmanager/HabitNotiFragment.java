package com.example.bpmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import com.example.bpmanager.Data.DrinkSurvey;
import com.example.bpmanager.Data.ExerciseSurvey;
import com.example.bpmanager.Data.SaltIntakeSurvey;
import com.example.bpmanager.Data.SmokeSurvey;
import com.example.bpmanager.Data.StressSurvey;
import com.example.bpmanager.Data.Survey;
import com.example.bpmanager.Data.WaistSurvey;
import com.example.bpmanager.Data.WeightSurvey;

public class HabitNotiFragment extends Fragment {

	private ArrayList<Map<String, String>> mGroupList = null;
	private ArrayList<ArrayList<Map<String, String>>> mChildList = null;
	private ArrayList<Map<String,String>> mChildListContent = null;
	private ExpandableListView mListView;
	DBhandler handle;
	
	//설문조사 결과 
	public String getEval(Survey sv){
		if(sv.getLastResult() == 1)
			return "<Good>";
		else if(sv.getLastResult() == 0) return "<Bad>";
		else return "";
	}
	public void putDataToChile(Survey sv, ArrayList<Map<String,String>> noti){
		
		Map<String, String> m = new HashMap<String, String>();
		String text= sv.getSurveyReport();
		if(text == null) text = "저장된 값이 없습니다. 먼저 설문조사를 진행해 주세요";
		m.put("T", " " +text); 
		//m.put("E", ""); 
		noti.add(m);
		
		mChildList.add(noti);
		
	}

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
		saltType.put("Type", "소금섭취관리   \t" + getEval(new SaltIntakeSurvey()));
		mGroupList.add(saltType);
		Map<String, String> weightType = new HashMap<String, String>();
		weightType.put("Type", "체중관리  \t" + getEval(new WeightSurvey()));
		mGroupList.add(weightType);
		Map<String, String> waistType = new HashMap<String, String>();
		waistType.put("Type", "복부둘레관리  \t" + getEval(new WaistSurvey()));
		mGroupList.add(waistType);
		Map<String, String> examType = new HashMap<String, String>();
		examType.put("Type", "운동관리  \t" + getEval(new ExerciseSurvey()));
		mGroupList.add(examType);
		Map<String, String> alcholeType = new HashMap<String, String>();
		alcholeType.put("Type", "알콜섭취관리  \t" + getEval(new DrinkSurvey()));
		mGroupList.add(alcholeType);
		Map<String, String> smokingType = new HashMap<String, String>();
		smokingType.put("Type", "금연관리  \t" + getEval(new SmokeSurvey()));
		mGroupList.add(smokingType);
		Map<String, String> stressType = new HashMap<String, String>();
		stressType.put("Type", "스트레스관리  \t" + getEval(new StressSurvey()));
		mGroupList.add(stressType);
		 
		
		putDataToChile(new SaltIntakeSurvey(), saltNoti);
		putDataToChile(new WeightSurvey(), weightNoti);
		putDataToChile(new WaistSurvey(), waistNoti);
		putDataToChile(new ExerciseSurvey(), examNoti);
		putDataToChile(new DrinkSurvey(), alcholeNoti);
		putDataToChile(new SmokeSurvey(), smokingNoti);
		putDataToChile(new StressSurvey(), stressNoti);
		
		ExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				getActivity(),
				this.mGroupList, //화면에 뿌려줄 데이터를 호출
				android.R.layout.simple_expandable_list_item_1, 				//사용할 리스트뷰를 호출
				new String[] { "Type" }, //뿌려줄 값의 Hash의 key를 적어준다.
				new int[] { android.R.id.text1 }, //뿌려줄 TextView를 불러온다.
				this.mChildList, 				//사용할 보조 데이터를 호출한다.
				R.layout.habit_advice,	//배열을 사용하여 호출 할 수 있다. 이 경우 View의 수와 꼭 맞게 적용해야 한다.
				new String[] { "T"},//, "E", "R" },	//String의 수와 View의 수가 1:1이어야 한다.
				new int[] {R.id.advice1}//, R.id.advice2, R.id.advice3 }
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
//				Toast.makeText(getActivity(),
//						"g Collapse = " + groupPosition, Toast.LENGTH_SHORT)
//						.show();
//			
				}
		});

		// 그룹 펼치기
		this.mListView.setOnGroupExpandListener(new OnGroupExpandListener() {
			@Override
			public void onGroupExpand(int groupPosition) {
//				Toast.makeText(getActivity(),
//						"g Expand = " + groupPosition, Toast.LENGTH_SHORT)
//						.show();
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
//				dialog.dismiss();
//				Toast.makeText(getActivity(), "weight is : ", 500).show();

			}
		});
		builder.show();
	}
}
