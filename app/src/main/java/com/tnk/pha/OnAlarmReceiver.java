package com.tnk.pha;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class OnAlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		long rowid = intent.getExtras().getLong(dbAdapter.REM_ROWID);
		WakeReminderIntentService.acquireStaticLock(context);
		Intent i = new Intent(context, ReminderService.class);
		i.putExtra(dbAdapter.REM_ROWID, rowid);
		context.startService(i);
	}

}
