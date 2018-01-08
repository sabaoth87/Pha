package com.tnk.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.tnk.myworkshop.R;

import org.w3c.dom.Text;

/**
 * Created by Tom on 2018-01-06.
 */

public class ToolCursorAdapter extends CursorAdapter {
    public ToolCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // the newView method is used to inflate a new view and return it,
    // you do not bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_tools, parent, false);
    }

    // the bindView method is used to bind all data to a given view
    // such as setting the text on a text view
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvBrand = (TextView) view.findViewById(R.id.item_tv_ToolBrand);
        TextView tvName = (TextView) view.findViewById(R.id.item_tv_ToolName);
        TextView tvSize  = (TextView) view.findViewById(R.id.item_tv_ToolSize);
        // Extract properties from cursor
        String brand = cursor.getString(cursor.getColumnIndexOrThrow(ToolContract.ToolEntry.COLUMN_NAME_BRAND));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(ToolContract.ToolEntry.COLUMN_NAME_NAME));
        String size = cursor.getString(cursor.getColumnIndexOrThrow(ToolContract.ToolEntry.COLUMN_NAME_SIZE));
        //Populate fields with extracted properties
        tvBrand.setText(brand);
        tvName.setText(name);
        tvSize.setText(size);
    }

}
