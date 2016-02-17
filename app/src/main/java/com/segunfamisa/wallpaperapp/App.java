package com.segunfamisa.wallpaperapp;

import android.app.Application;

import com.segunfamisa.wallpaperapp.data.DataManager;
import com.segunfamisa.wallpaperapp.di.components.ApplicationComponent;
import com.segunfamisa.wallpaperapp.di.components.DaggerApplicationComponent;
import com.segunfamisa.wallpaperapp.di.modules.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 13/02/2016.
 */
public class App extends Application {

    @Inject
    DataManager mDataManager;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.initializeInjector();
        this.getApplicationComponent().inject(this);
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
