package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    TextView tvScreenName;
    TextView tvName;
    Tweet tweet;
    RecyclerView rvFollowers;
    RecyclerView rvFollowing;
    List<User> followers;
    List<User> following;
    ProfileAdapter followersAdapter;
    ProfileAdapter followingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setCreate();
    }

    private void setCreate() {

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweetProfile"));
        Log.i("ProfileActivity", tweet.toString());

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvName = findViewById(R.id.tvName);
        rvFollowing = findViewById(R.id.rvFollowing);
        rvFollowers = findViewById(R.id.rvFollowers);

        followers = new ArrayList<>();
        following = new ArrayList<>();

        TwitterClient client = TwitterApp.getRestClient(this);
        client.getFollowersList(tweet.user.id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = json.jsonObject.getJSONArray("users");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        followers.add(User.fromJson(jsonArray.getJSONObject(i)));
                        followersAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

        client.getFollowingList(tweet.user.id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = json.jsonObject.getJSONArray("users");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        following.add(User.fromJson(jsonArray.getJSONObject(i)));
                        followingAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });

        followersAdapter = new ProfileAdapter(this, followers);
        followingAdapter = new ProfileAdapter(this, following);
        LinearLayoutManager followerLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager followingLayoutManager = new LinearLayoutManager(this);

        rvFollowers.setLayoutManager(followerLayoutManager);
        rvFollowing.setLayoutManager(followingLayoutManager);

        rvFollowers.setAdapter(followersAdapter);
        rvFollowing.setAdapter(followingAdapter);

        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .transform(new RoundedCornersTransformation(40, 20))
                .into(ivProfileImage);
        tvScreenName.setText(tweet.user.screenName);
        tvName.setText(tweet.user.name);

    }


}