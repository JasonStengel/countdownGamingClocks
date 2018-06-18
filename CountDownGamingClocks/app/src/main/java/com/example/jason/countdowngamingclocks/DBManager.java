package com.example.jason.countdowngamingclocks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//constant variables below
public class DBManager {
    static final String KEY_ROWID = "_id";
    static final String KEY_GAMENAME = "gameName";
    static final String KEY_RELEASEDATE = "releaseDate";
    static final String KEY_IMAGE = "image";
    static final String TAG = "DBManager";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "contacts";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE ="create table  IF NOT EXISTS contacts ( _id integer primary key autoincrement,"+" gameName text not null,"+"releaseDate text not null, "+" image text not null);";
    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    // Constructor
    public DBManager(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
    // opening and closing database
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //--- example method to backup database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS contacts");
            onCreate(db);
        }
    }
    //--- open the database in writable mode and throw Exception if fails
    public DBManager open() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return this;
    }
    // -- closes the database
    public void close(){
        DBHelper.close();
    }
    //--- insert a contact into the database---
    public long insertContact(String gameName, String releaseDate, String image)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_GAMENAME, gameName);
        initialValues.put(KEY_RELEASEDATE, releaseDate);
        initialValues.put(KEY_IMAGE, image);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    //--- deletes a particular contact---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
    //--- retrieves all the contacts---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_GAMENAME,
                KEY_RELEASEDATE,KEY_IMAGE}, null, null, null, null, null);
    }
    //--- retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[]
                {KEY_ROWID,KEY_GAMENAME, KEY_RELEASEDATE,KEY_IMAGE},
                 KEY_ROWID + "=" + rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //--- updates a contact---
    public boolean updateContact(long rowId, String gameName, String releaseDate, String image)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_GAMENAME, gameName);
        args.put(KEY_RELEASEDATE, releaseDate);
        args.put(KEY_IMAGE, image);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}