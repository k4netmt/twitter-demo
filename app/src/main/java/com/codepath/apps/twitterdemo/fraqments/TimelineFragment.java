package com.codepath.apps.twitterdemo.fraqments;


import android.os.Bundle;
import android.app.Fragment;
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

    private TwitterClient clients;
    private List<Tweet> mTweets;
    private TimelineAdapter mTimelineAdapter;
    int max_id;
    int since_id=0;
    int count=10;
    @Bind(R.id.fragment_timeline) SwipeRefreshLayout swipeRefreshLayout;
    public TimelineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_timeline, container, false);
        ButterKnife.bind(this, rootView);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
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
        mTweets.clear();
        popularTimeline(0);
    }

    private void setupView(View rootView) {
        RecyclerView rvSearch = (RecyclerView)rootView.findViewById(R.id.rvSearch);
        mTweets=new ArrayList<>();
        mTweets.clear();
        popularTimeline(0);
        mTimelineAdapter=new TimelineAdapter(mTweets);

        rvSearch.setHasFixedSize(true);
        rvSearch.setAdapter(mTimelineAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setItemAnimator(new SlideInUpAnimator());
        SpacesItemDecoration decoration = new SpacesItemDecoration(4);
        rvSearch.addItemDecoration(decoration);
        rvSearch.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
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
        if (mTweets.size()==0){
            since_id=1;
            params.put("count",10);
        }else{
            max_id=count*(page+1);
            params.put("count",max_id);
        }
        clients.getHomeTimeline(params,new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Gson gson=new GsonBuilder().create();
                int curSize=mTweets.size();
                mTweets.clear();
                List<Tweet> lstTweet=gson.fromJson(response.toString(),Tweet.listType);
                mTweets.addAll(lstTweet);
                mTimelineAdapter.notifyItemRangeInserted(curSize, mTweets.size());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                //Load local Data
                Toast.makeText(getActivity().getApplicationContext(),"No internet access",Toast.LENGTH_LONG).show();
            }
        });
    }
}
