package com.codepath.apps.twitterdemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.twitterdemo.fraqments.LikesFragment;
import com.codepath.apps.twitterdemo.fraqments.MediaFragment;
import com.codepath.apps.twitterdemo.fraqments.MentionsFragment;
import com.codepath.apps.twitterdemo.fraqments.MyTweetsFragment;
import com.codepath.apps.twitterdemo.models.User;

/**
 * Created by Kanet on 4/2/2016.
 */
public class UserTabsAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private User mUser;
    private String tabTitles[] = new String[] { "Tweets", "Media","Followers"};

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                return MyTweetsFragment.newInstance(0,mUser);
            }
            case 1:{
                return MediaFragment.newInstance(1,mUser);
            }
            case 2:{
                return LikesFragment.newInstance(2);
            }
        }
        return null;
    }

    public UserTabsAdapter(FragmentManager fm,User user) {
        super(fm);
        this.mUser=user;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
