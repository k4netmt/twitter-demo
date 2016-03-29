package com.codepath.apps.twitterdemo.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twitterdemo.R;

/**
 * Created by Kanet on 3/26/2016.
 */
public class ViewHolderTimelineText extends RecyclerView.ViewHolder {
    public TextView tvText;
    public TextView tvTime;
    public ImageView ivAvatar;
    public TextView tvName;
    public TextView tvScreenName;
    public ViewHolderTimelineText(View itemView) {
        super(itemView);
        tvText=(TextView)itemView.findViewById(R.id.tvText);
        tvTime=(TextView)itemView.findViewById(R.id.tvTime);
        tvName=(TextView)itemView.findViewById(R.id.tvName);
        tvScreenName=(TextView)itemView.findViewById(R.id.tvScreenName);
        ivAvatar=(ImageView)itemView.findViewById(R.id.ivAvatar);
    }
}
