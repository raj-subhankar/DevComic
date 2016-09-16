package com.yellowsoft.subhankar.devcomic;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by subhankar on 9/15/2016.
 */
public class ComicsAPI {
    private interface ComicService {
        @GET("/53.json")
        Observable<Comic> getComic();
    }

    private Observable<Comic> comicsObservable = new Retrofit.Builder()
            .baseUrl("https://comic.browserling.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build().create(ComicService.class).getComic().cache();


    public Observable<Comic> getComicsObservable() {
        return comicsObservable;
    }
}
