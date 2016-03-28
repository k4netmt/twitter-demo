package com.codepath.apps.twitterdemo.adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.models.TimeStamp;
import com.codepath.apps.twitterdemo.models.Tweet;
import com.codepath.apps.twitterdemo.models.Variants;
import com.codepath.apps.twitterdemo.viewholder.ViewHolderTimelinePhoto;
import com.codepath.apps.twitterdemo.viewholder.ViewHolderTimelineText;
import com.codepath.apps.twitterdemo.viewholder.ViewHolderTimelineVideo;
import com.yqritc.scalablevideoview.ScalableType;

import java.io.IOException;
import java.util.List;

/**
 * Created by Kanet on 3/26/2016.
 */
public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TEXT=0;
    private static final int PHOTO=1;
    private static final int GIF=2;
    private static final int VIDEO=3;
    private List<Tweet> mTweets;
    private Context context;
    public TimelineAdapter(List<Tweet> tweets) {
        this.mTweets=tweets;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TEXT: {
                View v2 = inflater.inflate(R.layout.item_timeline_text, parent, false);
                viewHolder = new ViewHolderTimelineText(v2);
                break;
            }
            case PHOTO: {
                View v2 = inflater.inflate(R.layout.item_timeline_photo, parent, false);
                viewHolder = new ViewHolderTimelinePhoto(v2);
                break;
            }
            case GIF: {
                View v2 = inflater.inflate(R.layout.item_timeline_video, parent, false);
                viewHolder = new ViewHolderTimelineVideo(v2);
                break;
            }
            case VIDEO: {
                View v2 = inflater.inflate(R.layout.item_timeline_video, parent, false);
                viewHolder = new ViewHolderTimelineVideo(v2);
                break;
            }
            default:
                View v = inflater.inflate(R.layout.item_timeline_text, parent, false);
                viewHolder = new ViewHolderTimelineText(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()){
            case TEXT:{
                ViewHolderTimelineText vhText = (ViewHolderTimelineText) viewHolder;
                configureViewHolderTimelineText(vhText, position);
                break;
            }
            case PHOTO:{
                ViewHolderTimelinePhoto vhPhoto = (ViewHolderTimelinePhoto) viewHolder;
                configureViewHolderTimelinePhoto(vhPhoto, position);
                break;
            }
            case GIF:{
                ViewHolderTimelineVideo vhVideo = (ViewHolderTimelineVideo) viewHolder;
                configureViewHolderTimelineGif(vhVideo, position);
                break;
            }
            case VIDEO:{
                ViewHolderTimelineVideo vhVideo = (ViewHolderTimelineVideo) viewHolder;
                configureViewHolderTimelineVideo(vhVideo, position);
                break;
            }
        }
    }

    private void configureViewHolderTimelineText(ViewHolderTimelineText vhText, int position) {
        final Tweet tweet =  mTweets.get(position);
        if (tweet!=null){
            vhText.tvText.setText(tweet.getBody().trim());
            vhText.tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime(Tweet.formatCreatedAt, tweet.getCreatedAt()), TimeStamp.CHARACTER_TIME));
            vhText.tvName.setText(tweet.getUser().getName());
            vhText.tvScreenName.setText(tweet.getUser().getScreenName());
            Glide.with(context)
                    .load(tweet.getUser().getProfileImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vhText.ivAvatar);
        }
    }

    private void configureViewHolderTimelinePhoto(ViewHolderTimelinePhoto vhText, int position) {
        final Tweet tweet =  mTweets.get(position);
        if (tweet!=null){
            vhText.tvText.setText(tweet.getBody().trim());
            vhText.tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime(Tweet.formatCreatedAt, tweet.getCreatedAt()), TimeStamp.CHARACTER_TIME));
            vhText.tvName.setText(tweet.getUser().getName());
            vhText.tvScreenName.setText(tweet.getUser().getScreenName());
            Glide.with(context)
                    .load(tweet.getUser().getProfileImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vhText.ivAvatar);
            Glide.with(context)
                    .load(tweet.getExtendedEntities().getMedia().get(0).getMedia_url())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(600,400)
                    .into(vhText.ivImage);
        }
    }

    private void configureViewHolderTimelineVideo(final ViewHolderTimelineVideo viewHolder, int position) {
        final Tweet tweet =  mTweets.get(position);
        if (tweet!=null){
            viewHolder.tvText.setText(tweet.getBody().trim());
            viewHolder.tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime(Tweet.formatCreatedAt, tweet.getCreatedAt())
                    , TimeStamp.CHARACTER_TIME));
            viewHolder.tvName.setText(tweet.getUser().getName());
            viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
            Glide.with(context)
                    .load(tweet.getUser().getProfileImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.ivAvatar);
            Variants variants=new Variants();
            for (Variants v :
                    tweet.getExtendedEntities().getMedia().get(0).getVideo_info().getVariants()) {
                if (v.getContent_type().compareTo("video/mp4")==0)
                    variants=v;
            }

            MediaController mediaControls = new MediaController(context);
            viewHolder.svvVideo.setVideoURI(Uri.parse(variants.getUrl()));
            viewHolder.svvVideo.setMediaController(mediaControls);
            viewHolder.svvVideo.requestFocus();
            viewHolder.svvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
    }

    private void configureViewHolderTimelineGif(final ViewHolderTimelineVideo viewHolder, int position) {
        final Tweet tweet =  mTweets.get(position);
        if (tweet!=null){
            viewHolder.tvText.setText(tweet.getBody().trim());
            viewHolder.tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime(Tweet.formatCreatedAt, tweet.getCreatedAt())
                    , TimeStamp.CHARACTER_TIME));
            viewHolder.tvName.setText(tweet.getUser().getName());
            viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
            Glide.with(context)
                    .load(tweet.getUser().getProfileImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.ivAvatar);
            Variants variants=new Variants();
            for (Variants v :
                    tweet.getExtendedEntities().getMedia().get(0).getVideo_info().getVariants()) {
                if (v.getContent_type().compareTo("video/mp4")==0)
                    variants=v;
            }
            MediaController mediaControls = new MediaController(context);
            viewHolder.svvVideo.setVideoURI(Uri.parse(variants.getUrl()));
            viewHolder.svvVideo.setMediaController(mediaControls);
            viewHolder.svvVideo.requestFocus();
            viewHolder.svvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    mp.setLooping(true);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    @Override
    public int getItemViewType(int position) {
        Tweet tweet=mTweets.get(position);
        if (tweet.getExtendedEntities()==null){
            return TEXT;
        }
        String type=tweet.getExtendedEntities().getMedia().get(0).getType();
        if (type.compareTo("photo")==0){
            return PHOTO;
        }
        if (type.compareTo("animated_gif")==0){
            return GIF;
        }
        if (type.compareTo("video")==0){
            return VIDEO;
        }
        return TEXT;
    }
}
