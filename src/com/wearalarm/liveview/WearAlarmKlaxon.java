/*
 * Copyright (c) 2011 Dan Walkes, Patrick Leonard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.wearalarm.liveview;

import android.util.Log;

import com.android.deskclock.Alarm;
import com.android.deskclock.AlarmKlaxon;

/**
 * Extends AlarmKlaxon to implement liveview vibrate
 * @author dan
 */
public class WearAlarmKlaxon extends AlarmKlaxon {

	LiveViewVibrator mLiveViewVibe = null;
	private static final String LOG_TAG = "WearAlarmKlaxon";
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(LOG_TAG, "onCreate");
		mLiveViewVibe = new LiveViewVibrator(this.getApplicationContext());
	}
	
	@Override
	protected void play(Alarm alarm) {
		super.play(alarm);
		mLiveViewVibe.start();
		Log.v(LOG_TAG, "play");
	}
	
	@Override
	public void stop() {
		super.stop();
		mLiveViewVibe.stop();
		Log.v(LOG_TAG, "stop");
	}
}
