package com.yellowsoft.subhankar.devcomic;

import android.view.View;

/**
 * Created by subhankar on 9/14/2016.
 */
public interface Presenter<T extends View> {
    void onCreate();
    void onStart();
    void onStop();
    void onPause();
    void attachView(T view);
}
