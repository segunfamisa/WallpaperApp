package com.segunfamisa.wallpaperapp.ui.base;

/**
 * Presenter class
 *
 * Created by segun.famisa on 15/02/2016.
 */
public interface Presenter<V extends MVPView> {

    void attachView(V mvpView);

    void detachView();
}
