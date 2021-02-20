package com.reptilesoft.voicemailreminder;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

public class Preferences extends PreferenceActivity implements OnPreferenceClickListener {

	Preference start_on_boot, reminder_freq, notification, vibration, vib_duration;
	boolean changes_have_been_made = false;
	
	public void onCreate(Bundle b){
		
		super.onCreate(b);
		
		addPreferencesFromResource(R.xml.preferences);
		
		start_on_boot = (Preference)findPreference("key_service");
		start_on_boot.setOnPreferenceClickListener(this);
		
		reminder_freq = (Preference)findPreference("key_frequency");
		reminder_freq.setOnPreferenceClickListener(this);
		
		notification = (Preference)findPreference("key_notification");
		notification.setOnPreferenceClickListener(this);

		vibration = (Preference)findPreference("key_vibrate");
		vibration.setOnPreferenceClickListener(this);

		vib_duration = (Preference)findPreference("key_vib_duration");
		vib_duration.setOnPreferenceClickListener(this);
	}

	public boolean onPreferenceClick(Preference p) {
		
		/* we've made changes... */
		changes_have_been_made = true;
		
		/*if(p == start_on_boot && start_on_boot)
		{
			Toast.makeText(this, " " + p.getTitle(), Toast.LENGTH_LONG).show();
		}
		*/
		return false;
	}
	
	public void onDestroy() {
		
		super.onDestroy();
		
		/* we're closing the screen, has anything changed? */
		if(changes_have_been_made)
			{
			Toast.makeText(this, "Changes made, restarting listener service.", Toast.LENGTH_LONG).show();
				stopService(new Intent(this, ListenerService.class));
				startService(new Intent(this,ListenerService.class));
			}
	}
}
