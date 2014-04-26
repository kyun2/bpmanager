package com.example.bpmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		Button menu_home = (Button) findViewById(R.id.home_menu);
		menu_home.setOnClickListener(clickListener);
		Button menu_data = (Button) findViewById(R.id.mydata_menu);
		menu_data.setOnClickListener(clickListener);
		Button menu_bp = (Button) findViewById(R.id.mybp_menu);
		menu_bp.setOnClickListener(clickListener);
		Button menu_med = (Button) findViewById(R.id.mymedicin_menu);
		menu_med.setOnClickListener(clickListener);
		Button menu_habit = (Button) findViewById(R.id.myhabit_menu);
		menu_habit.setOnClickListener(clickListener);
		Button menu_user = (Button) findViewById(R.id.user_menu);
		menu_user.setOnClickListener(clickListener);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.frag_viewer, new HomeFragment()).commit();
		}
		
		mDBHelper = new DBHelper(getApplicationContext());
		mUserData = new UserData();
		if (!mUserData.getData())
		{
			showNewUserDialog();
		}
		
		
		
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
	
	private void showNewUserDialog() {
		// TODO Auto-generated method stub
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("�븣由�");
		alert.setMessage("怨좏삁�븬 愿�由� �빋�뿉 �삤�떊 寃껋쓣 �솚�쁺�빀�땲�떎.\n 媛쒖씤�젙蹂대낫�샇�젙梨낆� �떎�쓬怨� 媛숈뒿�땲�떎.");
		alert.setCancelable(false);
		alert.setPositiveButton("媛쒖씤�젙蹂대낫�샇�젙梨�", new DialogInterface.OnClickListener() {
			
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
		alert.setNegativeButton("�궗�슜�옄 �벑濡�", new DialogInterface.OnClickListener() {
			
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
			newFragment = new InputBPFragment();
			break;
		case FRAGMENT_MED:
			newFragment = new MedicationFragment();
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

			case R.id.home_menu:
				mCurrentFragmentIndex = FRAGMENT_HOME;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.mydata_menu:
				mCurrentFragmentIndex = FRAGMENT_DATA;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.mybp_menu:
				mCurrentFragmentIndex = MainActivity.FRAGMENT_BP;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.myhabit_menu:
				mCurrentFragmentIndex =MainActivity.FRAGMENT_HABIT;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.user_menu:
				mCurrentFragmentIndex = MainActivity.FRAGMENT_USER;
				fragmentReplace(mCurrentFragmentIndex);
				break;
			case R.id.mymedicin_menu:
				mCurrentFragmentIndex = MainActivity.FRAGMENT_MED;
				fragmentReplace(mCurrentFragmentIndex);
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

}
