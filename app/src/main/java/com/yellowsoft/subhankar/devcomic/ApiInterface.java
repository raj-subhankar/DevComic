package com.yellowsoft.subhankar.devcomic;

/**
 * Created by subhankar on 9/5/2016.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by subhankar on 7/17/2016.
 */
public interface ApiInterface {
    @GET("{num}")
    Call<Comic> getComic(@Path("num") String number);
}

