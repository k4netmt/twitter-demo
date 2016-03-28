package com.codepath.apps.twitterdemo.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Kanet on 3/27/2016.
 */
public class Video_Info {

    public List<Variants> getVariants() {
        return variants;
    }

    @SerializedName("variants")
    private List<Variants> variants;

}
