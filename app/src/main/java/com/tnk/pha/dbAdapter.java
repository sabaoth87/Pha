package com.tnk.pha;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class dbAdapter extends Activity {
	public static final String REM_TITLE = "title";
	public static final String REM_BODY= "body";
	public static final String REM_DATE_TIME = "reminder_date_time";
	public static final String REM_ROWID = "_id";
	private static final String REM_TABLE_NAME = "RemindersDb" ;
	private static final String DATABASE_NAME = "PHAY_DbVersion_alpha_1";
	private static final int DATABASE_VERSION = 1;
	//
	private DatabaseHelper PHA_DbHelper;
	private SQLiteDatabase PHA_Db;
	
	private static final String DATABASE_CREATE = 
			"create table " + REM_TABLE_NAME + " ("
			+ REM_ROWID + " integer primary key autoincrement, "
			+ REM_TITLE + " text not null, "
			+ REM_BODY + " text not null, "
			+ REM_DATE_TIME + " text not null);";
	private Context nCtx;
	
	public dbAdapter(Context ctx){
		this.nCtx=ctx;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			/*
			 * could upgrade the database with alter scripts apparently
			 * maybe look into that for upgrading the database next time
			 * instead of always over writing
			 */
		}
	}
	
	public dbAdapter open() throws android.database.SQLException{
		PHA_DbHelper = new DatabaseHelper(nCtx);
		PHA_Db = PHA_DbHelper.getWritableDatabase();
		return this;
		/*
		 * The open() method opens (and creates if necessary) the database using the
		 * DatabaseHelper() class that was just created. This class then returns itself
		 * through the this Java keyword. The reason that the class is returning itself is
		 * because the caller (ReminderEditActivity or ReminderListActivity)
		 * needs to access data from this class and this method returns an instance of
		 * the RemindersDbAdapter.
		 */
	}
	
	public void close(){
		PHA_DbHelper.close();
	}
	
	public long createReminder(String title, String body, String reminderDateTime){
		ContentValues initialValues = new ContentValues();
		initialValues.put(REM_TITLE, title);
		initialValues.put(REM_BODY, body);
		initialValues.put(REM_DATE_TIME, reminderDateTime);
		return PHA_Db.insert(REM_TABLE_NAME, null, initialValues);
	}
	
	public boolean deleteReminder(long rowId){
		return PHA_Db.delete(REM_TABLE_NAME, REM_ROWID + "=" +rowId,null) >0;
	}
	
	public Cursor getReminders(){
		return PHA_Db.query(REM_TABLE_NAME, 
				new String[] {REM_ROWID,  REM_TITLE,  REM_BODY,  REM_DATE_TIME}, 
				null, null, null, null, null);
	}
	public Cursor getRem(long rowId) throws SQLiteException {
		Cursor mCursor = PHA_Db.query(true, REM_TABLE_NAME,
				new String[] {REM_ROWID, REM_TITLE, REM_BODY, REM_DATE_TIME}, REM_ROWID+"="+rowId, 
				null, null, null, null, null);
		if (mCursor !=null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	public boolean updateReminder(long rowId, String title, String body, String reminderDateTime){
		ContentValues args = new ContentValues();
		args.put(REM_TITLE, title);
		args.put(REM_BODY, body);
		args.put(REM_DATE_TIME, reminderDateTime);
		return
				PHA_Db.update(REM_TABLE_NAME, args, REM_ROWID+"="+rowId, null)>0;
	}
	
}