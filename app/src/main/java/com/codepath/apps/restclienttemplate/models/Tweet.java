package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {

    public String body;
    public String createdAt;
    public User user;
    public boolean retweet;
    public boolean like;
    public int likeCount;
    public int retweetCount;
    public Long id;


    //empty constructor fr parceler
    public Tweet() {
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.retweet = jsonObject.getBoolean("retweeted");
        tweet.like = jsonObject.getBoolean("favorited");
        tweet.likeCount = jsonObject.getInt("favorite_count");
        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.id = jsonObject.getLong("id");

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
