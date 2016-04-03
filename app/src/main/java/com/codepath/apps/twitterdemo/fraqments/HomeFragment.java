package com.codepath.apps.twitterdemo.fraqments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.adapters.HomeFraqmentAdapter;
import com.codepath.apps.twitterdemo.application.TwitterApplication;
import com.codepath.apps.twitterdemo.clients.TwitterClient;
import com.codepath.apps.twitterdemo.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */

public class HomeFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private User user;
    private int mPage;
    private TwitterClient clients;
    private HomeFraqmentAdapter homeFraqmentAdapter;
    @Bind(R.id.viewpager)ViewPager viewPager;
    @Bind(R.id.sliding_tabs)TabLayout tabsStrip;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ButterKnife.bind(this,view);
        clients= TwitterApplication.getRestClient();
        getUserInfo();
        Log.d("DEBUG", "Home");
        viewPager.setClipToPadding(false);
        homeFraqmentAdapter=new HomeFraqmentAdapter(getFragmentManager());
        viewPager.setAdapter(homeFraqmentAdapter);
        // Give the PagerSlidingTabStrip the ViewPager
        // Attach the view pager to the tab strip
        tabsStrip.setupWithViewPager(viewPager);
        return view;
    }

    private void getUserInfo() {
        clients.getAccountVerify( new JsonHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Gson gson = new GsonBuilder().create();
                user = gson.fromJson(response.toString(), User.class);
            }
        });
    }


    public void update() {
            for (int i=0;i<viewPager.getChildCount();i++){
                if(homeFraqmentAdapter.getItem(i) instanceof TimelineFragment){
                    TimelineFragment fragment=(TimelineFragment)homeFraqmentAdapter.getItem(i);
                    fragment.update();
                }
            }
    }
}
