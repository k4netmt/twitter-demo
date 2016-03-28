package com.codepath.apps.twitterdemo.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Kanet on 3/26/2016.
 */
public class ExtendedEntities{
    public static final int TYPE_PHOTO=0;
    public static final int TYPE_VIDEO=1;
    public static final int TYPE_TEXT=2;

    public List<Media> getMedia() {
        return media;
    }

    @SerializedName("media")
    private List<Media> media;


    public ExtendedEntities() {
        super();
    }


/*    public static ExtendedEntities fromJSON(JSONObject jsonObject){
        ExtendedEntities object=new ExtendedEntities();
        try{
            String type=jsonObject.has("type") ? jsonObject.getString("type") : "";
            if (jsonObject.has("media")){
                object.type=clasifyTypeKey(type);
                object.url=clasifyTypeValue(type,jsonObject.getJSONObject("media_url"));
            }else {
                object.type=TYPE_TEXT;
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return object;
    }*/

    private static int clasifyTypeKey(String type){
        switch (type){
            case "photo":{
                return TYPE_PHOTO;
            }
            case "animated_gif":{
                return  TYPE_VIDEO;
            }
            case "video":
                return TYPE_VIDEO;
            default:
                return TYPE_TEXT;
        }
    }
    private static String clasifyTypeValue(String type,JSONObject jsonObject){
        switch (type){
            case "photo":{
                return getUrlForPhoto(jsonObject);
            }
            case "animated_gif":{
                return  getUrlForVideo(jsonObject);
            }
            case "video":{
                    return  getUrlForVideo(jsonObject);
            }
            default:
                return "";
        }
    }

    private static String getUrlForPhoto(JSONObject jsonObject){
        String media_url="";
        try {
            media_url = jsonObject.has("media_url") ? jsonObject.getString("media_url") : "";
        }catch (JSONException e){
            e.printStackTrace();
        }
        return media_url;
    }
    private static String getUrlForVideo(JSONObject jsonObject){
        String media_url="";
        try {
            if (jsonObject.getJSONObject("video_info").has("variants")){
                JSONArray jsonArray=jsonObject.getJSONObject("video_info").getJSONArray("variants");
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject variants=jsonArray.getJSONObject(i);
                    if (variants.has("content_type")){
                        String content_type=variants.getString("content_type");
                        if (content_type=="video\\/mp4"){
                            if (variants.has("url")){
                                media_url=variants.getString("url");
                            }
                        }
                    }
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return media_url;
    }
}
