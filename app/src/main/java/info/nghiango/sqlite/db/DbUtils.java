package info.nghiango.sqlite.db;

public class DbUtils {

    // Database Version
    public static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "test_db";


    /**
     * USER TABLE
     */
    /**
     *     private int id;
     *     private String user;
     *     private String pass;
     *     private String displayName;
     *     private String timestamp;
     *     private String type;
     *     private String description;
     */

    public static final String USER_TABLE_NAME = "users";

    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_USER = "user";
    public static final String USER_COLUMN_PASS = "pass";
    public static final String USER_COLUMN_DISPLAY_NAME = "display_name";
    public static final String USER_COLUMN_TYPE = "type";
    public static final String USER_COLUMN_DESCRIPTION = "description";


    // Create table SQL query
    public static final String USER_CREATE_TABLE =
            "CREATE TABLE " + USER_TABLE_NAME + "("
                    + USER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + USER_COLUMN_USER + " TEXT,"
                    + USER_COLUMN_PASS + " TEXT,"
                    + USER_COLUMN_DISPLAY_NAME + " TEXT,"
                    + USER_COLUMN_TYPE + " TEXT,"
                    + USER_COLUMN_DESCRIPTION + " TEXT"
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
