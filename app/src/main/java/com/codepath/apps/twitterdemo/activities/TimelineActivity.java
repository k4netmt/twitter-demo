package com.codepath.apps.twitterdemo.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.application.TwitterApplication;
import com.codepath.apps.twitterdemo.clients.TwitterClient;
import com.codepath.apps.twitterdemo.fraqments.ComposeFragment;
import com.codepath.apps.twitterdemo.fraqments.HomeFragment;
import com.codepath.apps.twitterdemo.models.User;

import org.parceler.Parcels;
import org.scribe.services.HMACSha1SignatureService;

public class TimelineActivity extends AppCompatActivity {

    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mUser= Parcels.unwrap(getIntent().getParcelableExtra("user"));
        setSupportActionBar(toolbar);
        callHome();
    }

    private void callHome() {
        Fragment fragment  = getSupportFragmentManager().findFragmentByTag("home");
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().detach(fragment).commit();
            getSupportFragmentManager().beginTransaction().attach(fragment).commit();
        } else {
            fragment=new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container,fragment,"home").commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_bottom, menu);
        //menu.findItem(R.id.action_signout).setIcon(R.drawable.profile);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
           case R.id.action_compose:
            {
                ComposeFragment fragment=new ComposeFragment();
                Bundle bundle=new Bundle();
                bundle.putParcelable("user",Parcels.wrap(mUser));
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "compose");
                fragment.setOnSaveListener(new ComposeFragment.OnSaveListener() {
                    @Override
                    public void onSave() {
                        //callHome();
                        updateHomeChild();
                    }
                });
                break;
            }
            case R.id.action_profile:
            {
                Intent intent=new Intent(TimelineActivity.this, UserInfoActivity.class);
                intent.putExtra("user", Parcels.wrap(mUser));
                startActivity(intent);
                break;
            }
            case R.id.action_signout:{
                TwitterClient.getInstance(TwitterClient.class,getApplicationContext()).clearAccessToken();
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateHomeChild() {
        HomeFragment fragment  =(HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
        if (fragment != null) {
            fragment.update();
        }
    }


}
