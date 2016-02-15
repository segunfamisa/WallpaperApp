package com.segunfamisa.wallpaperapp.data.api;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.segunfamisa.wallpaperapp.data.model.Photo;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by segun.famisa on 15/02/2016.
 */
public interface PhotoService {

    String BASE_URL = "https://api.unsplash.com/";

    String CLIENT_ID = "8c63042b7286eac89ac8c9c73673d368c89dbc2575ba29dcb0a3b3b25880b2f6";

    @GET("/photos/?" + CLIENT_ID)
    Observable<Photo> getPhotos();

    /**
     * helper class to create new services
     */
    class Creator {

        public static ApiService newPhotoService() {
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
