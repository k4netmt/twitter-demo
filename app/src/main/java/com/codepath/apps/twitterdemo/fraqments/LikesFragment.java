package com.codepath.apps.twitterdemo.fraqments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twitterdemo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikesFragment extends Fragment {


    public LikesFragment() {
        // Required empty public constructor
    }

    public static LikesFragment newInstance(int page) {
        LikesFragment fragment = new LikesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_likes, container, false);
    }

}
