package com.tnk.pha;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.Toast;

public class PHA_SearchDisplay extends Activity {

	public String searchIntent = "searchIntent";
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pha_search_display);
		
		StringBuilder entryBuilder = new StringBuilder("");
		
		Intent intent = getIntent();
		
		entryBuilder.append(intent.getStringExtra(searchIntent));
		webView = (WebView) findViewById(R.id.PHAY_WebView);
		
		webView.loadUrl("http://www.google.com/search?q="+entryBuilder.toString());
		Toast.makeText(getApplication(), "Done", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_layout_one, menu);
		return true;
	}

}

