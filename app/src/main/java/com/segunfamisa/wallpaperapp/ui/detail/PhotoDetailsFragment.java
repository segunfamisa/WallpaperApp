package com.segunfamisa.wallpaperapp.ui.detail;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.base.BaseFragment;
import com.segunfamisa.wallpaperapp.ui.photos.PhotosPresenter;

import org.parceler.Parcels;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment to show photo details
 */
public class PhotoDetailsFragment extends BaseFragment {

    private Photo mPhoto;
    private static final String ARG_PHOTO = "arg_photo";

    @Inject
    PhotoDetailsPresenter mPresenter;

    @Bind(R.id.imageview_photo)
    ImageView mImagePhoto;

    /**
     * Gets new instance of the photo details fragment
     * @param photo
     * @return
     */
    public static Fragment newInstance(Photo photo) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_PHOTO, Parcels.wrap(photo));

        Fragment frag = new PhotoDetailsFragment();
        frag.setArguments(bundle);
        return frag;
    }

    public PhotoDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_details, container, false);
        ButterKnife.bind(this, view);

        Bundle args = getArguments();
        mPhoto = Parcels.unwrap(args.getParcelable(ARG_PHOTO));

        if(mPhoto != null) {
            Glide.with(getContext())
                    .load(mPhoto.getPhotoUrls().getRegular())
                    .into(mImagePhoto);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
