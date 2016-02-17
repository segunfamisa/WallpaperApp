package com.segunfamisa.wallpaperapp.data.model;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by segun.famisa on 15/02/2016.
 */

@Parcel
public class User {

    @SerializedName("id")
     String id;

    @SerializedName("username")
     String username;

    @SerializedName("name")
     String name;

    @SerializedName("profile_image")
     UserProfileImages profileImage;


    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public UserProfileImages getProfileImage() {
        return profileImage;
    }
}
