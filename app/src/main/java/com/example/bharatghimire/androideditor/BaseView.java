package com.example.bharatghimire.androideditor;

/**
 * Created by bharatghimire on 26/2/17.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void displayMessage(String message);

    void showProgressDialog();

    void hideProgressDialog();

}
