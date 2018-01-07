package com.tnk.pha;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

public class OnBootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		PHA_ReminderManager rMgr = new PHA_ReminderManager(context);
		dbAdapter dbHlpr = new dbAdapter(context);
		dbHlpr.open();
		Cursor cursor = dbHlpr.getReminders();
		if(cursor != null){
			cursor.moveToFirst();
			int rowIdColumnIndex = cursor.getColumnIndex(dbAdapter.REM_ROWID);
			int dateTimeColumnIndex = cursor.getColumnIndex(dbAdapter.REM_DATE_TIME);
			while (cursor.isAfterLast()==false){
				Long rowId = cursor.getLong(rowIdColumnIndex);
				String dateTime = cursor.getString(dateTimeColumnIndex);
				
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat format = new SimpleDateFormat(PHA_Reminder_Entry.DATE_TIME_FORMAT);
				try{
					java.util.Date date = format.parse(dateTime);
					cal.setTime(date);
					rMgr.setReminder(rowId, cal);
				}catch (ParseException e){
					Log.e("OnBootReceiver", e.getMessage(), e);
				}
				cursor.moveToNext();
			}
			cursor.close();
		}
		dbHlpr.close();
	}

	
}
