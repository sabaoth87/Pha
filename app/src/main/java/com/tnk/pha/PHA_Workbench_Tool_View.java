package com.tnk.pha;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tnk.R;
import com.tnk.db.Contract_Tool;
import com.tnk.db.DbHelper_Tools;
import com.tnk.db.Item_Tool;

//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;


public class PHA_Workbench_Tool_View extends AppCompatActivity {

    /**
     * START
     * #DatabaseHandler
     * #DB
     * #DBH
     * START
     */

    private String TAG="PHA:WB(View)";

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private Bundle extras;

    private ImageButton btn_WB_edit;

    private Item_Tool currentTool;

    private TextView tv_WB_main;
    private TextView tv_WB_id;
    private TextView tv_WB_cat;
    private TextView tv_WB_uses;
    private TextView tv_WB_name;
    private TextView tv_WB_quantity;
    private TextView tv_WB_brand;
    private TextView tv_WB_quality;
    private TextView tv_WB_type;
    private TextView tv_WB_ammo;
    private TextView tv_WB_size;
    private TextView tv_WB_status;
    private TextView tv_WB_location;
    private ImageView iv_WB_tool_image;

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
        setContentView(R.layout.pha_workbench_tool_entry_edit);

        /*
         * START
         *  #DatabaseHandler
         *  #DB
         *  #DBH
         *  START
         */

        btn_WB_edit = findViewById(R.id.ib_tool_edit);

        iv_WB_tool_image = findViewById(R.id.iv_tool_image);

        tv_WB_id = findViewById(R.id.tv_tool_id);
        tv_WB_name = findViewById(R.id.tv_tool_name);
        tv_WB_brand = findViewById(R.id.tv_tool_brand);
        tv_WB_type = findViewById(R.id.tv_tool_type);
        tv_WB_quality = findViewById(R.id.tv_tool_quality);
        tv_WB_quantity = findViewById(R.id.tv_tool_quantity);
        tv_WB_size = findViewById(R.id.tv_tool_size);
        tv_WB_status = findViewById(R.id.tv_tool_status);
        tv_WB_ammo = findViewById(R.id.tv_tool_ammo);
        tv_WB_cat = findViewById(R.id.tv_tool_cat);
        tv_WB_uses = findViewById(R.id.tv_tool_uses);
        tv_WB_location = findViewById(R.id.tv_tool_location);

        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras == null) {
                tv_WB_name.setText(R.string.string_null);
            } else {
                Log.v(TAG, "Setting intent values");

                Context context = getApplicationContext();
                DbHelper_Tools dbHelperTools = new DbHelper_Tools(context);

                Cursor cTool = dbHelperTools.findToolsByID(extras.getString(Contract_Tool.ToolEntry._ID));
                beginEntry(cTool);
            }
        }


        btn_WB_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.v(TAG, "ATTEMPT: Add Tool");
                addTool();
            }
        });
        /*
         * END
         * #DatabaseHandler
         * #DB
         * #DBH
         * END
         */
    }

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
                /*
                @TODO 00 Add 'Add Tool' UI
                 */
                //User chose the "Add Tool" item, show the "Add Tool" UI...
                addTool();
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

    public void addTool() {
        Intent editEntry = new Intent(this, PHA_Workbench_Tool_Edit.class);
                /*
                TODO ## - Pass the tool _id to edit activity
                 */
        editEntry.putExtra(Contract_Tool.ToolEntry._ID, currentTool.getID());
        startActivityForResult(editEntry, ACTIVITY_EDIT);
    }


    public void deleteToolByID(View view) {
        Context context = getApplicationContext();
        DbHelper_Tools dbHandler = new DbHelper_Tools(context);
        boolean result = dbHandler.deleteToolById(""+currentTool.getID());

        if (result) {
            tv_WB_main.setText(R.string.tvMain_toolDeleted);
        } else
        tv_WB_main.setText(R.string.tvMain_noSuchEntry);
    }

    public void beginEntry(Cursor iTool) {
        //load the reminder information into the UI here
        if (PHA_Util_Vars.mode_debug){Log.v(TAG,"Trying to load Editable reminder...");}
        if (iTool==null) {
            if (PHA_Util_Vars.mode_debug){Log.v(TAG,"Cursor with the Editable reminder was found to be null!");}
            tv_WB_name.setText(R.string.string_null);
        }
        else {
            if (PHA_Util_Vars.mode_debug){Log.v(TAG,"Cursor is valid! Populating UI");}
            //int entryIdId = reminder.getColumnIndexOrThrow(Contract_Reminder.ReminderEntry._ID);

            while(iTool.moveToNext()) {
                int index;

                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_NAME);
                String toolName = iTool.getString(index);
                tv_WB_name.setText(toolName);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_BRAND);
                String toolBrand = iTool.getString(index);
                tv_WB_brand.setText(toolBrand);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_TYPE);
                String toolType = iTool.getString(index);
                tv_WB_type.setText(toolType);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_QUALITY);
                String toolQuality = iTool.getString(index);
                tv_WB_quality.setText(toolQuality);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_QUANTITY);
                String toolQuantity = iTool.getString(index);
                tv_WB_quantity.setText(toolQuantity);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_SIZE);
                String toolSize = iTool.getString(index);
                tv_WB_size.setText(toolSize);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_STATUS);
                String toolStatus = iTool.getString(index);
                tv_WB_status.setText(toolStatus);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_AMMO);
                String toolAmmo = iTool.getString(index);
                tv_WB_ammo.setText(toolAmmo);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_CATEGORY);
                String toolCategory = iTool.getString(index);
                tv_WB_cat.setText(toolCategory);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_USES);
                String toolUses = iTool.getString(index);
                tv_WB_uses.setText(toolUses);
                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry.COLUMN_NAME_LOCATION);
                String toolLocation = iTool.getString(index);
                tv_WB_location.setText(toolLocation);


                index = iTool.getColumnIndexOrThrow(Contract_Tool.ToolEntry._ID);
                long id = iTool.getLong(index);

                Log.v(TAG, "Editable Reminder entry ID: " + id);
            }

        }
    }

    /*
     * END
     * #DatabaseHandler
     * #DB
     * #DBH
     * END
     */
}