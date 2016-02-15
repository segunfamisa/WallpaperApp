package com.segunfamisa.wallpaperapp.di.components;

import android.app.Activity;

import com.segunfamisa.wallpaperapp.di.PerActivity;
import com.segunfamisa.wallpaperapp.di.modules.ActivityModule;
import com.segunfamisa.wallpaperapp.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    //exposed to sub-graph
    Activity activity();
}
