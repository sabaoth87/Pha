package com.tnk.db;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by Tom on 2018-01-22.
 */

public class DbHelper_Reminders extends SQLiteOpenHelper {

    public static final String TAG = "DbHelper_Reminders";

    //information of database
    private static final int DATABASE_VERSION = 0;
    private static final String DATABASE_NAME = "phalanx.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contract_Reminder.ReminderEntry._ID + " INTEGER PRIMARY KEY," +
                    Contract_Reminder.ReminderEntry.COLUMN_TITLE + " TEXT," +
                    Contract_Reminder.ReminderEntry.COLUMN_BODY + " TEXT," +
                    Contract_Reminder.ReminderEntry.COLUMN_DATE + " TEXT," +
                    Contract_Reminder.ReminderEntry.COLUMN_TIME + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract_Reminder.ReminderEntry.TABLE_NAME;

    public DbHelper_Reminders(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION);}
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVerseion, int newVersion){
        // This database is only a cache for online data, so its upgrade policy is
        // to simply discard the data and start over
        db.execSQL(SQL_CREATE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addReminder(Item_Reminder reminder) {
        Log.v(TAG, "Opening the local Db for writing...");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v(TAG, "Db Open!");
        ContentValues values = new ContentValues();

        values.put(Contract_Reminder.ReminderEntry.COLUMN_TITLE, reminder.getRem_title());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_BODY, reminder.getRem_body());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_DATE, reminder.getRem_date());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_TIME, reminder.getRem_time());

        Log.v(TAG,"Adding reminder tot he Db...");
        long newRowId = db.insert(Contract_Reminder.ReminderEntry.TABLE_NAME, null, values);
        Log.v(TAG, "Entry " + newRowId + " added to Db");
        db.close();
        return(newRowId);
    }

    public Cursor findAllReminders(){
        SQLiteDatabase db = getReadableDatabase();

        //Define a projection that specifies which columns from the database
        //you will actually use after this query
        //To reduce the amount of overhead required for this task
        String[] projection = {
                Contract_Reminder.ReminderEntry._ID,
                Contract_Reminder.ReminderEntry.COLUMN_TITLE,
                Contract_Reminder.ReminderEntry.COLUMN_BODY,
                Contract_Reminder.ReminderEntry.COLUMN_DATE,
                Contract_Reminder.ReminderEntry.COLUMN_TIME
        };

        /*
        @TODO - Look into SQL query commands
        Attempt to make a small list, with examples, for doing the most common SQL tasks
         */
        //Filter results WHERE "_ID" = ANY    ...   I hope
        String selection = Contract_Reminder.ReminderEntry._ID + " = ?";
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor

        String sortOrder = Contract_Reminder.ReminderEntry.COLUMN_DATE + " DESC";

        Cursor cursor = db.query(
                Contract_Reminder.ReminderEntry.TABLE_NAME,     // The table you want to query
                projection,                                     // The columns you want to return
                null,                                  // The columns for the WHERE clause
                null,                               // The values for the WHERE clause
                null,                                  // Do not group the findings
                null,
                sortOrder
        );
        return cursor;
    }

    public boolean deleteReminderById(String reminderId ) {
        SQLiteDatabase db = getWritableDatabase();

        //Define WHERE of the query
        String selection = Contract_Reminder.ReminderEntry._ID + " LIKE ?";
        //Specify arguments in placeholder area
        String[] selectionArgs = { reminderId};
        //Issue the SQL statement
        db.delete(Contract_Reminder.ReminderEntry.TABLE_NAME, selection, selectionArgs);
        return TRUE;
    }

    public boolean updateReminderById(Item_Reminder freshReminder){
        SQLiteDatabase db = getWritableDatabase();

        //New value(s) for the column(s)
        ContentValues values = new ContentValues();
        values.put(Contract_Reminder.ReminderEntry.COLUMN_TITLE,
                freshReminder.getRem_title());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_BODY,
                freshReminder.getRem_body());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_DATE,
                freshReminder.getRem_date());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_TIME,
                freshReminder.getRem_time());

        //Which row to update, based on the freshReminder passed from caller
        String selection = Contract_Reminder.ReminderEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(freshReminder.getID())};

        int count = db.update(
                Contract_Reminder.ReminderEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        if (count>0){
            return TRUE;
        } else {
            return FALSE;
        }
    }

}
