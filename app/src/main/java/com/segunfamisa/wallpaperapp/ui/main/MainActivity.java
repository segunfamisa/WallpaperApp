package com.segunfamisa.wallpaperapp.ui.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.base.BaseActivity;
import com.segunfamisa.wallpaperapp.ui.photos.PhotosFragment;
import com.segunfamisa.wallpaperapp.utils.Logger;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = "NOK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new PhotosFragment(), PhotosFragment.class.getName())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
