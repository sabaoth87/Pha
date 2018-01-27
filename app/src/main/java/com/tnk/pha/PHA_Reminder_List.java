package com.tnk.pha;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tnk.R;
import com.tnk.db.Contract_Reminder;
import com.tnk.db.DbHelper_Reminders;
import com.tnk.db.ReminderCursorAdapter;


public class PHA_Reminder_List extends AppCompatActivity {

    private static final String TAG = "PHA:RemList";
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    public String[] lvIds = {};
    public ListView lvReminders;
    public TextView tvReminders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pha_reminders_list);

        Toolbar toolbar = findViewById(R.id.tb_PHA_Reminders);

        setSupportActionBar(toolbar);
        lvReminders = findViewById(R.id.list_pha_reminders);
        tvReminders = findViewById(R.id.tv_RemindersTV);
        registerForContextMenu(lvReminders);

        lvReminders.setOnItemClickListener(mMessageClickHandler);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.list_menu_item_longpress, menu);
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
                //phaDbHlpr.deleteReminder(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void fillData() {
        Context context = getApplicationContext();
        DbHelper_Reminders dbHelper = new DbHelper_Reminders(context);

        Cursor queryResult = dbHelper.findAllReminders();

        lvIds = new String[queryResult.getCount()];

        Log.v(TAG, "queryResult = " + queryResult.getCount());

        int totalList = queryResult.getCount();
        int count = 0;

        while (queryResult.moveToNext()) {
        //while(count<totalList) {
            int index;

            Log.v(TAG, "Query Loop " + count + "/" + totalList);
            index = queryResult.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry._ID);
            String entryId = queryResult.getString(index);

            lvIds[count] = entryId;
            count++;
        }


        //Setup Cursor adapter using the Cursor from the last step
        ReminderCursorAdapter reminderAdapter = new ReminderCursorAdapter(this, queryResult);
        //Attach Cursor adapter to the ListView
        lvReminders.setAdapter(reminderAdapter);
        tvReminders.setText("Count =" + queryResult.getCount() + " " + lvIds.toString());
        dbHelper.close();
    }

    private AdapterView.OnItemClickListener mMessageClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
            Context context = getApplicationContext();
            Toast.makeText(context,
                    "view " + view.getId() + " int " + i + " long " + id,
                    Toast.LENGTH_SHORT).show();

            //Contract_Reminder.ReminderEntry remmy = remin

            //adapterView.getItemAtPosition(i);

            //Cursor entryId =

            int castInt = ((int) id);

            Log.v(TAG, "<INFO> " + lvIds[0]);
            Log.v(TAG, "<INFO> " + lvIds[1]);
            Log.v(TAG, "<INFO> " + lvIds[2]);
            //Log.v(TAG, "<INFO> " + lvIds[3]);
            Log.v(TAG, "<INFO>    Sending i: " + lvIds[i]);
            Intent editIntent = new Intent(context, PHA_Reminder_Entry.class);
            editIntent.putExtra(Contract_Reminder.ReminderEntry._ID, lvIds[i]);
            startActivityForResult(editIntent, ACTIVITY_EDIT);
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