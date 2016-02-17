package com.segunfamisa.wallpaperapp.data.model;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by segun.famisa on 15/02/2016.
 */
@Parcel
public class UserProfileImages {

    @SerializedName("small")
     String small;

    @SerializedName("medium")
     String medium;

    @SerializedName("large")
     String large;

    public String getSmall() {
        return small;
    }

    public String getMedium() {
        return medium;
    }

    public String getLarge() {
        return large;
    }
}
