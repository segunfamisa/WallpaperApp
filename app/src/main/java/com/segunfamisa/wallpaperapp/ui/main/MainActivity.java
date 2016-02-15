package com.segunfamisa.wallpaperapp.ui.main;

import android.os.Bundle;

import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.base.BaseActivity;
import com.segunfamisa.wallpaperapp.utils.Logger;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMVPView {

    private static final String TAG = "NOK";

    @Inject
    MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);

        mMainPresenter.attachView(this);

        mMainPresenter.getPhotos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    @Override
    public void onLoadPhotosCompleted() {
        Logger.i(TAG, "Photos loaded successfully");
    }

    @Override
    public void onLoadPhotos(ArrayList<Photo> photos) {
        Logger.d(TAG, photos != null ? photos.size()+" photos" : "Photos is null");
    }

    @Override
    public void onLoadPhotosError(String message) {
        Logger.e(TAG, message);
    }
}
