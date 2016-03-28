package com.codepath.apps.twitterdemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kanet on 3/27/2016.
 */
public class Media {

    public String getType() {
        return type;
    }

    public Video_Info getVideo_info() {
        return video_info;
    }

    @SerializedName("type")
    private String type;

    @SerializedName("media_url")
    private String media_url;

    @SerializedName("video_info")
    private Video_Info video_info;


    public String getMedia_url() {
        return media_url;
    }

 /*   public Video_Info getVideo_info() {
        return video_info;
    }*/

    public Media() {
        super();
    }
}
