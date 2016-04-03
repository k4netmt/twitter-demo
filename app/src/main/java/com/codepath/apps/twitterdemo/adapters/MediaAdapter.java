package com.codepath.apps.twitterdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.models.ExtendedEntities;
import com.codepath.apps.twitterdemo.models.Media;
import com.codepath.apps.twitterdemo.models.Tweet;

import java.util.List;

/**
 * Created by Kanet on 4/3/2016.
 */
public class MediaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PHOTO=1;
    private static final int GIF=2;
    private static final int VIDEO=3;
    private List<Media> medias;
    private Context context;
    public MediaAdapter(List<Media> medias) {
        super();
        this.medias=medias;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()){
            case PHOTO:{
                ViewHolderMediaPhoto vhPhoto = (ViewHolderMediaPhoto) viewHolder;
                configureViewHolderMediaPhoto(vhPhoto, position);
                break;
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case PHOTO: {
                View v2 = inflater.inflate(R.layout.item_media_photo, parent, false);
                viewHolder = new ViewHolderMediaPhoto(v2);
                break;
            }
            default:
                View v = inflater.inflate(R.layout.item_media_photo, parent, false);
                viewHolder = new ViewHolderMediaPhoto(v);
                break;
        }
        return viewHolder;
    }

    private void configureViewHolderMediaPhoto(ViewHolderMediaPhoto viewHolder, int position) {
        final Media media =  medias.get(position);
        if (media!=null){
            Glide.with(context)
                    .load(media.getMedia_url())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.waitingphoto)
                    .into(viewHolder.ivImage);
        }
    }
    @Override
    public int getItemViewType(int position) {
        Media media=medias.get(position);
        String type=media.getType();
        if (type.compareTo("photo")==0){
            return PHOTO;
        }
        if (type.compareTo("animated_gif")==0){
            return GIF;
        }
        if (type.compareTo("video")==0){
            return VIDEO;
        }
        return PHOTO;
    }

    @Override
    public int getItemCount() {
        return medias.size();
    }

    public class ViewHolderMediaPhoto extends RecyclerView.ViewHolder{
        ImageView ivImage;
        public ViewHolderMediaPhoto(View itemView) {
            super(itemView);
            ivImage=(ImageView)itemView.findViewById(R.id.ivImage);
        }
    }
}
