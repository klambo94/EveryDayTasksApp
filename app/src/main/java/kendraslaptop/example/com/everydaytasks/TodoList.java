package kendraslaptop.example.com.everydaytasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Kendra's Laptop on 4/13/2017.
 */

public class TodoList extends AppCompatActivity {
    private ListView todoListView;
    private ListView doneTodoListView;

    /**
     * Adapter for the to-do list
     */
    private ArrayAdapter<String> todoArrayAdapter;

    /**
     * Adapter for the done to-do list
     *
     */
    private ArrayAdapter<String> doneTodoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        Toolbar myToolBar = (Toolbar) findViewById(R.id.todo_toolbar_id);
        setSupportActionBar(myToolBar);
        setListViews();
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
                case R.id.addTodo_btn_id:
                    //TODO: Data persistance here and return intent - possibly to how was your day/ add todo to adapter
               default:
                    return super.onOptionsItemSelected(item);
            }
        } else {
            return false;
        }
    }

    private void setListViews() {
        todoListView = (ListView) findViewById(R.id.todo_list_view_id);
        doneTodoListView = (ListView)  findViewById(R.id.done_todo_list_view_id);

        // Initialize the array adapter for the to-do list
        todoArrayAdapter = new ArrayAdapter<>(this, R.layout.todo_message);

        todoListView.setAdapter(todoArrayAdapter);

        // Initialize the array adapter for the done to-do list
        doneTodoAdapter = new ArrayAdapter<>(this, R.layout.done_todo_message);

        doneTodoListView.setAdapter(doneTodoAdapter);


    }


}
