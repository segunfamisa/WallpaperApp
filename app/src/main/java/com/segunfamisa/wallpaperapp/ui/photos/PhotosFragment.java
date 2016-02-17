package com.segunfamisa.wallpaperapp.ui.photos;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.adapters.PhotoListAdapter;
import com.segunfamisa.wallpaperapp.ui.base.BaseActivity;
import com.segunfamisa.wallpaperapp.ui.base.BaseFragment;
import com.segunfamisa.wallpaperapp.utils.Config;
import com.segunfamisa.wallpaperapp.utils.DialogUtils;
import com.segunfamisa.wallpaperapp.utils.Logger;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment to show the photos.
 */
public class PhotosFragment extends BaseFragment implements PhotosMVPView{

    @Inject
    PhotosPresenter mPhotosPresenter;

    @Inject
    PhotoListAdapter mAdapter;

    @Bind(R.id.recyclerview_photos)
    RecyclerView mRecyclerPhotos;

    @Bind(R.id.progress_loading)
    ProgressBar mProgressLoading;

    @Bind(R.id.swiperefresh_layout)
    SwipeRefreshLayout mSwipeLayout;

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos_list, container, false);

        ButterKnife.bind(this, view);
        ((BaseActivity)getActivity()).getActivityComponent().inject(this);
        mPhotosPresenter.attachView(this);


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_photo_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                // TODO: 17/02/2016 navigate to settings
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerPhotos.setHasFixedSize(true);
        mRecyclerPhotos.setAdapter(mAdapter);
        mRecyclerPhotos.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean hasLoadedToday = true;
                if(hasLoadedToday) {
                    DialogUtils.createSimpleOkDialog(getContext(), "We've delivered for today",
                            "Bummer :( . It looks like we've delivered today's wallpapers. Check again tomorrow").show();
                } else {
                    //get photos
                    mPhotosPresenter.getPhotos(Config.PHOTOS_PER_PAGE);
                }
            }
        });

        mPhotosPresenter.getPhotos(Config.PHOTOS_PER_PAGE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotosPresenter.detachView();
    }

    @Override
    public void onLoadPhotosCompleted() {
        Logger.i("NOK", "Photos loaded successfully");
    }

    @Override
    public void onLoadPhotos(ArrayList<Photo> photos) {
        Logger.d("NOK", (photos == null ? 0 : photos.size()) + " photos loaded");
        mAdapter.setPhotos(photos);
    }

    @Override
    public void onLoadPhotosError(String message) {
        DialogUtils.createSimpleOkDialog(getContext(), "Something went wrong", message);
    }

    @Override
    public void showProgress(boolean show) {
        mProgressLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        mSwipeLayout.setRefreshing(show);
    }

    @Override
    public void showEmptyView() {

    }
}
