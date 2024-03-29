package com.example.crudwithsqlite.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "Database.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserProfile.Users.TABLE_NAME + " (" +
                    UserProfile.Users._ID + "INTEGER PRIMARY KEY," +
                    UserProfile.Users.COLUMN_1 + "TEXT," +
                    UserProfile.Users.COLUMN_2 + "TEXT," +
                    UserProfile.Users.COLUMN_3 + "TEXT," +
                    UserProfile.Users.COLUMN_4 + "TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserProfile.Users.TABLE_NAME;

    public long addInfo (String username, String dob, String password, String gender) {
        //Gets the data repository in the write mode
        SQLiteDatabase db = getWritableDatabase();

        //create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_1, username);
        values.put(UserProfile.Users.COLUMN_2, dob);
        values.put(UserProfile.Users.COLUMN_3, password);
        values.put(UserProfile.Users.COLUMN_4, gender);

        //Inter the new row, returning the primary key value of the new row
        long newRowId = db.insert(UserProfile.Users.TABLE_NAME, null, values);

        return newRowId;
    }

    public boolean updateInfo (String username, String dob, String password, String gender) {
        SQLiteDatabase db = getWritableDatabase();

        //new value for one column
        ContentValues values = new ContentValues();
        values.put(UserProfile.Users.COLUMN_2, dob);
        values.put(UserProfile.Users.COLUMN_3, password);
        values.put(UserProfile.Users.COLUMN_4, gender);

        //Which row to update, based on the title
        String selection = UserProfile.Users.COLUMN_1 + " LIKE ?";
        String[] selectionArgs = {username};

        int count = db.update(
                UserProfile.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count >= 1) {
            return true;
        }
        else {
            return false;
        }

    }

    public void deleteInfo(String username) {
        SQLiteDatabase db = getWritableDatabase();

        //Define 'where' part of query
        String selection = UserProfile.Users.COLUMN_1 + "LIKE ?";
       //specify arguments in placeholder order.
       String[] selectionArgs = {username};
       //Issue SQL statement.
        int deletedRows = db.delete(UserProfile.Users.TABLE_NAME, selection, selectionArgs);

    }

    public List readAllInfo (String username) {

        SQLiteDatabase db = getReadableDatabase();
        //define a projection that specifies which columns from the database
        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_1,
                UserProfile.Users.COLUMN_2,
                UserProfile.Users.COLUMN_3,
                UserProfile.Users.COLUMN_4,
        };

        String selection = UserProfile.Users.COLUMN_1 + " LIKE ?";
        String[] selectionArgs = {username};

        String sortOrder = UserProfile.Users.COLUMN_1 + "ACS";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List userInfo = new ArrayList<>();
        while (cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_1));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_2));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_3));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_4));

            userInfo.add(user);
            userInfo.add(dob);
            userInfo.add(password);
            userInfo.add(gender);

        }
        cursor.close();
        return userInfo;

    }

    public boolean loginUser (String username, String password) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserProfile.Users.COLUMN_1,
                UserProfile.Users.COLUMN_3
        };

        String selection = UserProfile.Users.COLUMN_1 + " = ? AND " + UserProfile.Users.COLUMN_3 + " = ?";
        String[] selectionArgs = {username, password};

        String sortOrder = UserProfile.Users.COLUMN_1 + "ACS";

        Cursor cursor = db.query(
                UserProfile.Users.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List validUser = new ArrayList();

        while (cursor.moveToNext()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow(UserProfile.Users.COLUMN_1));
        }
        cursor.close();

        if (validUser.isEmpty()) {
            return false;
        }
        else {
            return true;
        }
    }
}
