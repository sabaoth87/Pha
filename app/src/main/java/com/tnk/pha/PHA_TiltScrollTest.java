package com.tnk.pha;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.os.Build;

public class PHA_TiltScrollTest extends Activity implements SensorEventListener {
	
	private static final String TAG = "PHA_TiltScroll";
	private SensorManager mgr;
	private Sensor accel;
	private Sensor compass;
	private Sensor orient;
	private TextView scrollUptxt;
	private TextView scrollDowntxt;
	private boolean ready = false;
	private float[] accelValues = new float[3];
	private float[] compassValues = new float[3];
	private float[] inR = new float[9];
	private float[] inclineMatrix = new float[9];
	private float[] orientationValues = new float[3];
	private float[] prefValues = new float[3];
	private float mAzimuth;
	private double rollValue;
	private int counter;
	private int scrollCounter;
	private boolean scrollingUp = false;
	private boolean scrollingDown = false;
	private int scrollUpThresh = 15;
	private int scrollDownThresh = 35;
	private int scrollSpeedSlow = 1;
	private int scrollSpeedMed = 2;
	private int scrollSpeedFast = 3;
	private int scrollSpeedDef = 0;
	private ScrollView scroller;
	private int scrollByInt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pha_tilt_scroll_test);
		
		scroller = (ScrollView) findViewById(R.id.tiltScreenScrollView);
		
		scrollUptxt = (TextView)findViewById(R.id.scrollUpCheckTxt);
		scrollDowntxt = (TextView)findViewById(R.id.scrollDownCheckTxt);
		mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		accel = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		compass = mgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		orient = mgr.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_layout_one, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onResume() {
		mgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
		mgr.registerListener(this, compass, SensorManager.SENSOR_DELAY_GAME);
		mgr.registerListener(this, orient,SensorManager.SENSOR_DELAY_GAME);
	    super.onResume();
	    }
	    
	@Override
	protected void onPause() {
		mgr.unregisterListener(this, accel);
	    mgr.unregisterListener(this, compass);
	    mgr.unregisterListener(this, orient);
	    super.onPause();
	    }
	
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    // ignore
	    }
	
	public void onSensorChanged(SensorEvent event) {
	    // Need to get both accelerometer and compass
	    // before we can determine our orientationValues
	    switch(event.sensor.getType()) {
	    	case Sensor.TYPE_ACCELEROMETER:
	    		for(int i=0; i<3; i++) {
	    			accelValues[i] = event.values[i];
	    		}
	    		if(compassValues[0] != 0)
	    			ready = true;
	    		break;
	    	case Sensor.TYPE_MAGNETIC_FIELD:
	    		for(int i=0; i<3; i++) {
	    			compassValues[i] = event.values[i];
	    		}
	    		if(accelValues[2] != 0)
	    			ready = true;
	    		break;
	    	case Sensor.TYPE_ORIENTATION:
	    		for(int i=0; i<3; i++) {
	    			orientationValues[i] = event.values[i];
	    		}
	    		break;
	    }

	    if(!ready)
	        return;
	    if(SensorManager.getRotationMatrix(inR, inclineMatrix, accelValues, compassValues)) {
	        // got a good rotation matrix
	        SensorManager.getOrientation(inR, prefValues);
	        // Display every nth value
	        if(counter++ % 25 == 0) {
	        doUpdate(null);
	        counter = 1;
	        }
	        //if(scrollCounter++ % 45 == 0) {
	        //	tiltScrollCheck2();
		    //    scrollCounter = 1;
		    //    }
	        //}
	        if(scrollCounter++ % 25 == 0){
	        	tiltScrollCheck2();
	        	try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	scrollCounter = 1;
	        }
	    }
	    }
	public void doUpdate(View view) {
		if(!ready)
			return;
		mAzimuth = (float) Math.toDegrees(prefValues[0]);
		if(mAzimuth < 0) {
			mAzimuth += 360.0f;
	    }
	    rollValue = orientationValues[2];
	    }
	
	public void tiltScrollCheck(){
		/*
		 * Check to see if the phone is tilted past the thresholds to be
		 * considered "scrolling-enabled"
		 * These values are for a landscape activity, hard buttons on the right
		 */
		
		if (rollValue < -scrollUpThresh && rollValue > ((-scrollUpThresh)-10))
		{
			scrollingUp = true;
			scrollUptxt.setText("^^ Scrolling ^^");
			scroller.pageScroll(View.FOCUS_UP);
			scrollByInt = (-scrollSpeedSlow);
			Log.v(TAG, "Slow Up");
		}
		else if(rollValue > scrollDownThresh && rollValue < (scrollDownThresh+10)){
			scrollingDown = true;
			scrollDowntxt.setText("**Scrolling**");
			scroller.pageScroll(View.FOCUS_DOWN);
			scrollByInt = scrollSpeedSlow;
			Log.v(TAG, "Slow Down");
		}
		
		if (rollValue < ((-scrollUpThresh)-10) && rollValue > ((-scrollUpThresh)-30))
		{
			scrollingUp = true;
			scrollUptxt.setText("^^ Scrolling ^^");
			scroller.pageScroll(View.FOCUS_UP);
			scrollByInt = (-scrollSpeedMed);
			Log.v(TAG, "Med Up");
		}
		else if(rollValue > (scrollDownThresh+10) && rollValue < (scrollDownThresh+30)){
			scrollingDown = true;
			scrollDowntxt.setText("**Scrolling**");
			scroller.pageScroll(View.FOCUS_DOWN);
			scrollByInt = scrollSpeedMed;
			Log.v(TAG, "Med Down");
		}
		
		if (rollValue < ((-scrollUpThresh)-31) && rollValue > ((-scrollUpThresh)-50))
		{
			scrollingUp = true;
			scrollUptxt.setText("^^ Scrolling ^^");
			scroller.pageScroll(View.FOCUS_UP);
			scrollByInt = (-scrollSpeedFast);
			Log.v(TAG, "Fast Up");
		}
		else if(rollValue > (scrollDownThresh+31) && rollValue < (scrollDownThresh+50)){
			scrollingDown = true;
			scrollDowntxt.setText("**Scrolling**");
			scroller.pageScroll(View.FOCUS_DOWN);
			scrollByInt = scrollSpeedFast;
			Log.v(TAG, "Fast Down");
		}
		
		
		else{
			scrollUptxt.setText(Double.toString(rollValue));
			scrollDowntxt.setText(Double.toString(rollValue));
			scrollingUp = false;
			scrollingDown = false;
			scrollByInt = scrollSpeedDef;
			Log.v(TAG, "Default");
		}
		scroller.scrollBy(0, scrollByInt);
	}
	
	public void tiltScrollCheck2(){
		if (rollValue < -scrollUpThresh && rollValue > ((-scrollUpThresh)-10))
		{
			scrollingUp = true;
			scrollUptxt.setText("^^ Scrolling ^^");
			scroller.pageScroll(View.FOCUS_UP);
			scrollByInt = (-scrollSpeedSlow);
			Log.v(TAG, "Slow Up");
		}
		else if(rollValue > scrollDownThresh && rollValue < (scrollDownThresh+10)){
			scrollingDown = true;
			scrollDowntxt.setText("**Scrolling**");
			scroller.pageScroll(View.FOCUS_DOWN);
			scrollByInt = scrollSpeedSlow;
			Log.v(TAG, "Slow Down");
		}
		
		if (rollValue < ((-scrollUpThresh)-10) && rollValue > ((-scrollUpThresh)-30))
		{
			scrollingUp = true;
			scrollUptxt.setText("^^ Scrolling ^^");
			scroller.pageScroll(View.FOCUS_UP);
			scrollByInt = (-scrollSpeedMed);
			Log.v(TAG, "Med Up");
		}
		else if(rollValue > (scrollDownThresh+10) && rollValue < (scrollDownThresh+30)){
			scrollingDown = true;
			scrollDowntxt.setText("**Scrolling**");
			scroller.pageScroll(View.FOCUS_DOWN);
			scrollByInt = scrollSpeedMed;
			Log.v(TAG, "Med Down");
		}
		
		if (rollValue < ((-scrollUpThresh)-31) && rollValue > ((-scrollUpThresh)-50))
		{
			scrollingUp = true;
			scrollUptxt.setText("^^ Scrolling ^^");
			scroller.pageScroll(View.FOCUS_UP);
			scrollByInt = (-scrollSpeedFast);
			Log.v(TAG, "Fast Up");
		}
		else if(rollValue > (scrollDownThresh+31) && rollValue < (scrollDownThresh+50)){
			scrollingDown = true;
			scrollDowntxt.setText("**Scrolling**");
			scroller.pageScroll(View.FOCUS_DOWN);
			scrollByInt = scrollSpeedFast;
			Log.v(TAG, "Fast Down");
		}
		
		
		else{
			scrollUptxt.setText(Double.toString(rollValue));
			scrollDowntxt.setText(Double.toString(rollValue));
			scrollingUp = false;
			scrollingDown = false;
			scrollByInt = scrollSpeedDef;
			//Log.v(TAG, "Default");
		}
		if (scrollingUp){
		scroller.scrollBy(0, (scroller.getScrollY()+(scrollByInt)));}
		if (scrollingDown){
			scroller.scrollBy(0, (scroller.getScrollY()+(scrollByInt)));}
	}
}



