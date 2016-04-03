package com.codepath.apps.twitterdemo.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.adapters.HomeFraqmentAdapter;
import com.codepath.apps.twitterdemo.adapters.UserTabsAdapter;
import com.codepath.apps.twitterdemo.application.TwitterApplication;
import com.codepath.apps.twitterdemo.clients.TwitterClient;
import com.codepath.apps.twitterdemo.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.scribe.builder.api.TwitterApi;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserInfoActivity extends AppCompatActivity {

    private User mUser=null;
    private TwitterClient clients;
    @Bind(R.id.ivAvatar)ImageView ivAvater;
    @Bind(R.id.tvName)TextView tvName;
    @Bind(R.id.tvScreenName)TextView tvScreenName;
    @Bind(R.id.tvFollowing)TextView tvFollowing;
    @Bind(R.id.tvFollowers)TextView tvFollowers;
    @Bind(R.id.viewpager)ViewPager viewPager;
    @Bind(R.id.sliding_tabs)TabLayout tabLayout;
    @Bind(R.id.ivBackgound)ImageView ivBackground;
    @Bind(R.id.toolbar)Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setTitle("");
        ButterKnife.bind(this);
        clients= TwitterApplication.getRestClient();
        mUser= Parcels.unwrap(getIntent().getParcelableExtra("user"));
        Log.d("DEBUG",mUser.getName());
        setupUser();
        setupTabs();
        setSupportActionBar(toolbar);

    }

    private void setupTabs() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager.setClipToPadding(false);
        viewPager.setAdapter(new UserTabsAdapter(getSupportFragmentManager(),mUser));
        // Give the PagerSlidingTabStrip the ViewPager
        // Attach the view pager to the tab strip
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupUser() {
        tvName.setText(mUser.getName());
        tvScreenName.setText(mUser.getScreenName());
        tvFollowers.setText(setBackTextNumber(mUser.getFollowers_count(), " FOLLOWERS"));
        tvFollowing.setText(setBackTextNumber(mUser.getFriends_count(), " FOLLOWING"));
        Glide.with(getApplicationContext())
                .load(mUser.getProfileBannerUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.backgrounddefault)
                .into(ivBackground);
        Glide.with(getApplicationContext())
                .load(mUser.getProfileImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivAvater);
    }

    private SpannableString setBackTextNumber(long number,String text){
        String sFollowers=String.valueOf(number);
        SpannableString ss=new SpannableString(sFollowers+text);
        ss.setSpan(new ForegroundColorSpan(Color.BLACK),0,sFollowers.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUser=null;
    }


}
