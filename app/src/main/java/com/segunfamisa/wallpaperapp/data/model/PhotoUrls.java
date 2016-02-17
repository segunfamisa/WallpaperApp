package com.segunfamisa.wallpaperapp.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by segun.famisa on 15/02/2016.
 */
@Parcel
public class PhotoUrls {

    @SerializedName("full")
     String full;

    @SerializedName("regular")
     String regular;

    @SerializedName("small")
     String small;

    @SerializedName("thumb")
     String thumb;

    public String getFull() {
        return full;
    }

    public String getRegular() {
        return regular;
    }

    public String getSmall() {
        return small;
    }

    public String getThumb() {
        return thumb;
    }
}
