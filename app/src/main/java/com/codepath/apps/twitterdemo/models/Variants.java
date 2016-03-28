package com.codepath.apps.twitterdemo.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kanet on 3/27/2016.
 */
public class Variants {
    @SerializedName("content_type")
    private String content_type;

    @SerializedName("url")
    private String url;

    public String getContent_type() {
        return content_type;
    }

    public String getUrl() {
        return url;
    }
}
