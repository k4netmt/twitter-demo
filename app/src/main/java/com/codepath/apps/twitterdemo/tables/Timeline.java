package com.codepath.apps.twitterdemo.tables;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.codepath.apps.twitterdemo.models.Tweet;


@Table(name="Timeline")
public class Timeline extends Model {

    public Timeline(Tweet tweet) {
        this.tweet_id=tweet.getUid();
        this.body=tweet.getBody();
        this.createdAt=tweet.getCreatedAt();
        this.user_id=tweet.getUser().getUid();
        this.user_name=tweet.getUser().getName();
        this.user_avatar=tweet.getUser().getProfileImageUrl();
        /*this.type=tweet.getExtendedEntities().getType();
        this.extendentities_url=tweet.getExtendedEntities().getUrl();*/
    }
    @Column(name = "body")
    private String body;

    @Column(name = "tweet_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long tweet_id;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "user_id")
    private long user_id;

    @Column(name = "user_avatar")
    private String user_avatar;

    @Column(name = "user_name")
    private String user_name;

    @Column(name = "type")
    private int type;

    @Column(name = "extendentities_url")
    private String extendentities_url;


}
