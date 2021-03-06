/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livelongmobile.wearalarm.android.deskclock;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.livelongmobile.wearalarm.R;

/**
 * Settings for the Intro Dialog.
 */
public class IntroDialogActivity extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener {

    private CheckBoxPreference mIntroPref;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.intro_dialog_prefs);
        
        mIntroPref = (CheckBoxPreference) findPreference("show");

//        mIntroPref.setChecked()
        
    }

    public boolean onPreferenceChange(Preference pref, Object newValue) {
        final ListPreference listPref = (ListPreference) pref;
        final int idx = listPref.findIndexOfValue((String) newValue);
        listPref.setSummary(listPref.getEntries()[idx]);
        return true;
    }

}
