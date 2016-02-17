package com.segunfamisa.wallpaperapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    ArrayList<Photo> photos;

    @Inject
    public PhotoListAdapter() {
        this.photos = new ArrayList<>();
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_photo, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);

        Glide.with(holder.photo.getContext())
                .load(photo.getPhotoUrls().getSmall())
                .placeholder(R.drawable.ic_photo_placeholder)
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }


    /**
     * Viewholder class for the photos
     */
    class PhotoViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imageview_photo)
        ImageView photo;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
