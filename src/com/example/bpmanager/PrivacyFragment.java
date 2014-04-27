package com.example.bpmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PrivacyFragment extends Fragment{
	ViewGroup group;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		group = container;
		Button privacy;
		Button regist;

		View view = inflater.inflate(R.layout.fragment_privacy, container, false);

//		LinearLayout f = (LinearLayout)view.findViewById(R.id.bp_graph_view);
//		ImageView img = new ImageView(view.getContext());
//		BitmapDrawable dr = (BitmapDrawable) view.getContext().getResources().getDrawable(R.drawable.bp_graph);
//        img.setImageDrawable(dr);
//        f.addView(img);
		privacy = (Button)view.findViewById(R.id.bth_privacy);
		regist = (Button)view.findViewById(R.id.bth_regist);
		
		privacy.setOnClickListener(click);
		regist.setOnClickListener(click);

		return view;
	}
	
	View.OnClickListener click = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId())
			{			
			case R.id.bth_privacy:
				PrivacyDialogFragment privacy = new PrivacyDialogFragment();
				privacy.show(getFragmentManager(), "TAG");
				break;
				
			case R.id.bth_regist:
				Fragment newFragment = null;

//				Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

				newFragment = new UserInformationFragment(); 

				// replace fragment
				final FragmentTransaction transaction = getActivity().getSupportFragmentManager()
						.beginTransaction();

				transaction.replace(R.id.frag_viewer, newFragment);
				transaction.addToBackStack(null);
				// Commit the transaction
				transaction.commit();
				break;
			
			}
			// TODO Auto-generated method stub
			
		}
	};
}
