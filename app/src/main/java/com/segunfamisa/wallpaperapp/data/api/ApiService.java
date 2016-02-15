package com.segunfamisa.wallpaperapp.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit API service
 *
 * Created by segun.famisa on 15/02/2016.
 */
public interface ApiService {

    String BASE_URL = "https://api.unsplash.com/";


    /**
     * helper class to create new services
     */
    class Creator {

        public static ApiService newService() {
            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            return retrofit.create(ApiService.class);
        }
    }
}
