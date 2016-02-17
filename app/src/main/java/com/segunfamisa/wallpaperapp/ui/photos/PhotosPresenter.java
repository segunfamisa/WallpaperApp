package com.segunfamisa.wallpaperapp.ui.photos;

import com.segunfamisa.wallpaperapp.data.DataManager;
import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.base.Presenter;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by segun.famisa on 16/02/2016.
 */
public class PhotosPresenter implements Presenter<PhotosMVPView> {

    private DataManager mDataManager;
    private PhotosMVPView mPhotosMVPView;
    private Subscription mSubscription;

    @Inject
    public PhotosPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PhotosMVPView mvpView) {
        mPhotosMVPView = mvpView;
    }

    @Override
    public void detachView() {
        mPhotosMVPView = null;
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void getPhotos(int count) {
        mPhotosMVPView.showProgress(true);
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }

        mSubscription = mDataManager.getPhotos(count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Photo>>() {
                    @Override
                    public void onCompleted() {
                        mPhotosMVPView.onLoadPhotosCompleted();
                        mPhotosMVPView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPhotosMVPView.onLoadPhotosError(e.getMessage());
                        mPhotosMVPView.showProgress(false);
                    }

                    @Override
                    public void onNext(ArrayList<Photo> photos) {
                        mPhotosMVPView.onLoadPhotos(photos);
                        mPhotosMVPView.showProgress(false);

                        if(photos == null || photos.isEmpty()) {
                            mPhotosMVPView.showEmptyView();
                        }
                    }
                });
    }
}
