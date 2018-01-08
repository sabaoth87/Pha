package com.tnk.pha;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class PHA_SensorTest extends Activity {

	
	SensorManager PHASM;
    LocationManager PHALM;
	
	float[] gData = new float[3];           // Gravity or accelerometer
    float[] mData = new float[3];           // Magnetometer
    float[] orientation = new float[3];
    float[] Rmat = new float[9];
    float[] R2 = new float[9];
    float[] Imat = new float[9];
    boolean haveGrav = false;
    boolean haveAccel = false;
    boolean haveMag = false;
    private String TAG = "sensorTesting";
    private SensorEventListener sensorListener;
    private LocationListener locationListener;
    private TextView gyro;
    private TextView locationTV;
    private TextView locationTV1;
    private TextView locInfo;
    private Geocoder PHAgeoCoder;
    private StringBuilder locationSB;
    private String[] geo1;
    private ArrayList<String> geo2;
    private int addIdx;
    private int addressIdx;
    private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pha_sensor_test);
		context = getApplicationContext();
		Log.v(TAG, "onCreate");
		PHAgeoCoder = new Geocoder(this);
		gyro = (TextView) findViewById(R.id.gyro);
		locInfo = (TextView) findViewById(R.id.locationGeocode);
		locationTV = (TextView) findViewById(R.id.locationTV);
		locationTV1 = (TextView) findViewById(R.id.locationTV1);
		
		locationSB = new StringBuilder();
		
        // Get the sensor manager from system services
    	PHASM =
          (SensorManager)getSystemService(Context.SENSOR_SERVICE);
    	PHALM =
    	  (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    	sensorListener = new PHA_SensorEventListener();
    	locationListener = new PHA_LocationEventListener();
    	
    	geo1 = new String[10];
    	geo2 = new ArrayList<String>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pha__sensor_test, menu);
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
	public void onResume() {
        super.onResume();
        // Register our listeners
        Sensor asensor = PHASM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor msensor = PHASM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        PHASM.registerListener(sensorListener, msensor, SensorManager.SENSOR_DELAY_NORMAL);
        PHASM.registerListener(sensorListener, asensor, SensorManager.SENSOR_DELAY_NORMAL);
        //PHASM.registerListener(this, gsensor, SensorManager.SENSOR_DELAY_GAME);


		//@FIXME Location is no longer working!!!!
		//Time to put the sextant to use and plot those coords!

        //PHALM.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);

		PHA_Location locationManager = PHA_Location.getLocationManager(context);

    }
	
	@Override
	protected void onPause() {
		super.onPause();
		//PHASM.unregisterListener(this);
		PHASM.unregisterListener(sensorListener);
		PHALM.removeUpdates(locationListener);
	}
	
	class PHA_SensorEventListener implements SensorEventListener {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			if (accuracy <= 1) {
		        Toast.makeText(PHA_SensorTest.this, "Please shake the " +
		          "device in a figure eight pattern to " +
		          "improve sensor accuracy!", Toast.LENGTH_LONG)
		          .show();
		      }
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			int sensorEventType = event.sensor.getType();
			if (sensorEventType == Sensor.TYPE_ACCELEROMETER){
				System.arraycopy(event.values, 0, gData, 0, 3);
			} else if (sensorEventType == Sensor.TYPE_MAGNETIC_FIELD){
				System.arraycopy(event.values, 0, mData, 0, 3);
			}else{
				return;
			}
			if (SensorManager.getRotationMatrix(R2, null, gData, mData)){
				SensorManager.getOrientation(R2, orientation);
//				gyro.setText
//				("Yaw:  "+ orientation[0] + "\n"
//				+ "Pitch: "+ orientation[1] + "\n"+
//					"Roll:  "+ orientation[2]);
				gyro.setText(String.format("" +
				 		"Yaw: %7.3f \n " +
				 		"Pitch: %7.3f \n" +
				 		"Roll: %7.3f \n",
						 orientation[0], 
						 orientation[1],
						 orientation[2]
						 ));
			}
		}
		
	}
	
	class PHA_LocationEventListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			addressIdx = 0;
			Log.v(TAG, "location changed");
			 locationTV.setText
			 		(  "Latitude: " + location.getLatitude() + "\n"
				     + "Longitude: " + location.getLongitude() + "\n"
					 + "Elevation: " + location.getAltitude() + "\n");
			
//			 locationTV.setText(String.format("" +
//			 		"Latitude: %7.3f \n " +
//			 		"Longitude: %7.3f \n" +
//			 		"Elevation: %7.3f \n",
//					 location.getLatitude(), 
//					 location.getLongitude(),
//					 location.getAltitude()
//					 ));
			 
			 locationTV1.setText
		 		("Provider: " + location.getProvider()+ "\n"
				 + "Bearing: " + location.getBearing() + "\n"
				 + "Speed: " + location.getSpeed() + "\n");
			 
//			 locationTV1.setText(String.format("" +
//				 		"Provider: %s \n " +
//				 		"Bearing: %7.3f \n" +
//				 		"Speed: %7.3f \n",
//						 location.getProvider(), 
//						 location.getSpeed(),
//						 location.getAltitude()
//						 ));
			 
			 if (Geocoder.isPresent()) {
				 try {
					 List<Address> addresses = PHAgeoCoder.getFromLocation(
							 location.getLatitude(), location.getLongitude(), 3);
					 if (addresses != null) {
						 for (Address namedLoc : addresses) {
							 String placeName = namedLoc.getLocality();
							 String featureName = namedLoc.getFeatureName();
							 String country = namedLoc.getCountryName();
							 String road = namedLoc.getThoroughfare();
							 String locality = namedLoc.getLocality();
							locationSB.append(String.format("[%s][%s][%s][%s][%s]\n",placeName, featureName, road, country, locality));
							geo1[addressIdx] = "["+placeName+"] ["+featureName+"] ["+ road+"] ["+country+"] [" + locality +"]";
							// geoLoc1 = String.format("[%s][%s][%s][%s]\n",placeName, featureName, road, country);
							 addIdx = namedLoc.getMaxAddressLineIndex();
							 for (int idx = 0; idx <= addIdx; idx++) {
								 String addLine = namedLoc.getAddressLine(idx);
								 locationSB.append("     Line "+idx+":"+ addLine+"\n");
								 geo2.add(addressIdx, "["+idx+ "]:" + "[" +addLine+"]");
								 addressIdx++;
							 }
							  
						 }
					 }
					 /*
					  * This works, the stringbuilder one just looks better
					  */
//					 locInfo.setText(geo1[0] + "\n" +
//							 geo2.get(0) +"\n"+
//							 geo2.get(1) +"\n"+
//							 geo2.get(2) +"\n"+
//							 geo1[1] + "\n" +
//							 geo2.get(3) +"\n"+
//							 geo2.get(4) +"\n"+
//							 geo2.get(5) +"\n"+
//							 geo1[2] + "\n" +
//							 geo2.get(6) +"\n"+
//							 geo2.get(7) +"\n"
							 //geo2[3] +"\n"+ 
							// geo2[4] +"\n"+
							 //geo2[5] +"\n"+
							// geo2[6] +"\n"+
							 //addIdx
							 //);
					 locInfo.setText(locationSB.toString());
				 } catch (IOException e) {
					 Log.v(TAG, "Failed to get address");
				 }
				 locationSB.delete(0,locationSB.length());
		 } else {
			Toast.makeText(PHA_SensorTest.this, "No geocoding available",
				 Toast.LENGTH_LONG).show();
				 }
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}

