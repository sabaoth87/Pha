package com.tnk.pha;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tnk.R;
import com.tnk.db.Contract_Tool;
import com.tnk.db.DbHelper_Reminders;
import com.tnk.db.DbHelper_Tools;
import com.tnk.db.ToolCursorAdapter;

//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;


public class PHA_Workbench_Tool_List extends AppCompatActivity {



    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    public String[] lvIds = {};
    private String TAG="PHA:WB(List)";
    /**
     * START
     * #DatabaseHandler
     * #DB
     * #DBH
     * START
     */
    private Button btn_WB_load;
    private TextView tv_WB_main;
    private ListView lv_WB_main;
    /**
     * END
     * #DatabaseHandler
     * #DB
     * #DBH
     * END
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workbench);
        Toolbar toolbar = findViewById(R.id.tb_Workbench);
        setSupportActionBar(toolbar);

        /*
         * START
         *  #DatabaseHandler
         *  #DB
         *  #DBH
         *  START
         */

        btn_WB_load = findViewById(R.id.btn_load_tools);
        tv_WB_main = findViewById(R.id.tv_workbench_main);
        lv_WB_main = findViewById(R.id.lv_pha_workbench);


        // Try to 'make' the db and table
        Context context = getApplicationContext();
        DbHelper_Tools dbHelper_tools = new DbHelper_Tools(context);
        SQLiteDatabase db = dbHelper_tools.getWritableDatabase();
        dbHelper_tools.close();


        btn_WB_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.v(TAG, "ATTEMPT: Loading all tools");
                luAllTools();
            }
        });
        lv_WB_main.setOnItemClickListener(mMessageClickHandler);
        /*
         * END
         * #DatabaseHandler
         * #DB
         * #DBH
         * END
         */
        luAllTools();
    }

    private AdapterView.OnItemClickListener mMessageClickHandler = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
            Context context = getApplicationContext();
            Log.v(TAG, "<INFO>    Sending i: " + lvIds[i]);
            Intent editIntent = new Intent(context, PHA_Workbench_Tool_View.class);
            editIntent.putExtra(Contract_Tool.ToolEntry._ID, lvIds[i]);
            startActivityForResult(editIntent, ACTIVITY_EDIT);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_workbench, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.menu_item_wb_settings:
            //User chose the "Settings" item, show the app settings UI...
            return true;

            case R.id.menu_item_wb_add_tool:
                Intent c = new Intent(this, PHA_Workbench_Tool_Edit.class);
                Log.v(TAG, "Opening the Tool Edit UI...");
                startActivity(c);
                return true;

            case R.id.menu_item_wb_db:
                /*
                @TODO ## - Add 'db operations' function(s)
                 */
                Intent d = new Intent(this, PHA_Workbench_Db_Tools.class);
                Log.v(TAG, "Opening the Db Edit UI...");
                startActivity(d);
                return true;

            case R.id.menu_item_wb_search_tool:
                //User would like to search for a tool
                /*
                @TODO 02 Add 'Search Tool' UI
                 */
                return true;

            default:
                // If we got here, the user's actions was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * START
     * #DatabaseHandler
     * #DB
     * #DBH
     * START
     */

    public void luAllTools(){
        Context context = getApplicationContext();
        DbHelper_Tools dbHandler = new DbHelper_Tools(context);

        Cursor queryResult = dbHandler.findAllTools();
        int totalList = queryResult.getCount();
        int count = 0;
        lvIds = new String[queryResult.getCount()];

        while (queryResult.moveToNext()) {
            Log.v(TAG, "Query Loop " + count + "/" + totalList);
            int index;
            index = queryResult.getColumnIndexOrThrow(Contract_Tool.ToolEntry._ID);
            String entryId = queryResult.getString(index);
            lvIds[count] = entryId;
            count++;
        }


        //Setup cursor adapter using cursor from the last step
        ToolCursorAdapter toolAdapter = new ToolCursorAdapter(this, queryResult);
        //Attach cursor adapter to the ListView
        lv_WB_main.setAdapter(toolAdapter);

        tv_WB_main.setText(queryResult.toString());
        //queryResult.close();
    }

    /*
    @TODO ## - LongPress Tool List, deleteTool

    public void deleteToolByID(View view) {
        Context context = getApplicationContext();
        DbHelper_Tools dbHandler = new DbHelper_Tools(context);
        boolean result = dbHandler.deleteToolById(et_ToolID.getText().toString());

        if (result) {
            tv_WB_main.setText(R.string.tvMain_toolDeleted);
        } else
            tv_WB_main.setText(R.string.tvMain_noSuchEntry);
    }
         */
    /*
     * END
     * #DatabaseHandler
     * #DB
     * #DBH
     * END
     */
}