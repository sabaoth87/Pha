package com.tnk.pha;

import static android.provider.BaseColumns._ID;
import static com.tnk.db.dbConstants.AUTHOR;
import static com.tnk.db.dbConstants.COMMENT;
import static com.tnk.db.dbConstants.MEMO;
import static com.tnk.db.dbConstants.TABLE_NAME;
import static com.tnk.db.dbConstants.TIME;
import static com.tnk.db.dbConstants.TITLE;
import static com.tnk.db.dbConstants.DATE;
import static com.tnk.db.dbConstants.TAGS;
import static com.tnk.db.dbConstants.CATEGORY;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.tnk.R;

public class PHA_Db extends Activity implements OnClickListener, OnItemSelectedListener {

	public PHA_DbData memos;
	private static final String TAG = "PHA_Memos";
	public static String[] FROM = { _ID, TIME, DATE, TITLE, AUTHOR, MEMO, COMMENT };
	public static String ORDER_BY = TIME + " DESC";
	public static int[] TO = { R.id.rowid, R.id.time, R.id.date, R.id.title, R.id.author, R.id.memo, R.id.comment};
	private EditText titleET;
	private EditText authorET;
	private EditText memoET;
	private EditText commentET;
	private EditText tagsET;
	//private Button memoSave;
	//private Button memoShow;
	private CharSequence toastTextSave = "Saved!";
	private Toast saveToast;
	private String category;
	private Bundle extras;
	private String newString;
	int duration = Toast.LENGTH_SHORT;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pha_db);
		//intialize the db hopefully
		memos = new PHA_DbData(this);	
		titleET = (EditText) findViewById(R.id.memoTitleEntry);
		authorET = (EditText) findViewById(R.id.memoAuthorEntry);
		memoET = (EditText) findViewById(R.id.memoEntry);
		commentET = (EditText) findViewById(R.id.memoCommentEntry);
		tagsET = (EditText) findViewById(R.id.memoTagsEntry);
		Log.v(TAG, "oncreate");
		//((Button) findViewById(R.id.memosSaveBttn)).setOnClickListener(this);
		//((Button) findViewById(R.id.memosShowBttn)).setOnClickListener(this);
		Spinner categorySpinner = (Spinner) findViewById(R.id.memoSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.PHAY_DbCategories, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		categorySpinner.setAdapter(adapter);
		categorySpinner.setOnItemSelectedListener(this);
		
		if (savedInstanceState == null) {
		    extras = getIntent().getExtras();
		    if(extras == null) {
		        newString= null;
		    } else {
		        newString= extras.getString("want");
		        memoET.setText(newString);
		    }
		} else {
		    newString= (String) savedInstanceState.getSerializable("want");
		}
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pha__db_data, menu);
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
		case R.id.memoMenu_opt_1:
			try{
				addMemo(titleET.getText().toString(), memoET.getText().toString(), commentET.getText().toString(), authorET.getText().toString(), category, tagsET.getText().toString());
			} finally {
				memos.close();
			}
			break;
		case R.id.memoMenu_opt_2:
			Log.v(TAG, "felt show");
			Intent i = new Intent(this, PHA_DbHandler.class);
			startActivity(i);
			break;
		case R.id.memoMenu_opt_3:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onClick(View v) {
//		switch (v.getId()){
//		case R.id.memosSaveBttn:
//			Log.v(TAG, "felt entry");
//			try{
//				addMemo(titleET.getText().toString(), memoET.getText().toString(), commentET.getText().toString(), authorET.getText().toString());
//			} finally {
//				memos.close();
//			}
//			break;
//		case R.id.memosShowBttn:
//			Log.v(TAG, "felt show");
//			Intent i = new Intent(this, PHA_MemoDBHandler.class);
//			startActivity(i);
//			break;
//		}
		
	}
	
	public void addMemo(String title, String memo, String comment, String author, String category, String tags){
		/*
		 * Insert a new entry into the memos.db
		 */
		Context context = getApplicationContext();	
		saveToast = Toast.makeText(context, toastTextSave, duration);
		long timeMilliSeconds= System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        Date resultdate = new Date(timeMilliSeconds);
        
		Log.v(TAG, "start add");
		SQLiteDatabase db = memos.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TIME, System.currentTimeMillis());
		values.put(DATE, sdf.format(resultdate));
		values.put(TITLE, title);
		values.put(AUTHOR, author);
		values.put(MEMO, memo);
		values.put(COMMENT, comment);
		values.put(CATEGORY, category);
		values.put(TAGS, tags);
		db.insert(TABLE_NAME, null, values);
		saveToast.show();
		Log.v(TAG, "finish add");
	}
	
	public Cursor getMemos(){
		// Perform a managed query. The Activity will handle closing
		// and re-querying the cursor when needed.
		Log.v(TAG, "start get");
		SQLiteDatabase db = memos.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
		null, ORDER_BY);
		startManagingCursor(cursor);
		Log.v(TAG, "returning get");
		return cursor;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View view, int pos,
			long id) {
		switch (pos){
		case 0:
			category = "Common Knowledge";
			break;
		case 1:
			category = "Memo";
			break;
		case 2:
			category = "To Do";
			break;
		case 3:
			category = "Formula";
			break;
		case 4:
			category = "Idea";
			break;
		case 5:
			category = "Prototype";
			break;
		case 6:
			category = "Improvement";
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	

}