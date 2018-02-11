package com.tnk.pha;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import com.tnk.db.Contract_Issue;
import com.tnk.db.Contract_Reminder;
import com.tnk.db.DbHelper_Issues;
import com.tnk.db.DbHelper_Reminders;
import com.tnk.db.IssueCursorAdapter;
import com.tnk.db.ReminderCursorAdapter;


public class PHA_Issue_List extends AppCompatActivity {

    private static final String TAG = "PHA:IssueList";
    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    public String[] lvIds = {};
    public ListView lvIssues;
    public TextView tvIssues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pha_issues_list);

        Toolbar toolbar = findViewById(R.id.tb_pha_issues);

        Context context = getApplicationContext();
        DbHelper_Issues dbHelper_issues = new DbHelper_Issues(context);
        SQLiteDatabase db = dbHelper_issues.getWritableDatabase();

        boolean table_ready = dbHelper_issues.checkTable(db);

        if (!table_ready){
            boolean table_created = dbHelper_issues.create_table(context);
        }

        setSupportActionBar(toolbar);
        lvIssues = findViewById(R.id.list_pha_issues);
        tvIssues = findViewById(R.id.tv_issues);
        registerForContextMenu(lvIssues);

        lvIssues.setOnItemClickListener(mMessageClickHandler);
        //call fill data after the LV and other objects have
        //been instantiated
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pha_issue_list, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_lp_issue_list, menu);
    }

    private void createIssue() {
        Intent j = new Intent(this, PHA_Issue_Entry.class);
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
        Context context = getApplicationContext();
        switch (item.getItemId()) {
            case R.id.menu_lp_issue_delete:
                //delete the selected issue
                if (PHA_Util_Vars.mode_debug) {Log.v(TAG, "Attempting to delete issue");}
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                //phaDbHlpr.deleteReminder(info.id);
                    /*
                    @TODO - Ensure the _id is properly fetched
                    Get the 'edit' option working first
                     */

                    Toast.makeText(context,
                            "MenuItem.Id " + item.getItemId()
                                    + " MenuItem.toString " + item.toString(),
                            Toast.LENGTH_SHORT).show();
                fillData();
                return true;
            case R.id.menu_lp_issue_edit:
                //edit the selected issue
                Toast.makeText(context,
                        "MenuItem.Id " + item.getItemId()
                                + " MenuItem.toString " + item.toString(),
                        Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    private void fillData() {
        Context context = getApplicationContext();
        DbHelper_Issues dbHelper = new DbHelper_Issues(context);
        Cursor queryResult = dbHelper.findAllIssues();
        Log.v(TAG, "queryResult = " + queryResult.getCount());
        int totalList = queryResult.getCount();
        int count = 0;
        lvIds = new String[queryResult.getCount()];
        while (queryResult.moveToNext()) {
            int index;
            Log.v(TAG, "Query Loop " + count + "/" + totalList);
            index = queryResult.getColumnIndexOrThrow(Contract_Issue.IssueEntry._ID);
            String entryId = queryResult.getString(index);
            lvIds[count] = entryId;
            count++;
        }


        //Setup Cursor adapter using the Cursor from the last step
        IssueCursorAdapter issueAdapter = new IssueCursorAdapter(this, queryResult);
        //Attach Cursor adapter to the ListView
        lvIssues.setAdapter(issueAdapter);
        tvIssues.setText("Count =" + queryResult.getCount() + " " + lvIds.toString());
        dbHelper.close();
    }

    private AdapterView.OnItemClickListener mMessageClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
            Context context = getApplicationContext();
            Log.v(TAG, "<INFO>    Sending i: " + lvIds[i]);
            Intent editIntent = new Intent(context, PHA_Issue_Entry.class);
            editIntent.putExtra(Contract_Issue.IssueEntry._ID, lvIds[i]);
            startActivityForResult(editIntent, ACTIVITY_EDIT);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.menu_issue_insert:
                //User chose the "+Reminder" item, show the app settings UI...
                createIssue();
                return true;
            case R.id.menu_issue_list:
                return true;
            case R.id.menu_issue_settings:
                return true;

            default:
                // If we got here, the user's actions was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}