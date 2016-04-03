package com.codepath.apps.twitterdemo.fraqments;


import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.application.TwitterApplication;
import com.codepath.apps.twitterdemo.clients.TwitterClient;
import com.codepath.apps.twitterdemo.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComposeFragment extends DialogFragment implements TextWatcher{

    public interface OnSaveListener{
        void onSave();
    }
    private OnSaveListener onSaveListener;
    private DialogInterface.OnCancelListener mOnCancelListener;
    public void setOnSaveListener(OnSaveListener onSaveListener){

        this.onSaveListener=onSaveListener;
    }
    public ComposeFragment() {
        // Required empty public constructor
    }
    private User mUser;
    private TwitterClient twitterClient;
    @Bind(R.id.ivAvatar) ImageView ivAvatar;
    @Bind(R.id.tvName) TextView tvName;
    @Bind(R.id.btnTweet) Button btnTweet;
    @Bind(R.id.editText) EditText etContent;
    @Bind(R.id.tvCountLength) TextView tvCountLength;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_compose, container, false);
        ButterKnife.bind(this, rootView);
        mUser= Parcels.unwrap(getArguments().getParcelable("user"));
        twitterClient=TwitterApplication.getRestClient();
        etContent.addTextChangedListener(this);
        setView();
        return rootView;
    }

    private void setView() {
        Glide.with(getActivity().getApplicationContext())
                .load(mUser.getProfileImageUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.waitingphoto)
                .into(ivAvatar);
        tvName.setText(mUser.getName());
        tvCountLength.setText("0");
    }

    @Override
    public void afterTextChanged(Editable s) {
        tvCountLength.setText(String.valueOf(etContent.getText().length()));
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @OnClick(R.id.btnTweet)
    public void Tweet(Button button){
        if (etContent.getText().length()>140){
            Toast.makeText(getActivity().getApplicationContext(),"Limited status is 140 character",Toast.LENGTH_LONG).show();
            return;
        }
        RequestParams params = new RequestParams();
        params.put("status", etContent.getText());
        twitterClient.postTweet(params,new JsonHttpResponseHandler(){

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("Fail", "tweet success");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                onSaveListener.onSave();
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
