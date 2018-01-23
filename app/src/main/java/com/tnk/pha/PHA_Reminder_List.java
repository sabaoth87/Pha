package com.tnk.pha;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.tnk.R;
import com.tnk.db.dbAdapter;

public class PHA_Reminder_List extends ListActivity {

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private dbAdapter phaDbHlpr;
    public String[] FROM;
    public int[] TO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pha_reminders_list);
        phaDbHlpr = new dbAdapter(this);
        phaDbHlpr.open();
        fillData();
        registerForContextMenu(getListView());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pha__reminder__list, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, PHA_Reminder_Entry.class);
        i.putExtra(dbAdapter.REM_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_menu_item_longpress, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_insert:
                Intent j = new Intent(this, PHA_Reminder_Entry.class);
                startActivityForResult(j, ACTIVITY_CREATE);
                return true;
            case R.id.menuAction_settings:
                Intent i = new Intent(this, PHA_RemTaskPrefs.class);
                startActivity(i);
                return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void createReminder() {
        Intent j = new Intent(this, PHA_Reminder_Entry.class);
        startActivityForResult(j, ACTIVITY_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //this is for reloading the list
        fillData();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lp_menu_delete:
                //delete the selected task
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                phaDbHlpr.deleteReminder(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void fillData() {
        /*
		 * When I�m inside the fillData() method, I fetch all the reminders
		 * from the database
		 * uses the manage startManagingCursor() method,
		 * which is present on the Activity class. This method allows the
		 * activity to take care of managing the given Cursor�s life cycle
		 * based on the activity�s life cycle. For example, when the activity
		 * is stopped, the activity automatically calls deactivate()
		 * on the Cursor, and when the activity is later restarted, it calls
		 * requery() for you. When the activity is destroyed, all managed
		 * Cursors are closed automatically.
		 */
        Cursor remindersCursor = phaDbHlpr.getReminders();
        /*
        @FIXME 00 startManagingCursor ??
        SimpleCursorAdapter as well
         */
        startManagingCursor(remindersCursor);
        //Create and array to specify the fields we want, only the title
        String[] FROM = new String[]{dbAdapter.REM_TITLE};
        //and an array of the fields we want to bind to the view
        int[] TO = new int[]{R.id.text1};
        //Create a simple cursor adapter and set it to display
        SimpleCursorAdapter reminders = new SimpleCursorAdapter(this, R.layout.reminder_entry,
                remindersCursor, FROM, TO);
        setListAdapter(reminders);
    }

}
