package com.example.android.habbittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.android.habbittracker.data.HabbitContract;
import com.example.android.habbittracker.data.HabbitDbHelper;
import com.example.android.habbittracker.data.HabbitContract.HabbitEntry;

import static com.example.android.habbittracker.data.HabbitDbHelper.LOG_TAG;

public class MainActivity extends AppCompatActivity {
    private HabbitDbHelper mDbHelper;
    private final String MONDAY = "Monday";
    private final String TUESDAY = "Tuesday";
    private final String WEDNESDAY = "Wednesday";
    private final String THURSDAY = "THURSDAY";
    private final String FRIDAY = "Friday";
    private final String SATURDAY = "Saturday";
    private final String SUNDAY = "Sunday";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new HabbitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        insertHabbit("Walking the dog", 1, "morning");
        insertHabbit("Taking medicines", 1, "noon");
        insertHabbit("Jogging", 2, "afternoon");
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define projection that specifies which columns from the database we will actually use after this query.
        String[] projection = {
                HabbitEntry._ID,
                HabbitEntry.COLUMN_HABBIT_NAME,
                HabbitEntry.COLUMN_HABBIT_WEEKDAY,
                HabbitEntry.COLUMN_HABBIT_DAYTIME};

        // Perform a query on the habbits table
        Cursor cursor = db.query(
                HabbitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                  // The sort order
        try {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabbitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabbitEntry.COLUMN_HABBIT_NAME);
            int dayColumnIndex = cursor.getColumnIndex(HabbitEntry.COLUMN_HABBIT_WEEKDAY);
            int dayTimeColumnIndex = cursor.getColumnIndex(HabbitEntry.COLUMN_HABBIT_DAYTIME);

            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the element at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentDay = cursor.getInt(dayColumnIndex);
                String currentTime = cursor.getString(dayTimeColumnIndex);

                String currentDayToShow = "";
                switch (currentDay) {
                    case HabbitEntry.DAY_MONDAY:
                        currentDayToShow = MONDAY;
                        break;
                    case HabbitEntry.DAY_TUESDAY:
                        currentDayToShow = TUESDAY;
                        break;
                    case HabbitEntry.DAY_WEDNESDAY:
                        currentDayToShow = WEDNESDAY;
                        break;
                    case HabbitEntry.DAY_THURSDAY:
                        currentDayToShow = THURSDAY;
                        break;
                    case HabbitEntry.DAY_FRIDAY:
                        currentDayToShow = FRIDAY;
                        break;
                    case HabbitEntry.DAY_SATURDAY:
                        currentDayToShow = SATURDAY;
                        break;
                    case HabbitEntry.DAY_SUNDAY:
                        currentDayToShow = SUNDAY;
                        break;
                }
                Log.d(LOG_TAG, currentID + " - " + currentName + " - " + currentDayToShow + " - " + currentTime);
            }
        } finally {
            cursor.close();
        }
    }

    private void insertHabbit(String habbitName, int habbitWeekDay, String habbitDayTime) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabbitEntry.COLUMN_HABBIT_NAME, habbitName);
        values.put(HabbitEntry.COLUMN_HABBIT_WEEKDAY, habbitWeekDay);
        values.put(HabbitEntry.COLUMN_HABBIT_DAYTIME, habbitDayTime);

        long newRowId = db.insert(HabbitEntry.TABLE_NAME, null, values);

        // Show a log message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Log.d(LOG_TAG, "Error with saving habbit");
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Log.d(LOG_TAG, "Habbit saved with row id: " + newRowId);
        }
    }
}
