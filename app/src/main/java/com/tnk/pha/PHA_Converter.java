package com.tnk.pha;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tnk.R;

public class PHA_Converter extends Activity implements OnClickListener, OnItemSelectedListener {

	private String TAG = "Converter";
	private Spinner toSpinner;
	private Spinner fromSpinner;
	private Spinner catSpinner;
	private EditText fromET;
	private TextView toTV;
	private int category;
	private String fromCat;
	private String toCat;
	private ArrayAdapter<CharSequence> toAdapter;
	private ArrayAdapter<CharSequence> fromAdapter;
	private ArrayAdapter<CharSequence> speedAdapter;
	private ArrayAdapter<CharSequence> massAdapter;
	private ArrayAdapter<CharSequence> elecAdapter;
	private ArrayAdapter<CharSequence> pressureAdapter;
	private ArrayAdapter<CharSequence> heatAdapter;
	private ArrayAdapter<CharSequence> catAdapter;
	private Button convertBtn;
	private String fromValue = "0.0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pha_converter);
		
		toTV = (TextView)findViewById(R.id.convertToTV);
		
		//Initialize the button(s)
		convertBtn = (Button) findViewById(R.id.converterBtn);
		convertBtn.setOnClickListener(this);
		//Initialize ETs
		fromET = (EditText) findViewById(R.id.convertFromET);
		//Initialize the spinners
		toSpinner = (Spinner) findViewById(R.id.converterToSpinner);
		fromSpinner = (Spinner) findViewById(R.id.converterFromSpinner);
		catSpinner = (Spinner) findViewById(R.id.converterCatSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		massAdapter = ArrayAdapter.createFromResource(this, R.array.mass, android.R.layout.simple_spinner_item);
		speedAdapter = ArrayAdapter.createFromResource(this, R.array.speed, android.R.layout.simple_spinner_item);
		elecAdapter = ArrayAdapter.createFromResource(this, R.array.elec, android.R.layout.simple_spinner_item);
		pressureAdapter = ArrayAdapter.createFromResource(this, R.array.pressure, android.R.layout.simple_spinner_item);
		heatAdapter = ArrayAdapter.createFromResource(this, R.array.heat, android.R.layout.simple_spinner_item);
		//Default settings for adapters
		toAdapter = ArrayAdapter.createFromResource(this, R.array.mass, android.R.layout.simple_spinner_item);
		fromAdapter = ArrayAdapter.createFromResource(this, R.array.mass, android.R.layout.simple_spinner_item);
		catAdapter = ArrayAdapter.createFromResource(this, R.array.converterCats, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		catSpinner.setAdapter(catAdapter);
		catSpinner.setOnItemSelectedListener(this);
		toSpinner.setAdapter(toAdapter);
		toSpinner.setOnItemSelectedListener(this);
		fromSpinner.setAdapter(fromAdapter);
		fromSpinner.setOnItemSelectedListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pha__converter, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int pos,
			long id) {
		Log.v(TAG, arg0 + " " + view + " " + pos);
		switch (arg0.getId()){
		case (R.id.converterCatSpinner):
			switch (pos){
			case 0:
				category = 0;
				//mass
				toSpinner.setAdapter(massAdapter);
				fromSpinner.setAdapter(massAdapter);
				break;
			case 2:
				category = 2;
				//volume
				toSpinner.setAdapter(massAdapter);
				fromSpinner.setAdapter(massAdapter);
				break;
			case 5:
				category = 5;
				//speed
				toSpinner.setAdapter(speedAdapter);
				fromSpinner.setAdapter(speedAdapter);
				break;
			case 1:
				category = 1;
				//pressure
				toSpinner.setAdapter(pressureAdapter);
				fromSpinner.setAdapter(pressureAdapter);
				break;
			case 3:
				category = 3;
				//heat
				toSpinner.setAdapter(heatAdapter);
				fromSpinner.setAdapter(heatAdapter);
				break;
			case 6:
				category = 6;
				//elec
				toSpinner.setAdapter(elecAdapter);
				fromSpinner.setAdapter(elecAdapter);
				break;
			case 7:
				category = 7;
				break;
			}
		break;
		
		case (R.id.converterFromSpinner):
			Object fromCatwork = arg0.getAdapter().getItem(pos);
			fromCat = fromCatwork.toString();
			Log.v(TAG, fromCat);
			break;
		case (R.id.converterToSpinner):
			Object toCatwork = arg0.getAdapter().getItem(pos);
			toCat = toCatwork.toString();
			Log.v(TAG, toCat);
			break;
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		/*
		TODO 06 - onNothingSelected Auto-generated
		 */
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.converterBtn:
			fromValue = fromET.getText().toString();
			doConversion(fromValue);
			break;
		}
		
	}
	public void doConversion(String from){
		Log.v(TAG, "doing conversion...");
		switch (category)
			/*
			 * 0-MASS
			 * 1-PRESSURE
			 * 2-VOLUME
			 * 3-HEAT
			 * 4-LIGHT
			 * 5-SPEED
			 * 6-ELEC
			 * 
			 */
		{
		case 0:
			
			//mass
			//First If statement eliminates 1-1 conversions
			if (fromCat.equals(toCat)){
				//Log.v(TAG, "kg kg");
				double val = Double.valueOf(fromValue) * 1;
				toTV.setText(""+ val);
				//Log.v(TAG, toCat+" "+fromCat+" "+val);
			}
			//These if's can be added and manipulated throughout the converter growth
			if (fromCat.equals("GRAM") && toCat.equals("KG")){
				double val = Double.valueOf(fromValue) * 1000;
				toTV.setText(""+ val);
			}
			break;
		case 1:
			break;
		}
	}

}
