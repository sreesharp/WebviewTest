package com.sreesharp.webviewdemo.utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.sreesharp.webviewdemo.models.Entry;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "entryDb";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    private static final String TABLE_ENTRY = "entry";

    // Entry Table Columns
    private static final String KEY_ENTRY_ID = "id";
    private static final String KEY_ENTRY_NAME = "name";
    private static final String KEY_ENTRY_VALUE = "value";
    private static final String KEY_ENTRY_DATE = "date";

    private static DbHelper sInstance;

    public static synchronized DbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    // Called when the database is created for the FIRST time.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ENTRY_TABLE = "CREATE TABLE " + TABLE_ENTRY +
                "(" +
                KEY_ENTRY_ID + " INTEGER PRIMARY KEY," +
                KEY_ENTRY_NAME + " TEXT," +
                KEY_ENTRY_VALUE + " TEXT," +
                KEY_ENTRY_DATE + " TEXT" +
                ")";
        db.execSQL(CREATE_ENTRY_TABLE);

    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTRY);
            onCreate(db);
        }
    }

    // Insert an entry into the database
    public long addEntry(Entry entry) {
        long entryId = -1;
        if(entry == null)
            return entryId;
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_ENTRY_NAME, entry.name);
            values.put(KEY_ENTRY_VALUE, entry.value);
            values.put(KEY_ENTRY_DATE, entry.dateTime);
            entryId = db.insertOrThrow(TABLE_ENTRY, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DB_ERROR", "Error while trying to add entry to database");
        } finally {
            db.endTransaction();
        }
        return entryId;
    }

    //Add the entries collection to the database
    public void addEntries(List<Entry> entries){
        if(entries != null){
            for (Entry entry : entries) {
                addEntry(entry);
            }
        }
    }

    // Get all entries in the database
    public List<Entry> getAllEntries() {
        List<Entry> entries = new ArrayList<>();

        Cursor cursor = getAllEntriesCursor();
        try {
            if (cursor.moveToFirst()) {
                do {
                    Entry entry = new Entry();
                    entry.name = cursor.getString(cursor.getColumnIndex(KEY_ENTRY_NAME));
                    entry.value = cursor.getString(cursor.getColumnIndex(KEY_ENTRY_VALUE));
                    entry.dateTime = cursor.getString(cursor.getColumnIndex(KEY_ENTRY_DATE));
                    entries.add(entry);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DB_ERROR", "Error while trying to get entries from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return entries;
    }

    //Get the Cursor related to the entries
    public Cursor getAllEntriesCursor(){
        String query = "select id as _id, name, value, date from entry order by date desc";
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(query, null);
    }

}