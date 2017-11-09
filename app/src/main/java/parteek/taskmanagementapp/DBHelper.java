package parteek.taskmanagementapp;


        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

        import parteek.taskmanagementapp.model.Event;
        import parteek.taskmanagementapp.util.DateUtils;

public class DBHelper extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Events";

    //  table name
    private static final String TABLE_EVENTS = "EventData";

    // Events Table Columns names
    private static final String KEY_ID = "id";

    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION= "description";
    private static final String KEY_DATETIME = "date";
    private Context c ;


    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c=context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SESSIONS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " TEXT," +
                KEY_DESCRIPTION + " TEXT," + KEY_DATETIME + " TEXT "+")";

        db.execSQL(CREATE_SESSIONS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);

        // Create tables again
        onCreate(db);
    }
    void deleteDatabase(){
        c.deleteDatabase(DATABASE_NAME);
    }

    public void addNewEvent(Event e)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, e.getTitle());
        values.put(KEY_DESCRIPTION, e.getDescription());
        values.put(KEY_DATETIME, DateUtils.getDateTime(e.getDate()));


        db.insert(TABLE_EVENTS, null, values);
        db.close();

    }



    // Getting All Events
    public List<Event> getAllEvents() {

        List<Event> sessionList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS+ " ORDER BY "+KEY_ID +"  DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id=(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                String title=cursor.getString(cursor.getColumnIndex(KEY_TITLE));
                String description=cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION));
                String date=cursor.getString(cursor.getColumnIndex(KEY_DATETIME));

                Date d=DateUtils.stringToDate(date);
                sessionList.add( new Event(id,title,d,description));

            } while (cursor.moveToNext());
        }
        db.close();
        return sessionList;
    }

}