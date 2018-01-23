package com.tnk.pha;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tnk.R;
import com.tnk.db.ToolContract;
import com.tnk.db.ToolCursorAdapter;
import com.tnk.db.ToolDbHelper;
import com.tnk.db.ItemTool;


public class Workbench extends AppCompatActivity {

    /**
     * START
     * #DatabaseHandler
     * #DB
     * #DBH
     * START
     */
    private Button dbAdd;
    private Button dbDelete;
    private Button dbFind;
    private Button dbLoad;
    private Button dbUpdate;
    private TextView tv_Main;
    private ListView lv_Main;
    // @NOTE
    // some of these EditText should be spinners or drop-downs
    private EditText et_ToolName;
    private Spinner sp_ToolType;
    private Spinner sp_ToolBrand;
    private EditText et_ToolSize;
    private EditText et_ToolID;
    private String TAG="WorkBench";
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

        dbAdd = findViewById(R.id.btn_AddTool);
        dbDelete = findViewById(R.id.btn_DeleteTool);
        dbFind = findViewById(R.id.btn_FindTool);
        dbLoad = findViewById(R.id.btn_LoadToolList);
        dbUpdate = findViewById(R.id.btn_UpdateTool);
        et_ToolName = findViewById(R.id.et_ToolName);
        et_ToolSize = findViewById(R.id.et_ToolSize);
        et_ToolID = findViewById(R.id.et_ToolID);
        tv_Main = findViewById(R.id.tv_Main);
        lv_Main = findViewById(R.id.lv_Main);
        sp_ToolBrand = findViewById(R.id.sp_ToolBrand);
        sp_ToolType = findViewById(R.id.sp_ToolType);

        dbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.v(TAG, "ATTEMPT: Add Tool");
                addTool(view);
            }
        });

        dbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "ATTEMPT: Delete Tool");
                deleteToolByID(view);
            }
        });

        dbFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "ATTEMPT: Find Tool");
                luToolId(view);
            }
        });

        dbLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.v(TAG, "ATTEMPT: Loading all tools");
                luAllTools(view);
            }
        });
        lv_Main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(),lv_Main.getItemAtPosition(i).toString(),Toast.LENGTH_SHORT).show();
            }
        });

        //                          FOR MY SPINNERS
        //Create and ArrayAdapter using the string array and default layout
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_types, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> brandAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_brands, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        sp_ToolType.setAdapter(typeAdapter);
        sp_ToolBrand.setAdapter(brandAdapter);

        /*
         * END
         * #DatabaseHandler
         * #DB
         * #DBH
         * END
         */


        /* @NOTE fab support is down?
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
            case R.id.menuAction_settings:
            //User chose the "Settings" item, show the app settings UI...
            return true;

            case R.id.menuAction_addTool:
                /*
                @TODO Add Tool UI
                 */
                //User chose the "Add Tool" item, show the "Add Tool" UI...
                return true;

            case R.id.menuAction_listTools:
                /*
                @TODO
                 */
                //User has chosen to list all owned tools in the main display
                return true;

            case R.id.meanAction_searchTool:
                //User would like to search for a tool
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

    //@TRY
    // pass the COLUMN with which you would like to search
    public void luToolType(View view) {
        Context context = getApplicationContext();
        ToolDbHelper dbHandler = new ToolDbHelper(context);

        //@TODO
        //Test to make sure the spinner call toString is grabbing what I need
        Cursor queryResult = dbHandler.findToolsByType(sp_ToolType.toString());

        //Setup cursor adapter using cursor from the last step
        ToolCursorAdapter toolAdapter = new ToolCursorAdapter(this, queryResult);
        //Attach cursor adapter to the ListView
        lv_Main.setAdapter(toolAdapter);

        //queryResult.close();
        clearInputs();
    }

    public void luToolId(View view) {
        Context context = getApplicationContext();
        ToolDbHelper dbHelper = new ToolDbHelper(context);

        Cursor queryResult = dbHelper.findToolsByID(et_ToolID.toString());
        ToolCursorAdapter toolCursorAdapter = new ToolCursorAdapter(this, queryResult);
        lv_Main.setAdapter(toolCursorAdapter);
        tv_Main.setText(queryResult.getString(queryResult.getColumnIndex(ToolContract.ToolEntry.COLUMN_NAME_NAME)));
    }

    public void luAllTools(View view){
        Context context = getApplicationContext();
        ToolDbHelper dbHandler = new ToolDbHelper(context);

        Cursor queryResult = dbHandler.findAllTools();
        //Setup cursor adapter using cursor from the last step
        ToolCursorAdapter toolAdapter = new ToolCursorAdapter(this, queryResult);
        //Attach cursor adapter to the ListView
        lv_Main.setAdapter(toolAdapter);

        tv_Main.setText(queryResult.toString());
        //queryResult.close();
        clearInputs();
    }

    public void addTool(View view) {
        Context context = getApplicationContext();
        ToolDbHelper dbHandler = new ToolDbHelper(context);
        String tool_name = et_ToolName.getText().toString();
        String tool_type = sp_ToolType.getSelectedItem().toString();
        String tool_brand = sp_ToolBrand.getSelectedItem().toString();
        String tool_size = et_ToolSize.getText().toString();

        ItemTool new_tool = new ItemTool();

        new_tool.setToolName(tool_name);
        new_tool.setToolType(tool_type);
        new_tool.setToolBrand(tool_brand);
        new_tool.setToolSize(tool_size);

        Long newEntryID = dbHandler.addToolHandler(new_tool);

        Toast toast = Toast.makeText(context, "Entry " + newEntryID + " added to Db!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.END,0,0);
        toast.show();
        //Clear the inputs
        clearInputs();
    }


    public void deleteToolByID(View view) {
        Context context = getApplicationContext();
        ToolDbHelper dbHandler = new ToolDbHelper(context);
        boolean result = dbHandler.deleteToolById(et_ToolID.getText().toString());

        if (result) {
            clearInputs();
            tv_Main.setText(R.string.tvMain_toolDeleted);
        } else
            clearInputs();
            tv_Main.setText(R.string.tvMain_noSuchEntry);
    }

    public void clearInputs() {
        et_ToolName.setText("");
        et_ToolSize.setText("");
    }
    /*
     * END
     * #DatabaseHandler
     * #DB
     * #DBH
     * END
     */
}