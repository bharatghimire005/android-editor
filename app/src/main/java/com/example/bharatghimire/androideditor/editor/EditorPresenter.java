package com.example.bharatghimire.androideditor.editor;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.example.bharatghimire.androideditor.repository.local.DatabaseLoader;
import com.example.bharatghimire.androideditor.repository.local.DatabaseQueries;
import com.example.bharatghimire.androideditor.repository.local.EditorContentProviderDb;
import com.example.bharatghimire.androideditor.repository.local.RepositoryCallBack;
import com.example.bharatghimire.androideditor.util.TimeUtil;

/**
 * Created by bharatghimire on 8/9/17.
 */

public class EditorPresenter implements EditorContract.Presenter {

    private DatabaseQueries databaseQueries;
    private EditorContract.View view;
    private DatabaseLoader databaseLoader;

    public EditorPresenter(DatabaseLoader databaseLoader, DatabaseQueries databaseQueries, EditorContract.View view) {
        this.databaseQueries = databaseQueries;
        this.view = view;
        this.databaseLoader = databaseLoader;
        setDataBaseLoader();
    }

    public void setDataBaseLoader() {
        databaseLoader.callBack(repositoryCallBack);
    }

    @Override
    public void saveData(int id, String html) {
        if (id != Integer.MAX_VALUE)
            databaseQueries.updateData(id, createContentValueToInsert(id, html));
        else {
            databaseQueries.insertData(createContentValueToInsert(id, html));
        }
    }

    private ContentValues createContentValueToInsert(int id, String html) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(EditorContentProviderDb.KEY_HTML, html);
        if (id != Integer.MAX_VALUE) {
            contentValues.put(EditorContentProviderDb.KEY_ROWID, id);
        }
        contentValues.put(EditorContentProviderDb.KEY_DATE, TimeUtil.timeInMillisecond());
        return contentValues;
    }

    RepositoryCallBack repositoryCallBack = new RepositoryCallBack() {
        @Override
        public void success(Cursor cursor) {
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        String data = cursor.getString(cursor.getColumnIndex(EditorContentProviderDb.KEY_ROWID))
                        +cursor.getString(cursor.getColumnIndex(EditorContentProviderDb.KEY_HTML));
                        Log.d("success", "success: "+data);
                    } while (cursor.moveToNext());
                }
            }
        }

        @Override
        public void failure(Cursor cursor) {

        }
    };
}
