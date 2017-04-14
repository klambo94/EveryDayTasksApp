package kendraslaptop.example.com.everydaytasks;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Kendra's Laptop on 3/28/2017.
 */

public class Settings extends AppCompatActivity {
    Intent settingsIntent = new Intent("RETRIVE_FONT");
    private static final int REQUEST_SETTINGS = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View theInflatedView = inflater.inflate(R.layout.activity_main, null);
        setContentView(theInflatedView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK) {
            //TODO: Change font of Journal entries
        }
    }

    public Intent getSettingsIntent() {
        return settingsIntent;
    }

    public int getRequestCode() {
        return REQUEST_SETTINGS;
    }
}
