package com.livelongmobile.wearalarm;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.widget.Toast;

import com.livelongmobile.wearalarm.android.deskclock.SettingsActivity;

public class WearAlarmSettings extends SettingsActivity {

	private static final String KEY_SEND_LOGS =
        "send_logs";
	@Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference)
	{
		if (preference == findPreference(KEY_SEND_LOGS)) {
			File logFile = Log.getInstance().getLogFile();
			if( logFile == null ) {
				Toast toast = Toast.makeText(this,
						R.string.log_file_not_available_insert_sdcard,
						Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
			    emailIntent.setType("plain/text");
			    // TODO: fill with livelongmobile support email address
			    //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] 
			    //{"wakemeski@ddubtech.com"}); 
			    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, 
			    "WearAlarm debug log"); 
			    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, 
			    "Description of problem:"); 
			    emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+ logFile.getAbsolutePath()));
			    startActivity(Intent.createChooser(emailIntent,this.getString(R.string.send_mail)));
			}
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
}
