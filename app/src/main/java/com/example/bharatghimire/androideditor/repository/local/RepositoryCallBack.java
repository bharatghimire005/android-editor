package com.example.bharatghimire.androideditor.repository.local;

/**
 * Created by bharatghimire on 8/9/17.
 */

public interface RepositoryCallBack<T> {

    void success(T t);

    void failure(T t);
}
