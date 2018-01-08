package com.tnk.pha;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.tnk.db.dbAdapter;


//@TODO
// These reminders and alarms will have to be stripped once we get this up and running
// I have no use for them, and it seems that this method of implementation is antiquated
public class ReminderService extends WakeReminderIntentService {
	public ReminderService(){
		super("ReminderService");
	}
	
	@Override
	void doReminderWork(Intent intent){
		Long rowId = intent.getExtras().getLong(dbAdapter.REM_ROWID);
		//status bar notification to go here :)
		NotificationManager nMgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Intent notIntent = new Intent(this, PHA_Reminder_Entry.class);
		notIntent.putExtra(dbAdapter.REM_ROWID, rowId);
		PendingIntent pi = PendingIntent.getActivity(this, 0, notIntent, PendingIntent.FLAG_ONE_SHOT);
		Notification note= new Notification(android.R.drawable.stat_sys_warning, getString(R.string.notifyNewTaskMessage), System.currentTimeMillis());
		//note.setLatestEventInfo(this, getString(R.string.notifyNewTaskTitle), getString(R.string.notifyNewTaskMessage), pi);
		note.defaults |= Notification.DEFAULT_SOUND;
		note.flags |= Notification.FLAG_AUTO_CANCEL;
		/*
		 * An issue could occur if the user were to enter more than 
		 * 2147483647 tasks, as this is the max INT value
		 */
		int id = (int)((long)rowId);
		nMgr.notify(id, note);
	}

}
