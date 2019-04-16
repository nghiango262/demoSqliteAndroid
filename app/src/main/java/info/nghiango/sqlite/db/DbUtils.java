package info.nghiango.sqlite.db;

public class DbUtils {
    /**
     * USER TABLE
     */

    public static final String USER_TABLE_NAME = "users";

    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_NOTE = "note";
    public static final String USER_COLUMN_TIMESTAMP = "timestamp";


    // Create table SQL query
    public static final String USER_CREATE_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME + "("
                    + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + USER_COLUMN_NOTE + " TEXT,"
                    + USER_COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";


    /**
     * NOTE_ TABLE
     */

    public static final String NOTE_TABLE_NAME = "notes";

    public static final String NOTE_COLUMN_ID = "id";
    public static final String NOTE_COLUMN_NOTE = "note";
    public static final String NOTE_COLUMN_TIMESTAMP = "timestamp";


    // Create table SQL query
    public static final String NOTE_CREATE_TABLE =
            "CREATE TABLE " + NOTE_TABLE_NAME + "("
                    + NOTE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NOTE_COLUMN_NOTE + " TEXT,"
                    + NOTE_COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";
}
