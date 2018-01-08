package com.tnk.pha;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tnk.R;

public class PHA_Search extends Activity implements OnClickListener {
    private static String ACTIVITYNAME = "Search";
    //public TextView srchEntryEcho;
    public EditText searchEntry;
    public String PHAY_SearchEntry;
    public Button entryBtn;
    public String searchIntent = "searchIntent";
    private Bundle extras;
    private String newString;
    private String[] searchArrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pha_search);

        Log.v(ACTIVITYNAME, "onCreate");

        ((Button) findViewById(R.id.searchBtn)).setOnClickListener(this);

        entryBtn = (Button) findViewById(R.id.searchBtn);
        //srchEntryEcho = (TextView) findViewById(R.id.srchEntryEcho);
        searchEntry = (EditText) findViewById(R.id.phaySearchETxt);


        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if (extras == null) {
                newString = null;
            } else {
                newString = extras.getString("want");
                beginSearch(newString);
            }
        } else {
            newString = (String) savedInstanceState.getSerializable("want");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout_one, menu);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchBtn:
                Intent intent = new Intent(this, PHA_SearchDisplay.class);
                PHAY_SearchEntry = searchEntry.getText().toString();
                StringBuilder str = new StringBuilder("");
                str.append(PHAY_SearchEntry);
                //srchEntryEcho.setText(PHAY_SearchEntry);
                Toast.makeText(getApplication(), "Searching...", Toast.LENGTH_LONG).show();
                intent.putExtra(searchIntent, PHAY_SearchEntry);
                startActivity(intent);
                break;
            default:
                throw new RuntimeException("Unknown button ID");
                /*
				 * more buttons go here later if i can get this to
				 * work. For now only seperate methods for each action
				 * with an onClick call from the button itself works
				 */
        }
    }

    public void beginSearch(String wantString) {
        Intent intent = new Intent(this, PHA_SearchDisplay.class);

        searchArrs = wantString.split(" ", 2);

        //PHAY_SearchEntry = searchEntry.getText().toString();
        //StringBuilder str = new StringBuilder("");
        //str.append (PHAY_SearchEntry);
        //srchEntryEcho.setText(PHAY_SearchEntry);

        intent.putExtra(searchIntent, searchArrs[1]);
        startActivity(intent);
        finish();
    }


}