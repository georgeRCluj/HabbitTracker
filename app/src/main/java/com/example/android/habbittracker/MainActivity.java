package com.example.android.habbittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.habbittracker.data.HabitDbHelper;
import com.example.android.habbittracker.data.HabitContract.HabitEntry;

import static com.example.android.habbittracker.data.HabitDbHelper.LOG_TAG;

public class MainActivity extends AppCompatActivity {
    private HabitDbHelper mDbHelper;
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
        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        insertHabit("Walking the dog", 1, "morning");
        insertHabit("Taking medicines", 1, "noon");
        insertHabit("Jogging", 2, "afternoon");
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        Cursor cursor = querryAllHabits();

        try {
            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int dayColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_WEEKDAY);
            int dayTimeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DAYTIME);

            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the element at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentDay = cursor.getInt(dayColumnIndex);
                String currentTime = cursor.getString(dayTimeColumnIndex);

                String currentDayToShow = "";
                switch (currentDay) {
                    case HabitEntry.DAY_MONDAY:
                        currentDayToShow = MONDAY;
                        break;
                    case HabitEntry.DAY_TUESDAY:
                        currentDayToShow = TUESDAY;
                        break;
                    case HabitEntry.DAY_WEDNESDAY:
                        currentDayToShow = WEDNESDAY;
                        break;
                    case HabitEntry.DAY_THURSDAY:
                        currentDayToShow = THURSDAY;
                        break;
                    case HabitEntry.DAY_FRIDAY:
                        currentDayToShow = FRIDAY;
                        break;
                    case HabitEntry.DAY_SATURDAY:
                        currentDayToShow = SATURDAY;
                        break;
                    case HabitEntry.DAY_SUNDAY:
                        currentDayToShow = SUNDAY;
                        break;
                }
                Log.d(LOG_TAG, currentID + " - " + currentName + " - " + currentDayToShow + " - " + currentTime);
            }
        } finally {
            cursor.close();
        }
    }

    private Cursor querryAllHabits() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define projection that specifies which columns from the database we will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_WEEKDAY,
                HabitEntry.COLUMN_HABIT_DAYTIME};

        // Perform a query on the habits table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                  // The sort order
        return cursor;
    }

    private void insertHabit(String habitName, int habitWeekDay, String habitDayTime) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, habitName);
        values.put(HabitEntry.COLUMN_HABIT_WEEKDAY, habitWeekDay);
        values.put(HabitEntry.COLUMN_HABIT_DAYTIME, habitDayTime);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        // Show a log message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Log.d(LOG_TAG, "Error with saving habbit");
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Log.d(LOG_TAG, "Habit saved with row id: " + newRowId);
        }
    }
}
