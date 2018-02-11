package com.tnk.db;

import android.provider.BaseColumns;

/**
 * Created by Tom on 2018-01-03.
 */

public final class Contract_Issue {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Contract_Issue() {}

    /* Inner Class that defines the table contents */
    public static class IssueEntry implements BaseColumns {
        public static final String TABLE_NAME = "pha_issues";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_BODY = "body";
        public static final String COLUMN_DATETIME = "datetime";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_TAGS = "tags";
        public static final String COLUMN_ASSIGNEE = "assignee";
        public static final String COLUMN_PROJECT = "project";
        public static final String COLUMN_MILESTONE = "milestone";
        public static final String COLUMN_PROGRESS = "progress";
        public static final String COLUMN_TICKET = "ticket";
        public static final String COLUMN_OWNER = "owner";
    }
}