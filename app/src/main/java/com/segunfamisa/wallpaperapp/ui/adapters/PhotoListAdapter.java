package com.segunfamisa.wallpaperapp.ui.adapters;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.segunfamisa.wallpaperapp.R;
import com.segunfamisa.wallpaperapp.data.model.Photo;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter to power the list
 *
 * Created by segun.famisa on 16/02/2016.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoViewHolder> {

    private int lastPosition;
    private ArrayList<Photo> photos;
    private OnPhotoClickedListener mPhotoClickListener;

    /**
     * Interface to implement click listener
     */
    public interface OnPhotoClickedListener {
        void OnPhotoClicked(int position, Photo photo, View view);
    }

    @Inject
    public PhotoListAdapter() {
        this.photos = new ArrayList<>();
    }

    /**
     * Sets list of {@code Photo} to use
     * @param photos photos
     */
    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    public ArrayList<Photo> getPhotos() {
        return this.photos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_photo, parent, false);

        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, final int position) {
        final Photo photo = photos.get(position);

        Glide.with(holder.photo.getContext())
                .load(photo.getPhotoUrls().getSmall())
                .placeholder(R.drawable.ic_photo_placeholder)
                .into(holder.photo);

        ViewCompat.setTransitionName(holder.photo, String.valueOf(position) + "");
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPhotoClickListener != null) {
                    mPhotoClickListener.OnPhotoClicked(position, photo, holder.photo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    /**
     * Sets the photo click listener
     * @param photoClickListener {@link com.segunfamisa.wallpaperapp.ui.adapters.PhotoListAdapter.OnPhotoClickedListener}
     */
    public void setPhotoClickListener(OnPhotoClickedListener photoClickListener) {
        mPhotoClickListener = photoClickListener;
    }

    /**
     * Viewholder class for the photos
     */
    class PhotoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.photo_layout)
        View container;

        @Bind(R.id.imageview_photo)
        ImageView photo;

        Photo mPhoto;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setPhoto(Photo photo) {
            mPhoto = photo;
        }
    }
}
