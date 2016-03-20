package com.segunfamisa.wallpaperapp.ui.detail;

import android.app.Activity;
import android.content.Intent;

import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.services.DownloadPhotoIntentService;
import com.segunfamisa.wallpaperapp.ui.base.Presenter;
import com.segunfamisa.wallpaperapp.ui.photos.PhotosMvpView;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by segun.famisa on 17/02/2016.
 */
public class PhotoDetailsPresenter implements Presenter<PhotoDetailsMvpView> {

    public Subscription mSubscription;

    private PhotoDetailsMvpView mPhotoDetailsView;

    @Inject
    public PhotoDetailsPresenter() {

    }

    @Override
    public void attachView(PhotoDetailsMvpView mvpView) {
        mPhotoDetailsView = mvpView;
    }

    @Override
    public void detachView() {
        mPhotoDetailsView = null;
    }

    public void startServiceForDownload(Activity activity, Photo photo) {
        //start download service
        Intent downloadIntent = DownloadPhotoIntentService.getCallingIntentForDownload(activity, photo);
        activity.startService(downloadIntent);
    }
}
