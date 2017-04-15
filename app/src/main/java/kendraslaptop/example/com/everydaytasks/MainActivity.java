package kendraslaptop.example.com.everydaytasks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button journalBtnId;
    Button foodLogBtnId;
    Button workoutLogBtnId;
    Button sleepLogBtnId;
    Button dayBtnId;
    Button todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolBar = (Toolbar)  findViewById(R.id.toolbarId);
        setSupportActionBar(myToolBar);
        setBtns();
    }

    private void setBtns() {
        journalBtnId = (Button) findViewById(R.id.journalBtnId);
        foodLogBtnId = (Button) findViewById(R.id.foodEntryBtnId);
        workoutLogBtnId = (Button) findViewById(R.id.exersiceBtnId);
        sleepLogBtnId = (Button) findViewById(R.id.sleepLogBtnId);
        dayBtnId = (Button) findViewById(R.id.dayBtnId);
        todoList = (Button) findViewById(R.id.todoBtnId);
    }


    public void onClickJournal(View view) {
        startActivity( new Intent(this, Journal.class));
    }

    public void onPreviousEntries(View view) {
        startActivity( new Intent(this, PreviousEntries.class));
    }

    public void onClickTodo(View view) {
        startActivity( new Intent(this, TodoList.class));
    }

    public void onClickSleepLog(View view) {
        startActivity( new Intent(this, SleepEntry.class));
    }

    public void onClickFoodLog(View view) {
        //TODO: Implicit intent
    }

    public void onClickWorkoutLog(View view) {
        //TODO: Implcit Intent
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_buttons_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
