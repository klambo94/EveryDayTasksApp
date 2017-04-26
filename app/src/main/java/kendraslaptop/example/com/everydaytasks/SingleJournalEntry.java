package kendraslaptop.example.com.everydaytasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Kendra's Laptop on 4/23/2017.
 */

public class SingleJournalEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_journal_entry);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.single_journal_entry_toolbar_id);
        setSupportActionBar(myToolBar);

        Intent inIntent = getIntent();
        String dateAndTime = inIntent.getStringExtra("DATE");
        String location = inIntent.getStringExtra("LOCATIONLAT") + "\n" +inIntent.getStringExtra("LOCATIONLONG");
        String entry = inIntent.getStringExtra("ENTRY");

        TextView dateView = (TextView)findViewById(R.id.date_Text_id);
        dateView.setText(dateAndTime);

        TextView startView = (TextView) findViewById(R.id.single_location_text_id);
        startView.setText(location);

        TextView endView = (TextView) findViewById(R.id.single_journal_entry_text_id);
        endView.setText(entry);
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
}

