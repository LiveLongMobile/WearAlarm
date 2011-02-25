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

import java.util.Timer;
import java.util.TimerTask;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.wearalarm.liveview.WearAlarmService.LocalBinder;

/**
 * Handles periodic vibrate using the WearAlarmService and the LiveView
 * SDK.
 */
public class LiveViewVibrator {

	private Timer mVibrateTimer = new Timer();
	private WearAlarmService mService = null;
	private boolean mBound = false;
	
	private static final int ON_TIME_MS = 500;
	private static final int OFF_TIME_MS = 500;
	private static final String TAG = "LiveViewVibrator";
	private Context mContext;
	
	private void doVibrate() 
	{
		if( mBound && mService != null ) {
			mService.vibrate(ON_TIME_MS);
		}
	}
	
	/**
	 * LiveView only supports a single vibrate.  We want to continue to issue with an on/off period
	 * until a wakeup happens.  This class handles the recurring vibrate
	 */
	private class RecurringVibrate extends TimerTask
	{
		private Timer mTimer;
		public RecurringVibrate( Timer timer ) {
			mTimer = timer;
		}
		public void run() {
			if( mBound && mService != null ) {
				doVibrate();
				/**
				 * Note- will be a no-op if cancel() has run on the timer
				 */
				mTimer.schedule(new RecurringVibrate(mTimer) , ON_TIME_MS + OFF_TIME_MS);
			} else {
				Log.w(TAG,"Attempt to vibrate with no bound service");
			}
		}
		
	}
	
	LiveViewVibrator( Context context )
	{
		mContext = context;
	}
	
	private void bind()
	{
		if( !mBound ) {
			// Bind to Local Service
	        Intent intent = new Intent(mContext, WearAlarmService.class);
	        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		}
	}
	
	/** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
    
    /**
     * Starts the periodic vibrate on LiveView
     */
	public void start()
	{
		bind();
		mVibrateTimer = new Timer();
		mVibrateTimer.schedule(new RecurringVibrate(mVibrateTimer), 0);
	}
	
	/**
	 * Stops periodic vibrate on liveview
	 */
	public void stop()
	{
		mVibrateTimer.cancel();
		mContext.unbindService(mConnection);
	}
}
