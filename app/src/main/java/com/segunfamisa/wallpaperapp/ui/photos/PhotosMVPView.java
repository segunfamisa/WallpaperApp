package com.segunfamisa.wallpaperapp.ui.photos;

import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.base.MVPView;

import java.util.ArrayList;

/**
 * Created by segun.famisa on 16/02/2016.
 */
public interface PhotosMVPView extends MVPView {

    void onLoadPhotosCompleted();

    void onLoadPhotos(ArrayList<Photo> photos);

    void onLoadPhotosError(String message);

    void showProgress(boolean show);

    void showEmptyView();
}
