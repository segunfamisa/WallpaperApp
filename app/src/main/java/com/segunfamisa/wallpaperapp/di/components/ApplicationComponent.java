package com.segunfamisa.wallpaperapp.di.components;

import android.content.Context;

import com.segunfamisa.wallpaperapp.navigator.Navigator;
import com.segunfamisa.wallpaperapp.ui.activities.BaseActivity;
import com.segunfamisa.wallpaperapp.di.modules.ApplicationModule;
import com.segunfamisa.wallpaperapp.ui.fragments.BaseFragment;
import com.segunfamisa.wallpaperapp.utils.PreferenceUtils;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

    Context context();
    PreferenceUtils preferenceUtils();
    Navigator navigator();
}
