package com.tnk.pha;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.tnk.db.dbAdapter;

public class PHA_ReminderManager {

    private Context schmontext;
    private AlarmManager mAM;

    public PHA_ReminderManager(Context context) {
        schmontext = context;
        mAM = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setReminder(Long taskId, Calendar when) {
        Intent i = new Intent(schmontext, OnAlarmReceiver.class);
        i.putExtra(dbAdapter.REM_ROWID, (long) taskId);
        PendingIntent pi = PendingIntent.getBroadcast(schmontext, 0, i, PendingIntent.FLAG_ONE_SHOT);
        mAM.set(AlarmManager.RTC_WAKEUP, when.getTimeInMillis(), pi);
    }
}
