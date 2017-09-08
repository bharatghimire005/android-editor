package com.example.bharatghimire.androideditor.editor;

import android.view.View;

import com.example.bharatghimire.androideditor.repository.local.DatabaseQueries;

/**
 * Created by bharatghimire on 8/9/17.
 */

public class EditorPresenter implements EditorContract.Presenter {

    private DatabaseQueries databaseQueries;


    public EditorPresenter(DatabaseQueries databaseQueries, View view) {
        this.databaseQueries = databaseQueries;
    }

    @Override
    public void saveData(String html) {

    }
}
