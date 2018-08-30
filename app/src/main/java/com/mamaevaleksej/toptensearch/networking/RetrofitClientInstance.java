package com.mamaevaleksej.toptensearch.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static final String TAG = RetrofitClientInstance.class.getSimpleName();
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://www.googleapis.com/customsearch/";

    // Синглтон Retrofit
    public static Retrofit getRetrofitInstance(){
        if (retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
