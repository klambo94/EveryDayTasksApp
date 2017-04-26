package kendraslaptop.example.com.everydaytasks;

import android.content.Intent;
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


/**
 * Created by Kendra's Laptop on 4/13/2017.
 */

public class PreviousEntries extends AppCompatActivity {
    private ListView prevAllEntriesListView;
    private ArrayAdapter<String> adapter;
    private final static String VIEW_SLEEP_LOGS = "View Sleep Logs";
    private final static String VIEW_JOURNAL_LOGS = "View Journal Logs";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prev_entries_view);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.prev_all_entries_tool_bar_id);
        setSupportActionBar(myToolBar);
        setListView();
    }

    private void setListView() {
        List entryList = new ArrayList<>();
        entryList.add(VIEW_SLEEP_LOGS);
        entryList.add(VIEW_JOURNAL_LOGS);

        prevAllEntriesListView = (ListView) findViewById(R.id.prev_all_entries_list_view_id);
        adapter = new ArrayAdapter<String>(this,
                R.layout.previous_sleep_journal_entry_line,
                R.id.prev_all_entry_line_id,
                entryList);
        prevAllEntriesListView.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_prev_all_entry, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item != null) {
            switch (item.getItemId()){
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

    public void viewEntryLogs(View view){
        View parent = (View) view.getParent();
        TextView selectedEntryLine = (TextView) parent.findViewById(R.id.prev_all_entry_line_id);
        String lineText = selectedEntryLine.getText().toString();

        if(VIEW_JOURNAL_LOGS.equals(lineText)){
            startActivity(new Intent(this, JournalEntriesLog.class));
        } else if(VIEW_SLEEP_LOGS.equals(lineText)){
            startActivity(new Intent(this, SleepLogs.class));
        }
    }
}
