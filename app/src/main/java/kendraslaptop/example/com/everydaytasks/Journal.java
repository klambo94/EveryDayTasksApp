package kendraslaptop.example.com.everydaytasks;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import kendraslaptop.example.com.everydaytasks.db.TaskContract;
import kendraslaptop.example.com.everydaytasks.db.TaskDBHelper;

/**
 * As mentioned in TodoList.java, I followed a tutorial and the
 * database implementation in these Journal files have been modified
 * for my needs.
 *
 * Information and steps to set up setting the location and acquiring it
 * is taken from developer.android.com/training/location
 * Created by Kendra's Laptop on 4/2/2017.
 */

public class Journal extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1000;
    private LocationManager locationManager;
    private TaskDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.journalToolBarId);
        setSupportActionBar(myToolBar);
        mHelper = new TaskDBHelper(this);

        clearJournalEntry();
        setDateAndTimeInHeader();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        getLocation();
    }

    private void setLocation(Location location) {
        String locationText = "";

        double lat = location.getLatitude();
        double lon = location.getLongitude();
        locationText = "Latitude: " + lat + "\n" + "Longitude: " + lon;
        TextView locationTextBox = (TextView) findViewById(R.id.locationTextId);
        locationTextBox.setText(locationText);
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
        } else{
            setLocation(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }

    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        final int MY_PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION = 1000;
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_COURSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    setLocation(location);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    private void clearJournalEntry() {

        AutoCompleteTextView journalEntry = (AutoCompleteTextView) findViewById(R.id.feelingsEditText);
        Editable journalEntryText = journalEntry.getText();

        if (journalEntryText.toString().equals("AutoCompleteTextView")) {
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
        if (item != null) {
            switch (item.getItemId()) {
                case R.id.home_btn:
                    startActivity(new Intent(this, MainActivity.class));
                    return true;
                case R.id.save_btn:
                    saveJournalEntry();
                    return true;
                case R.id.prevJournalEntryBtn:
                    startActivity(new Intent(this, JournalEntriesLog.class));
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return false;
        }
    }


    private void saveJournalEntry() {
        TextView dateAndTimeView = (TextView) findViewById(R.id.timeTextId);
        TextView locationView = (TextView) findViewById(R.id.locationTextId);
        TextView journalEntryView = (TextView)findViewById(R.id.feelingsEditText);

        String dateAndTimeText = dateAndTimeView.getText().toString();
        String locationText = locationView.getText().toString();
        String journalEntryText = journalEntryView.getText().toString();


        if(!journalEntryText.isEmpty()) {
            SQLiteDatabase db = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(TaskContract.JournalEntry.COL_DATE_AND_TIME, dateAndTimeText);
            values.put(TaskContract.JournalEntry.COL_LOCATION, locationText);
            values.put(TaskContract.JournalEntry.COL_JOURNAL_TEXT_ENTRY, journalEntryText);

            db.insertWithOnConflict(TaskContract.JournalEntry.TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);

            db.close();

            startActivity(new Intent(this, JournalEntriesLog.class));
        } else {
            Toast.makeText(getApplicationContext(),
                    "Unable to save an empty Journal Entry.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
