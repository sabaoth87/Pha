package com.tnk.pha;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tnk.R;
import com.tnk.db.Contract_Reminder;
import com.tnk.db.DbHelper_Reminders;
import com.tnk.db.Item_Reminder;

public class PHA_Reminder_Entry extends FragmentActivity implements OnClickListener {

    private String TAG = "PHA_RemEntry";
    private boolean beginEntrySet = false;
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
    private DbHelper_Reminders PHA_dbhelper;
    private Long RowId;
    private boolean incomingReminder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pha_reminders_entry);

        RowId = savedInstanceState != null ? savedInstanceState.getLong(Contract_Reminder.ReminderEntry._ID) : null;
        PHA_dbhelper = new DbHelper_Reminders(this);
        mDateButton = findViewById(R.id.remsDateBtn);
        mTimeButton = findViewById(R.id.remsTimeBtn);
        confirmBttn = findViewById(R.id.remsConfirmBtn);
        titleText = findViewById(R.id.remsTitleET);
        bodyText = findViewById(R.id.remsBodyET);
        mCalendar = Calendar.getInstance();
        mDateButton.setOnClickListener(this);
        mTimeButton.setOnClickListener(this);
        confirmBttn.setOnClickListener(this);

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
                newDate = null;
                newDay = 0;
                newMonth = 0;
                newYear = 0;
                newHour = 0;
                newMinute = 0;
            } else {
                Log.v(TAG, "Setting intent values");
                //findEntryForEdit(extras.getString(Contract_Reminder.ReminderEntry._ID));
                //<editor-fold desc="old extras">
                //newString = extras.getString("want");
                //newDate = extras.getString("date");
                //newDay = extras.getInt("day");
                //newMonth = extras.getInt("month");
                //newYear = extras.getInt("year");
                //newHour = extras.getInt("hour");
                //newMinute = extras.getInt("minute");
                //</editor-fold>
                incomingReminder = true;
                Cursor cReminder = findEntryForEdit(extras.getString(Contract_Reminder.ReminderEntry._ID));
                if (cReminder != null) {
                    Log.v(TAG, "<Success> Db entry location valid! /n Returning information");
                    beginEntry(cReminder);
                }
                Log.v(TAG, "<Error> Reminder Cursor was invalid!");
            }
        } else {
            Log.v(TAG, "Setting default values");
            newString = (String) savedInstanceState.getSerializable("want");
            newDate = (String) savedInstanceState.getSerializable("date");
            newMonth = (Integer) savedInstanceState.getSerializable("month");
            newYear = (Integer) savedInstanceState.getSerializable("year");
            newDay = (Integer) savedInstanceState.getSerializable("day");
            newHour = (Integer) savedInstanceState.getSerializable("hour");
            newMinute = (Integer) savedInstanceState.getSerializable("minute");
        }

        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            int rowId = extras != null ? extras.getInt("RowId") : -1;
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_DIALOG:
                return showDatePicker();
            case TIME_PICKER_DIALOG:
                return showTimePicker();
        }
        return super.onCreateDialog(id);
    }

    private DatePickerDialog showDatePicker() {
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

    private void updateDateButtonText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        String dateForButton = dateFormat.format(mCalendar.getTime());
        mDateButton.setText(dateForButton);
    }

    private TimePickerDialog showTimePicker() {
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

    private void updateTimeButtonText() {
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_FORMAT);
        String timeForButton = timeFormat.format(mCalendar.getTime());
        mTimeButton.setText(timeForButton);
    }

    private void saveState() {
        Log.v(TAG, ":: Saving State");
        String title = titleText.getText().toString();
        String body = bodyText.getText().toString();

        Context context = getApplicationContext();

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        String reminderDateTime = dateTimeFormat.format(mCalendar.getTime());

        Item_Reminder newRem = new Item_Reminder();
        newRem.setRem_title(title);
        newRem.setRem_body(body);
        newRem.setRem_date(reminderDateTime);

        if (RowId == null) {
            long id = PHA_dbhelper.addReminderHandler(newRem);
            if (id > 0) {
                RowId = id;
            }
        } else {
            Toast.makeText(context, "Tried to Update", Toast.LENGTH_LONG);
            // PHA_dbhelper.updateReminder(RowId, title, body, reminderDateTime);
        }

        new PHA_ReminderManager(this).setReminder(RowId, mCalendar);
    }

    private void setRowIdFromIntent() {
        if (RowId == null) {
            Bundle extras = getIntent().getExtras();
            /*
            @TRY 02 - Bundle _ROWIDs, if neccesary
             */
            //RowId = extras != null ? extras.getLong(dbAdapter.REM_ROWID) : null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        PHA_dbhelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRowIdFromIntent();
        populateFields();
    }

    private void populateFields() {
        if (RowId != null) {
            /*
            @FIXME
             */
            //Cursor reminder = PHA_dbhelper(RowId);
            //startManagingCursor(reminder);
            //titleText.setText(reminder.getString(
            //        reminder.getColumnIndexOrThrow(dbAdapter.REM_TITLE)));
            //bodyText.setText(reminder.getString(
            //        reminder.getColumnIndexOrThrow(dbAdapter.REM_BODY)));
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            Date date = null;
            /*
            try {
                String dateString = reminder.getString(reminder.getColumnIndexOrThrow(dbAdapter.REM_DATE_TIME));
                date = dateTimeFormat.parse(dateString);
                mCalendar.setTime(date);
            }
            catch () {
              Log.e("ReminderEntry", e.getMessage(), e);
            }
            */
        } else if (incomingReminder) {
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
            if ("".equals(defaultTitle) == false)
                titleText.setText(defaultTitle);
            if ("".equals(defaultTime) == false)
                mCalendar.add(Calendar.MINUTE, Integer.parseInt(defaultTime));
        }
        updateDateButtonText();
        updateTimeButtonText();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*@FIXME
        //outState.putLong(dbAdapter.REM_ROWID, RowId);
         */
    }

    public void beginEntry(Cursor reminder) {
        //load the reminder information into the UI here
        Log.v(TAG, "Trying to load Editable reminder...");
        if (reminder==null) {
            Log.v(TAG, "Cursor with the Editable reminder was found to be null!");
            titleText.setText(" @null! ");
            bodyText.setText(" This entry has been found to be null ");
            beginEntrySet = false;
        }
        else {
            Log.v(TAG, "Cursor is valid! Populating UI");
            //int entryIdId = reminder.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry._ID);

            while(reminder.moveToNext()) {
                int index;

                index = reminder.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.COLUMN_TITLE);
                String entryTitle = reminder.getString(index);

                index = reminder.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.COLUMN_BODY);
                String entryBody = reminder.getString(index);

                index = reminder.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.COLUMN_DATE);
                String entryDateTime = reminder.getString(index);
                String entryDateFormatted = formatDateTime(entryDateTime);

                index = reminder.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry._ID);
                long id = reminder.getLong(index);

                Log.v(TAG, "Editable Reminder entry ID: " + id);
                titleText.setText(entryTitle);
                bodyText.setText(entryBody);
                beginEntrySet = true;
            }

            beginEntrySet = false;
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case (R.id.remsDateBtn):
                //showDialog(DATE_PICKER_DIALOG);
                DialogFragment dateFragment = new PHA_Util_DatePicker();
                dateFragment.show(getFragmentManager(), "datePicker");
                break;
            case (R.id.remsTimeBtn):
                //showDialog(TIME_PICKER_DIALOG);
                DialogFragment timeFragment = new PHA_Util_TimePicker();
                timeFragment.show(getFragmentManager(), "timePicker");
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

    public Cursor findEntryForEdit(String id){
        Context context = getApplicationContext();
        DbHelper_Reminders dbHelper = new DbHelper_Reminders(context);

        Cursor forEdit = dbHelper.findById(id);
        //forEdit.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.)
        return forEdit;
    }


    // @TRY Date/Time Format
    // This is probably not required here
    // I should incorporate this into the entry values themselves
    // So just   <title>, <body>, <timeDate> are the variables for the object
    // format/convert using Calendar or android.text.format.DateUtils.formatDateTime
    public String formatDateTime(String timeToFormat) {

        Context context = getApplicationContext();

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (timeToFormat !=null){
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e){
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }

}
