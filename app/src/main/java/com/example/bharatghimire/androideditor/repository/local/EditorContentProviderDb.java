package com.example.bharatghimire.androideditor.repository.local;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by bharatghimire on 26/6/17.
 */

public class EditorContentProviderDb extends ContentProvider {
    public static final String AUTHORITY = "com.example.bharatghimire.androideditor";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_HTML= "Description";
    public static final String KEY_DATE = "date";
    public static final String SQLITE_TABLE = "editor";
    static final String CREATE_SCHEDULAR =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_HTML + " TEXT NOT NUll ," +
                    KEY_DATE + " TEXT NON NULL)";
    DataBaseHelper dataBaseHelper;
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/editors");
    private static final int ALL_HTML_FILES = 1;
    private static final int SINGLE_HTML_FILE = 2;
    public static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "editors", ALL_HTML_FILES);
        uriMatcher.addURI(AUTHORITY, "editors/#", SINGLE_HTML_FILE);

    }

    @Override
    public boolean onCreate() {
        dataBaseHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(SQLITE_TABLE);
        switch (uriMatcher.match(uri)) {
            case ALL_HTML_FILES:
                //
                break;
            case SINGLE_HTML_FILE:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(KEY_ROWID + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);

        }
        Cursor cursor = queryBuilder.query(sqLiteDatabase, projection, selection,
                selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        Uri result = doInsert(uri, contentValues, sqLiteDatabase);
        getContext().getContentResolver().notifyChange(result, null);
        return result;
    }


    private Uri doInsert(Uri uri, ContentValues values, SQLiteDatabase database) {
        Uri result = null;
        switch (uriMatcher.match(uri)) {
            case SINGLE_HTML_FILE:
                long id = database.insert(SQLITE_TABLE, "", values);
                if (id == -1) throw new SQLException();
                result = Uri.withAppendedPath(uri, String.valueOf(id));
        }
        return result;
    }


    private int doDelete(Uri uri, String selection, String[] selectionArgs, SQLiteDatabase database) {
        int result = -1;
        switch (uriMatcher.match(uri)) {
            case SINGLE_HTML_FILE:
                String id = uri.getPathSegments().get(1);
                selection = KEY_ROWID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                result = database.delete(SQLITE_TABLE, selection, selectionArgs);
                if (result == -1) throw new SQLException("Error inserting data!");

        }
        return result;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        int result = doDelete(uri, s, strings, sqLiteDatabase);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }


    private int doUpdate(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs, SQLiteDatabase database) {
        switch (uriMatcher.match(uri)) {
            case ALL_HTML_FILES:
                //do nothing
                break;
            case SINGLE_HTML_FILE:
                String id = uri.getPathSegments().get(1);
                selection = KEY_ROWID + "=" + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = database.update(SQLITE_TABLE, contentValues, selection, selectionArgs);
        return updateCount;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        int result = doUpdate(uri, contentValues, s, strings, sqLiteDatabase);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }


}
