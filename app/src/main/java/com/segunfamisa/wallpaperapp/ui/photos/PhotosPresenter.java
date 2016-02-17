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
public class PhotosPresenter implements Presenter<PhotosMvpView> {

    private DataManager mDataManager;
    private PhotosMvpView mPhotosView;
    private Subscription mSubscription;

    @Inject
    public PhotosPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(PhotosMvpView mvpView) {
        mPhotosView = mvpView;
    }

    @Override
    public void detachView() {
        mPhotosView = null;
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void getPhotos(int count) {
        mPhotosView.showProgress(true);
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }

        mSubscription = mDataManager.getPhotos(count)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Photo>>() {
                    @Override
                    public void onCompleted() {
                        mPhotosView.onLoadPhotosCompleted();
                        mPhotosView.showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPhotosView.onLoadPhotosError(e.getMessage());
                        mPhotosView.showProgress(false);
                    }

                    @Override
                    public void onNext(ArrayList<Photo> photos) {
                        mPhotosView.onLoadPhotos(photos);
                        mPhotosView.showProgress(false);

                        if(photos == null || photos.isEmpty()) {
                            mPhotosView.showEmptyView();
                        }
                    }
                });
    }
}
