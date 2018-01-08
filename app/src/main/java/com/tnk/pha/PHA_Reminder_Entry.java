package com.tnk.pha;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.tnk.db.dbAdapter;

public class PHA_Reminder_Entry extends Activity implements OnClickListener {

	private String TAG = "PHA_RemEntry";
	private boolean beginEntrySet=false;
	private Bundle extras;
	private String newString;
	private String newDate;
	private int newMonth;
	private int newDay;
	private int newYear;
	private int newHour;
	private int newMinute;
	private static final int DATE_PICKER_DIALOG = 0;
	private static final int TIME_PICKER_DIALOG = 1;
	private Calendar mCalendar;
	private Button mDateButton;
	private Button mTimeButton;
	private Button confirmBttn;
	private EditText titleText;
	private EditText bodyText;
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String TIME_FORMAT = "kk:mm";
	static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
	private dbAdapter PHA_dbhelper;
	private Long RowId;
	private boolean incomingReminder = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pha_reminders_entry);
		
		if (savedInstanceState == null) {
		    extras = getIntent().getExtras();
		    if(extras == null) {
		        newString= null;
		        newDate= null;
		        newDay=0;
		        newMonth=0;
		        newYear=0;
		        newHour=0;
		        newMinute=0;
		    } else {
		    	Log.v(TAG, "Setting intent values");
		        newString= extras.getString("want");
		        newDate= extras.getString("date");
		        newDay=extras.getInt("day");
		        newMonth=extras.getInt("month");
		        newYear=extras.getInt("year");
		        newHour=extras.getInt("hour");
		        newMinute=extras.getInt("minute");
		        incomingReminder=true;
		        beginEntry(newString, newDate);
		    }
		} else {
			Log.v(TAG, "Setting default values");
		    newString= (String) savedInstanceState.getSerializable("want");
		    newDate= (String) savedInstanceState.getSerializable("date");
		    newMonth= (Integer) savedInstanceState.getSerializable("month");
		    newYear= (Integer) savedInstanceState.getSerializable("year");
		    newDay= (Integer) savedInstanceState.getSerializable("day");
		    newHour= (Integer) savedInstanceState.getSerializable("hour");
		    newMinute= (Integer) savedInstanceState.getSerializable("minute");
		}
		
		RowId = savedInstanceState != null ? savedInstanceState.getLong(dbAdapter.REM_ROWID): null;
		PHA_dbhelper = new dbAdapter(this);
		mDateButton = (Button) findViewById(R.id.remsDateBtn);
		mTimeButton = (Button) findViewById(R.id.remsTimeBtn);
		confirmBttn = (Button) findViewById(R.id.remsConfirmBtn);
		titleText = (EditText) findViewById(R.id.remsTitleET);
		bodyText = (EditText) findViewById(R.id.remsBodyET);
		mCalendar = Calendar.getInstance();
		mDateButton.setOnClickListener(this);
		mTimeButton.setOnClickListener(this);
		confirmBttn.setOnClickListener(this);
		if(getIntent() !=null){
			Bundle extras = getIntent().getExtras();
			int rowId = extras != null ? extras.getInt("RowId"):-1;
			//Do things with the RowID here
		}
		updateDateButtonText();
		updateTimeButtonText();
