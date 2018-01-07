package com.tnk.pha;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;

public class PHA_SudokuSettings extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);		    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.xml.settings, menu);
		return true;
	}
	
}
