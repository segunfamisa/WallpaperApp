package com.segunfamisa.wallpaperapp.ui.detail;

import com.segunfamisa.wallpaperapp.ui.base.Presenter;
import com.segunfamisa.wallpaperapp.ui.photos.PhotosMvpView;

import javax.inject.Inject;

import rx.Subscription;

/**
 * Created by segun.famisa on 17/02/2016.
 */
public class PhotoDetailsPresenter implements Presenter<PhotoDetailsMvpView> {

    public Subscription mSubscription;

    private PhotosMvpView photosMvpView;

    @Inject
    public PhotoDetailsPresenter() {

    }

    @Override
    public void attachView(PhotoDetailsMvpView mvpView) {

    }

    @Override
    public void detachView() {

    }
}
