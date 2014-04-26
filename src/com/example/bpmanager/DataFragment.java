package com.example.bpmanager;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DataFragment extends Fragment{

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_mydata, container, false);

//		LinearLayout f = (LinearLayout)view.findViewById(R.id.bp_graph_view);
//		ImageView img = new ImageView(view.getContext());
//		BitmapDrawable dr = (BitmapDrawable) view.getContext().getResources().getDrawable(R.drawable.bp_graph);
//        img.setImageDrawable(dr);
//        f.addView(img);



		return view;
	}

	public void onClick(View v) {

		switch (v.getId()) {

//		case R.id.bt_ok:
//			Toast.makeText(getActivity(), "One Fragment", Toast.LENGTH_SHORT)
//					.show();
//			break;

		}
	}
}
