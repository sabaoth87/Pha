package com.tnk.pha;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.text.method.DigitsKeyListener;

import com.tnk.R;

public class PHA_RemTaskPrefs extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_one);
        addPreferencesFromResource(R.xml.mm_prefs);

        EditTextPreference timeDefault = (EditTextPreference) findPreference(getString(R.string.pref_default_time_from_now_key));
        timeDefault.getEditText().setKeyListener(DigitsKeyListener.getInstance());
        EditTextPreference upDefault = (EditTextPreference) findPreference(getString(R.string.prefs_tilt_scroll_up_key));
        EditTextPreference downDefault = (EditTextPreference) findPreference(getString(R.string.prefs_tilt_scroll_down_key));
        upDefault.getEditText().setKeyListener(DigitsKeyListener.getInstance());
        downDefault.getEditText().setKeyListener(DigitsKeyListener.getInstance());
    }
}
