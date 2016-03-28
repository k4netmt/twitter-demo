package com.codepath.apps.twitterdemo.fraqments;


import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.application.TwitterApplication;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComposeFragment extends DialogFragment {


    public ComposeFragment() {
        // Required empty public constructor
    }


    @Bind(R.id.btnTweet) Button btnTweet;
    @Bind(R.id.editText) EditText etContent;
    @Bind(R.id.tvCountLength) TextView tvCountLength;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_compose, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btnTweet)
    public void Tweet(Button button){
        SharedPreferences.Editor edit = getActivity().getPreferences(0).edit();
        edit.putString("294453477-yYGSmZNVUp3tyDf8rseKKtHoGX9ET5Y6KTzT3Pyb", "");
        edit.putString("TVz1CGQmpZvuhS6FRRU1ZwKUF9MdHkoTNrIZtHnOeL9Dj", "");
        edit.commit();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
