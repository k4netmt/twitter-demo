package com.codepath.apps.twitterdemo.adapters;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.activities.UserInfoActivity;
import com.codepath.apps.twitterdemo.models.TimeStamp;
import com.codepath.apps.twitterdemo.models.Tweet;
import com.codepath.apps.twitterdemo.models.Variants;
import com.yqritc.scalablevideoview.ScalableType;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by Kanet on 3/26/2016.
 */
public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int TEXT=0;
    private static final int PHOTO=1;
    private static final int GIF=2;
    private static final int VIDEO=3;
    public static final int TIMELINE_FRAQMENT=0;
    public static final int USERTIMELINE_FRAQMENT=1;
    public final List<Tweet> mTweets;
    private Context context;
    private Tweet mTweet;
    private int mFlagSetOnClickListener=0;

    public void setmFlagSetOnClickListener(int mFlagSetOnClickListener) {
        this.mFlagSetOnClickListener = mFlagSetOnClickListener;
    }

    public TimelineAdapter(List<Tweet> tweets) {
        this.mTweets=tweets;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private boolean checkSetOnClickListener(){
        switch (mFlagSetOnClickListener){
            case TIMELINE_FRAQMENT: return true;
            case USERTIMELINE_FRAQMENT: return false;
        }
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TEXT: {
                View v2 = inflater.inflate(R.layout.item_timeline_text, parent, false);
                viewHolder = new ViewHolderTimelineText(context,v2);
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
                viewHolder = new ViewHolderTimelineText(context,v);
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

    private void configureViewHolderTimelineText(ViewHolderTimelineText viewHolder, int position) {
        final Tweet tweet =  mTweets.get(position);
        if (tweet!=null){
            mTweet=tweet;
            viewHolder.tvText.setText(tweet.getBody().trim());
            viewHolder.tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime(Tweet.formatCreatedAt, tweet.getCreatedAt()), TimeStamp.CHARACTER_TIME));
            viewHolder.tvName.setText(tweet.getUser().getName());
            viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
            viewHolder.tvLike.setText(String.valueOf(tweet.getFavorite_count()));
            viewHolder.tvRetweet.setText(String.valueOf(tweet.getRetweet_count()));
            int idLike=tweet.isFavorited() ? R.drawable.likeactionon : R.drawable.likeaction;
            viewHolder.ivLike.setImageResource(idLike);
            Glide.with(context)
                    .load(tweet.getUser().getProfileImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.ivAvatar);
        }
    }

    private void configureViewHolderTimelinePhoto(ViewHolderTimelinePhoto viewHolder, int position) {
        final Tweet tweet =  mTweets.get(position);
        if (tweet!=null){
            mTweet=tweet;
            viewHolder.tvText.setText(tweet.getBody().trim());
            viewHolder.tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime(Tweet.formatCreatedAt, tweet.getCreatedAt()), TimeStamp.CHARACTER_TIME));
            viewHolder.tvName.setText(tweet.getUser().getName());
            viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
            viewHolder.tvLike.setText(String.valueOf(tweet.getFavorite_count()));
            viewHolder.tvRetweet.setText(String.valueOf(tweet.getRetweet_count()));
            int idLike=tweet.isFavorited() ? R.drawable.likeactionon : R.drawable.likeaction;
            viewHolder.ivLike.setImageResource(idLike);
            Glide.with(context)
                    .load(tweet.getUser().getProfileImageUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.ivAvatar);
            //if(checkSetOnClickListener()==true) viewHolder.ivAvatar.setOnClickListener(this);
            Glide.with(context)
                    .load(tweet.getExtendedEntities().getMedia().get(0).getMedia_url())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .override(600,400)
                    .into(viewHolder.ivImage);
        }
    }

    private void configureViewHolderTimelineVideo(final ViewHolderTimelineVideo viewHolder, int position) {
        final Tweet tweet =  mTweets.get(position);
        if (tweet!=null){
            mTweet=tweet;
            viewHolder.tvText.setText(tweet.getBody().trim());
            viewHolder.tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime(Tweet.formatCreatedAt, tweet.getCreatedAt())
                    , TimeStamp.CHARACTER_TIME));
            viewHolder.tvName.setText(tweet.getUser().getName());
            viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
            viewHolder.tvLike.setText(String.valueOf(tweet.getFavorite_count()));
            viewHolder.tvRetweet.setText(String.valueOf(tweet.getRetweet_count()));
            int idLike=tweet.isFavorited() ? R.drawable.likeactionon : R.drawable.likeaction;
            viewHolder.ivLike.setImageResource(idLike);
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
            mTweet=tweet;
            viewHolder.tvText.setText(tweet.getBody().trim());
            viewHolder.tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime(Tweet.formatCreatedAt, tweet.getCreatedAt())
                    , TimeStamp.CHARACTER_TIME));
            viewHolder.tvName.setText(tweet.getUser().getName());
            viewHolder.tvScreenName.setText(tweet.getUser().getScreenName());
            int idLike=tweet.isFavorited() ? R.drawable.likeactionon : R.drawable.likeaction;
            viewHolder.ivLike.setImageResource(idLike);
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

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
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
    public class ViewHolderTimelineText extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        public TextView tvText;
        public TextView tvTime;
        public ImageView ivAvatar;
        public TextView tvName;
        public TextView tvScreenName;
        public TextView tvLike;
        public TextView tvRetweet;
        public ImageView ivLike;
        public ViewHolderTimelineText(Context context,View itemView) {
            super(itemView);
            this.context=context;
            tvText=(TextView)itemView.findViewById(R.id.tvText);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvScreenName=(TextView)itemView.findViewById(R.id.tvScreenName);
            ivAvatar=(ImageView)itemView.findViewById(R.id.ivAvatar);
            tvLike=(TextView)itemView.findViewById(R.id.tvLike);
            tvRetweet=(TextView)itemView.findViewById(R.id.tvRetweet);
            ivLike=(ImageView)itemView.findViewById(R.id.ivLike);
            if(checkSetOnClickListener()==true) ivAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivAvatar:{
                    Intent intent=new Intent(context, UserInfoActivity.class);
                    intent.putExtra("user", Parcels.wrap(mTweets.get(getAdapterPosition()).getUser()));
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }

    public class ViewHolderTimelineVideo extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvText;
        public TextView tvTime;
        public ImageView ivAvatar;
        public TextView tvName;
        public TextView tvScreenName;
        public VideoView svvVideo;
        public TextView tvLike;
        public TextView tvRetweet;
        public ImageView ivLike;
        public ViewHolderTimelineVideo(View itemView) {
            super(itemView);
            tvText=(TextView)itemView.findViewById(R.id.tvText);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvScreenName=(TextView)itemView.findViewById(R.id.tvScreenName);
            ivAvatar=(ImageView)itemView.findViewById(R.id.ivAvatar);
            svvVideo=(VideoView)itemView.findViewById(R.id.vvVideo);
            tvLike=(TextView)itemView.findViewById(R.id.tvLike);
            tvRetweet=(TextView)itemView.findViewById(R.id.tvRetweet);
            ivLike=(ImageView)itemView.findViewById(R.id.ivLike);
            if(checkSetOnClickListener()==true) ivAvatar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivAvatar:{
                    Intent intent=new Intent(context, UserInfoActivity.class);
                    intent.putExtra("user", Parcels.wrap(mTweets.get(getAdapterPosition()).getUser()));
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }

    public class ViewHolderTimelinePhoto extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView tvText;
        public TextView tvTime;
        public ImageView ivAvatar;
        public TextView tvName;
        public TextView tvScreenName;
        public ImageView ivImage;
        public TextView tvLike;
        public TextView tvRetweet;
        public ImageView ivLike;
        public ViewHolderTimelinePhoto(View itemView) {
            super(itemView);
            tvText=(TextView)itemView.findViewById(R.id.tvText);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            tvName=(TextView)itemView.findViewById(R.id.tvName);
            tvScreenName=(TextView)itemView.findViewById(R.id.tvScreenName);
            ivAvatar=(ImageView)itemView.findViewById(R.id.ivAvatar);
            tvLike=(TextView)itemView.findViewById(R.id.tvLike);
            tvRetweet=(TextView)itemView.findViewById(R.id.tvRetweet);
            ivLike=(ImageView)itemView.findViewById(R.id.ivLike);
            if(checkSetOnClickListener()==true) ivAvatar.setOnClickListener(this);
            ivImage=(ImageView)itemView.findViewById(R.id.ivImage);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivAvatar:{
                    Intent intent=new Intent(context, UserInfoActivity.class);
                    intent.putExtra("user", Parcels.wrap(mTweets.get(getAdapterPosition()).getUser()));
                    context.startActivity(intent);
                    break;
                }
            }
        }
    }
}
