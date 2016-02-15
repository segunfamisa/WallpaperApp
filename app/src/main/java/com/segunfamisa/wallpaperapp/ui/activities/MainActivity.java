package com.segunfamisa.wallpaperapp.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.navigator.Navigator;
import com.segunfamisa.wallpaperapp.utils.PreferenceUtils;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
