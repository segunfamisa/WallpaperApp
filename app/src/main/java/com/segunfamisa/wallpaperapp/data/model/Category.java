package com.segunfamisa.wallpaperapp.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by segun.famisa on 15/02/2016.
 */
@Parcel
public class Category {

    @SerializedName("id")
    String id;

    @SerializedName("title")
    String title;

    @SerializedName("photo_count")
    String photoCount;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPhotoCount() {
        return photoCount;
    }
}
