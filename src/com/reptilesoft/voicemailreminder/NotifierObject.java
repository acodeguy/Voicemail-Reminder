package com.reptilesoft.voicemailreminder;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;

public class NotifierObject extends RingtoneManager {
	
	private final String TAG = "Voicemail Reminder";
	Context ctx; 
	SharedPreferences prefs;
	int frequency = 0;
	Ringtone notification;
	boolean vib_on = false;
	private Vibrator vib;
	int vibration_duration = 0;

	public NotifierObject(Context c) {
		super(c);
		
		//Log.d(TAG, "OBJECT CREATED");
		ctx = com.reptilesoft.voicemailreminder.ListenerService.ctx;
		
		prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
		
		/* are we gonna vibrate? */
		vib_on = prefs.getBoolean("key_vibrate", false);
		Log.d(TAG, "vibration is " + vib_on);
		
		/* how frequently do we remind? */
		frequency = Integer.parseInt(prefs.getString("key_frequency", "60000"));
		Log.d(TAG, "reminder frequency: " + frequency);
		
		/* how long do we vibrate for? */
		vibration_duration = Integer.parseInt(prefs.getString("key_vib_duration", "3000"));
		
		/* create the vibrator object */
		vib = (Vibrator)ctx.getSystemService(Context.VIBRATOR_SERVICE);
	}
	
	public void notifyUser(){
		
		/* remind */
		
		/* vibrate */
		
		if(vib_on)
		{
			Log.d(TAG, "Vibrating...");
			vib.vibrate(vibration_duration);
		}
		else
			Log.d(TAG, "No vibrate for you.");		
		
		notification = RingtoneManager.getRingtone(ctx, Uri.parse(com.reptilesoft.voicemailreminder.ListenerObject.notification_pref));
		notification.play();
		
	}
	
	public void stopNotifying(){
		
		notification.stop();
	}

}
