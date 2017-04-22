package kendraslaptop.example.com.everydaytasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Kendra's Laptop on 4/2/2017.
 */

public class Journal extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.journalToolBarId);
        setSupportActionBar(myToolBar);
        clearJournalEntry();
        setDateAndTimeInHeader();
        setLocation();
    }

    private void setLocation() {
        //TODO: set location
    }

    private void clearJournalEntry() {

        AutoCompleteTextView journalEntry = (AutoCompleteTextView) findViewById(R.id.feelingsEditText);
        Editable journalEntryText = journalEntry.getText();

        if(journalEntryText.toString().equals("AutoCompleteTextView")) {
            journalEntry.setText("");
        }
    }

    private void setDateAndTimeInHeader() {
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyyy h:mm a");
        String dateString = sdf.format(date);
        TextView dateAndTimeHeader = (TextView) findViewById(R.id.timeTextId);
        dateAndTimeHeader.setText(dateString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_buttons_journal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item != null) {
            switch (item.getItemId()){
                case R.id.home_btn:
                    startActivity(new Intent(this, MainActivity.class));
                    return true;
                case R.id.save_btn:
                    //TODO: Data persistance here and return intent - possibly to how was your day
                case R.id.prevJournalEntryBtn:
                    //TODO: Retrive list of previous entries - this will have to show up as an intent
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return false;
        }
    }
}
