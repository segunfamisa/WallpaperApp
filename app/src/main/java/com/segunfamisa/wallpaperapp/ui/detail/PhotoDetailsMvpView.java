package com.segunfamisa.wallpaperapp.ui.detail;

import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.base.MVPView;

/**
 * Created by segun.famisa on 17/02/2016.
 */
public interface PhotoDetailsMvpView extends MVPView {

    void showDetails(Photo photo);
}
