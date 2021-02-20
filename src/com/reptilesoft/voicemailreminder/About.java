package com.reptilesoft.voicemailreminder;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class About extends Activity {
	
	
	PackageInfo pi;
	PackageManager pm;
	
	TextView tv_app_title;

	public void onCreate(Bundle b){
		
		super.onCreate(b);
		
		setContentView(R.layout.about);
		
		/* controls */
		tv_app_title = (TextView)findViewById(R.id.text_title);
		
		/* get the packagemanager */
		pm = getPackageManager();
		
		try{
			
			/* get package info */
			pi = pm.getPackageInfo("com.reptilesoft.voicemailreminder",0);
			
			/* set the version # to append to app title */
			
			tv_app_title.setText("Voicemail Reminder" + " v" + pi.versionName);
		}
		catch(NameNotFoundException e){
			e.printStackTrace();
		}
	}
}
