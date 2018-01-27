package com.tnk.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tnk.R;

/**
 * Created by Tom on 2018-01-06.
 */

public class ReminderCursorAdapter extends CursorAdapter {
    public ReminderCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // the newView method is used to inflate a new view and return it,
    // you do not bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_reminders, parent, false);
    }

    // the bindView method is used to bind all data to a given view
    // such as setting the text on a text view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvId = (TextView) view.findViewById(R.id.item_tv_id);
        TextView tvTitle = (TextView) view.findViewById(R.id.item_tv_title);
        TextView tvBody  = (TextView) view.findViewById(R.id.item_tv_body);
        TextView tvDate  = (TextView) view.findViewById(R.id.item_tv_date);
        TextView tvTime  = (TextView) view.findViewById(R.id.item_tv_time);
        // Extract properties from cursor
        Integer id = cursor.getInt(cursor.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.COLUMN_TITLE));
        String body = cursor.getString(cursor.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.COLUMN_BODY));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.COLUMN_DATE));
        String time = cursor.getString(cursor.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry.COLUMN_TIME));
        //Populate fields with extracted properties
        tvId.setText(id.toString());
        tvTitle.setText(title);
        tvBody.setText(body);
        tvDate.setText(date);
        tvTime.setText(time);
    }

}
