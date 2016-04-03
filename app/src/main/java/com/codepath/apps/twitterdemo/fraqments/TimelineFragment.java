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
import com.codepath.apps.twitterdemo.internet.Internet;
import com.codepath.apps.twitterdemo.models.Tweet;
import com.codepath.apps.twitterdemo.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final int PAGE_REFRESH = -1;
    private static final int PAGE_NEW = 0;
    private User user;
    private TwitterClient clients;
    private List<Tweet> mTweets;
    private TimelineAdapter mTimelineAdapter;
    @Bind(R.id.fragment_timeline) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.rvSearch) RecyclerView recyclerView;
    private static TimelineFragment timelineFragment;

    public TimelineFragment() {
        // Required empty public constructor
    }

    public static TimelineFragment newInstance(int page) {
        if (timelineFragment==null){
           timelineFragment=new TimelineFragment();
        }
        Log.d("DEBUG","Timeline new");
        return timelineFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, rootView);
        Log.d("DEBUG", "Timeline");
        clients= TwitterApplication.getRestClient();
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        popularTimeline(PAGE_REFRESH);
    }


    private void setupView(View rootView) {
        mTweets=new ArrayList<>();
        mTweets.clear();

        mTimelineAdapter=new TimelineAdapter(mTweets);
        mTimelineAdapter.setmFlagSetOnClickListener(TimelineAdapter.TIMELINE_FRAQMENT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mTimelineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(4);
        recyclerView.addItemDecoration(decoration);
        popularTimeline(PAGE_NEW);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                popularTimeline(page);
            }
        });
    }

    private void popularTimeline(int page) {
        RequestParams params = new RequestParams();
        swipeRefreshLayout.setRefreshing(true);
        Tweet beforeThisTweet = null;
        Tweet afterThisTweet = null;
        if ((page == PAGE_REFRESH) && !mTweets.isEmpty()) {
            // page < 0, we're refreshing the list.  look for tweets more recent than first in list
            afterThisTweet = mTweets.get(0);
            params.put("since_id", afterThisTweet.getUid());
        } else if (page == PAGE_NEW) {
            mTimelineAdapter.clear();
            params.put("since_id", 1);
        } else if (!mTweets.isEmpty()){
            // page > 0, we're scrolling back in time -- look for tweets before the last in list
            beforeThisTweet = mTweets.get(mTweets.size() - 1);
            params.put("max_id", beforeThisTweet.getUid());
        }
        params.put("count",TwitterClient.COUNT_PAGE);
        clients.getHomeTimeline(params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson=new GsonBuilder().create();
                int curSize=mTweets.size();
                mTweets.clear();
                List<Tweet> lstTweet=gson.fromJson(response.toString(),Tweet.listType);
                mTweets.addAll(lstTweet);
                mTimelineAdapter.notifyItemRangeInserted(curSize, mTweets.size());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //Load local Data
                Toast.makeText(getActivity().getApplicationContext(),"No internet access",Toast.LENGTH_LONG).show();
                Log.d("Debug,",errorResponse.toString());
            }
        });
    }

    protected void update(){
        popularTimeline(PAGE_NEW);
    }
}
