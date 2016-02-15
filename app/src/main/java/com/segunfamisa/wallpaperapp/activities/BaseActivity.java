package com.segunfamisa.wallpaperapp.activities;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.segunfamisa.wallpaperapp.App;
import com.segunfamisa.wallpaperapp.di.components.ActivityComponent;
import com.segunfamisa.wallpaperapp.di.components.ApplicationComponent;
import com.segunfamisa.wallpaperapp.di.components.DaggerActivityComponent;
import com.segunfamisa.wallpaperapp.di.modules.ActivityModule;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getApplicationComponent().inject(this);
        this.getApplicationComponent().inject(this);
    }

    /**
     * Gets the application component for dependency injection
     *
     * @return {@link ApplicationComponent}
     */
    private ApplicationComponent getApplicationComponent() {
        return ((App) getApplication()).getApplicationComponent();
    }

    /**
     * Gets the activity component for dependecy injection
     *
     * @return {@link ActivityComponent}
     */
    protected ActivityComponent getActivityComponent() {
        if(mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(new ActivityModule(this))
                    .build();
        }

        return mActivityComponent;
    }

    /**
     * Gets the activity module for dependency injection
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
