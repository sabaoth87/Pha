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

public class DbHelper_Issues extends SQLiteOpenHelper {

    public static final String TAG = "DbHelper_Issues";

    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "phalanx.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contract_Issue.IssueEntry.TABLE_NAME + " (" +
                    Contract_Issue.IssueEntry._ID + " INTEGER PRIMARY KEY," +
                    Contract_Issue.IssueEntry.COLUMN_TITLE + " TEXT," +
                    Contract_Issue.IssueEntry.COLUMN_BODY + " TEXT," +
                    Contract_Issue.IssueEntry.COLUMN_DATETIME + " TEXT," +
                    Contract_Issue.IssueEntry.COLUMN_STATUS + " TEXT," +
                    Contract_Issue.IssueEntry.COLUMN_TAGS + " TEXT," +
                    Contract_Issue.IssueEntry.COLUMN_ASSIGNEE + " TEXT," +
                    Contract_Issue.IssueEntry.COLUMN_PROJECT + " TEXT," +
                    Contract_Issue.IssueEntry.COLUMN_MILESTONE + " TEXT," +
                    Contract_Issue.IssueEntry.COLUMN_PROGRESS + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract_Issue.IssueEntry.TABLE_NAME;

    public DbHelper_Issues(Context context){ super(context, DATABASE_NAME, null, DATABASE_VERSION);}
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

    public long addReminderHandler(Item_Issue issue) {
        Log.v(TAG, "Opening the local Db for writing...");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v(TAG, "Db Open!");
        ContentValues values = new ContentValues();

        values.put(Contract_Issue.IssueEntry.COLUMN_TITLE, issue.getIssueTitle());
        values.put(Contract_Issue.IssueEntry.COLUMN_BODY, issue.getIssueBody());
        values.put(Contract_Issue.IssueEntry.COLUMN_DATETIME, issue.getIssueDatetime());
        values.put(Contract_Issue.IssueEntry.COLUMN_STATUS, issue.getIssueStatus());
        values.put(Contract_Issue.IssueEntry.COLUMN_TAGS, issue.getIssueTags());
        values.put(Contract_Issue.IssueEntry.COLUMN_ASSIGNEE, issue.getIssueAssignee());
        values.put(Contract_Issue.IssueEntry.COLUMN_PROJECT, issue.getIssueProject());
        values.put(Contract_Issue.IssueEntry.COLUMN_MILESTONE, issue.getIssueMilestone());
        values.put(Contract_Issue.IssueEntry.COLUMN_PROGRESS, issue.getIssueProgress());

        Log.v(TAG,"Adding Issues to the Db...");
        long newRowId = db.insert(
                Contract_Issue.IssueEntry.TABLE_NAME,
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
                Contract_Issue.IssueEntry._ID,
                Contract_Issue.IssueEntry.COLUMN_TITLE,
                Contract_Issue.IssueEntry.COLUMN_BODY,
                Contract_Issue.IssueEntry.COLUMN_DATETIME,
                Contract_Issue.IssueEntry.COLUMN_STATUS,
                Contract_Issue.IssueEntry.COLUMN_TAGS,
                Contract_Issue.IssueEntry.COLUMN_ASSIGNEE,
                Contract_Issue.IssueEntry.COLUMN_PROJECT,
                Contract_Issue.IssueEntry.COLUMN_MILESTONE,
                Contract_Issue.IssueEntry.COLUMN_PROGRESS
        };

        //Filter results where "title" = 'findMe'
        String selection = Contract_Issue.IssueEntry.COLUMN_TITLE + " ?";
        String[] selectionArgs = { findMe };

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Contract_Issue.IssueEntry.COLUMN_BODY + " DESC";

        Cursor cursor = db.query(
                Contract_Issue.IssueEntry.TABLE_NAME,         // The table to query
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

        String query = "SELECT * FROM " + Contract_Issue.IssueEntry.TABLE_NAME + " WHERE _id = " + id;

        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }


    public Cursor findAllReminders (){
        Log.v(TAG,"Searching the local Db for all Reminders");
        SQLiteDatabase db = this.getReadableDatabase();

        //Define a projection that specifies which columns from the database
        //you will actually use after this query
        String[] projection = {
                Contract_Issue.IssueEntry._ID,
                Contract_Issue.IssueEntry.COLUMN_TITLE,
                Contract_Issue.IssueEntry.COLUMN_BODY,
                Contract_Issue.IssueEntry.COLUMN_DATETIME,
                Contract_Issue.IssueEntry.COLUMN_STATUS,
                Contract_Issue.IssueEntry.COLUMN_TAGS,
                Contract_Issue.IssueEntry.COLUMN_ASSIGNEE,
                Contract_Issue.IssueEntry.COLUMN_PROJECT,
                Contract_Issue.IssueEntry.COLUMN_MILESTONE,
                Contract_Issue.IssueEntry.COLUMN_PROGRESS
        };

        /*
        @TRY 03 - SQL Query of ALL entries
         */
        //Find all entries that have an _ID
        String selection = Contract_Issue.IssueEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = {};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                Contract_Issue.IssueEntry.COLUMN_DATETIME + " DESC";

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
        Cursor cursor = db.rawQuery("select * from " + Contract_Issue.IssueEntry.TABLE_NAME, null);

        return cursor;
    }

}