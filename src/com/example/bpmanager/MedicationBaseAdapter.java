package com.example.bpmanager;

import java.util.ArrayList;

import com.example.bpmanager.BaseExpandableAdapter.ViewHolder;
import com.example.bpmanager.DB.INFOMedication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MedicationBaseAdapter extends BaseAdapter {
	
	private LayoutInflater inflater = null;
    private ArrayList<MedicationScheduleData.MedicationSchedule> mList = null;
    private Context mContext = null;
    
    public class MedicineInfoHolder
    {
    	public ImageView img = null;
    	public TextView name = null;
    }
    private MedicineInfoHolder infoHolder = null;

    public MedicationBaseAdapter(Context c, ArrayList<MedicationScheduleData.MedicationSchedule> items)
    {
    	this.inflater = LayoutInflater.from(c);
    	this.mContext = c;
    	this.mList = items;
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
		
		if (view == null)
		{
            view = inflater.inflate(R.layout.medicineitem_add, null);
            
            infoHolder = new MedicineInfoHolder();
            //infoHolder.img = (ImageView)view.findViewById(R.id.medicine_img);
            infoHolder.name = (TextView)view.findViewById(R.id.medicine_name);
            
            view.setTag(infoHolder);
		}
		else
		{
			infoHolder = (MedicineInfoHolder)view.getTag();			
		}
		
		//Log.i("ID: ", getCount() + " : " + position);
		
		// Image
		//infoHolder.img.setBackgroundResource(resid);
		
		// Text
		infoHolder.name.setText(INFOMedication.getInfoMedicine(getItem(position).mId).mName);

		return view;
	}
	
	/*
	private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	//Toast.makeText(mContext, "Tag: " + infoHolder.tv.getText() + " / " + ((MedicineInfoHolder)v.getTag()).tv.getText(), Toast.LENGTH_SHORT).show();
        	
        }
    };
    */    
}
