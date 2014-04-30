package com.example.bpmanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bpmanager.DB.DBHelper;
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
	public static MedicationHistoryData mMediHistData;
	
	//private static Button home;
	private static LinearLayout footer;
	private static ImageView data;
	private static ImageView bp;
	private static ImageView med;
	private static ImageView habit;
	private static ImageView user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		footer = (LinearLayout) findViewById(R.id.footer);		
		//home = (Button) findViewById(R.id.home_menu);
		data = (ImageView) findViewById(R.id.iv_go_mydata);
		bp = (ImageView) findViewById(R.id.iv_go_bp);
		med = (ImageView) findViewById(R.id.iv_go_medicine);
		habit = (ImageView) findViewById(R.id.iv_go_habit);
		user = (ImageView) findViewById(R.id.iv_go_conf);

		//home.setOnClickListener(clickListener);
		data.setOnClickListener(clickListener);
		bp.setOnClickListener(clickListener);
		med.setOnClickListener(clickListener);
		habit.setOnClickListener(clickListener);		
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
		mMedicationScheduleData = new MedicationScheduleData(this);
		mMedicationScheduleData.getData();
		mMediHistData = new MedicationHistoryData();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.frag_viewer, initFragment).commit();
		}
		
		// 의약품 정보 만들기
		INFOMedication.Make();
	}
	
	public static void showFooter() {
		footer.setVisibility(View.VISIBLE);
	}
	
	public static void hideFooter() {
		footer.setVisibility(View.GONE);
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
			newFragment = new MyInfoFragment();
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
			case R.id.iv_go_mydata:
				fragmentReplace(FRAGMENT_DATA);
				break;
			case R.id.iv_go_bp:
				fragmentReplace(FRAGMENT_BP);
				break;
			case R.id.iv_go_habit:
				fragmentReplace(FRAGMENT_HABIT);
				break;
			case R.id.iv_go_conf:
				fragmentReplace(FRAGMENT_USER);
				break;
			case R.id.iv_go_medicine:
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
	
	public void sendEmail()
	{
		String[] to = new String[] { "besthannah@naver.com", "csyong715@gmail.com" };
		String subject = "데이터[" + mUserData.getName() + mUserData.getBirth() + "]";
		String body = "고혈압 관리 프로그램의 데이터를 전송합니다.";
		//body = Html.fromHtml(body).toString();

		// 디렉토리
		File cacheDir = this.getExternalCacheDir();
		if (!cacheDir.exists())
		{
			Toast.makeText(this, "파일 쓰기에 실패하였습니다.", Toast.LENGTH_LONG).show();
			return;
		}
		
		// 유저정보
		File userData = null;
		String userDataString = mUserData.buildDataString();
		try
		{
			userData = new File(cacheDir, "UserData.txt");;
			FileWriter fileWriter = new FileWriter(userData);
			BufferedWriter out = new BufferedWriter(fileWriter);
			out.write(userDataString);
			out.close();
			fileWriter.close();
		}
		catch( IOException e )
		{
			Toast.makeText(this, "유저 데이터 생성 실패", Toast.LENGTH_LONG).show();
			return;
		}
		// 혈압정보/변동내역
		File bpData = null;
		String bpDataString = BloodPressure.buildDataString();
		try
		{
			bpData = new File(cacheDir, "BPData.txt");
			FileWriter fileWriter = new FileWriter(bpData);
			BufferedWriter out = new BufferedWriter(fileWriter);
			out.write(bpDataString);
			out.close();
			fileWriter.close();
		}
		catch( IOException e )
		{
			Toast.makeText(this, "혈압 데이터 생성 실패", Toast.LENGTH_LONG).show();
			return;
		}
		// 복용정보
		File medScheduleData = null;
		String medScheduleDataString = mMedicationScheduleData.buildDataString();
		try
		{
			medScheduleData = new File(cacheDir, "medScheduleData.txt");
			FileWriter fileWriter = new FileWriter(medScheduleData);
			BufferedWriter out = new BufferedWriter(fileWriter);
			out.write(medScheduleDataString);
			out.close();
			fileWriter.close();
		}
		catch(IOException e)
		{
			Toast.makeText(this, "복약관리 데이터 생성 실패", Toast.LENGTH_LONG).show();
			return;
		}
		// 복용기록
		File medTookData = null;
		String medTookDataString = mMediHistData.buildDataString();
		try
		{
			medTookData = new File(cacheDir, "MedTookData.txt");
			FileWriter fileWriter = new FileWriter(medTookData);
			BufferedWriter out = new BufferedWriter(fileWriter);
			out.write(medTookDataString);
			out.close();
			fileWriter.close();
		}
		catch( IOException e )
		{
			Toast.makeText(this, "복용기록 데이터 생성 실패", Toast.LENGTH_LONG).show();
			return;
		}		

		//Uri uri = Uri.fromFile(habitData);
		ArrayList<Uri> uris = new ArrayList<Uri>();
		uris.add(Uri.fromFile(userData));
		uris.add(Uri.fromFile(bpData));
		uris.add(Uri.fromFile(medScheduleData));
		uris.add(Uri.fromFile(medTookData));
		
		Intent email = new Intent(Intent.ACTION_SEND_MULTIPLE);
		email.setData(Uri.parse( "matilto:" ));
		//email.setType( "text/plain" );
		email.setType("message/rfc822");
		email.putExtra(Intent.EXTRA_EMAIL, to);

		email.putExtra(Intent.EXTRA_SUBJECT, subject);
		email.putExtra(Intent.EXTRA_TEXT, body);
		//email.putExtra(Intent.EXTRA_STREAM, uris);
		email.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);
				
		try {
		    startActivity(Intent.createChooser(email, "Send Email"));
		} catch ( android.content.ActivityNotFoundException ex ) {
		    Toast.makeText( this, "메일 전송을 할 수 없습니다.", Toast.LENGTH_LONG).show();
		}
	}	
}
