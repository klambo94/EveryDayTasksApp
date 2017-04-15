package kendraslaptop.example.com.everydaytasks;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;


/**
 * Created by Kendra's Laptop on 4/13/2017.
 */

public class SleepEntry extends AppCompatActivity {
    private Settings settings;
    private String format = "";
    int hour, min;
    TextView startTime;
    TextView endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_log_entry);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.sleep_entry_toolbar_id);
        setSupportActionBar(myToolBar);
        settings = new Settings();

        startTime = (TextView) findViewById(R.id.sleep_start_btn_id);
        endTime = (TextView) findViewById(R.id.sleep_end_btn_id);
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
                    //TODO: Data persistance here and return intent - possibly to how was your day
                case R.id.prevSleepEntryBtn:
                    //TODO: Retrive list of previous entries - this will have to show up as an intent
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return false;
        }
    }

    public void setStartTime(View view) {
        showDialog(1000);
    }

    public void setEndTime(View view) {
        showDialog(999);
    }

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
        Dialog timePicker = null;
       if(id == 1000) {
           timePicker =  new TimePickerDialog(this, myStartTimeListener, hour, min, false);
       } else if(id == 999) {
           timePicker = new TimePickerDialog(this, myEndTimeListener, hour, min, false);
       }
        return timePicker;
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK) {
            if(requestCode == settings.getRequestCode()){
                settings.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
