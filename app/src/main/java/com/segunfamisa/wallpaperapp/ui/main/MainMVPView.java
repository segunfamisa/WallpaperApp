package com.segunfamisa.wallpaperapp.ui.main;

import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.base.MVPView;

import java.util.ArrayList;

/**
 * Created by segun.famisa on 15/02/2016.
 */
public interface MainMVPView extends MVPView {

    void onLoadPhotosCompleted();

    void onLoadPhotos(ArrayList<Photo> photos);

    void onLoadPhotosError(String message);
}
