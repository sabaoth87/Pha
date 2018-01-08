package com.tnk.db;

import android.provider.BaseColumns;

/**
 * Created by Tom on 2018-01-03.
 */

public final class ToolContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ToolContract() {}

    /* Inner Class that defines the table contents */
    public static class ToolEntry implements BaseColumns {
        public static final String TABLE_NAME = "tools";
        public static final String COLUMN_NAME_TYPE = "toolType";
        public static final String COLUMN_NAME_NAME = "toolName";
        public static final String COLUMN_NAME_BRAND = "toolBrand";
        public static final String COLUMN_NAME_QUANTITY = "toolQuantity";
        public static final String COLUMN_NAME_QUALITY = "toolQuality";
        public static final String COLUMN_NAME_LOCATION = "toolLocation";
        public static final String COLUMN_NAME_NOTE = "toolNote";
        public static final String COLUMN_NAME_LINK = "toolLink";
        public static final String COLUMN_NAME_PIC = "toolPic";
        public static final String COLUMN_NAME_SIZE = "toolSizes";
        public static final String COLUMN_NAME_USES = "toolUses";
        public static final String COLUMN_NAME_AMMO = "toolAmmo";
        public static final String COLUMN_NAME_CATEGORY = "toolCategory";
        public static final String COLUMN_NAME_STATUS = "toolStatus";

    }
}