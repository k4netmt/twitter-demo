package com.codepath.apps.twitterdemo.fraqments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.adapters.TimelineAdapter;
import com.codepath.apps.twitterdemo.application.TwitterApplication;
import com.codepath.apps.twitterdemo.clients.TwitterClient;
import com.codepath.apps.twitterdemo.interfaces.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitterdemo.interfaces.SpacesItemDecoration;
import com.codepath.apps.twitterdemo.models.Tweet;
import com.codepath.apps.twitterdemo.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyTweetsFragment extends Fragment {

    private User mUser;
    private TwitterClient clients;
    private List<Tweet> mTweets;
    private TimelineAdapter mTimelineAdapter;
    @Bind(R.id.rvSearch) RecyclerView recyclerView;

    public MyTweetsFragment() {
        // Required empty public constructor
    }

    public static MyTweetsFragment newInstance(int page,User user) {
        MyTweetsFragment fragment = new MyTweetsFragment();
        fragment.mUser=user;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_my_tweets, container, false);
        ButterKnife.bind(this, rootView);
        clients= TwitterApplication.getRestClient();
        setupView(rootView);
        return rootView;
    }
    private void setupView(View rootView) {
        mTweets=new ArrayList<>();
        mTweets.clear();
        popularTimeline();
        mTimelineAdapter=new TimelineAdapter(mTweets);
        mTimelineAdapter.setmFlagSetOnClickListener(TimelineAdapter.USERTIMELINE_FRAQMENT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mTimelineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(4);
        recyclerView.addItemDecoration(decoration);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                popularTimeline();
            }
        });
    }

    private void popularTimeline() {
        RequestParams params = new RequestParams();
        params.put("user_id",mUser.getUid());
        params.put("count", 20);
        clients.getUserTimeLine(params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson = new GsonBuilder().create();
                int curSize = mTweets.size();
                mTweets.clear();
                List<Tweet> lstTweet = gson.fromJson(response.toString(), Tweet.listType);
                mTweets.addAll(lstTweet);
                mTimelineAdapter.notifyItemRangeInserted(curSize, mTweets.size());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //Load local Data
                Toast.makeText(getActivity().getApplicationContext(), "No internet access", Toast.LENGTH_LONG).show();
                Log.d("Debug,", errorResponse.toString());
            }
        });
    }
}
