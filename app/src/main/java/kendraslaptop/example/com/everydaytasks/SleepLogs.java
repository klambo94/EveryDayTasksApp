package kendraslaptop.example.com.everydaytasks;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
 * Created by Kendra's Laptop on 4/13/2017.
 */

public class SleepLogs extends AppCompatActivity {

    private final static String DATE = "Date: ";
    private final static String START_TIME = "Sleep Start: ";
    private final static String END_TIME = "Sleep End: ";
    private final static String QUAL_NOTES = "Notes: ";
    private final static String RATING = "Rating: ";
    private final static String[] SLEEP_KEYS = {DATE, START_TIME, END_TIME, QUAL_NOTES, RATING};

    private ListView prevEntryListView;

    private ArrayAdapter<String> prevEntryAdapter;

    private TaskDBHelper mHelper;

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
                    startActivity(new Intent(this, SleepEntry.class));
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
        Cursor cursor = db.query(TaskContract.SleepLogEntry.TABLE,
                new String[]{
                        TaskContract.SleepLogEntry._ID,
                        TaskContract.SleepLogEntry.COL_SLEEP_DATE,
                        TaskContract.SleepLogEntry.COL_SLEEP_START,
                        TaskContract.SleepLogEntry.COL_SLEEP_END,
                        TaskContract.SleepLogEntry.COL_SLEEP_QUAL,
                        TaskContract.SleepLogEntry.COL_SLEEP_RATING
                }, null, null, null, null, null);

        while(cursor.moveToNext()) {
            int index = cursor.getColumnIndex(TaskContract.SleepLogEntry.COL_SLEEP_DATE);
            String date = cursor.getString(index);

            index = cursor.getColumnIndex(TaskContract.SleepLogEntry.COL_SLEEP_START);
            String startTime = cursor.getString(index);

            index = cursor.getColumnIndex(TaskContract.SleepLogEntry.COL_SLEEP_END);
            String endTime = cursor.getString(index);

            index = cursor.getColumnIndex(TaskContract.SleepLogEntry.COL_SLEEP_QUAL);
            String qualNotes = cursor.getString(index);

            index = cursor.getColumnIndex(TaskContract.SleepLogEntry.COL_SLEEP_RATING);
            String rating = String.valueOf(cursor.getFloat(index));

            String[] values = {date, startTime, endTime, qualNotes, rating};

            addToEntryList(entryList, values);
        }

        if(prevEntryAdapter == null) {
            prevEntryAdapter = new ArrayAdapter<>(this,
                    R.layout.prev_sleep_entry_line,
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

    private void addToEntryList(List<String> entryList,  String[] values) {
        String entry = "";
        for(int i = 0; i < SLEEP_KEYS.length && i < values.length; i++) {
            entry += SLEEP_KEYS[i] + " " + values[i] + "\n";
        }

        entryList.add(entry);
    }

    public void deleteEntry(View view) {
        View parent = (View) view.getParent();
        TextView entryTextView = (TextView) parent.findViewById(R.id.prev_entry_text_line_id);

        if(entryTextView != null){
            String entryText = entryTextView.getText().toString();

            String date = "";
            String startTime = "";
            String endTime = "";
            String rating = "";
            String qual = "";
            if(entryText.contains("\n")){
                String[] entryParts = entryText.split("\n");
                if(entryParts.length == 5) {
                    date = cleanString(entryParts[0]);
                    startTime = cleanString(entryParts[1]);
                    endTime = cleanString(entryParts[2]);
                    qual = cleanString(entryParts[3]);
                    rating = cleanString(entryParts[4]);

                    SQLiteDatabase db = mHelper.getWritableDatabase();
                    db.delete(TaskContract.SleepLogEntry.TABLE,
                            TaskContract.SleepLogEntry.COL_SLEEP_DATE + "= ?"
                                    + " AND " +
                                    TaskContract.SleepLogEntry.COL_SLEEP_START + "= ?"
                                    + " AND " +
                                    TaskContract.SleepLogEntry.COL_SLEEP_END + "= ?"
                                    + " AND " +
                                    TaskContract.SleepLogEntry.COL_SLEEP_QUAL + "= ?"
                                    + " AND " +
                                    TaskContract.SleepLogEntry.COL_SLEEP_RATING + "= ?",
                            new String[] {date, startTime, endTime, qual, rating}
                    );
                    db.close();
                }


            }
            updateUI();

        }
    }

    public void viewMoreEntry(View view) {
        View parent = (View) view.getParent();
        TextView entryTextView = (TextView) parent.findViewById(R.id.prev_entry_text_line_id);

        String entryText = entryTextView.getText().toString();
        String date = "";
        String startTime = "";
        String endTime = "";
        String rating = "";
        String qual = "";
        if(entryText.contains("\n")) {
            String[] entryParts = entryText.split("\n");
            if (entryParts.length == 5) {
                date = entryParts[0];
                startTime = entryParts[1];
                endTime = entryParts[2];
                qual = cleanString(entryParts[3]);
                rating = cleanString(entryParts[4]);


                Intent intent = new Intent(this, SingleSleepLogEntry.class);
                intent.putExtra("DATE", date);
                intent.putExtra("START", startTime);
                intent.putExtra("END", endTime);
                intent.putExtra("RATING", rating);
                intent.putExtra("NOTES", qual);

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
