package kendraslaptop.example.com.everydaytasks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kendraslaptop.example.com.everydaytasks.db.TaskContract;
import kendraslaptop.example.com.everydaytasks.db.TaskDBHelper;

/**
 * As mentioned in TodoList.java, I followed a tutorial and the
 * database implementation in these Sleep files have been modified
 * for my needs.
 *
 * Created by Kendra's Laptop on 4/13/2017.
 */

public class JournalEntriesLog extends AppCompatActivity {
    private TaskDBHelper mHelper;
    private ListView prevEntryListView;
    private ArrayAdapter<String> prevEntryAdapter;

    private final static String DATE_TIME = "Date and Time: ";
    private final static String LOCATION = "Location: ";
    private final static String JOURNAL_ENTRY = "";
    private final static String[] JOURNAL_KEYS = {DATE_TIME, LOCATION, JOURNAL_ENTRY};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.previous_sleep_log);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.prev_sleep_log_toolbar_id);
        setSupportActionBar(myToolBar);
        setListView();

        updateUI();
    }

    private void setListView() {
        mHelper = new TaskDBHelper(this);
        prevEntryListView = (ListView) findViewById(R.id.prev_sleep_list_viewe_id);
        TextView header = (TextView) findViewById(R.id.prev_sleep_text_id);
        header.setText("Past Journal Entries");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_prev_sleep_log, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item != null) {
            switch (item.getItemId()){
                case R.id.new_entry_btn:
                    startActivity(new Intent(this, Journal.class));
                    return true;
                case R.id.home_btn:
                    startActivity(new Intent(this, MainActivity.class));
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return false;
        }
    }

    private void updateUI() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<String> entryList = new ArrayList<>();

        Cursor cursor = db.query(TaskContract.JournalEntry.TABLE,
                new String[] {
                        TaskContract.JournalEntry._ID,
                        TaskContract.JournalEntry.COL_LOCATION,
                        TaskContract.JournalEntry.COL_DATE_AND_TIME,
                        TaskContract.JournalEntry.COL_JOURNAL_TEXT_ENTRY},
                null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            int index = cursor.getColumnIndex(TaskContract.JournalEntry.COL_DATE_AND_TIME);
            String dateAndTime = cursor.getString(index);

            index = cursor.getColumnIndex(TaskContract.JournalEntry.COL_LOCATION);
            String location = cursor.getString(index);

            index = cursor.getColumnIndex(TaskContract.JournalEntry.COL_JOURNAL_TEXT_ENTRY);
            String journalText = cursor.getString(index);

            String[] values = {dateAndTime, location, journalText};

            addToEntryList(entryList, values);
        }

        if(prevEntryAdapter == null) {
            prevEntryAdapter = new ArrayAdapter<>(this,
                    R.layout.prev_journal_entry_line,
                    R.id.prev_entry_text_line_id,
                    entryList);
            prevEntryListView.setAdapter(prevEntryAdapter);
        } else {
            prevEntryAdapter.clear();
            prevEntryAdapter.addAll(entryList);
            prevEntryAdapter.notifyDataSetChanged();

            db.close();
        }
    }

    private void addToEntryList(List<String> entryList, String[] values) {
        String entry = "";
        for(int i = 0; i < JOURNAL_KEYS.length && i < values.length; i++) {
            entry += JOURNAL_KEYS[i] + " " + values[i] + "\n";
        }

        entryList.add(entry);
    }

    public void deleteJournalEntry(View view) {
        Log.i("JournalEntriesLog", "Delete Journal Entry clicked.");
        View parent = (View) view.getParent();
        TextView entryTextView = (TextView) parent.findViewById(R.id.prev_entry_text_line_id);

        if(entryTextView != null){
            String entryText = entryTextView.getText().toString();

            String dateAndTime = "";
            String location = "";
            String journalEntry = "";
            if(entryText.contains("\n")){
                String[] entryParts = entryText.split("\n");
                if(entryParts.length == 4) {
                    dateAndTime = cleanString(entryParts[0]);
                    location = cleanString(entryParts[1] + "\n" +entryParts[2]);
                    journalEntry = entryParts[3];

                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    db.delete(TaskContract.JournalEntry.TABLE,
                            TaskContract.JournalEntry.COL_DATE_AND_TIME + "= ?"
                                    + " AND " +
                                    TaskContract.JournalEntry.COL_LOCATION + "= ?"
                                    + " AND " +
                                    TaskContract.JournalEntry.COL_JOURNAL_TEXT_ENTRY + "= ?",
                            new String[] {dateAndTime, location, journalEntry}
                    );
                    db.close();
                }


            }
            updateUI();
        }
    }

    public void viewMoreJournalEntry(View view) {
        Log.i("JournalEntriesLog", "View More Journal Entry clicked.");
        View parent = (View) view.getParent();
        TextView entryTextView = (TextView) parent.findViewById(R.id.prev_entry_text_line_id);

        String entryText = entryTextView.getText().toString();
        String dateAndTime = "";
        String locationLat = "";
        String locationLong = "";
        String journalEntry = "";
        if(entryText.contains("\n")){
            String[] entryParts = entryText.split("\n");
            if(entryParts.length == 4) {
                dateAndTime = cleanString(entryParts[0]);
                locationLat = cleanString(entryParts[1]);
                locationLong = entryParts[2];
                journalEntry = entryParts[3];


                Intent intent = new Intent(this, SingleJournalEntry.class);
                intent.putExtra("DATE", dateAndTime);
                intent.putExtra("LOCATIONLAT", locationLat);
                intent.putExtra("LOCATIONLONG", locationLong);
                intent.putExtra("ENTRY", journalEntry);

                startActivity(intent);
            }
        }
    }

    private String cleanString(String str) {
        int index = str.indexOf(":") + 1;
        if(str.length() > index) {
            return str.substring(index).trim();
        }
        return str;
    }
}
