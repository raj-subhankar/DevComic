package com.yellowsoft.subhankar.devcomic;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by subhankar on 9/16/2016.
 */
@Component(modules = {AppModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);
}
