package com.codepath.apps.twitterdemo.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.adapters.TimelineAdapter;
import com.codepath.apps.twitterdemo.application.TwitterApplication;
import com.codepath.apps.twitterdemo.clients.TwitterClient;
import com.codepath.apps.twitterdemo.fraqments.ComposeFragment;
import com.codepath.apps.twitterdemo.fraqments.TimelineFragment;
import com.codepath.apps.twitterdemo.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

import java.util.List;

public class TimelineActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment  = getFragmentManager().findFragmentByTag("timeline");
        if (fragment != null) {
            getFragmentManager().beginTransaction().detach(fragment).commit();
            getFragmentManager().beginTransaction().attach(fragment).commit();

        } else {
            fragment=new TimelineFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container,fragment,"timeline").commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bottom, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_compose:
            {
               final ComposeFragment fragment=new ComposeFragment();
                fragment.show(getFragmentManager(),"compose");
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }



}
