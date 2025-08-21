package com.test.tools;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.test.tools.DBHelper;

public class MyContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.test.tools.provider";

    private static final UriMatcher uriMatcher;

    private static final int ITEM = 1;

    private Context context;

    private DBHelper dbHelper;

    private SQLiteDatabase db;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, DBHelper.TB_NAME, ITEM);
    }

    public MyContentProvider() {
    }

    private String getTableName(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ITEM:
                return DBHelper.TB_NAME;
            default:
                return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String tableName = getTableName(uri);
        db.insert(tableName, null, values);

        context.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();

        db.execSQL("DELETE FROM " + DBHelper.TB_NAME + ";");
        db.execSQL("INSERT INTO " + DBHelper.TB_NAME + " VALUES(1, 'com.example.studyapplication', 'libc.so');");
        db.execSQL("INSERT INTO " + DBHelper.TB_NAME + " VALUES(2, 'com.example.studyapplication', 'libart.so');");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String tableName = getTableName(uri);
        return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}