package com.tnk.pha;

import static android.provider.BaseColumns._ID;
import static com.tnk.db.dbConstants.AUTHOR;
import static com.tnk.db.dbConstants.COMMENT;
import static com.tnk.db.dbConstants.MEMO;
import static com.tnk.db.dbConstants.TABLE_NAME;
import static com.tnk.db.dbConstants.TIME;
import static com.tnk.db.dbConstants.TITLE;
import static com.tnk.db.dbConstants.DATE;
import static com.tnk.db.dbConstants.TAGS;
import static com.tnk.db.dbConstants.CATEGORY;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PHA_DbData extends SQLiteOpenHelper {

    private static final String DB_NAME = "PHAY_DbVersion_alpha_1.db";
    private static final int DATABASE_VERSION = 2;

    public PHA_DbData(Context ctx) {
        super(ctx, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TIME + " INTEGER, "
                + DATE + " TEXT NOT NULL,"
                + TITLE + " TEXT NOT NULL, "
                + AUTHOR + " TEXT NOT NULL, "
                + MEMO + " TEXT NOT NULL, "
                + CATEGORY + " TEXT NOT NULL, "
                + TAGS + " TEXT NOT NULL, "
                + COMMENT + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
