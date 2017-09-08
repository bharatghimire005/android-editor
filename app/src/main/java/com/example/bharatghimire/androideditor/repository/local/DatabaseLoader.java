package com.example.bharatghimire.androideditor.repository.local;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

/**
 * Created by bharatghimire on 6/8/17.
 */

public class DatabaseLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private Context context;

    public DatabaseLoader(Context context) {
        this.context = context;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                EditorContentProviderDb.KEY_ROWID,
                EditorContentProviderDb.KEY_HTML,
                EditorContentProviderDb.KEY_DATE};
        CursorLoader cursorLoader = new CursorLoader(context,
                EditorContentProviderDb.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
