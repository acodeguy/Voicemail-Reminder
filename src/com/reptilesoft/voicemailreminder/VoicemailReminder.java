package com.reptilesoft.voicemailreminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class VoicemailReminder extends Activity implements OnClickListener {
	
	Button btn_start, btn_stop, btn_prefs;
	SharedPreferences prefs;
	ImageButton btn_contact;
	
	public static Context ctx;
	
	/* CONTEXT MENU IDS */
	private final int MENU_ABOUT = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        btn_start = (Button)findViewById(R.id.button_start_service);
        btn_start.setOnClickListener(this);
        btn_stop = (Button)findViewById(R.id.button_stop_service);
        btn_stop.setOnClickListener(this);
        btn_prefs = (Button)findViewById(R.id.button_preferences);
        btn_prefs.setOnClickListener(this);
        btn_contact=(ImageButton)findViewById(R.id.image_rs_logo);
        btn_contact.setOnClickListener(this);
        
        ctx = getApplicationContext();
        
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        
        //Log.d("DN", "DN = " + RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
    }

	public void onClick(View v) {
		

		switch(v.getId())
		{
		case R.id.button_start_service:
			startService(new Intent(this, ListenerService.class));
			break;
		case R.id.button_stop_service:
			stopService(new Intent(this, ListenerService.class));
			break;
		case R.id.button_preferences:
			startActivity(new Intent(this, Preferences.class));
			break;
		case R.id.image_rs_logo:
			/* go to market to view all my apps */
			startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://search?q=pub:\"Reptile Soft\"")));
			break;
		}
	}
	
	/* CONTEXT MENU */
	@Override
    public boolean onCreateOptionsMenu(Menu m) {
		
		m.add(0,MENU_ABOUT,0,"About").setIcon(android.R.drawable.ic_menu_info_details);
		
		return true;
	}
	
	/* HANDLING CONTEXT MENU */
	@Override
    public boolean onOptionsItemSelected(MenuItem mi) {
		
		switch (mi.getItemId()) 
		{
		case MENU_ABOUT:
			startActivity(new Intent(this, About.class));
			break;
		}
		return false;
	}
}