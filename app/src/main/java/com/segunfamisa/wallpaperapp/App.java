package com.segunfamisa.wallpaperapp;

import android.app.Application;

import com.segunfamisa.wallpaperapp.di.components.ApplicationComponent;
import com.segunfamisa.wallpaperapp.di.components.DaggerApplicationComponent;
import com.segunfamisa.wallpaperapp.di.modules.ApplicationModule;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 13/02/2016.
 */
public class App extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        this.initializeInjector();

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
