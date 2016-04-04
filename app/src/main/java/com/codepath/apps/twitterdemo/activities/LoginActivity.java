package com.codepath.apps.twitterdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.twitterdemo.R;
import com.codepath.apps.twitterdemo.application.TwitterApplication;
import com.codepath.apps.twitterdemo.clients.TwitterClient;
import com.codepath.apps.twitterdemo.models.Tweet;
import com.codepath.apps.twitterdemo.models.User;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.List;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	private TwitterClient clients;
	private User mUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		clients=TwitterApplication.getRestClient();
	}


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {
		clients.getAccountVerify(new JsonHttpResponseHandler() {
			@Override
			public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
				Log.d("DEBUG",throwable.toString());
				TwitterClient.getInstance(TwitterClient.class,getApplicationContext()).clearAccessToken();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
				Gson gson = new GsonBuilder().create();
				mUser = new User();
				mUser = gson.fromJson(response.toString(), User.class);
				Intent intent= new Intent(LoginActivity.this, TimelineActivity.class);
				intent.putExtra("user", Parcels.wrap(mUser));
				startActivity(intent);

			}
		});

	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}
