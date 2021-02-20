package com.reptilesoft.voicemailreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class AppReceiver extends BroadcastReceiver {

	SharedPreferences prefs;
	
	public static Context ctx;
	
	boolean listener_service = false;
	
	@Override
	public void onReceive(Context c, Intent i) {
		
		ctx = c;
		//Toast.makeText(c, "Voicemail Reminder started.", Toast.LENGTH_LONG).show();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(c);
		getPrefs();
		
		/* start service? */
		if(listener_service)
			c.startService(new Intent(c, ListenerService.class));
		else
			{
				//Toast.makeText(c, "Voicemail Reminder listener service not set at boot, exiting!", Toast.LENGTH_LONG).show();
			}
		
	}
	
	public void getPrefs(){
		
		listener_service = prefs.getBoolean("key_service", false);
	}

}
