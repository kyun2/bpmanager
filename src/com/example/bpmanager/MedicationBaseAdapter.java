package com.example.bpmanager;

import java.util.ArrayList;

import com.example.bpmanager.BaseExpandableAdapter.ViewHolder;

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
    private ArrayList<LinearLayout> mList = null;
    private Context mContext = null;
    
    private class MedicineInfoHolder
    {
    	public ImageView img = null;
    	public TextView name = null;
    }
    private MedicineInfoHolder infoHolder = null;

    public MedicationBaseAdapter(Context c, ArrayList<LinearLayout> items)
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
	public LinearLayout getItem(int position) {
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
            infoHolder.img = (ImageView)view.findViewById(R.id.medicine_img);
            infoHolder.name = (TextView)view.findViewById(R.id.medicine_name);
            
            view.setTag(infoHolder);
		}
		else
		{
			infoHolder = (MedicineInfoHolder)view.getTag();
		}
		
		// 약 이미지
		//infoHolder.img.setImageDrawable(null);
		
		// 약 이름
		infoHolder.name.setText(Integer.toString(position));
				
		view.setOnClickListener(buttonClickListener);
		
		return view;
	}
	
	private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	//Toast.makeText(mContext, "Tag: " + infoHolder.tv.getText() + " / " + ((MedicineInfoHolder)v.getTag()).tv.getText(), Toast.LENGTH_SHORT).show();
        	
        }
    };
	
	public void setArrayList(ArrayList<LinearLayout> items){
        this.mList = items;
    }
     
    public ArrayList<LinearLayout> getArrayList(){
        return this.mList;
    }
    
}
