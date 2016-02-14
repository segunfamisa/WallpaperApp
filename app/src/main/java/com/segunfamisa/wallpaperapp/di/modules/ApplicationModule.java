package com.segunfamisa.wallpaperapp.di.modules;

import android.app.Application;
import android.content.Context;

import com.segunfamisa.wallpaperapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 13/02/2016.
 */
@Module
public class ApplicationModule {

    App mApplication;

    public ApplicationModule(App application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    Context provideAppContext() {
        return mApplication.getApplicationContext();
    }
}
