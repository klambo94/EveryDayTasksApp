package kendraslaptop.example.com.everydaytasks.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Followed a To-do tutorial and modified for my needs/added
 * functionality for my needs as well
 * Tutorial found here:
 * https://www.sitepoint.com/starting-android-development-creating-todo-app/
 * Created by Kendra's Laptop on 4/16/2017.
 */

public class TaskDBHelper extends SQLiteOpenHelper {

    public TaskDBHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskNewEntry.TABLE + " ( " +
                TaskContract.TaskNewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskNewEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TaskContract.TaskDoneEntry.TABLE + " ( " +
                TaskContract.TaskDoneEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskDoneEntry.COL_TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TaskContract.SleepLogEntry.TABLE + " ( " +
                TaskContract.SleepLogEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.SleepLogEntry.COL_SLEEP_QUAL +" TEXT, " +
                TaskContract.SleepLogEntry.COL_SLEEP_RATING + " FLOAT NOT NULL, " +
                TaskContract.SleepLogEntry.COL_SLEEP_DATE + " TEXT NOT NULL, " +
                TaskContract.SleepLogEntry.COL_SLEEP_START + " TEXT NOT NULL, " +
                TaskContract.SleepLogEntry.COL_SLEEP_END + " TEST NOT NULL );";

        db.execSQL(createTable);

        createTable = "CREATE TABLE " + TaskContract.JournalEntry.TABLE + " ( " +
                TaskContract.JournalEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.JournalEntry.COL_DATE_AND_TIME +" TEXT NOT NULL, " +
                TaskContract.JournalEntry.COL_LOCATION + " TEXT NOT NULL, " +
                TaskContract.JournalEntry.COL_JOURNAL_TEXT_ENTRY + " TEST NOT NULL );";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskNewEntry.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskDoneEntry.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.SleepLogEntry.TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.JournalEntry.TABLE);
        onCreate(db);
    }
}
