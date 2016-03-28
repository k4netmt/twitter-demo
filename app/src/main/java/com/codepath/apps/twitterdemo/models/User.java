package com.codepath.apps.twitterdemo.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

/**
 * Created by Kanet on 3/23/2016.
 *  {
 "name": "OAuth Dancer",
 "profile_sidebar_fill_color": "DDEEF6",
 "profile_background_tile": true,
 "profile_sidebar_border_color": "C0DEED",
 "profile_image_url": "http://a0.twimg.com/profile_images/730275945/oauth-dancer_normal.jpg",
 "created_at": "Wed Mar 03 19:37:35 +0000 2010",
 "location": "San Francisco, CA",
 "follow_request_sent": false,
 "id_str": "119476949",
 "is_translator": false,
 "profile_link_color": "0084B4",
 */
public class User{
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private long uid;

    @SerializedName("screen_name")
    private String screenName;

    @SerializedName("profile_image_url")
    private String profileImageUrl;

    public String getName() {
        return name;
    }

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return "@"+screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public User() {
        super();
    }

    @ParcelConstructor
    public User(String name, long uid, String screenName, String profileImageUrl) {
        this.name = name;
        this.uid = uid;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
    }

  /*  public static User fromJSON(JSONObject jsonObject){
        User user=new User();

        try {
            user.name=jsonObject.has("name") ? jsonObject.getString("name") : "";
            user.uid=jsonObject.has("id") ? jsonObject.getLong("id") : 0;
            user.screenName=jsonObject.has("screen_name") ? jsonObject.getString("screen_name") : "";
            user.profileImageUrl=jsonObject.has("profile_image_url") ? jsonObject.getString("profile_image_url") : "";
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }*/
}