//		registerButtonListenersAndSetDefaultText();
	}

	private void registerButtonListenersAndSetDefaultText() {
//		mDateButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showDialog(DATE_PICKER_DIALOG);
//			}
//		});
//		
//		mTimeButton.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				showDialog(TIME_PICKER_DIALOG);
//			}
//		});
//		confirmBttn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				saveState();
//				setResult(RESULT_OK);
//				Toast.makeText(PHA_Reminder_Entry.this, 
//						getString(R.string.saveConfirm), Toast.LENGTH_SHORT).show();
//				finish();
//				
//			}
//		});
//		updateDateButtonText();
//		updateTimeButtonText();
	}
	
	@Override
	protected Dialog onCreateDialog(int id){
		switch(id){
		case DATE_PICKER_DIALOG:
			return showDatePicker();
		case TIME_PICKER_DIALOG:
			return showTimePicker();
		}
		return super.onCreateDialog(id);
	}
	
	private DatePickerDialog showDatePicker(){
		DatePickerDialog datePicker = new DatePickerDialog(PHA_Reminder_Entry.this, new DatePickerDialog.OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				mCalendar.set(Calendar.YEAR, year);
				mCalendar.set(Calendar.MONTH, monthOfYear);
				mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateDateButtonText();
				
			}
		}, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
		return datePicker;
	}
	
	private void updateDateButtonText(){
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		String dateForButton = dateFormat.format(mCalendar.getTime());
		mDateButton.setText(dateForButton);
	}
	
	private TimePickerDialog showTimePicker(){
		TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
				mCalendar.set(Calendar.MINUTE, minute);
				updateTimeButtonText();
			}
		}, mCalendar.get(Calendar.HOUR_OF_DAY), mCalendar.get(Calendar.MINUTE), true);
		return timePicker;
	}
	
	private void updateTimeButtonText(){
		SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
		String timeForButton = timeFormat.format(mCalendar.getTime());
		mTimeButton.setText(timeForButton);
	}
	
	private void saveState(){
		String title = titleText.getText().toString();
		String body = bodyText.getText().toString();
		
		SimpleDateFormat dateTimeFormat =new SimpleDateFormat(DATE_TIME_FORMAT);
		String reminderDateTime = dateTimeFormat.format(mCalendar.getTime());
		
		if(RowId == null){
			long id = PHA_dbhelper.createReminder(title, body, reminderDateTime);
			if(id>0){
				RowId = id;
			}
		}else {
			PHA_dbhelper.updateReminder(RowId, title, body, reminderDateTime);
		}
		
		new PHA_ReminderManager(this).setReminder(RowId, mCalendar);
	}
	
	private void setRowIdFromIntent(){
		if(RowId == null){
			Bundle extras = getIntent().getExtras();
			RowId = extras != null ? extras.getLong(dbAdapter.REM_ROWID):null;
		}
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		PHA_dbhelper.close();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		PHA_dbhelper.open();
		setRowIdFromIntent();
		populateFields();
	}
	
	private void populateFields(){
		if(RowId != null){
			Cursor reminder = PHA_dbhelper.getRem(RowId);
			startManagingCursor(reminder);
			titleText.setText(reminder.getString(
					reminder.getColumnIndexOrThrow(dbAdapter.REM_TITLE)));
			bodyText.setText(reminder.getString(
					reminder.getColumnIndexOrThrow(dbAdapter.REM_BODY)));
			SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
			Date date = null;
			try{
				String dateString = reminder.getString(reminder.getColumnIndexOrThrow(dbAdapter.REM_DATE_TIME));
				date = dateTimeFormat.parse(dateString);
				mCalendar.setTime(date);
			}catch (ParseException e){
				Log.e("ReminderEntry", e.getMessage(), e);
			}
		} 
		else if (incomingReminder) {
				titleText.setText(newString);
				mCalendar.add(Calendar.MINUTE, newMinute);
				mCalendar.add(Calendar.HOUR, newHour);
				mCalendar.add(Calendar.DAY_OF_MONTH, newDay);
				mCalendar.add(Calendar.MONTH, newMonth);
				mCalendar.add(Calendar.YEAR, newYear);
		} else {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
			String defaultTitleKey = getString(R.string.pref_task_title_key);
			String defaultTimeKey = getString(R.string.pref_default_time_from_now_key);
			String defaultTitle = prefs.getString(defaultTitleKey, "");
			String defaultTime = prefs.getString(defaultTimeKey, "");
			if("".equals(defaultTitle)==false)
				titleText.setText(defaultTitle);
			if("".equals(defaultTime)==false)
				mCalendar.add(Calendar.MINUTE, Integer.parseInt(defaultTime));
		}
		updateDateButtonText();
		updateTimeButtonText();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putLong(dbAdapter.REM_ROWID, RowId);
	}
	
	public void beginEntry(String memo, String date){
		mCalendar.set(0, newYear);
		mCalendar.set(1, newMonth);
		mCalendar.set(2, newDay);
		mCalendar.set(3, newHour);
		mCalendar.set(4, newMinute);
		bodyText.setText(newString);
		beginEntrySet=true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()){
		case (R.id.remsDateBtn):
			showDialog(DATE_PICKER_DIALOG);
			break;
		case (R.id.remsTimeBtn):
			showDialog(TIME_PICKER_DIALOG);
			break;
		case (R.id.remsConfirmBtn):
			saveState();
			setResult(RESULT_OK);
			Toast.makeText(PHA_Reminder_Entry.this, 
				getString(R.string.saveConfirm), Toast.LENGTH_SHORT).show();
			finish();
			break;
		}
		
	}
}
