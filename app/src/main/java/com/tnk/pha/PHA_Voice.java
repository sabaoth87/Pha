package com.tnk.pha;

import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.RecognizerIntent;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import static com.tnk.db.dbConstants.vdbCommands1;
import static com.tnk.db.dbConstants.vdbDateKWs;

public class PHA_Voice extends Activity {
	
	int durationShort = Toast.LENGTH_SHORT;
	int durationLong = Toast.LENGTH_LONG;
	private Toast theTimeTST;
	private Toast uninitToast;
	private String uninitString = "Not Yet Implemented";
	static final String DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss";
	private String commandVars;
	private String ssynResultCTop;
	private String commandWordFound;
	private int foundAt;
	private int commandIs;
	private boolean commandFound;
	private static final int VOICE_RECOGNITION_REQUEST = 1;
	private static final String TAG = "PHA_Voice";
	private static final String TAG_SSYN = "SSYN";
	public int ttsResult;
	private TextView ssynTV;
	private ArrayList<String> matches;
	private String[] vdbC0 = {"find", "launch", "note", "open", "search", "time", "who", "what", "where","when", "fay", "phay", "remind", "set"};

	private String[] vdbCWorker;
	
	private int remMonth = 0;
	private int remDay = 0;
	
	private static int vdbCommandLocale = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pha_voice);
		//sequencer = (RatingBar) findViewById(R.id.ratingBar1);
		ssynTV = (TextView) findViewById(R.id.SSYNTV);
		
//		Context context1 = getApplicationContext();
//		uninitToast = Toast.makeText(context1, uninitString, durationLong);
//		uninitToast.show();
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
		getMenuInflater().inflate(R.menu.pha__voice, menu);
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
	
	public void recordSpeech (View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Clearly");
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == VOICE_RECOGNITION_REQUEST && resultCode == RESULT_OK) {
			matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			TextView textSaid = (TextView) findViewById(R.id.textSaidTV);
			TextView TTSTV1 = (TextView) findViewById(R.id.SSYNTV);
			textSaid.setText(matches.get(0));
			TTSTV1.setText(matches.get(0)+ vdbCommandLocale);
		}
		//commandCheck(matches);
		commandCheckii(matches);
		super.onActivityResult(requestCode, resultCode, data);
	}

//	@Override
	/*
	 * 
	 * Keeps stackoverflowing and crashing the app at finding the button
	 * I dont think it is the button itself causing the problem though,
	 * need to control the life cycle better
	 */
//	public void onInit(int status) {
//		
//		// public void onInit(int status){
////		int result;
////		String ttsString = "";
////		result = phaTTS.setLanguage(	Locale.	CANADA	);	if (result == TextToSpeech.LANG_MISSING_DATA ||result == TextToSpeech.LANG_NOT_SUPPORTED) {ttsString +=	 "	CANADA	 not supported<br>"	;}else{ttsString+="	CANADA	 supported<br>";}
////		result = phaTTS.setLanguage(	Locale.	CANADA_FRENCH	);	if (result == TextToSpeech.LANG_MISSING_DATA ||result == TextToSpeech.LANG_NOT_SUPPORTED) {ttsString +=	 "	CANADA_FRENCH	 not supported<br>"	;}else{ttsString+="	CANADA_FRENCH	 supported<br>";}
////		result = phaTTS.setLanguage(	Locale.	ENGLISH	);	if (result == TextToSpeech.LANG_MISSING_DATA ||result == TextToSpeech.LANG_NOT_SUPPORTED) {ttsString +=	 "	ENGLISH	 not supported<br>"	;}else{ttsString+="	ENGLISH	 supported<br>";}
////		result = phaTTS.setLanguage(	Locale.	PRC	);	if (result == TextToSpeech.LANG_MISSING_DATA ||result == TextToSpeech.LANG_NOT_SUPPORTED) {ttsString +=	 "	PRC	 not supported<br>"	;}else{ttsString+="	PRC	 supported<br>";}
////		result = phaTTS.setLanguage(	Locale.	ROOT	);	if (result == TextToSpeech.LANG_MISSING_DATA ||result == TextToSpeech.LANG_NOT_SUPPORTED) {ttsString +=	 "	ROOT	 not supported<br>"	;}else{ttsString+="	ROOT	 supported<br>";}
////		result = phaTTS.setLanguage(	Locale.	US	);	if (result == TextToSpeech.LANG_MISSING_DATA ||result == TextToSpeech.LANG_NOT_SUPPORTED) {ttsString +=	 "	US	 not supported<br>"	;}else{ttsString+="	US	 supported<br>";}
////		
////		TextView textSaid = (TextView) findViewById(R.id.TTSTV);
////		textSaid.setText(ttsString);
//		
//		TextToSpeech phaTTS = new TextToSpeech(this, this);
//		Button readButton = (Button) findViewById(R.id.TTSBtn);
//		if(status == TextToSpeech.SUCCESS) {
//			int ttsResult = phaTTS.setLanguage(Locale.CANADA);
//			//Log.v(TAG, Integer.toString(ttsResult));
//			//ttsResult == TextToSpeech.LANG_MISSING_DATA || TextToSpeech.LANG_NOT_SUPPORTED
//			try{
//			if ( ttsResult == TextToSpeech.LANG_MISSING_DATA) {
//				Log.v(TAG, "TTS Language not availabe");
//				
//				readButton.setEnabled(false);
//			} else {
//				readButton.setEnabled(true);
//			}
//		} finally {
//			Log.v(TAG, "Could not initialize the TTS engine");
//			readButton.setEnabled(false);
//		}
//		}
//	}
	
	@Override
	protected void onDestroy(){
//		if (phaTTS != null) {
//			phaTTS.stop();
//			phaTTS.shutdown();
//        }
//		Log.v(TAG, "Destroyed TTS");
 
        super.onDestroy();
	}
	
