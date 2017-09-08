package com.example.bharatghimire.androideditor.repository.local;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * Created by bharatghimire on 5/9/17.
 */

public class DatabaseQueries {
    private Context context;

    public DatabaseQueries(Context context) {
        this.context = context;
    }

    public void insertData(ContentValues contentValue) {
        ContentValues contentValues= new ContentValues();
        Uri uri = Uri.parse(EditorContentProviderDb.CONTENT_URI+"/"+10);
        context.getContentResolver().insert(uri, contentValues);
    }

}
