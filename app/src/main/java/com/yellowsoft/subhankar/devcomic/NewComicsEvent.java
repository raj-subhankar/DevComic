package com.yellowsoft.subhankar.devcomic;

import java.util.List;

/**
 * Created by subhankar on 9/15/2016.
 */
public class NewComicsEvent {
    Comic comic;

    public Comic getComics() {
        return comic;
    }

    public NewComicsEvent(Comic comic) {
        this.comic = comic;
    }
}
