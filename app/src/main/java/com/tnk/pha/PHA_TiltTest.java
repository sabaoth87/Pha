package com.tnk.pha;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.content.Intent;

public class PHA_TiltTest extends Activity implements SensorEventListener {
	
	private static final String TAG = "VirtualJax";
	private SensorManager mgr;
	private Sensor accel;
	private Sensor compass;
	private Sensor orient;
	private TextView preferred;
	private TextView orientation;
	private boolean ready = false;
	private float[] accelValues = new float[3];
	private float[] compassValues = new float[3];
	private float[] inR = new float[9];
	private float[] inclineMatrix = new float[9];
	private float[] orientationValues = new float[3];
	private float[] prefValues = new float[3];
	private float mAzimuth;
	private int counter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		setContentView(R.layout.pha_tilt_test);
		preferred = (TextView)findViewById(R.id.preferred);
		orientation = (TextView)findViewById(R.id.orientation);
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
	        if(SensorManager.getRotationMatrix(
	        inR, inclineMatrix, accelValues, compassValues)) {
	        // got a good rotation matrix
	        SensorManager.getOrientation(inR, prefValues);
	        // Display every 10th value
	        if(counter++ % 10 == 0) {
	        doUpdate(null);
	        counter = 1;
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
	    String msg = String.format(
	    "Preferred:\nazimuth (Z): %7.3f \npitch (X): %7.3f\nroll (Y): %7.3f",
	    mAzimuth, Math.toDegrees(prefValues[1]),
	    Math.toDegrees(prefValues[2]));
	    preferred.setText(msg);
	    msg = String.format(
	    "Orientation Sensor:\nazimuth (Z): %7.3f\npitch (X): %7.3f\nroll (Y): %7.3f",
	    orientationValues[0],
	    orientationValues[1],
	    orientationValues[2]);
	    orientation.setText(msg);
	    preferred.invalidate();
	    orientation.invalidate();
	    }
	public void doShow(View view) {
	    // google.streetview:cbll=30.32454,-81.6584&cbp=1,yaw,,pitch,1.0
	    // yaw = degrees clockwise from North
	    // For yaw we can use either mAzimuth or orientationValues[0].
	    //
	    // pitch = degrees up or down. -90 is looking straight up,
	    // +90 is looking straight down
	    // except that pitch doesn't work properly
	    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(
	    		"google.streetview:cbll=30.32454,-81.6584&cbp=1," +Math.round(orientationValues[0]) + ",,0,1.0"));
	    startActivity(intent);
	    return;
	    }
}



