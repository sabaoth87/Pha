package com.tnk.db;

/**
 * Created by Tom on 2017-12-29.
 *
 * @TRY
 * I think I have to make this into a AsyncTask in order to prevent hangups
 */



import android.content.ClipData;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import com.tnk.database.ToolContract;

public class HandlerTool extends SQLiteOpenHelper  {

    //NEW TO TRY
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

    //information of database
    private static final int DATABASE_VERSION = 0;
    private static final String DATABASE_NAME = "toolsDB.db";
    public static final String TABLE_NAME = "ItemTool";
    public static final String COLUMN_ID = "ToolID";
    public static final String COLUMN_TYPE = "ToolType";
    public static final String COLUMN_NAME = "ToolName";
    public static final String COLUMN_BRAND = "ToolBrand";
    public static final String COLUMN_QUANTITY = "ToolQuantity";
    public static final String COLUMN_QUALITY = "ToolQuality";
    public static final String COLUMN_LOCATION = "ToolLocation";
    public static final String COLUMN_NOTE = "ToolNote";
    public static final String COLUMN_LINK = "ToolLink";
    public static final String COLUMN_PIC = "ToolPic";
    public static final String COLUMN_SIZE = "ToolSize";
    public static final String COLUMN_USES = "ToolUses";
    public static final String COLUMN_AMMO = "ToolAmmo";
    public static final String COLUMN_CAT = "ToolCategory";
    public static final String COLUMN_STATUS = "ToolStatus";

    //initialize the database

    public HandlerTool(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" +
                COLUMN_ID + "INTEGER PRIMARYKEY," +
                COLUMN_TYPE + "TEXT," +
                COLUMN_NAME + "TEXT," +
                COLUMN_BRAND + "TEXT," +
                COLUMN_QUANTITY + "TEXT," +
                COLUMN_QUALITY + "TEXT," +
                COLUMN_LOCATION + "TEXT," +
                COLUMN_NOTE + "TEXT," +
                COLUMN_LINK + "TEXT," +
                COLUMN_PIC + "TEXT," +
                COLUMN_SIZE + "TEXT," +
                COLUMN_USES + "TEXT," +
                COLUMN_AMMO + "TEXT," +
                COLUMN_CAT + "TEXT," +
                COLUMN_STATUS + "TEXT," +
                " )";
        db.execSQL(CREATE_TABLE);

    }
    @Override

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}
    public String loadHandler() {
        String result = "";
        String query = "Select*FROM" + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");

        }
        cursor.close();
        db.close();
        return result;
    }

    public void addHandler(ItemTool tool) {
        ContentValues values = new ContentValues();
        //values.put(COLUMN_ID, tool.getID());
        values.put(COLUMN_TYPE, tool.getToolType());
        values.put(COLUMN_NAME, tool.getToolName());
        values.put(COLUMN_BRAND, tool.getToolBrand());
        values.put(COLUMN_QUANTITY, tool.getToolQuantity());
        values.put(COLUMN_QUALITY, tool.getToolQuality());
        values.put(COLUMN_LOCATION, tool.getToolLocation());
        values.put(COLUMN_NOTE, tool.getToolNote());
        values.put(COLUMN_LINK, tool.getToolLink());
        values.put(COLUMN_PIC, tool.getToolPic());
        values.put(COLUMN_SIZE, tool.getToolSize());
        values.put(COLUMN_USES, tool.getToolUses());
        values.put(COLUMN_AMMO, tool.getToolAmmo());
        values.put(COLUMN_CAT, tool.getToolCat());
        values.put(COLUMN_STATUS, tool.getToolStatus());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public ItemTool findByNameHandler(String toolName) {

        String query = "Select * FROM " + TABLE_NAME + "WHERE" + COLUMN_NAME + " = " + "'" + toolName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ItemTool tool = new ItemTool();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            tool.setID(Integer.parseInt(cursor.getString(0)));
            tool.setToolName(cursor.getString(1));
            cursor.close();
        } else {
            tool = null;
        }
        db.close();
        return tool;
    }

    public boolean deleteByIDHandler(int ID) {
        boolean result = false;
        String query = "Select*FROM" + TABLE_NAME + "WHERE" + COLUMN_ID + "= '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ItemTool tool = new ItemTool();
        if (cursor.moveToFirst()) {
            tool.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, COLUMN_ID + "=?",
                    new String[] {
                String.valueOf(tool.getID())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean deleteByNameHandler(String entryName) {
        boolean result = false;
        String query = "Select*FROM" + TABLE_NAME + "WHERE" + COLUMN_NAME + "= '" + entryName + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        ItemTool tool = new ItemTool();
        if (cursor.moveToFirst()) {
            tool.setToolName(entryName);
            db.delete(TABLE_NAME, COLUMN_NAME + "=?",
                    new String[] {
                            String.valueOf(tool.getToolName())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateByIDHandler(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_NAME, name);
        return db.update(TABLE_NAME, args, COLUMN_ID + "=" + ID, null) > 0;
    }

    /*
    @TODO
    Will work, would like to find a way to just was the ItemTool class around and have the handlers
    figure everything else out

    Have a main handler, with a boolean or INT tag that sorts the info of the ItemTool class after
    the caller sends it here
     */

    public boolean updateByNameHandler(String type,
                                       String name,
                                       String brand,
                                       String quantity,
                                       String quality,
                                       String location,
                                       String note,
                                       String link,
                                       String pic,
                                       String size,
                                       String uses,
                                       String ammo,
                                       String category,
                                       String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_TYPE, type);
        args.put(COLUMN_NAME, name);
        args.put(COLUMN_BRAND, brand);
        args.put(COLUMN_QUANTITY, quantity);
        args.put(COLUMN_QUALITY, quality);
        args.put(COLUMN_LOCATION, location);
        args.put(COLUMN_NOTE, note);
        args.put(COLUMN_LINK, link);
        args.put(COLUMN_PIC, pic);
        args.put(COLUMN_SIZE, size);
        args.put(COLUMN_USES, uses);
        args.put(COLUMN_AMMO, ammo);
        args.put(COLUMN_CAT, category);
        args.put(COLUMN_STATUS, status);
        /*
        @TODO
        Catch that the 'name' is in the db before hand
         */
        return db.update(TABLE_NAME, args, COLUMN_NAME + "= '" + name + "'", null) > 0;
    }
}
