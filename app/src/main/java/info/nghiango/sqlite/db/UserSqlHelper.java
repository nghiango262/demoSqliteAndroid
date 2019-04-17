package info.nghiango.sqlite.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import info.nghiango.sqlite.db.model.Note;
import info.nghiango.sqlite.db.model.User;

import static info.nghiango.sqlite.db.DbUtils.DATABASE_NAME;
import static info.nghiango.sqlite.db.DbUtils.DATABASE_VERSION;

/**
 * Created by ravi on 15/03/18.
 */

public class UserSqlHelper extends SQLiteOpenHelper {

    public UserSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        db.execSQL(DbUtils.USER_CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DbUtils.USER_TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertUser(String user, String pass, String displayName, String type, String description) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(DbUtils.USER_COLUMN_USER, user);
        values.put(DbUtils.USER_COLUMN_PASS, pass );
        values.put(DbUtils.USER_COLUMN_DISPLAY_NAME, displayName);
        values.put(DbUtils.USER_COLUMN_TYPE, type);
        values.put(DbUtils.USER_COLUMN_DESCRIPTION, description);


        // insert row
        long id = db.insert(DbUtils.USER_TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public User getUser(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(DbUtils.USER_TABLE_NAME,
                new String[]{DbUtils.USER_COLUMN_ID, DbUtils.USER_COLUMN_USER, DbUtils.USER_COLUMN_PASS, DbUtils.USER_COLUMN_DISPLAY_NAME, DbUtils.USER_COLUMN_TYPE, DbUtils.USER_COLUMN_DESCRIPTION},
                DbUtils.USER_COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare user object
        User user = new User(
                cursor.getInt(cursor.getColumnIndex(DbUtils.USER_COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_USER)),
                cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_PASS)),
                cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_DISPLAY_NAME)),
                cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_TYPE)),
                cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_DESCRIPTION)));

        // close the db connection
        cursor.close();

        return user;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DbUtils.USER_TABLE_NAME + " ORDER BY " +
                DbUtils.USER_COLUMN_ID + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(DbUtils.USER_COLUMN_ID)));
                user.setUser(cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_USER)));
                user.setPass(cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_PASS)));
                user.setDisplayName(cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_DISPLAY_NAME)));
                user.setType(cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_TYPE)));
                user.setDescription(cursor.getString(cursor.getColumnIndex(DbUtils.USER_COLUMN_DESCRIPTION)));

                users.add(user);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return users list
        return users;
    }

    public int getUserCount() {
        String countQuery = "SELECT  * FROM " + DbUtils.USER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        // return count
        return count;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DbUtils.USER_COLUMN_USER, user.getUser());
        values.put(DbUtils.USER_COLUMN_PASS, user.getPass());
        values.put(DbUtils.USER_COLUMN_DISPLAY_NAME, user.getDisplayName());
        values.put(DbUtils.USER_COLUMN_TYPE, user.getType());
        values.put(DbUtils.USER_COLUMN_DESCRIPTION, user.getDescription());

        // updating row
        return db.update(DbUtils.USER_TABLE_NAME, values, DbUtils.USER_COLUMN_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DbUtils.USER_TABLE_NAME,                 //Tên Bảng
                DbUtils.USER_COLUMN_ID + " = ?", //Mệnh đề where
                new String[]{String.valueOf(user.getId())}); //Tham số cho mệnh đề where
        db.close();
    }
}
