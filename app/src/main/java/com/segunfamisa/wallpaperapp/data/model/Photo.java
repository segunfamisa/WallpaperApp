package com.segunfamisa.wallpaperapp.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Model for photo
 *
 * Created by segun.famisa on 15/02/2016.
 */
@Parcel
public class Photo {

    @SerializedName("id")
     String id;

    @SerializedName("width")
     int width;

    @SerializedName("height")
     int height;

    @SerializedName("color")
     String color;

    @SerializedName("user")
     User user;

    @SerializedName("urls")
     PhotoUrls photoUrls;

    @SerializedName("categories")
     ArrayList<Category> categories;

    @SerializedName("links")
     PhotoLinks links;

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getColor() {
        return color;
    }

    public User getUser() {
        return user;
    }

    public PhotoUrls getPhotoUrls() {
        return photoUrls;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public PhotoLinks getLinks() {
        return links;
    }
}
