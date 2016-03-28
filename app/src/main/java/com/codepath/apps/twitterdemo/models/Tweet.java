package com.codepath.apps.twitterdemo.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.codepath.apps.twitterdemo.tables.Timeline;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.lang.reflect.Type;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarException;

/**
 *   {
 "coordinates": null,
 "truncated": false,
 "created_at": "Tue Aug 28 21:16:23 +0000 2012",
 "favorited": false,
 "id_str": "240558470661799936",
 "in_reply_to_user_id_str": null,
 "entities": {
     "urls": [ ],
     "hashtags": [ ],
     "user_mentions": [ ]
    },
 "text": "just another test",
 "contributors": null,
 "id": 240558470661799936,
 "retweet_count": 0,
 "in_reply_to_status_id_str": null,
 "geo": null,
 "retweeted": false,
 "in_reply_to_user_id": null,
 "place": null,
 "source": "<a href="//realitytechnicians.com%5C%22" rel="\"nofollow\"">OAuth Dancer Reborn</a>",
 "user":
 "entities": {
     "url": {
         "urls": [{
         "expanded_url": null,
         "url": "http://bit.ly/oauth-dancer",
         "indices": [0,26],
         "display_url": null
         }]
     },
 "description": null
 },
 */
public class Tweet{
    /*public ExtendedEntities getExtendedEntities() {
        return extendedEntities;
    }*/

    public static final Type listType = new TypeToken<ArrayList<Tweet>>() {}.getType();
    public static final String formatCreatedAt="EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    public static Type getListType() {
        return listType;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public ExtendedEntities getExtendedEntities() {
        return extendedEntities;
    }

    @Column(name = "body")
    @SerializedName("text")
    private String body;

    @Column(name = "tweet_id")
    @SerializedName("id")
    private long uid;

    @SerializedName("user")
    private User user;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("extended_entities")
    private ExtendedEntities extendedEntities;

  /*  public static Tweet fromJSON(JSONObject jsonObject){
        Tweet tweet=new Tweet();
        try{
            tweet.body=jsonObject.has("text") ? jsonObject.getString("text") : "";
            tweet.uid=jsonObject.has("id") ? jsonObject.getLong("id") : 0;
            tweet.createdAt=jsonObject.has("created_at") ? jsonObject.getString("created_at") : "";
            tweet.user=User.fromJSON(jsonObject.getJSONObject("user"));
            //tweet.extendedEntities=ExtendedEntities.fromJSON(jsonObject.getJSONObject("extended_entities"));
            *//*Timeline timeline=new Timeline(tweet);
            timeline.save();*//*
        }catch (JSONException e){
            e.printStackTrace();
        }

        return tweet;
    }*/

/*    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets=new ArrayList<>();
        try{
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                Tweet tweet=Tweet.fromJSON(jsonObject);
                if(tweet!=null)
                    tweets.add(tweet);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return tweets;
    }*/
}
