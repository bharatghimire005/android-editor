package com.example.bharatghimire.androideditor.editor;

import android.content.ContentValues;

import com.example.bharatghimire.androideditor.repository.local.DatabaseQueries;
import com.example.bharatghimire.androideditor.repository.local.EditorContentProviderDb;
import com.example.bharatghimire.androideditor.util.TimeUtil;

/**
 * Created by bharatghimire on 8/9/17.
 */

public class EditorPresenter implements EditorContract.Presenter {

    private DatabaseQueries databaseQueries;
    private EditorContract.View view;

    public EditorPresenter(DatabaseQueries databaseQueries, EditorContract.View view) {
        this.databaseQueries = databaseQueries;
        this.view = view;
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
}
