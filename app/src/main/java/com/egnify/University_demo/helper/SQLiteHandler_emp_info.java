package com.egnify.University_demo.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by janardhanyerranagu on 2/22/16.
 */
public class SQLiteHandler_emp_info extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler_emp_info.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "contact_info";
    //contact_table
    private static final String TABLE_CONTACT= "contact_table";
    // Login Table Columns names
    private static final String KEY_S_ID = "id";
    private static final String KEY_EMP_NAME = "emp_name";
    private static final String KEY_DESIG= "designation";
    private static final String KEY_mobile= "mobile";
    private static final String KEY_EMAIL= "email";
    private static final String KEY_ADMIN_RIGHTS = "admin_rights";
    public SQLiteHandler_emp_info(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE2 = "CREATE TABLE " + TABLE_CONTACT + "("
                + KEY_S_ID + " TEXT," + KEY_EMP_NAME + " TEXT,"
                + KEY_DESIG + " TEXT,"
                + KEY_mobile+ " TEXT ,"
                + KEY_EMAIL+ " TEXT ,"
                + KEY_ADMIN_RIGHTS + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE2);
        Log.d(TAG, "Database two tables created");
    }
    public void addcontactinfo(String e_id,String emp_name, String designation,String mobile, String email, String admin_rights) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_S_ID, e_id);
        values.put(KEY_EMP_NAME, emp_name);
        values.put(KEY_DESIG, designation);
        values.put(KEY_mobile, mobile);
        values.put(KEY_EMAIL, email);
        values.put(KEY_ADMIN_RIGHTS, admin_rights);

        // Inserting Row
        long id = db.insert(TABLE_CONTACT, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New User inserted into sqlite: " + id);
    }
    public HashMap<String, String> getcontactDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACT;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("e_id", cursor.getString(0));
            user.put("emp_name", cursor.getString(1));
            user.put("designation", cursor.getString(2));
            user.put("mobile", cursor.getString(3));
            user.put("email", cursor.getString(4));
            user.put("admin_rights", cursor.getString(5));

        }
        cursor.close();
        db.close();
        // return User
        Log.d(TAG, "Fetching User from Sqlite: " + user.toString());

        return user;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);

        // Create tables again
        onCreate(db);

    }
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }
    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CONTACT, null, null);
        db.close();

        Log.d(TAG, "Deleted all User info from sqlite");
    }

}
