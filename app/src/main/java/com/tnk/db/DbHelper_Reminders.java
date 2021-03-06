package com.tnk.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
 * Created by Tom on 2018-01-22.
 */

public class DbHelper_Reminders extends SQLiteOpenHelper {

    public static final String TAG = "DbHelper_Reminders";

    //information of database
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "PHA.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contract_Reminder.ReminderEntry.TABLE_NAME + " (" +
                    Contract_Reminder.ReminderEntry._ID + " INTEGER PRIMARY KEY," +
                    Contract_Reminder.ReminderEntry.COLUMN_TITLE + " TEXT," +
                    Contract_Reminder.ReminderEntry.COLUMN_BODY + " TEXT," +
                    Contract_Reminder.ReminderEntry.COLUMN_DATE + " TEXT," +
                    Contract_Reminder.ReminderEntry.COLUMN_TIME + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract_Reminder.ReminderEntry.TABLE_NAME;

    public DbHelper_Reminders(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION);}
    public void onCreate(SQLiteDatabase db){
        Log.v(TAG, "<<>> CREATING DB <<>> " + DATABASE_NAME + " " + Contract_Reminder.ReminderEntry.TABLE_NAME + " v- " + DATABASE_VERSION);
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVerseion, int newVersion){
        // This database is only a cache for online data, so its upgrade policy is
        // to simply discard the data and start over
        Log.v(TAG, ":: Attempting to upgrade the Db");

        Log.v(TAG, "<<>> DELETING THE CURRENT DB <<>> " + DATABASE_NAME + " " + Contract_Reminder.ReminderEntry.TABLE_NAME + " v- " + DATABASE_VERSION);
        db.execSQL(SQL_DELETE_ENTRIES);
        Log.v(TAG, "<<>> Creating a NEW DB at version '" + DATABASE_VERSION + "' <<>>");
        //db.execSQL(SQL_CREATE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addReminderHandler(Item_Reminder reminder) {
        Log.v(TAG, "Opening the local Db for writing...");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v(TAG, "Db Open!");
        ContentValues values = new ContentValues();

        values.put(Contract_Reminder.ReminderEntry.COLUMN_TITLE, reminder.getRem_title());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_BODY, reminder.getRem_body());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_DATE, reminder.getRem_date());
        values.put(Contract_Reminder.ReminderEntry.COLUMN_TIME, reminder.getRem_time());

        Log.v(TAG,"Adding reminder to the Db...");
        long newRowId = db.insert(
                Contract_Reminder.ReminderEntry.TABLE_NAME,
                null, 
                values);
        Log.v(TAG, "Entry " + newRowId + " added to Db");
        db.close();
        return(newRowId);
    }

    public Cursor findByTitle (String findMe){
        Log.v(TAG,"Searching the local Db for " + findMe);
        SQLiteDatabase db = this.getReadableDatabase();

        //Define a projection that specifies which columns from the database
        //you will actually use after this query
        String[] projection = {
                Contract_Reminder.ReminderEntry._ID,
                Contract_Reminder.ReminderEntry.COLUMN_TITLE,
                Contract_Reminder.ReminderEntry.COLUMN_BODY
        };

        //Filter results where "title" = 'findMe'
        String selection = Contract_Reminder.ReminderEntry.COLUMN_TITLE + " ?";
        String[] selectionArgs = { findMe };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Contract_Reminder.ReminderEntry.COLUMN_BODY + " DESC";

        Cursor cursor = db.query(
                Contract_Reminder.ReminderEntry.TABLE_NAME,         // The table to query
                projection,                                         // The columns to return
                selection,                                          // The columns for the WHERE clause
                selectionArgs,                                      // The values for the WHERE clause
                null,                                       // Do not group the rows
                null,                                        // Do not filter by row group
                sortOrder                                           // The sort order
        );

        return cursor;
    }

    public Cursor findById (String id){
        Log.v(TAG,"Searching the local Db for entry #" + id);
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + Contract_Reminder.ReminderEntry.TABLE_NAME + " WHERE _id = " + id;

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }


    public Cursor findAllReminders (){
        Log.v(TAG,"Searching the local Db for all Reminders");
        SQLiteDatabase db = this.getReadableDatabase();

        //Define a projection that specifies which columns from the database
        //you will actually use after this query
        String[] projection = {
                Contract_Reminder.ReminderEntry._ID,
                Contract_Reminder.ReminderEntry.COLUMN_TITLE,
                Contract_Reminder.ReminderEntry.COLUMN_BODY,
                Contract_Reminder.ReminderEntry.COLUMN_DATE,
                Contract_Reminder.ReminderEntry.COLUMN_TIME
        };

        /*
        @TRY 03 - SQL Query of ALL entries
         */
        //Find all entries that have an _ID
        String selection = Contract_Reminder.ReminderEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Contract_Reminder.ReminderEntry.COLUMN_DATE + " DESC";

        /*
        Cursor cursor = db.query(
                Contract_Reminder.ReminderEntry.TABLE_NAME,         // The table to query
                projection,                                         // The columns to return
                selection,                                          // The columns for the WHERE clause
                selectionArgs,                                      // The values for the WHERE clause
                null,                                       // Do not group the rows
                null,                                        // Do not filter by row group
                sortOrder                                           // The sort order
        );*/
        Cursor cursor = db.rawQuery("select * from " + Contract_Reminder.ReminderEntry.TABLE_NAME, null);

        return cursor;
    }

    public boolean checkReminderTable (SQLiteDatabase db){

        if(db == null || !db.isOpen()){
            db = getReadableDatabase();
        }

        if(!db.isReadOnly()) {
            db.close();
            db = getReadableDatabase();
        }

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                Contract_Reminder.ReminderEntry.TABLE_NAME + "'", null);

        if (cursor!=null) {
            if (cursor.getCount() > 0) {
                Log.v(TAG, "Table was not found null ¯_(ツ)_¯");
                cursor.close();
                return true;
            }
            Log.v(TAG, Contract_Reminder.ReminderEntry.TABLE_NAME + " - table was found!");
            cursor.close();
        }
        return false;
    }

    public boolean createReminderTable (Context context) {
        /*
        @TODO ## - createIssuesTable
         */
        DbHelper_Reminders dbHelper_reminders = new DbHelper_Reminders(context);
        SQLiteDatabase db = dbHelper_reminders.getWritableDatabase();
        db.execSQL("CREATE TABLE " + Contract_Reminder.ReminderEntry.TABLE_NAME + " (" +
                Contract_Reminder.ReminderEntry._ID + " INTEGER PRIMARY KEY," +
                Contract_Reminder.ReminderEntry.COLUMN_TITLE + " TEXT," +
                Contract_Reminder.ReminderEntry.COLUMN_BODY + " TEXT," +
                Contract_Reminder.ReminderEntry.COLUMN_DATE + " TEXT," +
                Contract_Reminder.ReminderEntry.COLUMN_TIME + " TEXT)");
        return true;
    }

}
