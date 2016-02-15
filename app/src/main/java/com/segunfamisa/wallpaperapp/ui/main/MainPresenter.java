package com.segunfamisa.wallpaperapp.ui.main;

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
 * Created by segun.famisa on 15/02/2016.
 */
public class MainPresenter implements Presenter<MainMVPView> {

    private DataManager mDataManager;
    private MainMVPView mMVPView;
    private Subscription mSubscription;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMVPView mvpView) {
        mMVPView = mvpView;
    }

    @Override
    public void detachView() {
        mMVPView = null;
        if(mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void getPhotos() {
        mSubscription = mDataManager.getPhotos()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<ArrayList<Photo>>() {
                    @Override
                    public void onCompleted() {
                        mMVPView.onLoadPhotosCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mMVPView.onLoadPhotosError(e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Photo> photos) {
                        mMVPView.onLoadPhotos(photos);
                    }
                });
    }
}
