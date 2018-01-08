package com.tnk.db;

import android.provider.BaseColumns;

public interface dbConstants extends BaseColumns {
	
	public static final String DATABASE_NAME= "PHAY_DbVersion_alpha_1";
	public static final String TABLE_NAME="MemosDb";
	// Columns in the 'memos'  db
	public static final String TITLE = "title" ;
	public static final String DATE = "date";
	public static final String AUTHOR = "author" ;
	public static final String MEMO = "memo" ;
	public static final String COMMENT = "comment" ;
	public static final String TIME = "time";
	public static final String CATEGORY = "category";
	public static final String TAGS = "tags";
	
	
	//vdb Strings
	public static final String[] vdbCommands1 = { 	"open", 
													"find", 
													"what", 
													"where",
													"when",
													"search",
													"time",
													"date",
												};
	public static final String[] vdbDateKWs = {
		"January","january","February", "february","March", "march",
		"April","april","May","may","June","june","July","july",
		"August","august","September","september","October","october",
		"November","november","December","december",
		"Monday","monday","Tuesday","tuesday","Wednesday","wednesday",
		"Thursday","thursday","Friday","friday","Saturday","saturday", "Sunday","sunday"};
	
	public static final String[] vdbDateObscure = {
		"next","tomorrow","tonight", "this"
	};
	
	public static final String[] vdbDateObscure1 = {
		"week", "month", "morning","afternoon", "evening"
	};
}