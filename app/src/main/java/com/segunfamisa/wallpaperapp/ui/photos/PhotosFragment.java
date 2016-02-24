package com.segunfamisa.wallpaperapp.ui.photos;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.adapters.PhotoListAdapter;
import com.segunfamisa.wallpaperapp.ui.animator.ItemAnimatorHelper;
import com.segunfamisa.wallpaperapp.ui.animator.ScaleInAnimator;
import com.segunfamisa.wallpaperapp.ui.base.BaseActivity;
import com.segunfamisa.wallpaperapp.ui.base.BaseFragment;
import com.segunfamisa.wallpaperapp.ui.detail.PhotoDetailsFragment;
import com.segunfamisa.wallpaperapp.ui.widget.transitions.PhotoTransition;
import com.segunfamisa.wallpaperapp.utils.Config;
import com.segunfamisa.wallpaperapp.utils.DialogUtils;
import com.segunfamisa.wallpaperapp.utils.Logger;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment to show the photos.
 */
public class PhotosFragment extends BaseFragment implements PhotosMvpView {
    private static final String ARG_PHOTOS = "arg_photos";

    @Inject
    PhotosPresenter mPhotosPresenter;

    @Inject
    PhotoListAdapter mAdapter;


    @Bind(R.id.recyclerview_photos) RecyclerView mRecyclerPhotos;
    @Bind(R.id.progress_loading) ProgressBar mProgressLoading;
    @Bind(R.id.swiperefresh_layout) SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.toolbar) Toolbar toolbar;

    public PhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
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
        menu.clear();
        inflater.inflate(R.menu.menu_photo_list, menu);
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

        if(savedInstanceState != null) {
            if(savedInstanceState.containsKey(ARG_PHOTOS)) {
                mAdapter.setPhotos(Parcels.<ArrayList<Photo>>unwrap(savedInstanceState.getParcelable(ARG_PHOTOS)));
            }
        } else {
            mPhotosPresenter.getPhotos(Config.PHOTOS_PER_PAGE);
        }

        toolbar.setTitle("Graffiti");
        toolbar.inflateMenu(R.menu.menu_photo_list);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                }
                return false;
            }
        });

        mRecyclerPhotos.setHasFixedSize(true);
        mRecyclerPhotos.setAdapter(mAdapter);
        mRecyclerPhotos.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerPhotos.setItemAnimator(ItemAnimatorHelper.scaleIn());


        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boolean hasLoadedToday = true;
                if (hasLoadedToday) {
                    mSwipeLayout.setRefreshing(false);
                    DialogUtils.createSimpleOkDialog(getContext(), getString(R.string.dialog_daily_limit_title),
                            getString(R.string.dialog_daily_limit_message)).show();
                } else {
                    //get photos
                    mPhotosPresenter.getPhotos(Config.PHOTOS_PER_PAGE);
                }
            }
        });

        mAdapter.setPhotoClickListener(new PhotoListAdapter.OnPhotoClickedListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void OnPhotoClicked(int position, Photo photo, View view) {

                Fragment frag = PhotoDetailsFragment.newInstance(photo);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    frag.setSharedElementEnterTransition(new PhotoTransition());
                    frag.setEnterTransition(new Fade());

                    setExitTransition(new Fade());
                    frag.setSharedElementReturnTransition(new PhotoTransition());
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addSharedElement(view, "photoImage")
                        .add(R.id.container, frag)
                        .addToBackStack(null)
                        .commit();
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(outState != null) {
            if(!mAdapter.getPhotos().isEmpty()) {
                outState.putParcelable(ARG_PHOTOS, Parcels.wrap(mAdapter.getPhotos()));
            }
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotosPresenter.detachView();
    }

    @Override
    public void onLoadPhotosCompleted() {

    }

    @Override
    public void onLoadPhotos(ArrayList<Photo> photos) {
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
