package com.tnk.pha;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import com.tnk.R;

public class PHA_Sudoku extends Activity implements OnClickListener {

    private static final String TAG = "PHA:LANX(su)";
    protected static final int DIFFICULTY_CONTINUE = -1;
    public static final String KEY_DIFFICULTY = "tk.phalanx.v2.difficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pha_sudoku);
        // Set up click listeners for all the buttons
        View continueButton = findViewById(R.id.sudokuButtonContinue);
        continueButton.setOnClickListener(this);
        View newButton = findViewById(R.id.sudokuButtonNewGame);
        newButton.setOnClickListener(this);
        View aboutButton = findViewById(R.id.sudokuButtonAbout);
        aboutButton.setOnClickListener(this);
        View exitButton = findViewById(R.id.sudokuButtonExit);
        exitButton.setOnClickListener(this);
        // If the activity is restarted, do a continue next time
        getIntent().putExtra(KEY_DIFFICULTY, DIFFICULTY_CONTINUE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_layout_one, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sudokuButtonAbout:
                Intent i = new Intent(this, SudokuAbout.class);
                startActivity(i);
                break;
            case R.id.sudokuButtonNewGame:
                openNewGameDialog();
                break;
            case R.id.sudokuButtonContinue:
                startGame(PHA_SudokuGame.DIFFICULTY_CONTINUE);
                break;
            // more buttons go here later
        }
    }

    /**
     * Ask the user which difficulty they want
     */
    private void openNewGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.sudokuNGTitle)
                .setItems(R.array.difficulty, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        startGame(i);
                    }
                })
                .show();
    }

    /**
     * Start a game with the given difficulty level
     */
    public void startGame(int i) {
        Log.d(TAG, "clicked on " + i);
        Intent intent = new Intent(PHA_Sudoku.this, PHA_SudokuGame.class);
        intent.putExtra(PHA_SudokuGame.KEY_DIFFICULTY, i);
        startActivity(intent);
    }
}
