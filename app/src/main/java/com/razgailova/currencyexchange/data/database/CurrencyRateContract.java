package com.razgailova.currencyexchange.data.database;

import android.provider.BaseColumns;

/**
 * Created by Катерина on 19.11.2017.
 */

public final class CurrencyRateContract {
    private CurrencyRateContract() {}

    public static class RatesEntry implements BaseColumns {
        public static final String TABLE_NAME = "rates";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_NUM_CODE = "num_code";
        public static final String COLUMN_NAME_CHAR_CODE = "char_code";
        public static final String COLUMN_NAME_NOMINAL = "nominal";
        public static final String COLUMN_NAME_VOLUTE_NAME = "name";
        public static final String COLUMN_NAME_VALUE = "value";
    }

    public static class MetadataEntry implements BaseColumns {
        public static final String TABLE_NAME = "metadata";
        public static final String COLUMN_NAME_DATE = "date";

    }
}