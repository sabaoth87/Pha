package com.tnk.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.tnk.pha.PHA_Util_File;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by Tom on 2018-01-03.
 */

public class DbHelper_Tools extends SQLiteOpenHelper {
    public static final String TAG = "DbHelper_Tools";
    //If you change the database scheme, you must increment the database version.
    // Ver 02 :: Feb 14, 2018
    public static final int DATABASE_VERSION = 02;
    public static final String DATABASE_NAME = "WorkBenchTools.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Contract_Tool.ToolEntry.TABLE_NAME + " (" +
                    Contract_Tool.ToolEntry._ID + " INTEGER PRIMARY KEY," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_TYPE + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_NAME + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_BRAND + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_QUANTITY + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_QUALITY + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_LOCATION + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_NOTE + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_LINK + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_PIC + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_SIZE + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_USES + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_AMMO + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_CATEGORY + " TEXT," +
                    Contract_Tool.ToolEntry.COLUMN_NAME_STATUS + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract_Tool.ToolEntry.TABLE_NAME;

    public static String DB_FILEPATH = "/data/data/com.tnk.pha/databases/WorkBenchTools.db";



    public DbHelper_Tools(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVerseion, int newVersion){
        // This database is only a cache for online data, so its upgrade policy is
        // to simply discard the data and start over
        Log.v(TAG, ":: Attempting to upgrade the Db");

        Log.v(TAG, "<<>> DELETING THE CURRENT DB <<>>");
        db.execSQL(SQL_DELETE_ENTRIES);
        Log.v(TAG, "<<>> Creating a NEW DB at version '" + DATABASE_VERSION + "' <<>>");
        //db.execSQL(SQL_CREATE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addToolHandler(Item_Tool tool) {
        Log.v(TAG, "Opening the Local Db for writing...");
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v(TAG, "Db open!");
        ContentValues values = new ContentValues();

        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_TYPE, tool.getToolType());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_NAME, tool.getToolName());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_BRAND, tool.getToolBrand());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_QUANTITY, tool.getToolQuantity());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_QUALITY, tool.getToolQuality());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_LOCATION, tool.getToolLocation());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_NOTE, tool.getToolNote());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_LINK, tool.getToolLink());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_PIC, tool.getToolPic());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_SIZE, tool.getToolSize());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_USES, tool.getToolUses());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_AMMO, tool.getToolAmmo());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_CATEGORY, tool.getToolCat());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_STATUS, tool.getToolStatus());

        Log.v(TAG, "Adding tool entry to Db...");
        long newRowId = db.insert(Contract_Tool.ToolEntry.TABLE_NAME, null, values);
        Log.v(TAG, "Entry " + newRowId + " added to Db");
        db.close();
        return(newRowId);
    }

    public Cursor findToolsByType(String toolType){
        SQLiteDatabase db = getReadableDatabase();

        //Define a projection that specifies which columns from the database
        // you wll actually use after this query.
        String[] projection = {
                Contract_Tool.ToolEntry._ID,
                Contract_Tool.ToolEntry.COLUMN_NAME_TYPE,
                Contract_Tool.ToolEntry.COLUMN_NAME_NAME,
                Contract_Tool.ToolEntry.COLUMN_NAME_BRAND,
                Contract_Tool.ToolEntry.COLUMN_NAME_SIZE
        };
        // Filter results WHERE "type" = et_ToolType entry
        String selection = Contract_Tool.ToolEntry.COLUMN_NAME_TYPE + " = ?";
        String[] selectionArgs = { toolType };

        // How you want the results sorted int he resulting Cursor

        String sortOrder = Contract_Tool.ToolEntry.COLUMN_NAME_BRAND + " DESC";

        Cursor cursor = db.query(
                Contract_Tool.ToolEntry.TABLE_NAME,  // The table you want to query
                projection,                          // The columns you want returned
                selection,                           // the columns for the WHERE clause
                selectionArgs,                      // the values for the WHERE clause
                null,                      // do not group the rows
                null,                       // dont filter by row groups
                sortOrder                           // the sort order
        );
        return cursor;
    }

    public Cursor findToolsByID(String toolId){
        SQLiteDatabase db = getReadableDatabase();

        //Define a projection that specifies which columns from the database
        // you wll actually use after this query.
        String[] projection = {
                Contract_Tool.ToolEntry._ID,
                Contract_Tool.ToolEntry.COLUMN_NAME_TYPE,
                Contract_Tool.ToolEntry.COLUMN_NAME_NAME,
                Contract_Tool.ToolEntry.COLUMN_NAME_BRAND,
                Contract_Tool.ToolEntry.COLUMN_NAME_SIZE
        };
        // Filter results WHERE "type" = et_ToolType entry
        //String selection = Contract_Tool.ToolEntry._ID + " = ?";
        String selection = Contract_Tool.ToolEntry._ID + " = ?";
        String[] selectionArgs = { toolId };

        // How you want the results sorted int he resulting Cursor

        String sortOrder = Contract_Tool.ToolEntry._ID + " DESC";

        Cursor cursor = db.query(
                Contract_Tool.ToolEntry.TABLE_NAME,  // The table you want to query
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
                Contract_Tool.ToolEntry._ID,
                Contract_Tool.ToolEntry.COLUMN_NAME_TYPE,
                Contract_Tool.ToolEntry.COLUMN_NAME_NAME,
                Contract_Tool.ToolEntry.COLUMN_NAME_BRAND,
                Contract_Tool.ToolEntry.COLUMN_NAME_SIZE
        };
        // Filter results WHERE "type" = et_ToolType entry
        String selection = Contract_Tool.ToolEntry._ID + " = ?";
        String[] selectionArgs = {};

        // How you want the results sorted int he resulting Cursor

        String sortOrder = Contract_Tool.ToolEntry.COLUMN_NAME_BRAND + " DESC";

        Cursor cursor = db.query(
                Contract_Tool.ToolEntry.TABLE_NAME,  // The table you want to query
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
        String selection = Contract_Tool.ToolEntry._ID + " LIKE ?";
        //Specify arguments in placeholder order.
        String[] selectionArgs = { toolId };
        //Issue the SQL statement.
        db.delete(Contract_Tool.ToolEntry.TABLE_NAME, selection, selectionArgs);
        return TRUE;
    }

    //@NOTE
    // This may be useless in the case of our interface for the time being.
    // Would have to have another set of activities that handle the per-tool
    // information display so that the user can have all the pertinent information
    // on one view, and the _ID has come from whatever launched such viewer
    // activities
    public boolean updateToolById(Item_Tool targetTool){
        SQLiteDatabase db = getWritableDatabase();

        //New value(s) for the column(s)
        ContentValues values = new ContentValues();
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_TYPE,
                targetTool.getToolType());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_NAME,
                targetTool.getToolName());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_BRAND,
                targetTool.getToolBrand());
        values.put(Contract_Tool.ToolEntry.COLUMN_NAME_SIZE,
                targetTool.getToolSize());

        //Which row to update, based on the targetTool passed from caller
        String selection = Contract_Tool.ToolEntry._ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(targetTool.getID()) };

        int count = db.update(
                Contract_Tool.ToolEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count>0){
            return TRUE;
        } else {
            return FALSE;
        }
    }

    /**
     * Copies the database file at the specified location over the current
     * internal application database.
     */
    public boolean importDatabase(String dbPath) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage
        close();
        File newDb = new File(dbPath);
        File oldDb = new File(DB_FILEPATH);
        if (newDb.exists()) {
            PHA_Util_File.copyFile(new FileInputStream(newDb), new FileInputStream(oldDb));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            getWritableDatabase().close();
            return true;
        }
        return false;
    }

    public boolean exportDatabase(String exportPath) throws IOException {

        // Close the SQLiteOpenHelper so it will commit the created empty
        // database to internal storage
        close();
        File destination = new File(exportPath);
        File dbPath = new File(DB_FILEPATH);
        if (dbPath.exists()) {
            PHA_Util_File.copyFile(new FileInputStream(destination), new FileInputStream(dbPath));
            // Access the copied database so SQLiteHelper will cache it and mark
            // it as created.
            getWritableDatabase().close();
            return true;
        }
        return false;
    }


}