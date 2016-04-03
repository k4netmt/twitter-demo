package com.codepath.apps.twitterdemo.clients;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.Parameter;

import android.content.Context;

import com.codepath.apps.twitterdemo.models.Tweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "hIXxNgdzILMW9koiw9SA0tNAs";       // Change this
	public static final String REST_CONSUMER_SECRET = "BiiJsXMLn9pNase0zQEbFTPe3ClS06oot2NfFuQrUSqa8nZ933"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://TwitterClientKanet"; // Change this (here and in manifest)
	public static final int COUNT_PAGE=20;
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(RequestParams params,JsonHttpResponseHandler hanlder){
		String apiUrl = getApiUrl("statuses/home_timeline.json");
	/*	RequestParams parameter=new RequestParams();
		parameter.put("count",COUNT);
		parameter.put("since_id",1);*/
		client.get(apiUrl, params, hanlder);
	}

	public void getMentionsTimeline(RequestParams params,JsonHttpResponseHandler hanlder){
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");
		client.get(apiUrl, params, hanlder);
	}

	public void postTweet(RequestParams params,AsyncHttpResponseHandler handler){
		String apiUrl = getApiUrl("statuses/update.json");
		client.post(apiUrl, params, handler);
	}

	public void getAccountVerify(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		client.get(apiUrl, null, handler);
	}

	public void getUserShow(RequestParams params,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("users/show.json");
		// Can specify query string params directly or through RequestParams.
		client.get(apiUrl, params, handler);
	}

	public void getUserTimeLine(RequestParams params,AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");
		client.get(apiUrl, params, handler);
	}
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

}