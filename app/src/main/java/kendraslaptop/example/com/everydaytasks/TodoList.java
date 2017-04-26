package kendraslaptop.example.com.everydaytasks;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kendraslaptop.example.com.everydaytasks.db.TaskContract;
import kendraslaptop.example.com.everydaytasks.db.TaskDBHelper;

/**
 * Followed a To-do tutorial and modified for my needs/added
 * functionality for my needs as well. Some of this list view code/
 * database code was also ported over to other class files for Journal,
 * and sleep log views. Tutorial found here:
 * https://www.sitepoint.com/starting-android-development-creating-todo-app/
 * Created by Kendra's Laptop on 4/13/2017.
 */

public class TodoList extends AppCompatActivity {
    private final String TAG = "Todo List";
    private ListView mTaskListView;
    private ListView mDoneTodoListView;
    private TaskDBHelper mHelper;

    /**
     * Adapter for the to-do list
     */
    private ArrayAdapter<String> mTodoAdapter;

    /**
     * Adapter for the done to-do list
     *
     */
    private ArrayAdapter<String> doneTodoAdapter;


    //TODO: Add back in the done todo list back into the todo list.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.todo_toolbar_id);
        setSupportActionBar(myToolBar);
        setListViews();

        updateUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_todo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item != null) {
            switch (item.getItemId()){
                case R.id.addTodo_btn_id:
                    final EditText taskEditText = new EditText(this);
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setTitle("Add a new task")
                            .setMessage("What do you want to do next?")
                            .setView(taskEditText)
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String task = String.valueOf(taskEditText.getText());
                                    SQLiteDatabase db = mHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();
                                    values.put(TaskContract.TaskNewEntry.COL_TASK_TITLE, task);
                                    db.insertWithOnConflict(TaskContract.TaskNewEntry.TABLE,
                                            null,
                                            values,
                                            SQLiteDatabase.CONFLICT_REPLACE);
                                    db.close();
                                    updateUI();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .create();
                    dialog.show();
                    return true;
                case  R.id.home_btn:
                    startActivity(new Intent(this, MainActivity.class));
                default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return false;
        }
    }

    private void setListViews() {
        mHelper = new TaskDBHelper(this);
        mTaskListView = (ListView) findViewById(R.id.todo_list_view_id);
        mDoneTodoListView = (ListView)  findViewById(R.id.done_todo_list_view_id);
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();

        //Delete Task from To-do's list
        db.delete(TaskContract.TaskNewEntry.TABLE,
                    TaskContract.TaskNewEntry.COL_TASK_TITLE + "= ?",
                    new String[] {task});

        //Add task to done to-do's list
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskDoneEntry.COL_TASK_TITLE, task);
        db.insertWithOnConflict(TaskContract.TaskDoneEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        db.close();
        updateUI();
    }

    public void removeTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.done_task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();

        //Delete Task from To-do's list
        db.delete(TaskContract.TaskDoneEntry.TABLE,
                TaskContract.TaskDoneEntry.COL_TASK_TITLE + "= ?",
                new String[] {task});

        db.close();
        updateUI();
    }

    public void addTaskBackToStillTodoList(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.done_task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskNewEntry.COL_TASK_TITLE, task);
        db.insertWithOnConflict(TaskContract.TaskNewEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        task = String.valueOf(taskTextView.getText());

        //Delete Task from To-do's list
        db.delete(TaskContract.TaskDoneEntry.TABLE,
                TaskContract.TaskDoneEntry.COL_TASK_TITLE + "= ?",
                new String[] {task});

        db.close();
        updateUI();
    }

    private void updateUI() {
        Log.d(TAG, "Update UI");
        SQLiteDatabase db = mHelper.getReadableDatabase();
        updateNewEntryUI(db);
        updateDoneEntriesUI(db);
        db.close();
    }

    private void updateNewEntryUI(SQLiteDatabase db) {
        ArrayList<String> taskList = new ArrayList<>();
        Cursor cursor = db.query(TaskContract.TaskNewEntry.TABLE,
                new String[]{TaskContract.TaskNewEntry._ID, TaskContract.TaskNewEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskNewEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
            taskList.add(cursor.getString(idx));
        }

        if (mTodoAdapter == null) {
            mTodoAdapter = new ArrayAdapter<>(this,
                    R.layout.todo_message,

                    R.id.task_title,
                    taskList);
            mTaskListView.setAdapter(mTodoAdapter);
        } else {
            mTodoAdapter.clear();
            mTodoAdapter.addAll(taskList);
            mTodoAdapter.notifyDataSetChanged();
        }
        cursor.close();
    }

    private void updateDoneEntriesUI(SQLiteDatabase db) {
        ArrayList<String> taskList = new ArrayList<>();
        Cursor cursor = db.query(TaskContract.TaskDoneEntry.TABLE,
                new String[]{TaskContract.TaskDoneEntry._ID, TaskContract.TaskDoneEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskDoneEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
            taskList.add(cursor.getString(idx));
        }

        if (doneTodoAdapter == null) {
            doneTodoAdapter = new ArrayAdapter<>(this,
                    R.layout.done_todo_message,
                    R.id.done_task_title,
                    taskList);
            mDoneTodoListView.setAdapter(doneTodoAdapter);
        } else {
            doneTodoAdapter.clear();
            doneTodoAdapter.addAll(taskList);
            doneTodoAdapter.notifyDataSetChanged();
        }
        cursor.close();
    }
}
