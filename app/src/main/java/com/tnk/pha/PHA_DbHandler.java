package com.tnk.pha;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;
import static com.tnk.pha.dbConstants.AUTHOR;
import static com.tnk.pha.dbConstants.COMMENT;
import static com.tnk.pha.dbConstants.MEMO;
import static com.tnk.pha.dbConstants.TABLE_NAME;
import static com.tnk.pha.dbConstants.TIME;
import static com.tnk.pha.dbConstants.TITLE;
import static com.tnk.pha.dbConstants.DATE;
import static com.tnk.pha.dbConstants.TAGS;
import static com.tnk.pha.dbConstants.CATEGORY;

public class PHA_DbHandler extends Activity {

	public PHA_DbData memos;
	public static final String KEY_ROWID = "_id";
	private static final String TAG = "PHA_MemoDBr";
	public static String[] FROM = { _ID, TIME, DATE, TITLE, AUTHOR, MEMO, COMMENT, CATEGORY, TAGS };
	public static String ORDER_BY = TIME + " DESC";
	public static int[] TO = { R.id.rowid,  R.id.time, R.id.date, R.id.title, R.id.author, R.id.memo, R.id.comment, R.id.category, R.id.tags};
	private ListView memoLV;
	private SimpleCursorAdapter memoAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(TAG, "onCreate");
		setContentView(R.layout.pha_db_handler);
		memos = new PHA_DbData(this);
		memoLV = (ListView) findViewById(R.id.memoListView);
		
		registerForContextMenu(memoLV);
		try{
			Cursor cursor = getMemos();
			Log.v(TAG, "to show");
			showMemos(cursor);
		} finally {
			memos.close();
		}
	
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
	
	public void addMemo(String title, String memo, String comment, String author){
		/*
		 * Insert a new entry into the memos.db
		 */
		long timeMilliSeconds= System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        Date resultdate = new Date(timeMilliSeconds);
        
		SQLiteDatabase db = memos.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TIME, System.currentTimeMillis());
		values.put(DATE, sdf.format(resultdate));
		values.put(TITLE, title);
		values.put(AUTHOR, author);
		values.put(MEMO, memo);
		values.put(COMMENT, comment);
		db.insert(TABLE_NAME, null, values);	
		
	}
	
	public void showMemos(Cursor cursor){
		/*
		 * Stuff them all into a big string
		 */
		Log.v(TAG, "showing");
		StringBuilder memoBuilder = new StringBuilder(
				"Saved memos:\n");
//		while (cursor.moveToNext()){
//			//could use getColumnIndexOrThrow() to get indexes
//			long id = cursor.getLong(0);
//			long time = cursor.getLong(1);
//			String title = cursor.getString(2);
//			String author = cursor.getString(3);
//			String memo = cursor.getString(4);
//			String comment = cursor.getString(5);
//			memoBuilder.append(id).append(": ");
//			memoBuilder.append(time).append(": ");
//			memoBuilder.append(title).append("\n");
//			memoBuilder.append(author).append(" _ ");
//			memoBuilder.append(memo).append("\n");
//			memoBuilder.append(comment).append("\n");
//		}
		/*
		 * VERY VERY IMPORTANT!!!!!!!!
		 */
		memoAdapter = new SimpleCursorAdapter(this, R.layout.memointerface, cursor, FROM, TO);
		
		TextView memoViewText = (TextView) findViewById(R.id.memoListLext);
		memoViewText.setText(memoBuilder);
		memoLV.setAdapter(memoAdapter);
		
    	//SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
		//		R.layout.act, cursor, FROM, TO);
	}
	
	public Cursor getMemos(){
		// Perform a managed query. The Activity will handle closing
		// and re-querying the cursor when needed.
		SQLiteDatabase db = memos.getReadableDatabase();
		Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null,
		null, ORDER_BY);
		startManagingCursor(cursor);				
		return cursor;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, view, menuInfo);
		
//		if(view.getId() == R.id.memoListView){
//            menu.setHeaderIcon(R.drawable.ic_launcher);
//            menu.setHeaderTitle("Record List");
//            menu.add(0,1,menu.NONE,"Delete Record");
//            menu.add(0,2,menu.NONE,"Show Record");
//        }
		MenuInflater lpinflater = getMenuInflater();
		lpinflater.inflate(R.menu.list_menu_item_longpress, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
//		int itemId = item.getItemId();
//        if(itemId == 1)
//        {
//        	Log.v(TAG, "context1");
//        }
//        if(itemId == 2){
//        	Log.v(TAG, "context2");
//        }
//		
		switch (item.getItemId()){
		case R.id.lp_menu_delete:
			Log.v(TAG, "delete option");
			deleteMemo(item);
			return true;
			
		case R.id.lp_menu_search:
			Intent a = new Intent(this, PHA_Search.class);
			Log.v(TAG, "search option");
			startActivity(a);
		return true;
		}
		return super.onContextItemSelected(item);
	}
	
	public void deleteMemo (MenuItem item){
		Log.v(TAG, "starting delete");
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		String logString = Long.toString(info.id);
		Log.v(TAG, logString);
		SQLiteDatabase db = memos.getWritableDatabase();
		
		db.delete(TABLE_NAME, _ID + "=" +info.id ,null );
		
		try{
			Cursor cursor = getMemos();
			Log.v(TAG, "to show");
			showMemos(cursor);
		} finally {
			memos.close();
		}	
	}
	
	
}
