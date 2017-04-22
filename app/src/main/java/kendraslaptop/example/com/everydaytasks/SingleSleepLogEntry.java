package kendraslaptop.example.com.everydaytasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import kendraslaptop.example.com.everydaytasks.db.TaskDBHelper;

/**
 * Created by Kendra's Laptop on 4/22/2017.
 */

public class SingleSleepLogEntry extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_sleep_entry);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.single_sleep_entry_toolbar_id);
        setSupportActionBar(myToolBar);

        Intent inIntent = getIntent();
        String date = inIntent.getStringExtra("DATE");
        String startTime = inIntent.getStringExtra("START");
        String endTime = inIntent.getStringExtra("END");
        Float rating = Float.parseFloat(inIntent.getStringExtra("RATING"));
        String notes = inIntent.getStringExtra("NOTES");

        TextView dateView = (TextView)findViewById(R.id.date_Text_id);
        dateView.setText(date);

        TextView startView = (TextView) findViewById(R.id.sleep_start_text_id);
        startView.setText(startTime);

        TextView endView = (TextView) findViewById(R.id.sleep_end_text__id);
        endView.setText(endTime);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.single_sleep_rating_bar_id);
        ratingBar.setRating(rating);

        TextView notesView = (TextView) findViewById(R.id.notes_entry_text_id);
        notesView.setText(notes);

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
}
