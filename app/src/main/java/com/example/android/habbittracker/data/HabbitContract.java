package com.example.android.habbittracker.data;

import android.provider.BaseColumns;

public final class HabbitContract {
    private HabbitContract() {
    }

    public static final class HabbitEntry implements BaseColumns {
        public final static String TABLE_NAME = "habbits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABBIT_NAME ="name";
        public final static String COLUMN_HABBIT_WEEKDAY ="weekday";
        public final static String COLUMN_HABBIT_DAYTIME ="daytime";
        public static final int DAY_MONDAY = 1;
        public static final int DAY_TUESDAY  = 2;
        public static final int DAY_WEDNESDAY  = 3;
        public static final int DAY_THURSDAY  = 4;
        public static final int DAY_FRIDAY  = 5;
        public static final int DAY_SATURDAY  = 6;
        public static final int DAY_SUNDAY  = 7;
    }
}
