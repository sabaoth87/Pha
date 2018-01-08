package com.tnk.pha;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by Tom on 2018-01-07.
 */

public class PHA_Location implements LocationListener {

    //The minimum distance to change updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 10 meters

    //The minimum distance to change updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1; // 1 minute

    private final static boolean forceNetwork = false;
    private final String TAG = "PHA_Location";
    private static PHA_Location instance = null;

    private LocationManager locationManager;
    public Location location;
    public double longitude;
    public double latitude;
    public boolean isGPSEnabled;
    public boolean isNetworkEnabled;
    public boolean locationServiceAvailable;

    /**
     * Singleton Implementation
     */
    public static PHA_Location getLocationManager(Context context) {
        if (instance == null) {
            instance = new PHA_Location(context);
        }
        return instance;
    }

    /**
     * Local Constructor
     */
    private PHA_Location( Context context) {
        initLocationService(context);
        Log.v(TAG, "PHA_Location created");
    }

    // Sets up location service after permissions is granted
    @TargetApi(23)
    private void initLocationService(Context context){
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        try {
            this.longitude = 0.0;
            this.latitude = 0.0;
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            //Get Status of GPS and Network
            this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (forceNetwork) isGPSEnabled = false;

            if(!isNetworkEnabled && !isGPSEnabled) {
                // Unable to find location
                this.locationServiceAvailable = false;
            }

            {
                this.locationServiceAvailable = true;

                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        //updateCoordinates();
                    }
                }

                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        //updateCoordinates();
                    }
                }
            }
        } catch (Exception exc) {
            Log.v(TAG, "Error creating location service: " + exc.getMessage());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //Use this to do tasks with the location object
        //@TODO
        //Look into what to do with this location object!
    }

    @Override
    public void onStatusChanged(String string, int i, Bundle bundle){
        //
    }

    @Override
    public void onProviderEnabled(String provider){}

    @Override
    public void onProviderDisabled(String provider) {}

}
