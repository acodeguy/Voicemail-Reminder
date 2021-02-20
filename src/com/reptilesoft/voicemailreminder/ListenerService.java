package com.reptilesoft.voicemailreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class ListenerService extends Service {
	
	TelephonyManager tm;
	NotificationManager notMgr;
	Notification notification;
	private final int NOTIFICATION = 1;
	
	public static Context ctx;
	
	@Override
	public IBinder onBind(Intent i) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent i, int id){
		
		Toast.makeText(getApplicationContext(), "VM Reminder service up", Toast.LENGTH_LONG).show();
		
		ctx = getApplicationContext();
		
		PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		
		tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		tm.listen(new ListenerObject(), PhoneStateListener.LISTEN_MESSAGE_WAITING_INDICATOR);
		
		notifyIcon();
		
	}
	
	@Override
	public void onDestroy(){
	
		Toast.makeText(getApplicationContext(), "VM Reminder service downed", Toast.LENGTH_LONG).show();
		notMgr.cancelAll();
	}
	
	public void notifyIcon(){
		
		/* show a notif in the status bar, that app is listening... */
		notMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		
		notification = new Notification(R.drawable.icon, "VM Reminder is listening...", System.currentTimeMillis());
		
		Intent notIntent = new Intent(this, VoicemailReminder.class);
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notIntent, 0);
		
		notification.flags |= Notification.FLAG_NO_CLEAR;
		//notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		notification.setLatestEventInfo(getApplicationContext(), "Voicemail Reminder", "I'm listening for new voicemail...", contentIntent);
		
		notMgr.notify(NOTIFICATION, notification);
	}
	
	

}
