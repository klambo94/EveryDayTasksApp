package kendraslaptop.example.com.everydaytasks.db;

/**
 * Followed a To-do tutorial and modified for my needs/added
 * functionality for my needs as well
 * Tutorial found here:
 * https://www.sitepoint.com/starting-android-development-creating-todo-app/
 * Created by Kendra's Laptop on 4/16/2017.
 */
import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "Wellness.db";
    public static final int DB_VERSION = 4;

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

    public class JournalEntry implements BaseColumns {
        public static final String TABLE = "journals";

        public static final String COL_LOCATION = "location";

        public static final String COL_DATE_AND_TIME = "date_time";

        public static final String COL_JOURNAL_TEXT_ENTRY = "journal_text_entry";
    }
}
