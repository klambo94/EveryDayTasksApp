package kendraslaptop.example.com.everydaytasks;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import kendraslaptop.example.com.everydaytasks.db.TaskContract;
import kendraslaptop.example.com.everydaytasks.db.TaskDBHelper;


/**
 * Created by Kendra's Laptop on 4/13/2017.
 */

public class SleepEntry extends AppCompatActivity {
    private Settings settings;
    private String format = "";
    private String qualityString = "";
    private int hour, min;
    private int month, day, year;
    private TextView startTime;
    private TextView endTime;
    private TextView date;
    private Button quality;
    private TaskDBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_log_entry);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.sleep_entry_toolbar_id);
        setSupportActionBar(myToolBar);
        settings = new Settings();

        mHelper = new TaskDBHelper(this);
        startTime = (TextView) findViewById(R.id.sleep_start_btn_id);
        endTime = (TextView) findViewById(R.id.sleep_end_btn_id);
        date = (TextView) findViewById(R.id.date_btn_id);
        quality = (Button) findViewById(R.id.sleep_quality_btn_id);
        setDefaultDateToCurrentDate();

    }

    private void setDefaultDateToCurrentDate() {
        long date = System.currentTimeMillis();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String currentDate = sdf.format(date);
        String[] dateParts = currentDate.split("/");
        month = Integer.parseInt(dateParts[0]);
        day = Integer.parseInt(dateParts[1]);
        year = Integer.parseInt(dateParts[2]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_buttons_sleep_entery, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item != null) {
            switch (item.getItemId()){
                case R.id.settings_btn:
                    startActivityForResult(settings.getSettingsIntent(), settings.getRequestCode());
                    return true;
                case R.id.save_btn:
                    addSleepInformationToDb();
                    //TODO: Go to prevSleepEntries
                case R.id.prevSleepEntryBtn:
                    //TODO: Retrive list of previous entries - this will have to show up as an intent
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return false;
        }
    }

    public void enterSleepQuality(View view) {
        final EditText taskEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("How well did you sleep?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        qualityString = String.valueOf(taskEditText.getText());
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void setStartTime(View view) {
        showDialog(1000);
    }

    public void setEndTime(View view) {
        showDialog(999);
    }

    public void setDate(View view) {showDialog(888);}

    public void showTime(TextView view, int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        view.setText(new StringBuilder().append(hour).append(" : ")
                .append(min).append(" ").append(format));

    }

    @Override
    protected Dialog onCreateDialog(int id) {
       if(id == 1000) {
           return new TimePickerDialog(this, myStartTimeListener, hour, min, false);
       } else if(id == 999) {
           return new TimePickerDialog(this, myEndTimeListener, hour, min, false);
       } else if(id == 888) {
           return new DatePickerDialog(this,
                   myDateListener, year, month, day);
       }
        return null;
    }
    private TimePickerDialog.OnTimeSetListener myStartTimeListener = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    showTime(startTime, hourOfDay, minute);
                }
            };

    private TimePickerDialog.OnTimeSetListener myEndTimeListener = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    showTime(endTime, hourOfDay, minute);
                }
            };

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date.setText(new StringBuilder().append(month).append("/")
                .append(day).append("/").append(year));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK) {
            if(requestCode == settings.getRequestCode()){
                settings.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    private void addSleepInformationToDb() {
        String start = String.valueOf(startTime.getText());
        String end = String.valueOf(endTime.getText());
        String dateToLog = String.valueOf(date.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.SleepLogEntry.COL_SLEEP_QUAL, qualityString);
        values.put(TaskContract.SleepLogEntry.COL_SLEEP_DATE, dateToLog);
        values.put(TaskContract.SleepLogEntry.COL_SLEEP_START, start);
        values.put(TaskContract.SleepLogEntry.COL_SLEEP_END, end);
        db.insertWithOnConflict(TaskContract.SleepLogEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
}
