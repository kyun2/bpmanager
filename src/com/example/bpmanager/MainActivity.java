package com.example.bpmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bpmanager.DB.DBHelper;
//import com.example.bpmanager.DB.DBhandler;
import com.example.bpmanager.DB.INFOMedication;

public class MainActivity extends ActionBarActivity {
	
	
	int mCurrentFragmentIndex;
	public final static int FRAGMENT_HOME = 0;
	public final static int FRAGMENT_DATA = 1;
	public final static int FRAGMENT_BP = 2;
	public final static int FRAGMENT_MED = 3;
	public final static int FRAGMENT_HABIT = 4;
	public final static int FRAGMENT_USER = 5;
	
	public static DBHelper mDBHelper;
	public static UserData mUserData;
	public static MedicationScheduleData mMedicationScheduleData;
	
	//private static Button home;
	private static Button data;
	private static Button bp;
	private static Button med;
	private static Button habit;
	private static Button user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		//home = (Button) findViewById(R.id.home_menu);
		//home.setOnClickListener(clickListener);
		data = (Button) findViewById(R.id.mydata_menu);
		data.setOnClickListener(clickListener);
		bp = (Button) findViewById(R.id.mybp_menu);
		bp.setOnClickListener(clickListener);
		med = (Button) findViewById(R.id.mymedicin_menu);
		med.setOnClickListener(clickListener);
		habit = (Button) findViewById(R.id.myhabit_menu);
		habit.setOnClickListener(clickListener);
		user = (Button) findViewById(R.id.user_menu);
		user.setOnClickListener(clickListener);
		
		Fragment initFragment = null;
		
		mDBHelper = new DBHelper(getApplicationContext());
		mUserData = new UserData();
		if (!mUserData.getData())
		{
			//showNewUserDialog();
			MainActivity.hideFooter();
			initFragment = new PrivacyFragment();
		}
		else
		{
			initFragment = new HomeFragment();
		}
		mMedicationScheduleData = new MedicationScheduleData();
		mMedicationScheduleData.getData();		
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.frag_viewer, initFragment).commit();
		}
		
		// 의약품 정보 만들기
		INFOMedication.Make();
		
		
		//Toast.makeText(this, "activity start", 3000).show();
		//DBhandler handle = new DBhandler(this);
		//handle.readOpen();
		//List<user> retval = handle.getUsers();
		//if(retval.isEmpty()){
			//Toast.makeText(this, "user not found", 3000).show();
			//handle.close();
			//showNewUserDialog();
			
		//}else {
			//cu.moveToPosition(cu.getCount() - 1);
			//Toast.makeText(this, "user found : "+cu.getInt(0)+" "+cu.getString(1), 3000).show();
			//handle.close();
		//}
		
	}
	
	public static void showFooter() {
		//home.setVisibility(View.VISIBLE);
		data.setVisibility(View.VISIBLE);
		bp.setVisibility(View.VISIBLE);
		med.setVisibility(View.VISIBLE);
		habit.setVisibility(View.VISIBLE);
		user.setVisibility(View.VISIBLE);	
	}
	
	public static void hideFooter() {
		//home.setVisibility(View.INVISIBLE);
		data.setVisibility(View.INVISIBLE);
		bp.setVisibility(View.INVISIBLE);
		med.setVisibility(View.INVISIBLE);
		habit.setVisibility(View.INVISIBLE);
		user.setVisibility(View.INVISIBLE);	
	}
	
	private void showNewUserDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("");
		alert.setMessage("");
		alert.setCancelable(false);
		alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				dialog.dismiss();
				final FragmentTransaction transaction = getSupportFragmentManager()
						.beginTransaction();

				transaction.replace(R.id.frag_viewer, new PrivacyFragment());
				transaction.addToBackStack(null);
				// Commit the transaction
				transaction.commit();
			}
		});
		alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				finish();
			}
		});
		
		alert.show();
	}

	public void fragmentReplace(int reqNewFragmentIndex) {
		mCurrentFragmentIndex = reqNewFragmentIndex;

		Fragment newFragment = null;

//		Log.d(TAG, "fragmentReplace " + reqNewFragmentIndex);

		newFragment = getFragment(reqNewFragmentIndex); 

		// replace fragment
		final FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();

		transaction.replace(R.id.frag_viewer, newFragment);
		transaction.addToBackStack(null);
		// Commit the transaction
		transaction.commit();

	}
	
	public void changeFragment(Fragment fragment)
	{
		getSupportFragmentManager().beginTransaction().replace(R.id.frag_viewer, fragment).addToBackStack(null).commit();
	}

	private Fragment getFragment(int idx) {
		Fragment newFragment = null;
		
		switch (idx) {
		case FRAGMENT_HOME:
			newFragment = new HomeFragment();
			break;
		case FRAGMENT_DATA:
			newFragment = new DataFragment();
			break;
		case FRAGMENT_BP:
			newFragment = new BloodPressureInputFragment();
			break;
		case FRAGMENT_MED:
			newFragment = new MedicationTopFragment();
			break;
		case FRAGMENT_HABIT:
			newFragment = new HabitFragment();
			break;
		case FRAGMENT_USER:
			newFragment = new UserMenuFragment();
			break;

		default:
			break;
		}

		return newFragment;
	}
	
	private View.OnClickListener clickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {

			switch (v.getId()) {

			//case R.id.home_menu:
			//	mCurrentFragmentIndex = FRAGMENT_HOME;
			//	fragmentReplace(mCurrentFragmentIndex);
			//	break;
			case R.id.mydata_menu:
				fragmentReplace(FRAGMENT_DATA);
				break;
			case R.id.mybp_menu:
				fragmentReplace(FRAGMENT_BP);
				break;
			case R.id.myhabit_menu:
				fragmentReplace(FRAGMENT_HABIT);
				break;
			case R.id.user_menu:
				fragmentReplace(FRAGMENT_USER);
				break;
			case R.id.mymedicin_menu:
				fragmentReplace(FRAGMENT_MED);
				break;
			}

		}
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			return rootView;
		}
	}
	
	public void OpenBrowser(String url)
	{
		Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
		startActivity(viewIntent);
	}	
}
