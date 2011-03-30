package com.livelongmobile.wearalarm;

import android.app.Application;

public class WearAlarm extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.getInstance().setContext(this.getApplicationContext());
	}
	
}
