package com.segunfamisa.wallpaperapp.data.api;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.segunfamisa.wallpaperapp.data.model.Photo;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
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

    @GET("/photos/?client_id=" + ApiConstants.CLIENT_ID)
    Observable<ArrayList<Photo>> getPhotos(@Query("per_page")int count);

    @GET
    Observable<Response> downloadPhoto(String photo);

    /**
     * helper class to create new services
     */
    class Creator {

        public static PhotoService newPhotoService() {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            // add logging as last interceptor
            httpClient.addInterceptor(logging);

            Gson gson = new GsonBuilder()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();

            return retrofit.create(PhotoService.class);
        }
    }
}
