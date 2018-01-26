package com.tnk.pha;

import android.content.Context;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.tnk.R;
import com.tnk.db.Contract_Reminder;
import com.tnk.db.dbAdapter;

import java.util.List;

public class PHA_Reminder_List extends AppCompatActivity {

    private static final String TAG = "Reminder List";
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private dbAdapter phaDbHlpr;
    private Button dbAdd;
    public ListView lvReminders;
    public String[] FROM;
    public int[] TO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pha_reminders_list);

        Toolbar toolbar = findViewById(R.id.tb_PHA_Reminders);

        setSupportActionBar(toolbar);
        phaDbHlpr = new dbAdapter(this);
        phaDbHlpr.open();
        lvReminders = findViewById(R.id.list_pha_reminders);
        registerForContextMenu(lvReminders);

        //call fill data after the LV and other objects have
        //been instantiated
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pha__reminder__list, menu);
        return true;
    }

    /*
    @FIXME
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, PHA_Reminder_Entry.class);
        i.putExtra(dbAdapter.REM_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }
    */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_menu_item_longpress, menu);
    }

    /*
    @FIXME

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
    */

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
        setListAdapter
         */

        String[] fromColumns = {Contract_Reminder.ReminderEntry.COLUMN_TITLE};
        int[] toViews = {R.id.remindView};

        /*
        startManagingCursor(remindersCursor);
        //Create and array to specify the fields we want, only the title
        String[] FROM = new String[]{dbAdapter.REM_TITLE};
        //and an array of the fields we want to bind to the view
        int[] TO = new int[]{R.id.text1};
        */

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.reminder_list_layout, remindersCursor, fromColumns, toViews, 0);
        lvReminders.setAdapter(adapter);
        lvReminders.setOnItemClickListener(mMessageClickHandler);
    }

    private AdapterView.OnItemClickListener mMessageClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Context context = getApplicationContext();
            Toast.makeText(context,
                    "view " + view.getId() + " int " + i + " long " + l,
                    Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.menu_insert:
                //User chose the "+Reminder" item, show the app settings UI...
                createReminder();
                return true;
            default:
                // If we got here, the user's actions was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}