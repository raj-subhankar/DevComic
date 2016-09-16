package com.yellowsoft.subhankar.devcomic;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by subhankar on 9/15/2016.
 */
public class ComicsPresenter {
    ComicsAPI comicsAPI;

    @Inject
    public ComicsPresenter(ComicsAPI comicsAPI) {
        this.comicsAPI = comicsAPI;
    }

    public void loadComicsFromAPI() {
        comicsAPI.getComicsObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Comic>() {
                    @Override
                    public void onNext(Comic newComic) {
                        EventBus.getDefault().post(new NewComicsEvent(newComic));

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        EventBus.getDefault().post(new ErrorEvent());
                    }


                });

    }

}
