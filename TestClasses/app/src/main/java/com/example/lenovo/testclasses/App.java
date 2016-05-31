package com.example.lenovo.testclasses;

import android.app.Application;

/**
 * Created by ovidiu.latcu on 3/1/2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Injector.init(this);
    }
}
