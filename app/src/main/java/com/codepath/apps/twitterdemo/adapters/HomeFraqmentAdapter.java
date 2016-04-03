package com.codepath.apps.twitterdemo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.codepath.apps.twitterdemo.fraqments.MentionsFragment;
import com.codepath.apps.twitterdemo.fraqments.TimelineFragment;

/**
 * Created by Kanet on 3/30/2016.
 */
public class HomeFraqmentAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Home", "Mentions"};

    @Override
    public Fragment getItem(int position) {
        Log.d("DEBUG",String.valueOf(position));
        switch (position){
            case 0:{
                return TimelineFragment.newInstance(0);
            }
            case 1:{
                return MentionsFragment.newInstance(1);
            }
        }
        return null;
    }

    public HomeFraqmentAdapter(FragmentManager fm) {
        super(fm);
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
