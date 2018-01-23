package com.tnk.db;

import android.provider.BaseColumns;

/**
 * Created by Tom on 2018-01-03.
 */

public final class Contract_Reminder {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Contract_Reminder() {}

    /* Inner Class that defines the table contents */
    public static class ReminderEntry implements BaseColumns {
        public static final String TABLE_NAME = "PHA_Reminders";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";

    }
}