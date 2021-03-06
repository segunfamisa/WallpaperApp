package com.segunfamisa.wallpaperapp.data;

import android.graphics.Bitmap;

import com.segunfamisa.wallpaperapp.data.api.PhotoService;
import com.segunfamisa.wallpaperapp.data.model.Photo;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by segun.famisa on 15/02/2016.
 */
@Singleton
public class DataManager {

    private PhotoService mPhotoService;

    @Inject
    public DataManager(PhotoService photoService) {
        this.mPhotoService = photoService;
    }

    public Observable<ArrayList<Photo>> getPhotos(int count) {
        return mPhotoService.getPhotos(count);
    }
}
