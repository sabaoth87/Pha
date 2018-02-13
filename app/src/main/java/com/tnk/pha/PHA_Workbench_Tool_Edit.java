package com.tnk.pha;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tnk.R;
import com.tnk.db.Contract_Tool;
import com.tnk.db.DbHelper_Tools;
import com.tnk.db.Item_Tool;



public class PHA_Workbench_Tool_Edit extends FragmentActivity {

    /**
     * START
     * #DatabaseHandler
     * #DB
     * #DBH
     * START
     */

    private String TAG="PHA:WB(Edit)";

    private Button btn_WB_add;
    private Button btn_WB_clear;

    /*
    TODO xx - Complete editable tool fields
    The following TextView should be editable/dynamically
    loaded in the future
     */
    private TextView tv_WB_cat;
    private TextView tv_WB_uses;


    private EditText et_WB_name;
    private EditText et_WB_quantity;
    private Spinner sp_WB_brand;
    private Spinner sp_WB_quality;
    private Spinner sp_WB_type;
    private Spinner sp_WB_ammo;
    private Spinner sp_WB_size;
    private Spinner sp_WB_status;
    private Spinner sp_WB_location;

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
        btn_WB_add = findViewById(R.id.btn_tool_add);
        btn_WB_clear = findViewById(R.id.btn_tool_clear_fields);

        et_WB_name = findViewById(R.id.et_edit_tool_name);
        et_WB_quantity = findViewById(R.id.et_edit_tool_quantity);

        sp_WB_brand = findViewById(R.id.sp_edit_tool_brand);
        sp_WB_type = findViewById(R.id.sp_edit_tool_type);
        sp_WB_ammo = findViewById(R.id.sp_edit_tool_ammo);
        sp_WB_quality = findViewById(R.id.sp_edit_tool_quality);
        sp_WB_size = findViewById(R.id.sp_edit_tool_size);
        sp_WB_status = findViewById(R.id.sp_edit_tool_status);
        sp_WB_location = findViewById(R.id.sp_edit_tool_location);


        btn_WB_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.v(TAG, "ATTEMPT: Add Tool");
                addTool();
            }
        });

        btn_WB_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "ATTEMPT: Clear Fields!");
                clearInputs();
            }
        });


        //                          FOR MY SPINNERS
        //Create and ArrayAdapter using the string array and default layout
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.tool_types, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> brandAdapter = ArrayAdapter.createFromResource(this,
                R.array.tool_brands, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> ammoAdapter = ArrayAdapter.createFromResource(this,
                R.array.tool_ammo, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> qualAdapter = ArrayAdapter.createFromResource(this,
                R.array.tool_quality, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.tool_size, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.tool_status, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> locationAdapter = ArrayAdapter.createFromResource(this,
                R.array.tool_location, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ammoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qualAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        sp_WB_type.setAdapter(typeAdapter);
        sp_WB_brand.setAdapter(brandAdapter);
        sp_WB_ammo.setAdapter(ammoAdapter);
        sp_WB_quality.setAdapter(qualAdapter);
        sp_WB_size.setAdapter(sizeAdapter);
        sp_WB_status.setAdapter(statusAdapter);
        sp_WB_location.setAdapter(locationAdapter);
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
            case R.id.menuAction_settings:
            //User chose the "Settings" item, show the app settings UI...
            return true;

            case R.id.menuAction_addTool:
                addTool();
                return true;

            case R.id.menuAction_listTools:
                //User has chosen to list all owned tools in the main display
                Context context = getApplicationContext();
                Intent editIntent = new Intent(context, PHA_Workbench_Tool_List.class);
                startActivityForResult(editIntent, 0);
                return true;

            case R.id.meanAction_searchTool:
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
        Context context = getApplicationContext();
        DbHelper_Tools dbHandler = new DbHelper_Tools(context);

        Item_Tool new_tool = new Item_Tool();

        String tool_name = et_WB_name.getText().toString();
        new_tool.setToolName(tool_name);
        String tool_brand = sp_WB_brand.getSelectedItem().toString();
        new_tool.setToolBrand(tool_brand);
        String tool_type = sp_WB_type.getSelectedItem().toString();
        new_tool.setToolType(tool_type);
        String tool_quality = sp_WB_quality.getSelectedItem().toString();
        new_tool.setToolQuality(tool_quality);
        String tool_quantity = et_WB_quantity.getText().toString();
        new_tool.setToolQuantity(tool_quantity);
        String tool_size = sp_WB_size.getSelectedItem().toString();
        new_tool.setToolSize(tool_size);
        String tool_status = sp_WB_status.getSelectedItem().toString();
        new_tool.setToolStatus(tool_status);
        String tool_ammo = sp_WB_ammo.getSelectedItem().toString();
        new_tool.setToolAmmo(tool_ammo);
        //String tool_category = .getSelectedItem().toString();
        String tool_category = " category ";
        new_tool.setToolLocation(tool_category);
        //String tool_uses = et_WB_uses.getSelectedItem().toString();
        String tool_uses = "this, that, fix, build";
        new_tool.setToolLocation(tool_uses);
        String tool_location = sp_WB_location.getSelectedItem().toString();
        new_tool.setToolLocation(tool_location);

        Long newEntryID = dbHandler.addToolHandler(new_tool);

        Toast toast = Toast.makeText(context, "Entry " + newEntryID + " added to Db!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP|Gravity.END,0,0);
        toast.show();
        //Clear the inputs
        clearInputs();
    }


    public void clearInputs() {
        et_WB_name.setText("");
        et_WB_quantity.setText("");
        sp_WB_type.refreshDrawableState();
        sp_WB_brand.refreshDrawableState();
        sp_WB_quality.refreshDrawableState();
        sp_WB_size.refreshDrawableState();
        sp_WB_status.refreshDrawableState();
        sp_WB_ammo.refreshDrawableState();
        sp_WB_location.refreshDrawableState();
    }
    /*
     * END
     * #DatabaseHandler
     * #DB
     * #DBH
     * END
     */
}