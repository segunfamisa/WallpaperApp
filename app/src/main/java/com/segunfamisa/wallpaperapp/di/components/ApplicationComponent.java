package com.segunfamisa.wallpaperapp.di.components;

import android.content.Context;

import com.segunfamisa.wallpaperapp.activities.BaseActivity;
import com.segunfamisa.wallpaperapp.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    Context context();
}
