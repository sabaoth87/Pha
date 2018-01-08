package com.tnk.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by Tom on 2018-01-03.
 */

public class ToolDbHelper extends SQLiteOpenHelper {
    public static final String TAG = "ToolDbHelper";
    //If you change the database scheme, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WorkBenchTools.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ToolContract.ToolEntry.TABLE_NAME + " (" +
                    ToolContract.ToolEntry._ID + " INTEGER PRIMARY KEY," +
                    ToolContract.ToolEntry.COLUMN_NAME_TYPE + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_NAME + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_BRAND + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_QUANTITY + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_QUALITY + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_LOCATION + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_NOTE + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_LINK + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_PIC + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_SIZE + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_USES + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_AMMO + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_CATEGORY + " TEXT," +
                    ToolContract.ToolEntry.COLUMN_NAME_STATUS + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ToolContract.ToolEntry.TABLE_NAME;


    public ToolDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
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

    public long addToolHandler(ItemTool tool) {
        Log.v(TAG, "Opening the Local Db for writing...");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v(TAG, "Db open!");
        ContentValues values = new ContentValues();

        values.put(ToolContract.ToolEntry.COLUMN_NAME_TYPE, tool.getToolType());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_NAME, tool.getToolName());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_BRAND, tool.getToolBrand());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_QUANTITY, tool.getToolQuantity());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_QUALITY, tool.getToolQuality());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_LOCATION, tool.getToolLocation());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_NOTE, tool.getToolNote());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_LINK, tool.getToolLink());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_PIC, tool.getToolPic());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_SIZE, tool.getToolSize());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_USES, tool.getToolUses());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_AMMO, tool.getToolAmmo());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_CATEGORY, tool.getToolCat());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_STATUS, tool.getToolStatus());

        Log.v(TAG, "Adding tool entry to Db...");
        long newRowId = db.insert(ToolContract.ToolEntry.TABLE_NAME, null, values);
        Log.v(TAG, "Entry " + newRowId + " added to Db");
        db.close();
        return(newRowId);
    }

    public Cursor findToolsByType(String toolType){
        SQLiteDatabase db = getReadableDatabase();

        //Define a projection that specifies which columns from the database
        // you wll actually use after this query.
        String[] projection = {
                ToolContract.ToolEntry._ID,
                ToolContract.ToolEntry.COLUMN_NAME_TYPE,
                ToolContract.ToolEntry.COLUMN_NAME_NAME,
                ToolContract.ToolEntry.COLUMN_NAME_BRAND,
                ToolContract.ToolEntry.COLUMN_NAME_SIZE
        };
        // Filter results WHERE "type" = et_ToolType entry
        String selection = ToolContract.ToolEntry.COLUMN_NAME_TYPE + " = ?";
        String[] selectionArgs = { toolType };

        // How you want the results sorted int he resulting Cursor

        String sortOrder = ToolContract.ToolEntry.COLUMN_NAME_BRAND + " DESC";

        Cursor cursor = db.query(
                ToolContract.ToolEntry.TABLE_NAME,  // The table you want to query
                projection,                         // The columns you want to return
                selection,                          // the columns you want to return
                selectionArgs,                     // the columns for the WHERE clause
                null,                      // the values for the WHERE clause
                null,                       // do not group the rows
                sortOrder
        );
        return cursor;
    }

    public Cursor findToolsByID(String toolId){
        SQLiteDatabase db = getReadableDatabase();

        //Define a projection that specifies which columns from the database
        // you wll actually use after this query.
        String[] projection = {
                ToolContract.ToolEntry._ID,
                ToolContract.ToolEntry.COLUMN_NAME_TYPE,
                ToolContract.ToolEntry.COLUMN_NAME_NAME,
                ToolContract.ToolEntry.COLUMN_NAME_BRAND,
                ToolContract.ToolEntry.COLUMN_NAME_SIZE
        };
        // Filter results WHERE "type" = et_ToolType entry
        //String selection = ToolContract.ToolEntry._ID + " = ?";
        String selection = ToolContract.ToolEntry._ID + " = ?";
        String[] selectionArgs = { toolId };

        // How you want the results sorted int he resulting Cursor

        String sortOrder = ToolContract.ToolEntry._ID + " DESC";

        Cursor cursor = db.query(
                ToolContract.ToolEntry.TABLE_NAME,  // The table you want to query
                projection,                         // The columns you want to return
                selection,                          // the columns for the WHERE clause
                selectionArgs,                     // the values for the WHERE clause
                null,                      // do not group the rows
                null,                       // ?
                sortOrder                           // sort by?
        );
        return cursor;
    }

    public Cursor findAllTools(){
        SQLiteDatabase db = getReadableDatabase();

        //Define a projection that specifies which columns from the database
        // you wll actually use after this query.
        String[] projection = {
                ToolContract.ToolEntry._ID,
                ToolContract.ToolEntry.COLUMN_NAME_TYPE,
                ToolContract.ToolEntry.COLUMN_NAME_NAME,
                ToolContract.ToolEntry.COLUMN_NAME_BRAND,
                ToolContract.ToolEntry.COLUMN_NAME_SIZE
        };
        // Filter results WHERE "type" = et_ToolType entry
        String selection = ToolContract.ToolEntry._ID + " = ?";
        String[] selectionArgs = {};

        // How you want the results sorted int he resulting Cursor

        String sortOrder = ToolContract.ToolEntry.COLUMN_NAME_BRAND + " DESC";

        Cursor cursor = db.query(
                ToolContract.ToolEntry.TABLE_NAME,  // The table you want to query
                projection,                         // The columns you want to return
                null,                          // the columns you want to return
                null,                     // the columns for the WHERE clause
                null,                      // the values for the WHERE clause
                null,                       // do not group the rows
                sortOrder
        );
        return cursor;
    }

    public boolean deleteToolById(String toolId){
        SQLiteDatabase db = getWritableDatabase();

        //Define 'where' part of the query.
        String selection = ToolContract.ToolEntry._ID + " LIKE ?";
        //Specify arguments in placeholder order.
        String[] selectionArgs = { toolId };
        //Issue the SQL statement.
        db.delete(ToolContract.ToolEntry.TABLE_NAME, selection, selectionArgs);
        return TRUE;
    }

    //@NOTE
    // This may be useless in the case of our interface for the time being.
    // Would have to have another set of activities that handle the per-tool
    // information display so that the user can have all the pertinent information
    // on one view, and the _ID has come from whatever launched such viewer
    // activities
    public boolean updateToolById(ItemTool targetTool){
        SQLiteDatabase db = getWritableDatabase();

        //New value(s) for the column(s)
        ContentValues values = new ContentValues();
        values.put(ToolContract.ToolEntry.COLUMN_NAME_TYPE,
                targetTool.getToolType());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_NAME,
                targetTool.getToolName());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_BRAND,
                targetTool.getToolBrand());
        values.put(ToolContract.ToolEntry.COLUMN_NAME_SIZE,
                targetTool.getToolSize());

        //Which row to update, based on the targetTool passed from caller
        String selection = ToolContract.ToolEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(targetTool.getID()) };

        int count = db.update(
                ToolContract.ToolEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count>0){
            return TRUE;
        } else {
            return FALSE;
        }
    }


}