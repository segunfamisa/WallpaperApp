package com.segunfamisa.wallpaperapp.ui.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.segunfamisa.wallpaperapp.App;
import com.segunfamisa.wallpaperapp.di.components.ActivityComponent;
import com.segunfamisa.wallpaperapp.di.components.ApplicationComponent;
import com.segunfamisa.wallpaperapp.di.components.DaggerActivityComponent;
import com.segunfamisa.wallpaperapp.di.modules.ActivityModule;
import com.segunfamisa.wallpaperapp.navigator.Navigator;
import com.segunfamisa.wallpaperapp.utils.PreferenceUtils;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public ActivityComponent getActivityComponent() {
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

    /**
     * Adds a fragment to a container view
     *
     * @param containerViewId container view id
     * @param fragment fragment to add
     * @param addToBackStack flag whether to add to back stack or not
     */
    protected void addFragment(int containerViewId, Fragment fragment, boolean addToBackStack) {
        if(containerViewId > -1 && fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(containerViewId, fragment, fragment.getClass().getName());
            if(addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }

    /**
     * Replaces a fragment in a container view.
     *
     * @param containerViewId container view id
     * @param fragment fragment to replace
     * @param addToBackStack flag whether or not to add to backstack
     */
    protected void replaceFragment(int containerViewId, Fragment fragment, boolean addToBackStack) {
        if(containerViewId > -1 && fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(containerViewId, fragment, fragment.getClass().getName());
            if(addToBackStack) {
                ft.addToBackStack(null);
            }
            ft.commit();
        }
    }
}
