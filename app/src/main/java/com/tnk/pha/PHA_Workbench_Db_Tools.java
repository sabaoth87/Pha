package com.tnk.pha;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tnk.R;
import com.tnk.db.Contract_Tool;
import com.tnk.db.DbHelper_Tools;
import com.tnk.db.Item_Tool;

import java.io.IOException;


public class PHA_Workbench_Db_Tools extends FragmentActivity {

    private String TAG="PHA:WB-DbTools";

    private static final int ACTIVITY_CREATE = 0;
    private static final int ACTIVITY_EDIT = 1;
    private static final int ACTIVITY_RUN = 3;
    private Bundle extras;

    private ImageButton ibtn_WBDB_export;
    private ImageButton ibtn_WBDB_import;

    private TextView tv_WBDB_main;
    private TextView tv_WBDB_import;
    private TextView tv_WBDB_export;
    private TextView tv_WBDB_import_path;
    private TextView tv_WBDB_export_path;
    private Spinner sp_WBDB_import_path;
    private Spinner sp_WBDB_export_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pha_workbench_db_tools);

        ibtn_WBDB_export = findViewById(R.id.ibtn_WBDB_export);
        ibtn_WBDB_import = findViewById(R.id.ibtn_WBDB_import);

        sp_WBDB_export_path = findViewById(R.id.sp_WBDB_export_path);
        sp_WBDB_import_path = findViewById(R.id.sp_WBDB_import_path);

        tv_WBDB_export_path = findViewById(R.id.tv_WBDB_export_path);
        tv_WBDB_import_path = findViewById(R.id.tv_WBDB_import_path);

        //                          FOR MY SPINNERS
        //Create and ArrayAdapter using the string array and default layout
        ArrayAdapter<CharSequence> importPathAdapter = ArrayAdapter.createFromResource(this,
                R.array.db_import_paths, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> exportPathAdapter = ArrayAdapter.createFromResource(this,
                R.array.db_export_paths, android.R.layout.simple_spinner_item);
        //Apply the adapter to the spinner
        sp_WBDB_import_path.setAdapter(importPathAdapter);
        sp_WBDB_export_path.setAdapter(exportPathAdapter);

        sp_WBDB_import_path.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                tv_WBDB_import_path.setText(sp_WBDB_import_path.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {
                tv_WBDB_import_path.setText(sp_WBDB_import_path.getSelectedItem().toString());
            }

        });

        sp_WBDB_export_path.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                tv_WBDB_export_path.setText(sp_WBDB_export_path.getSelectedItem().toString());
            }

            public void onNothingSelected(AdapterView<?> parent) {
                tv_WBDB_export_path.setText(sp_WBDB_export_path.getSelectedItem().toString());
            }

        });

        ibtn_WBDB_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.v(TAG, "ATTEMPT: Export Db");
                Context context = getApplicationContext();
                DbHelper_Tools dbHelperTools = new DbHelper_Tools(context);

                try {
                    dbHelperTools.exportDatabase(sp_WBDB_export_path.getSelectedItem().toString());
                } catch (IOException e){
                    Log.v(TAG, e.toString());
                }
            }
        });

        ibtn_WBDB_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Log.v(TAG, "ATTEMPT: Import Db");
                Context context = getApplicationContext();
                DbHelper_Tools dbHelperTools = new DbHelper_Tools(context);

                try {
                    dbHelperTools.importDatabase(sp_WBDB_import_path.getSelectedItem().toString());
                } catch (IOException e){
                    Log.v(TAG, e.toString());
                }
            }
        });

    }

}