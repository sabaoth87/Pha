package com.tnk.pha;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tnk.R;
import com.tnk.db.Contract_Issue;
import com.tnk.db.Contract_Reminder;
import com.tnk.db.DbHelper_Issues;
import com.tnk.db.DbHelper_Reminders;
import com.tnk.db.Item_Issue;
import com.tnk.db.Item_Reminder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class PHA_Issue_Entry extends FragmentActivity implements OnClickListener {

    private String TAG = "PHA_Issue Entry";
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
    private ImageButton ib_edit;
    private Button btn_add;
    private TextView tv_datetime;
    private TextView tv_id;
    private EditText et_body;
    private EditText et_tags;
    private EditText et_title;
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "kk:mm";
    static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
    private DbHelper_Issues PHA_dbhelper;
    private Long RowId;
    private boolean incomingIssue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pha_issues_entry);

        RowId = savedInstanceState != null ? savedInstanceState.getLong(Contract_Reminder.ReminderEntry._ID) : null;
        PHA_dbhelper = new DbHelper_Issues(this);

        et_title = findViewById(R.id.et_issue_title);
        et_body = findViewById(R.id.et_issue_body);
        et_tags = findViewById(R.id.et_issue_tags);
        btn_add = findViewById(R.id.btn_issue_add);
        ib_edit = findViewById(R.id.ib_issue_edit);
        tv_datetime = findViewById(R.id.tv_issue_datetime);
        tv_id = findViewById(R.id.tv_issue_id);

        mCalendar = Calendar.getInstance();
        btn_add.setOnClickListener(this);
        ib_edit.setOnClickListener(this);

        tv_datetime.setText(mCalendar.get(Calendar.DAY_OF_WEEK) + " " +
        mCalendar.get(Calendar.HOUR_OF_DAY ) + " " +
        mCalendar.get(Calendar.MINUTE) + ";");

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
                incomingIssue = true;
                Cursor cIssue = findEntryForEdit(extras.getString(Contract_Issue.IssueEntry._ID));
                if (cIssue != null) {
                    Log.v(TAG, "<Success> Db entry location valid! /n Returning information");
                    beginEntry(cIssue);
                }
                Log.v(TAG, "<Error> Issue Cursor was invalid!");
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


    private void saveState() {
        Log.v(TAG, ":: Saving State");
        String title = et_title.getText().toString();
        String body = et_body.getText().toString();
        String tags = et_tags.getText().toString();

        Context context = getApplicationContext();

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        String reminderDateTime = dateTimeFormat.format(mCalendar.getTime());

        Item_Issue newIssue = new Item_Issue();
        newIssue.setIssueTitle(title);
        newIssue.setIssueBody(body);
        newIssue.setIssueTags(tags);
        newIssue.setIssueDatetime(reminderDateTime);
        newIssue.setIssueAssignee("Sabaoth87");
        newIssue.setIssueOwner("Sabaoth87");
        newIssue.setIssueMilestone("null milestone");
        newIssue.setIssueProgress("null progress");
        newIssue.setIssueProject("null project");
        newIssue.setIssueStatus("null status");
        newIssue.setIssueTicket("null ticket");
        /*
        @TODO - Issue Entry saveState
        Need to finish populating the appropriate fields in the Issue
         */

        if (RowId == null) {
            long id = PHA_dbhelper.addIssue(newIssue);
            if (id > 0) {
                RowId = id;
            }
        } else {
            Toast.makeText(context, "Tried to Update", Toast.LENGTH_LONG).show();
            // PHA_dbhelper.updateReminder(RowId, title, body, reminderDateTime);
        }

        //old call to make a Reminder Event
        //new PHA_ReminderManager(this).setReminder(RowId, mCalendar);
    }

    private void setRowIdFromIntent() {
        if (RowId == null) {
            Bundle extras = getIntent().getExtras();
            /*
            @TRY_ME 02 - Bundle _IDs, if necessary
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
            @FIX_ME
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
        } else if (incomingIssue) {
            et_title.setText(newString);
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
                et_title.setText(defaultTitle);
            if ("".equals(defaultTime) == false)
                mCalendar.add(Calendar.MINUTE, Integer.parseInt(defaultTime));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        /*
        @FIX_ME 03 - onSaveInstance

        //outState.putLong(dbAdapter.REM_ROWID, RowId);
         */
    }

    public void beginEntry(Cursor issue) {
        //load the reminder information into the UI here
        if (PHA_Util_Vars.mode_debug){Log.v(TAG,"Trying to load Editable reminder...");}
        if (issue==null) {
            if (PHA_Util_Vars.mode_debug){Log.v(TAG,"Cursor with the Editable reminder was found to be null!");}
            et_title.setText(R.string.et_entry_null);
            et_body.setText(R.string.et_entry_null_body);
            beginEntrySet = false;
        }
        else {
            if (PHA_Util_Vars.mode_debug){Log.v(TAG,"Cursor is valid! Populating UI");}
            //int entryIdId = reminder.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry._ID);

            while(issue.moveToNext()) {
                int index;

                index = issue.getColumnIndexOrThrow(Contract_Issue.IssueEntry.COLUMN_TITLE);
                String entryTitle = issue.getString(index);

                index = issue.getColumnIndexOrThrow(Contract_Issue.IssueEntry.COLUMN_BODY);
                String entryBody = issue.getString(index);

                index = issue.getColumnIndexOrThrow(Contract_Issue.IssueEntry.COLUMN_DATETIME);
                String entryDateTime = issue.getString(index);

                index = issue.getColumnIndexOrThrow(Contract_Issue.IssueEntry.COLUMN_TAGS);
                String entryTags = issue.getString(index);

                String entryDateFormatted = formatDateTime(entryDateTime);

                index = issue.getColumnIndexOrThrow(Contract_Issue.IssueEntry._ID);
                long id = issue.getLong(index);

                Log.v(TAG, "Editable Reminder entry ID: " + id);
                et_title.setText(entryTitle);
                et_body.setText(entryBody);
                et_tags.setText(entryTags);
                tv_datetime.setText(entryDateFormatted);
                beginEntrySet = true;
            }

            beginEntrySet = false;
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case (R.id.btn_issue_add):
                saveState();
                setResult(RESULT_OK);
                Toast.makeText(PHA_Issue_Entry.this,
                        getString(R.string.saveConfirm), Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    public Cursor findEntryForEdit(String id){
        Context context = getApplicationContext();
        DbHelper_Issues dbHelper = new DbHelper_Issues(context);

        Cursor forEdit = dbHelper.findById(id);
        //forEdit.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.)
        return forEdit;
    }


    // @TRY_ME Date/Time Format
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
