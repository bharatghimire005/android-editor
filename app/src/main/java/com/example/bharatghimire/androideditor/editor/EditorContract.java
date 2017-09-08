package com.example.bharatghimire.androideditor.editor;


import com.example.bharatghimire.androideditor.BasePresenter;
import com.example.bharatghimire.androideditor.BaseView;

/**
 * Created by bharatghimire on 26/2/17.
 */

public interface EditorContract {

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter {
        void saveData(int id, String html);
    }
}
