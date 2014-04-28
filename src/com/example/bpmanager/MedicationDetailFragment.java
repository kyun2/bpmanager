package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.bpmanager.DB.DBMedicationTook;
import com.example.bpmanager.DB.INFOMedication;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MedicationDetailFragment extends Fragment {
	
	private int medicine_id;
	
	ImageView img;
	TextView name;
	
	Button btnOpenWebInfo;
	Button btnTook;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_med_detail, container, false);
		INFOMedication info = INFOMedication.getInfoMedicine(medicine_id);
		
		img = (ImageView) view.findViewById(R.id.medicine_img);
		name = (TextView) view.findViewById(R.id.medicine_name);
		
		// Image
		//
		// name
		name.setText(info.mName);
		
		// 자세히 보기
		btnOpenWebInfo = (Button) view.findViewById(R.id.medicine_web_open);
		btnOpenWebInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((MainActivity)getActivity()).OpenBrowser("http://www.kimsonline.co.kr");
			}
		});
		
		// 복용하기
		btnTook = (Button) view.findViewById(R.id.med_btn_took_it);
		if (hasTookToday())
		{
			btnTook.setEnabled(false);
			btnTook.setClickable(false);
			btnTook.setTextColor(Color.BLACK);
			btnTook.setAlpha(0.5f);
		}
		else
		{
			btnTook.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// DB
					insert();
					// Disable button
					btnTook.setEnabled(false);
					btnTook.setClickable(false);
					btnTook.setTextColor(Color.BLACK);
					btnTook.setAlpha(0.5f);
					// 알림
					AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
					alert.setMessage("복용 완료").setTitle("");
					alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
	
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					alert.show();
				}
				
				private void insert()
				{
					ContentValues values = new ContentValues();
					values.put(DBMedicationTook.SCHEMA.COLUMN_MEDID, medicine_id);
					values.put(DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME, (new SimpleDateFormat("yyyy/MM/dd")).format(Calendar.getInstance().getTime()));
					
					MainActivity.mDBHelper.insertData(DBMedicationTook.SCHEMA.TB_NAME, values);
				}
			});
		}
		
		return view;
	}
	
	public void setMedicineId(int id)
	{
		this.medicine_id = id;
	}
	
	public boolean hasTookToday()
	{
		boolean ret = false;
		try
		{
			SQLiteDatabase db = MainActivity.mDBHelper.getReadableDatabase();
			
			String[] projection = {
				DBMedicationTook.SCHEMA.COLUMN_MEDID,
				DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME
			};
			
			Cursor c1 = db.query(DBMedicationTook.SCHEMA.TB_NAME, projection, " medicine_id = " + this.medicine_id, null, null, null, " inject_time desc");
			
			if (c1.moveToFirst())
			{
				String[] times = c1.getString(c1.getColumnIndex(DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME)).split("/");
				
				try
				{
					int day = Integer.parseInt(times[2]);
					int cday = Integer.parseInt((new SimpleDateFormat("dd")).format(Calendar.getInstance().getTime()));
					
					if (Math.abs(day - cday) == 0)
						ret = true;
					else
						ret = false;
				}
				catch (NumberFormatException e)
				{
				}
			}
			else
			{
				ret = false;
			}
			
			c1.close();
		}
		catch (SQLiteException e)
		{
			ret = false;
		}
		return ret;
	}
}
