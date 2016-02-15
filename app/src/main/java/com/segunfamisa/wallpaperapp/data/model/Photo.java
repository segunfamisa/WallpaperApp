package com.segunfamisa.wallpaperapp.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Model for photo
 *
 * Created by segun.famisa on 15/02/2016.
 */
public class Photo {

    @SerializedName("id")
    private String id;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    @SerializedName("color")
    private String color;

    @SerializedName("user")
    private User user;

    @SerializedName("urls")
    private PhotoUrls photoUrls;

    @SerializedName("categories")
    private ArrayList<Category> categories;

    @SerializedName("links")
    private PhotoLinks links;

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
