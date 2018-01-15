package com.tnk.pha;

import java.util.ArrayList;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

import com.tnk.R;

public class PHA_MM extends Activity implements OnClickListener, SensorEventListener, OnSharedPreferenceChangeListener {

    private static final int ACTION_PREF_CHANGE = 1;
    ListView reminderLV;
    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private static final String TAG = "PHA_MainScreen";
    private SensorManager mgr;
    private Sensor accel;
    private Sensor compass;
    private Sensor orient;
    private boolean ready = false;
    private float[] accelValues = new float[3];
    private float[] compassValues = new float[3];
    private float[] inR = new float[9];
    private float[] inclineMatrix = new float[9];
    private float[] orientationValues = new float[3];
    private float[] prefValues = new float[3];
    //private double mInclination;
    private int counter;
    //private int mRotation;
    private double scrollUpThresh;
    private double scrollDownThresh;
    //private Button button1;
    //private Button button2;
    //private Button button3;
    //private Button button4;
    //private Button button5;
    //private Button button6;

    private boolean scrollTiltEnabled;
    private SharedPreferences mmPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.pha_mm);
        mmPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String TSEnabledKey = getString(R.string.prefs_tilt_scroll_key);
        String defaultUpScrollKey = getString(R.string.prefs_tilt_scroll_up_key);
        String defaultDownScrollKey = getString(R.string.prefs_tilt_scroll_down_key);
        String defaultUpScroll = mmPrefs.getString(defaultUpScrollKey, "80");
        String defaultDownScroll = mmPrefs.getString(defaultDownScrollKey, "80");
        scrollTiltEnabled = mmPrefs.getBoolean(TSEnabledKey, true);

        mmPrefs.registerOnSharedPreferenceChangeListener(this);

        if ("80.0".equals(defaultUpScroll) == false) {
            scrollUpThresh = Double.valueOf(defaultUpScroll) * (-1.0);
        }
        if ("80.0".equals(defaultDownScroll) == false) {
            scrollDownThresh = Double.valueOf(defaultDownScroll);
        }
        Log.v(TAG, "Up thresh set to " +
                scrollUpThresh +
                "Down thresh set to " +
                scrollDownThresh +
                scrollTiltEnabled);
        /*
         * Initialize the buttons to ensure focus will take effect
         */
        //button1=(Button) findViewById(R.id.button1);
        //button2=(Button) findViewById(R.id.button2);
        //button3=(Button) findViewById(R.id.button3);
        //button4=(Button) findViewById(R.id.button4);
        //button5=(Button) findViewById(R.id.button5);
        //button6=(Button) findViewById(R.id.button6);
        /*
         * set the onClickListener up for the menu
         */
        ((Button) findViewById(R.id.btn_mm_00)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_01)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_02)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_03)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_04)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_05)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_06)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_07)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_08)).setOnClickListener(this);
        ((Button) findViewById(R.id.btn_mm_09)).setOnClickListener(this);
        /*
         * Initialize the scroller so the tilt will scroll the page
         */

        Button MMBtn1 = (Button) findViewById(R.id.btn_mm_00);
        Button MMBtn2 = (Button) findViewById(R.id.btn_mm_01);
        Button MMBtn3 = (Button) findViewById(R.id.btn_mm_02);
        Button MMBtn4 = (Button) findViewById(R.id.btn_mm_03);
        Button MMBtn5 = (Button) findViewById(R.id.btn_mm_04);
        Button MMBtn6 = (Button) findViewById(R.id.btn_mm_05);
        Button MMBtn7 = (Button) findViewById(R.id.btn_mm_06);
        Button MMBtn8 = (Button) findViewById(R.id.btn_mm_07);
        Button MMBtn9 = (Button) findViewById(R.id.btn_mm_08);
        Button MMBtn10 = (Button) findViewById(R.id.btn_mm_09);
        //MMET1 = (EditText)findViewById(R.id.mainScreenTV1);
        //MMET2 = (EditText)findViewById(R.id.mainScreenTV2);

        //scrollUptxt = (TextView)findViewById(R.id.scrollUpCheckTxt);
        //scrollDowntxt = (TextView)findViewById(R.id.scrollDownCheckTxt);
        mgr = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        accel = mgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compass = mgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        orient = mgr.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //if(apiLevel <8) {
        //mRotation = window.getDefaultDisplay().getOrientation();
        //}
        //else {
        //mRotation = window.getDefaultDisplay().getRotation();
        //}

        reminderLV = (ListView) findViewById(R.id.reminderListView);
        adapter = new ArrayAdapter<String>(this, R.layout.reminder_list_layout, listItems);
        registerForContextMenu(reminderLV);
        reminderLV.setAdapter(adapter);

        MMBtn1.setAlpha(0);

        Animation rotateIn = AnimationUtils.loadAnimation(this, R.anim.mm_btn_rotate_in);
        if (MMBtn1.hasFocus()) {

            MMBtn1.startAnimation(rotateIn);
        }
        MMBtn2.startAnimation(rotateIn);
        MMBtn3.startAnimation(rotateIn);
        MMBtn4.startAnimation(rotateIn);
        MMBtn5.startAnimation(rotateIn);
        MMBtn6.startAnimation(rotateIn);
        MMBtn7.startAnimation(rotateIn);
        MMBtn8.startAnimation(rotateIn);
        MMBtn9.startAnimation(rotateIn);
        MMBtn10.startAnimation(rotateIn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout_one, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_opt_1:
                Log.v(TAG, "hit opt1");
                return true;

            case R.id.menu_opt_2:
                Intent a = new Intent(this, PHA_Search.class);
                Log.v(TAG, "search option");
                startActivity(a);
                return true;

            case R.id.menu_opt_5:
                Intent b = new Intent(this, PHA_RemTaskPrefs.class);
                Log.v(TAG, "prefs option");
                startActivityForResult(b, ACTION_PREF_CHANGE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mm_00:
                Intent i = new Intent(this, PHA_Sudoku.class);
                Log.v(TAG, "Option1");
                startActivity(i);
                break;
            case R.id.btn_mm_01:
                Intent j = new Intent(this, PHA_Reminder_List.class);
                Log.v(TAG, "Option2");
                startActivity(j);
                break;
            case R.id.btn_mm_02:
                Intent k = new Intent(this, PHA_SensorTest.class);
                Log.v(TAG, "Option3");
                startActivity(k);
                break;
            case R.id.btn_mm_03:
                Intent l = new Intent(this, PHA_Calc.class);
                Log.v(TAG, "Option4");
                startActivity(l);
                break;
            case R.id.btn_mm_04:
                Intent h = new Intent(this, PHA_TiltTest.class);
                Log.v(TAG, "Option5");
                startActivity(h);
                break;
            case R.id.btn_mm_05:
                Intent a = new Intent(this, PHA_TiltScrollTest.class);
                Log.v(TAG, "Option6");
                startActivity(a);
                break;

            case R.id.btn_mm_06:
                Intent b = new Intent(this, PHA_Db.class);
                Log.v(TAG, "Option7");
                startActivity(b);
                break;
            case R.id.btn_mm_07:
                Intent c = new Intent(this, PHA_Voice.class);
                Log.v(TAG, "Launching Voice...");
                startActivity(c);
                break;
            case R.id.btn_mm_08:
                Intent d = new Intent(this, PHA_Converter.class);
                Log.v(TAG, "Launching the Converter...");
                startActivity(d);
                break;
            case R.id.btn_mm_09:
                Intent wbIntent = new Intent(this, Workbench.class);
                Log.v(TAG, "Launching the Workbench...");
                startActivity(wbIntent);
                break;
            // more butons go here later
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //ignore for now
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        // Need to get both accelerometer and compass
        // before we can determine our orientationValues
        if (scrollTiltEnabled) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i = 0; i < 3; i++) {
                        accelValues[i] = event.values[i];
                    }
                    if (compassValues[0] != 0)
                        ready = true;
                    break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i = 0; i < 3; i++) {
                        compassValues[i] = event.values[i];
                    }
                    if (accelValues[2] != 0)
                        ready = true;
                    break;
                case Sensor.TYPE_ORIENTATION:
                    for (int i = 0; i < 3; i++) {
                        orientationValues[i] = event.values[i];
                    }
                    break;
            }

            if (!ready)
                return;
            if (SensorManager.getRotationMatrix(inR, inclineMatrix, accelValues, compassValues)) {
                // got a good rotation matrix
                SensorManager.getOrientation(inR, prefValues);
                // mInclination = SensorManager.getInclination(inclineMatrix);
                // Display every 10th value
                if (counter++ % 20 == 0) {
                    doUpdate(null);
                    tiltScrollCheck();
                    counter = 1;
                }
            }
        }
    }

    public void doUpdate(View view) {
        float mAzimuth;
        if (!ready)
            return;
        mAzimuth = (float) Math.toDegrees(prefValues[0]);
        if (mAzimuth < 0) {
            mAzimuth += 360.0f;
        }
    }

    public void tiltScrollCheck() {
        /*
		 * Check to see if the phone is tilted past the thresholds to be
		 * considered "scrolling-enabled"
		 * These values are for a landscape activity, top->left
		 */

        //@FIXME TiltScrolling is NOT working!
        /*
		if (rollValue < -(R.string.prefs_tilt_scroll_up_key));
		{
			scrollingUp = true;
			scroller.pageScroll(View.FOCUS_UP);
			Log.v(TAG,"Scroll up");
		}if(rollValue > R.string.prefs_tilt_scroll_up_key){
			scrollingDown = true;
			scroller.pageScroll(View.FOCUS_DOWN);
			Log.v(TAG,"Scroll down");
		}
		else{
			scrollingUp = false;
			scrollingDown = false;
		}

		String defaultScrollUpKey = getString(R.string.prefs_tilt_scroll_up_key);
		String defaultScrollDownKey = getString(R.string.prefs_tilt_scroll_down_key);
		float defaultScrollUp = prefs.getFloat(defaultScrollUpKey, 0);
		if("".equals(defaultScrollUp)==false){
			MMET1.setText(defaultScrollUp);
				if (rollValue < -(Integer.valueOf(defaultScrollUp)));
					{
						scrollingUp = true;
						scroller.pageScroll(View.FOCUS_UP);
						Log.v(TAG,"Scroll up");
		}}
		if("".equals(defaultScrollDown)==false)
			MMET1.setText(defaultScrollDown);
				if (rollValue < -(Integer.valueOf(defaultScrollDown)));
				{
					scrollingDown = true;
					scroller.pageScroll(View.FOCUS_DOWN);
					Log.v(TAG,"Scroll Down");
				}
				*/
    }


    @Override
    protected void onResume() {
        mgr.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
        mgr.registerListener(this, compass, SensorManager.SENSOR_DELAY_GAME);
        mgr.registerListener(this, orient, SensorManager.SENSOR_DELAY_GAME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        mgr.unregisterListener(this, accel);
        mgr.unregisterListener(this, compass);
        mgr.unregisterListener(this, orient);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            Log.v(TAG, "result received OK");
            listItems.add(intent.getStringExtra("reminder_text"));
            adapter.notifyDataSetChanged();
        }
        if (resultCode == 87) {
            Log.v(TAG, "pref changes felt");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater lpinflater = getMenuInflater();
        lpinflater.inflate(R.menu.list_menu_item_longpress, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lp_menu_delete:
                deleteReminder(item);
                return true;

            case R.id.lp_menu_search:
                Intent a = new Intent(this, PHA_Search.class);
                Log.v(TAG, "search option");
                startActivity(a);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    void deleteReminder(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        listItems.remove(info.position);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        String defaultUpScrollKey;
        String defaultDownScrollKey;
        String defaultUpScroll;
        String defaultDownScroll;
        mmPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        defaultUpScrollKey = getString(R.string.prefs_tilt_scroll_up_key);
        defaultDownScrollKey = getString(R.string.prefs_tilt_scroll_down_key);
        defaultUpScroll = mmPrefs.getString(defaultUpScrollKey, "80");
        defaultDownScroll = mmPrefs.getString(defaultDownScrollKey, "80");

        Log.v(TAG, "Prefs Changed \n Up Thresh set to " +
                scrollUpThresh +
                "Down thresh set to " +
                scrollDownThresh + "\n Tilt? " +
                scrollTiltEnabled + defaultUpScrollKey + " " + defaultDownScrollKey
                + defaultUpScroll + " " + defaultDownScroll);

        scrollUpThresh = Double.valueOf(defaultUpScroll) * (-1.0);
        scrollDownThresh = Double.valueOf(defaultDownScroll);

        Log.v(TAG, "Up thresh set to " +
                scrollUpThresh +
                "Down thresh set to " +
                scrollDownThresh +
                scrollTiltEnabled);
    }

}
