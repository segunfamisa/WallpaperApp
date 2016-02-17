package com.segunfamisa.wallpaperapp.data.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by segun.famisa on 15/02/2016.
 */
@Parcel
public class PhotoLinks {

    @SerializedName("self")
     String self;

    @SerializedName("html")
     String html;

    @SerializedName("download")
     String download;

    public String getSelf() {
        return self;
    }

    public String getHtml() {
        return html;
    }

    public String getDownload() {
        return download;
    }
}