//	public void readText(View view) {
//		TextView textSaid = (TextView) findViewById(R.id.TTSTV);
//		phaTTS.speak((String) textSaid.getText(), TextToSpeech.QUEUE_FLUSH, null);}

	public void commandCheck(ArrayList<String> commandEntry){
		if (!commandEntry.isEmpty()){
			Log.v(TAG, "Not Empty");
//			if (commandEntry.get(0).contains("hello")){
//				Log.v(TAG, "Knows you said hello");
//				phaTTS.speak("Hello,  how are you", TextToSpeech.QUEUE_FLUSH, null);
//			}
//			if (commandEntry.get(0).contains("search")){
//				Log.v(TAG, "Knows you want to search");
//				phaTTS.speak("Searching", TextToSpeech.QUEUE_FLUSH, null);
//				Intent a = new Intent(this, PHAY_SearchMain.class);
//				a.putExtra("want", commandEntry.get(1));			
//				Log.v(TAG, "PHAY Search Option");
//				startActivity(a);
//			}
//			if (commandEntry.get(0).contains("open")){
//				Log.v(TAG, "Knows you want to open something");
//				phaTTS.speak("Opening", TextToSpeech.QUEUE_FLUSH, null);
//			}
			Log.v(TAG, "Not Empty");
			for (int i=0; i<5;i++){
				if (commandEntry.get(0).contains(vdbCommands1[i])){
					Log.v(TAG, "Command Known");
					//phaTTS.speak("Hello,  how are you", TextToSpeech.QUEUE_FLUSH, null);
					vdbCommandLocale = i;
					commandReference(vdbCommandLocale, commandEntry);
				}
			}
		}
	}
	
	public void commandReference(int location, ArrayList<String> commandEntry){
		switch (location){
		case -1:
			//default
			Log.v(TAG, "Default action, none");
			break;
		case 0:
			//open
			Log.v(TAG, "open an activity or possible throw intent");
			break;
		case 1:
			//find
			Log.v(TAG, "Find a place, file, location, person(hopefully)");
			break;
		case 2:
			//what
			Log.v(TAG, "Search for topic");
			break;
		case 3:
			//where
			Log.v(TAG, "Find location; self, entry, place");
			break;
		case 4:
			//when
			Log.v(TAG, "Check reminder times");
			break;
		case 5:
			//search
			Log.v(TAG, "Knows you want to search");
//			phaTTS.speak("Searching", TextToSpeech.QUEUE_FLUSH, null);
			Intent a = new Intent(this, PHA_Search.class);
			a.putExtra("want", commandEntry.get(1));			
			Log.v(TAG, "PHAY Search Option");
			startActivity(a);
			break;
		case 6:
			//time
//			Log.v(TAG, "The time is");
//			long timeMilliSeconds= System.currentTimeMillis();
//			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//	        Date resultdate = new Date(timeMilliSeconds);
//	        
//			phaTTS.speak(sdf.format(resultdate).toString(), TextToSpeech.QUEUE_FLUSH, null);
			break;
		case 7:
			//date
//			Log.v(TAG, "The date is");
//			long dateMilliSeconds= System.currentTimeMillis();
//			SimpleDateFormat dateformat = new SimpleDateFormat("MMM dd,yyyy");
//	        Date resultdate1 = new Date(dateMilliSeconds);
//	        phaTTS.speak(dateformat.format(resultdate1).toString(), TextToSpeech.QUEUE_FLUSH, null);
		}
	}
	
	public void commandCheckii(ArrayList<String> ssynResults){
		String ssynString = ssynResults.toString();
		int ssynResultLength = ssynString.length();
		ssynResultCTop = ssynResults.get(0);
		String[] worker= ssynResultCTop.split(" ");
		Log.v(TAG_SSYN, "worker length: "+worker.length);
		/*
		 * Set the length to 1, as it won't return less than that, 
		 * and we're looking for whitespace this time around
		 */
		int ssynResultsTopLength = 0;
		int workerArrayLocale = 0;
		
		if (ssynResultCTop != null){
			for (int t = 0; t < ssynResultCTop.length(); t++){
				if(Character.isWhitespace(ssynResultCTop.charAt(t))){
					ssynResultsTopLength++;
				}
			}
		}
		//for (int i=0; i < ssynResultLength; i++){
		//	vdbCWorker[i] = ssynResults.get(i);
		//}
		
		for (int b=0; b < ssynResultCTop.length(); b++){
			Log.v(TAG_SSYN, "Building word: Adding character "+b);
			Log.v(TAG_SSYN, "               to word "+workerArrayLocale);
			if(Character.isWhitespace(ssynResultCTop.charAt(b))){
				ssynResultsTopLength++;
				Log.v(TAG_SSYN, "Built word: "+workerArrayLocale + " " + worker[workerArrayLocale]);
			}
		}
		
		ssynTV.setTag(ssynString +" ::Length:: " +ssynResultLength);
		doCommandWork(vdbCWorker, ssynString, ssynResultLength, ssynResultsTopLength, worker);
	}
	
	public void doCommandWork(String[] arrayOfCommands, String stringOfAllSpoken, int numberOfChars, int numberOfSpoken, String[] arrayOfSpoken){
		/*
		 * This finally WORKS
		 */
		Log.v(TAG_SSYN, "Array: "+arrayOfCommands +"\nString: "+stringOfAllSpoken+"\nInt: "+numberOfChars+"\n#"+numberOfSpoken);
		//for (int z=0;z<(numberOfSpoken-1);z++){
		//	Log.v(TAG_SSYN, "Array1: "+arrayOfSpoken[z]);
		//}
		Log.v(TAG_SSYN, "Array1: "+arrayOfSpoken);
		for (int x=0; x < vdbC0.length ; x++){
			/*
			 * this is my array searcher
			 * search an array or single strings to match
			 * a single string entry in another array
			 */
			Log.v(TAG_SSYN, "Testing for command: "+x);
				for(int i=0; i < arrayOfSpoken.length; i++){
					Log.v(TAG_SSYN, "Testing at word: "+i);
					if(arrayOfSpoken[i].contentEquals(vdbC0[x])){
						foundAt = i;
						commandIs = x;
						Log.v(TAG_SSYN, "Found a command at word: "+foundAt + "\nCommand is Index: " + commandIs+ "\nCommand is : " + vdbC0[commandIs]);
						commandWordFound = vdbC0[commandIs];
						commandFound=true;
					}
				}
			
		}
		if (commandFound&& arrayOfSpoken.length > 1){
			for (int z = (foundAt+1); z < (arrayOfSpoken.length-1); z++){
				//Log.v(TAG, z+ " " +arrayOfSpoken.length + " "+ foundAt);
				//Log.v(TAG, "looking for:"+commandWordFound);
				//Log.v(TAG, "looking in:"+ ssynResultCTop);
				String[] newArray = ssynResultCTop.split(commandWordFound, 2);
				commandVars = newArray[1];
				Log.v(TAG, "Possible 'wants': " +newArray[1]);
			}
			//Log.v(TAG, "wants:"+ wantBuilder.toString());
			commandHandler(foundAt, commandIs, arrayOfSpoken, commandVars);
		}
	}
	
	public void commandHandler(int foundAt, int commandIs, String[] arrayOfSpoken, String wants){
		Context context = getApplicationContext();	
		switch (commandIs){
		/*
		 * 0=find
		 * 1=launch
		 * 2=note
		 * 3=open
		 * 4=search
		 * 5=time
		 * 6=who
		 * 7=what
		 * 8=where
		 * 9=when
		 */
		case 0:
			//find
			Log.v(TAG, "Knows you want to find: " + wants);
//			phaTTS.speak("Searching", TextToSpeech.QUEUE_FLUSH, null);
			Intent findIntent = new Intent(this, PHA_Search.class);
			findIntent.putExtra("want", wants);	
			startActivity(findIntent);
			break;
		case 1:
			//launch
			uninitToast.show();
			break;
		case 2:
			//note
			Intent noteIntent = new Intent(this, PHA_Db.class);
			noteIntent.putExtra("want", wants);
			startActivity(noteIntent);
			break;
		case 3:
			//open
			uninitToast.show();
			break;
		case 4:
			//search
			Log.v(TAG, "Knows you want to search for " + wants);
//			phaTTS.speak("Searching", TextToSpeech.QUEUE_FLUSH, null);
			Intent searchintent = new Intent(this, PHA_Search.class);
			searchintent.putExtra("want", wants);
			startActivity(searchintent);
			break;
		case 5:
			//time			
			Log.v(TAG, "The time is");
			long timeMilliSeconds= System.currentTimeMillis();
	        Date resultdate = new Date(timeMilliSeconds);
	        theTimeTST = Toast.makeText(context, resultdate.toString(), durationLong);
	        theTimeTST.show();
			break;
		case 6:
			//who
			Log.v(TAG, "Knows you want to know who " + wants);
//			phaTTS.speak("Searching", TextToSpeech.QUEUE_FLUSH, null);
			Intent whoIntent = new Intent(this, PHA_Search.class);
			whoIntent.putExtra("want", wants);
			startActivity(whoIntent);
			break;
		case 7:
			//what
			Log.v(TAG, "Knows you want to know what " + wants);
//			phaTTS.speak("Searching", TextToSpeech.QUEUE_FLUSH, null);
			Intent whatIntent = new Intent(this, PHA_Search.class);
			whatIntent.putExtra("want", wants);
			startActivity(whatIntent);
			break;
		case 8:
			//where
			Log.v(TAG, "Knows you want to know where " + wants);
//			phaTTS.speak("Searching", TextToSpeech.QUEUE_FLUSH, null);
			Intent whereIntent = new Intent(this, PHA_Search.class);
			whereIntent.putExtra("want", wants);
			startActivity(whereIntent);
			break;
		case 9:
			//when
			Log.v(TAG, "Knows you want to know when " + wants);
//			phaTTS.speak("Searching", TextToSpeech.QUEUE_FLUSH, null);
			Intent whenIntent = new Intent(this, PHA_Search.class);
			whenIntent.putExtra("want", wants);
			startActivity(whenIntent);
			break;
		case 10:
			//fay
			uninitToast.show();
			break;
		case 11:
			//phay
			uninitToast.show();
			break;
		case 12:
			//remind
			if (arrayOfSpoken[foundAt+1].contentEquals("me")){
				doReminderCommandWork(foundAt,  commandIs,  arrayOfSpoken,  wants);
			}
			break;
		case 13:
			//set
			uninitToast.show();
			break;
		}
	}
	
	public void doReminderCommandWork(int foundAt, int commandIs, String[] arrayOfSpoken, String wants){
		//look for important info within the wants for the reminder method, DATES possibly
		Log.v(TAG_SSYN, "Checking to see if there is a date in the wants...");
		for (int a = 0; a < vdbDateKWs.length; a++){
			for(int b = (foundAt+1); b < arrayOfSpoken.length; b++){
				if (arrayOfSpoken[b].contentEquals(vdbDateKWs[a])){
					int foundDateKW = a;
					Log.v(TAG_SSYN, "Found DateKW: "+vdbDateKWs[b]);
					/*
					 * Now check the KW against the index to figure out what to do with it
					 * If its a YEAR,MONTH,DAY,HOUR,MINUTE
					 * 		set the appropriate variable to send to the activity 
					 */
					switch(foundDateKW){
					case 0:
					case 1:
						remMonth=1;
						break;
					case 2:
					case 3:
						remMonth=2;
						break;
					case 4:
					case 5:
						remMonth=3;
						break;
					case 6:
					case 7:
						remMonth=4;
						break;
					case 8:
					case 9:
						remMonth=5;
						break;
					case 10:
					case 11:
						remMonth=6;
						break;
					case 12:
					case 13:
						remMonth=7;
						break;
					case 14:
					case 15:
						remMonth=8;
						break;
					case 16:
					case 17:
						remMonth=9;
						break;
					case 18:
					case 19:
						remMonth=10;
						break;
					case 20:
					case 21:
						remMonth=11;
						break;
					case 22:
					case 23:
						remMonth=12;
						break;
					case 24:
					case 25:
						remDay=1;
						break;
					case 26:
					case 27:
						remDay=2;
						break;
					case 28:
					case 29:
						remDay=3;
						break;
					case 30:
					case 31:
						remDay=4;
						break;
					case 32:
					case 33:
						remDay=5;
						break;
					case 34:
					case 35:
						remDay=6;
						break;
					case 36:
					case 37:
						remDay=7;
						break;
					}
					Log.v(TAG, "Date Vars: " +":"+remMonth +":"+remDay);
				}
			}
		}
		
		//Intent whenIntent = new Intent(this, PHA_Reminder_Entry.class);
		//reminderIntent.putExtra("want", wants);
		//reminderIntent.putExtra("month", remMonth);
		//reminderIntent.putExtra("day", remDay);
		//reminderIntent.putExtra("year", year);
		//reminderIntent.putExtra("hour", hour);
		//reminderIntent.putExtra("minute", minute);
		//startActivity(reminderIntent);
		
	}
	
}