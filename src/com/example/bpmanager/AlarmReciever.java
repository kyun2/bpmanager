package com.example.bpmanager;

import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class AlarmReciever extends BroadcastReceiver 
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		String type = intent.getExtras().getString("type");
		if (type.equals("MEDICINE"))
		{
			showMedicineMessage(context, intent);
		}
		else if (type.equals("HOSPITAL"))
		{
			showHospitalMessage(context, intent);
		}
	}
	
	private void showMedicineMessage(Context context, Intent intent)
	{
		int medicineId = intent.getExtras().getInt("medicineId");
		String medicineName = intent.getExtras().getString("medicineName");
		Toast.makeText(context, medicineName, Toast.LENGTH_LONG).show();
		
		Vibrator alarmVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		alarmVibrator.vibrate(2000);
		
		Uri alarmAlert = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_ALARM );
		if (alarmAlert == null)
		{
			alarmAlert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		}
		if (alarmAlert == null)
		{
			alarmAlert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		}
		Ringtone alarmRingtone = RingtoneManager.getRingtone(context, alarmAlert);
		alarmRingtone.play();
		
		NotificationCompat.Builder notiBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(context.getText(R.string.app_name))
		        .setContentText(medicineName);
		
		// Creates an explicit intent for an Activity in your app
		Intent notiIntent = new Intent(context, MainActivity.class);
		PendingIntent notiPendingIntent = PendingIntent.getActivity(context, medicineId, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notiBuilder.setContentIntent(notiPendingIntent);
		
		NotificationManager notiManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		notiManager.notify(medicineId, notiBuilder.build());
	}
	
	private void showHospitalMessage(Context context, Intent intent)
	{
		Toast.makeText(context, "내일은 병원에 방문하는 날 입니다.", Toast.LENGTH_LONG).show();
		
		Vibrator alarmVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		alarmVibrator.vibrate(2000);
		
		Uri alarmAlert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		if (alarmAlert == null)
		{
			alarmAlert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		}
		if (alarmAlert == null)
		{
			alarmAlert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		}
		Ringtone alarmRingtone = RingtoneManager.getRingtone(context, alarmAlert);
		alarmRingtone.play();
		
		NotificationCompat.Builder notiBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle(context.getText(R.string.app_name))
		        .setContentText("내일은 병원에 방문하는 날 입니다.");
		
		// Creates an explicit intent for an Activity in your app
		Intent notiIntent = new Intent(context, MainActivity.class);
		PendingIntent notiPendingIntent = PendingIntent.getActivity(context, UserData.ALARM_ID, notiIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		notiBuilder.setContentIntent(notiPendingIntent);
		
		NotificationManager notiManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notiManager.notify(UserData.ALARM_ID, notiBuilder.build());
	}

}
