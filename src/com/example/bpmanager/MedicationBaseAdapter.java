package com.example.bpmanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.example.bpmanager.BaseExpandableAdapter.ViewHolder;
import com.example.bpmanager.DB.DBMedicationTook;
import com.example.bpmanager.DB.INFOMedication;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MedicationBaseAdapter extends BaseAdapter {
	
	private LayoutInflater inflater = null;
    private ArrayList<MedicationScheduleData.MedicationSchedule> mList = null;
    private Context mContext = null;
    
    private int resId;
    
    public class MedicineInfoHolder
    {
    	public int medicine_id = 0;
    	public ImageView img = null;
    	public TextView name = null;
    	public ImageButton button = null;
    }
    
    public MedicationBaseAdapter(Context c, ArrayList<MedicationScheduleData.MedicationSchedule> items, int itemResId)
    {
    	this.inflater = LayoutInflater.from(c);
    	this.mContext = c;
    	this.mList = items;
    	this.resId = itemResId;
    }

	@Override
	public int getCount() {
		return this.mList.size();
	}

	@Override
	public MedicationScheduleData.MedicationSchedule getItem(int position) {
		return this.mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		MedicineInfoHolder infoHolder = null;
		
		if (view == null)
		{
            view = inflater.inflate(resId, null);
            
            infoHolder = new MedicineInfoHolder();
            infoHolder.medicine_id = getItem(position).mMedicineId;
            //infoHolder.img = (ImageView)view.findViewById(R.id.medicine_img);
            infoHolder.name = (TextView)view.findViewById(R.id.medicine_name);
            switch (resId)
            {
            case R.layout.medicineitem_mine:
            	infoHolder.button = (ImageButton) view.findViewById(R.id.medicine_took);
            	break;
            case R.layout.medicineitem_add:
            	break;
            }
            
            view.setTag(infoHolder);
		}
		else
		{
			infoHolder = (MedicineInfoHolder)view.getTag();			
		}
		
		//Log.i("ID: ", getCount() + " : " + position);
		
		// ID
		infoHolder.medicine_id = getItem(position).mMedicineId;
		
		// Image
		//infoHolder.img.setBackgroundResource(resid);
		
		// Text
		infoHolder.name.setText(INFOMedication.getInfoMedicine(infoHolder.medicine_id).mName);
		
		// Act
		switch (resId)
		{
		case R.layout.medicineitem_mine:
			if (MedicationData.hasTookToday(infoHolder.medicine_id))
			{
				infoHolder.button.setVisibility(View.INVISIBLE);
				infoHolder.button.setEnabled(false);
				infoHolder.button.setClickable(false);
			}
			else
			{
				infoHolder.button.setEnabled(true);
				infoHolder.button.setClickable(true);
			}
			infoHolder.button.setOnClickListener(buttonClickListener);
			infoHolder.name.setOnClickListener(buttonClickListener2);
			break;
		case R.layout.medicineitem_add:
			break;
		}

		return view;
	}
	
	private View.OnClickListener buttonClickListener = new View.OnClickListener() {
		
        @Override
        public void onClick(View v) {
        	MedicineInfoHolder infoHolder = (MedicineInfoHolder)(((LinearLayout)v.getParent().getParent()).getTag());

        	ContentValues values = new ContentValues();
			values.put(DBMedicationTook.SCHEMA.COLUMN_MEDID, infoHolder.medicine_id);
			values.put(DBMedicationTook.SCHEMA.COLUMN_INJECT_TIME, (new SimpleDateFormat("yyyy/MM/dd")).format(Calendar.getInstance().getTime()));
			
			MainActivity.mDBHelper.insertData(DBMedicationTook.SCHEMA.TB_NAME, values);
			MainActivity.mMediHistData.resetData();
			
			infoHolder.button.setVisibility(View.INVISIBLE);
			infoHolder.button.setEnabled(false);
			infoHolder.button.setClickable(false);
        }
    };
    
    private View.OnClickListener buttonClickListener2 = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			MedicineInfoHolder infoHolder = (MedicineInfoHolder)(((LinearLayout)v.getParent().getParent()).getTag());
			
			MedicationDetailFragment next = new MedicationDetailFragment();
			next.setMedicineId(infoHolder.medicine_id);
			((MainActivity)mContext).changeFragment(next);
		}
	};

}
