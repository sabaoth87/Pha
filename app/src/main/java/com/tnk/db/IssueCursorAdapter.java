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

public class IssueCursorAdapter extends CursorAdapter {
    public IssueCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // the newView method is used to inflate a new view and return it,
    // you do not bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_issues, parent, false);
    }

    // the bindView method is used to bind all data to a given view
    // such as setting the text on a text view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvId = view.findViewById(R.id.item_tv_id);
        TextView tvTitle = (TextView) view.findViewById(R.id.item_tv_title);
        TextView tvTags = (TextView) view.findViewById(R.id.item_tv_tags);
        TextView tvDate  = (TextView) view.findViewById(R.id.item_tv_date);
        // Extract properties from cursor
        String id = cursor.getString(cursor.getColumnIndexOrThrow(Contract_Issue.IssueEntry._ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(Contract_Issue.IssueEntry.COLUMN_TITLE));
        String tags = cursor.getString(cursor.getColumnIndexOrThrow(Contract_Issue.IssueEntry.COLUMN_TAGS));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(Contract_Issue.IssueEntry.COLUMN_DATETIME));
        //Populate fields with extracted properties
        tvId.setText(id);
        tvTitle.setText(title);
        tvTags.setText(tags);
        tvDate.setText(date);
    }

}
