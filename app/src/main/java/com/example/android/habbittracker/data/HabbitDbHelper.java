package com.example.android.habbittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habbittracker.data.HabbitContract.HabbitEntry;

public class HabbitDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = HabbitDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "habbits.db";
    private static final int DATABASE_VERSION = 1;

    public HabbitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_HABBITS_TABLE =  "CREATE TABLE " + HabbitEntry.TABLE_NAME + " ("
                + HabbitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabbitEntry.COLUMN_HABBIT_NAME + " TEXT NOT NULL, "
                + HabbitEntry.COLUMN_HABBIT_WEEKDAY + " INTEGER NOT NULL, "
                + HabbitEntry.COLUMN_HABBIT_DAYTIME+ " TEXT NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_HABBITS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
