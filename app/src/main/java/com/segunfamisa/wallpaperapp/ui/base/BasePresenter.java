package com.segunfamisa.wallpaperapp.ui.base;

/**
 * Created by segun.famisa on 15/02/2016.
 */
public class BasePresenter<T extends MVPView> implements Presenter<T> {

    private T mMvpView;

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMVPView() {
        return mMvpView;
    }

    public void checkViewAttached() {

    }

    public static class MVPViewNotAttachedException extends RuntimeException {
        public MVPViewNotAttachedException() {
            super("Please call Presenter.attachView(MVPView) before request data to the presenter");
        }
    }
}
