package com.yellowsoft.subhankar.devcomic;

/**
 * Created by subhankar on 9/16/2016.
 */

public class DaggerInjector {
    private static AppComponent appComponent = DaggerAppComponent.builder().appModule(new AppModule()).build();

    public static AppComponent get() {
        return appComponent;
    }
}
