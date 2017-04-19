package kendraslaptop.example.com.everydaytasks.db;

/**
 * Created by Kendra's Laptop on 4/16/2017.
 */
import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "Wellness.db";
    public static final int DB_VERSION = 1;

    public class TaskNewEntry implements BaseColumns {
        public static final String TABLE = "todo_tasks";

        public static final String COL_TASK_TITLE = "title";
    }

    public class TaskDoneEntry implements BaseColumns {
        public static final String TABLE = "done_todo_tasks";

        public static final String COL_TASK_TITLE = "title";
    }

    public class SleepLogEntry implements  BaseColumns {
        public static final String TABLE = "sleep_log";

        public static final String COL_SLEEP_QUAL = "quality";

        public static final String COL_SLEEP_START = "sleep_start";

        public static final String COL_SLEEP_END = "sleep_end";

        public static final String COL_SLEEP_DATE = "sleep_date";

        public  static final String COL_SLEEP_RATING = "rating";
    }
}
