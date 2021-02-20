package com.reptilesoft.voicemailreminder;

import java.util.Timer;
import java.util.TimerTask;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.PhoneStateListener;
import android.util.Log;

public class ListenerObject extends PhoneStateListener {

	NotifierObject nObj;
	SharedPreferences prefs;
	int frequency = 0;
	public static String notification_pref;
	Timer t;
	NotificationManager notMgr;
	boolean new_message=false;
	private final String TAG="ListenerObject";
	
	public void onMessageWaitingIndicatorChanged(boolean mwi){
		
		/* message has either arrived or been deleted */
		
		// get prefs
		prefs = PreferenceManager.getDefaultSharedPreferences(com.reptilesoft.voicemailreminder.ListenerService.ctx);
		frequency = Integer.parseInt(prefs.getString("key_frequency", "0"));
		notification_pref = prefs.getString("key_notification", null);
		//Log.i(TAG, "FREQ: " + frequency);
		
		nObj = new NotifierObject(com.reptilesoft.voicemailreminder.AppReceiver.ctx);
		
		if(mwi)
		{
			/* we have a message waiting */
			Log.d(TAG, "there is new voicemail!");
			new_message=true;
			createNotifier();
		}
		else
		{
			Log.d(TAG, "no new voicemail.");
			new_message=false;
			//t.cancel();
			this.cancelNotifier();
		}
	}
	
	public void createNotifier(){
		
		t = new Timer();
		//t.cancel();
		t.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				Log.i(TAG, "Timer is running every "+frequency+"ms");
				if(new_message)
				{
					Log.i(TAG, "notifying phone of new voicemail");
					nObj.notifyUser();
				}
			}
		}, 0, frequency);
		//Log.d("TIMER", "TIMER CREATED");
		
	}
	
	public void cancelNotifier(){
		
		/* stop the notifier timer */
		Log.i(TAG,"cancelNotifier()");
		try{
			t.cancel();
		}catch(Exception e){
			Log.e(TAG,e.toString());
		}
		
	}
	
	public void setDefaultPrefs(){
		
		/* sets default prefs, ONCE! */
		
		PreferenceManager.setDefaultValues(com.reptilesoft.voicemailreminder.VoicemailReminder.ctx, R.xml.preferences, false);
	}
}
