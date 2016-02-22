package com.segunfamisa.wallpaperapp.ui.detail;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.model.Photo;
import com.segunfamisa.wallpaperapp.ui.base.BaseFragment;
import com.segunfamisa.wallpaperapp.ui.photos.PhotosPresenter;
import com.segunfamisa.wallpaperapp.ui.widget.BlurringView;

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

    @Bind(R.id.text_photo_date)
    TextView mTextDate;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.fab_options)
    FloatingActionButton fab;

    Drawable mBackDrawable;


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

        mBackDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_action_nav_back);
        toolbar.setNavigationIcon(mBackDrawable);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });

        if(mPhoto != null) {
            Glide.with(getContext())
                    .load(mPhoto.getPhotoUrls().getRegular())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            Bitmap bitmap = ((GlideBitmapDrawable) resource).getBitmap();

                            if (bitmap != null) {
                                mImagePhoto.setImageBitmap(bitmap);
                                setDetailsBackground(bitmap);
                            }
                        }
                    });
        }

        return view;
    }

    private void setDetailsBackground(Bitmap bitmap) {
        Palette palette = Palette.from(bitmap).generate();

        Palette.Swatch s = palette.getVibrantSwatch();
        if (s == null) {
            s = palette.getDarkVibrantSwatch();
        }
        if (s == null) {
            s = palette.getLightVibrantSwatch();
        }
        if (s == null) {
            s = palette.getMutedSwatch();
        }

        if (s != null) {
            mBackDrawable.setColorFilter(s.getRgb(), PorterDuff.Mode.MULTIPLY);
            fab.setVisibility(View.VISIBLE);
            fab.setColorNormal(palette.getVibrantColor(ContextCompat.getColor(getContext(), R.color.colorPrimary)));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
