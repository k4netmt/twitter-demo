package com.codepath.apps.twitterdemo.viewholder;

import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.codepath.apps.twitterdemo.R;
import com.yqritc.scalablevideoview.ScalableType;
import com.yqritc.scalablevideoview.ScalableVideoView;

/**
 * Created by Kanet on 3/27/2016.
 */
public class ViewHolderTimelineVideo extends RecyclerView.ViewHolder {
    public TextView tvText;
    public TextView tvTime;
    public ImageView ivAvatar;
    public TextView tvName;
    public TextView tvScreenName;
    public VideoView svvVideo;
    public ScalableType stType;
    public ViewHolderTimelineVideo(View itemView) {
        super(itemView);
        tvText=(TextView)itemView.findViewById(R.id.tvText);
        tvTime=(TextView)itemView.findViewById(R.id.tvTime);
        tvName=(TextView)itemView.findViewById(R.id.tvName);
        tvScreenName=(TextView)itemView.findViewById(R.id.tvScreenName);
        ivAvatar=(ImageView)itemView.findViewById(R.id.ivAvatar);
        svvVideo=(VideoView)itemView.findViewById(R.id.vvVideo);
    }

    public void setScalableType(ScalableType type) {
        stType = type;
    }
}
