package com.example.bharatghimire.androideditor.repository.local;

import android.database.Cursor;

/**
 * Created by bharatghimire on 8/9/17.
 */

public interface RepositoryCallBack {

    void success(Cursor cursor);

    void failure(Cursor cursor);
}
