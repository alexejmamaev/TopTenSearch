package com.mamaevaleksej.toptensearch.networking;

import com.mamaevaleksej.toptensearch.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {
    @GET("v1?key=AIzaSyCnn5ygPUzGz6iPqRoZ0ddiWGXmw-FYreg&cx=011220814421415113470%3Aeoysj1xnusy")
    Call<Example> getResultObject(@Query("q") String query);
}

