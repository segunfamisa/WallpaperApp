package com.segunfamisa.wallpaperapp.di.components;

import android.app.Application;
import android.content.Context;

import com.segunfamisa.wallpaperapp.App;
import com.segunfamisa.wallpaperapp.data.DataManager;
import com.segunfamisa.wallpaperapp.data.api.PhotoService;
import com.segunfamisa.wallpaperapp.di.ApplicationContext;
import com.segunfamisa.wallpaperapp.navigator.Navigator;
import com.segunfamisa.wallpaperapp.ui.base.BaseActivity;
import com.segunfamisa.wallpaperapp.di.modules.ApplicationModule;
import com.segunfamisa.wallpaperapp.ui.base.BaseFragment;
import com.segunfamisa.wallpaperapp.utils.PreferenceUtils;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Segun Famisa <segunfamisa@gmail.com> on 14/02/2016.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(App app);
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

    Context context();
    Application application();
    PreferenceUtils preferenceUtils();
    DataManager dataManager();
    Navigator navigator();
    PhotoService photoService();
}
