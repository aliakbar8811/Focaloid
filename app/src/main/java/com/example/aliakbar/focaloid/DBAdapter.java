package com.example.aliakbar.focaloid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 2/14/2017.
 */

public class DBAdapter {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "focaloid_ldb";

    // Login table name
    private static final String TABLE_USER = "user";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CREATED_AT = "created_at";

    static final String DATABASE_CREATE ="CREATE TABLE "+TABLE_USER+"("+KEY_ID+" integer primary key autoincrement,"+KEY_NAME+" text,"+KEY_EMAIL+" text unique,"+KEY_PASSWORD+" text,"+KEY_CREATED_AT+" text)";

    public SQLiteDatabase db;
    private final Context context;
    private DBHelper dbHelper;

    public  DBAdapter(Context _context)
    {
        context= _context;
        dbHelper=new DBHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    public  DBAdapter open() throws SQLException
    {
        //dbHelper=new DbHelper(context);
        db=dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {

        db.close();
        dbHelper.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void RegisterUser(String name,String email, String password, String created_at)
    {
        ContentValues newValues=new ContentValues();

        newValues.put("name",name);
        newValues.put("email",email);
        newValues.put("password",password);
        newValues.put("created_at",created_at);

        db.insert(TABLE_USER,null,newValues);
    }

    public String getsingleUser(String input_user)
    {
        Cursor cursor = db.query(TABLE_USER, null, " email=?", new String[]{input_user}, null, null, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        cursor.moveToFirst();
        String password=cursor.getString(cursor.getColumnIndex("password"));

        cursor.close();

        return password;
    }
    public String getUserName(String input_user)
    {
        Cursor cursor = db.query(TABLE_USER, null, " email=?", new String[]{input_user}, null, null, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return "NOT EXIST";
        }

        cursor.moveToFirst();
        String uName=cursor.getString(cursor.getColumnIndex("name"));

        cursor.close();

        return uName;
    }
}
