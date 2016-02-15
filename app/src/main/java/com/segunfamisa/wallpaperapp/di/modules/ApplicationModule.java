package com.segunfamisa.wallpaperapp.di.modules;

import android.app.Application;
import android.content.Context;

import com.segunfamisa.wallpaperapp.App;
import com.segunfamisa.wallpaperapp.navigator.Navigator;
import com.segunfamisa.wallpaperapp.utils.PreferenceUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 13/02/2016.
 */
@Module
public class ApplicationModule {

    Application mApplication;

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

    @Provides
    @Singleton
    Navigator provideNavigator() {
        return new Navigator();
    }

    @Provides
    @Singleton
    PreferenceUtils providePreferences(Application application) {
        return PreferenceUtils.init(application);
    }
}
