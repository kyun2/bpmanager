package com.example.bpmanager;

import java.util.List;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class bpAdapter extends ArrayAdapter<BloodPressure> {
	
	List<BloodPressure> value;
	private final Context ctx;
	private final LayoutInflater mInflater;
	final String TAG = "MainActivity";
	private final FragmentManager manager;
	
	public bpAdapter(Context context, int resource, List<BloodPressure> objects, FragmentManager f) {
		super(context, resource, objects);
		value = objects;
		
		ctx = context;
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		manager = f;
		
		// TODO Auto-generated constructor stub
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.bpitem, parent, false);
        }
        
        TextView sys = (TextView) v.findViewById(R.id.systolic_item);
        TextView dia = (TextView) v.findViewById(R.id.diastolic_item);
        TextView time = (TextView) v.findViewById(R.id.bptime_item);
        BloodPressure temp = value.get(position);
        sys.setText(String.valueOf(temp.getSystolic()));
        dia.setText(String.valueOf(temp.getDiastolic()));
        time.setText(String.valueOf(temp.getBpdatetime()));
        
        
//        BitmapDrawable dr = (BitmapDrawable) ctx.getResources().getDrawable(R.drawable.ic_launcher);
//        image.setImageDrawable(dr);
//        name.setText(listItem.get(position));
//        image.setTag(listItem.get(position));
//        name.setTag(listItem.get(position));
////        name.setOnTouchListener(touchListener);
////        image.setOnTouchListener(touchListener);
//        name.setOnClickListener(clickListener);
//        image.setOnClickListener(clickListener);
        
        return v;
    }


}
