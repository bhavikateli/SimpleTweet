package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailsActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    TextView tvScreenName;
    TextView tvBody;
    TextView tvTimeStamp;
    ImageView ivRetweet;
    ImageView ivLike;
    ImageView ivReply;
    TextView tvLikeCount;
    TextView tvRetweetCount;

    Boolean like;
    Boolean retweet;

    Tweet tweet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTweetDetails();
    }

    void setTweetDetails() {
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvBody = findViewById(R.id.tvBody);
        tvTimeStamp = findViewById(R.id.tvTimeStamp);
        tvLikeCount = findViewById(R.id.tvLikeCount);
        tvRetweetCount = findViewById(R.id.tvRetweetCount);

        Glide.with(this).load(tweet.user.profileImageUrl).into(ivProfileImage);
        tvBody.setText(tweet.body);
        tvScreenName.setText(tweet.user.screenName);
        tvTimeStamp.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.createdAt));
        tvLikeCount.setText(String.valueOf(tweet.likeCount));
        tvRetweetCount.setText(String.valueOf(tweet.retweetCount));

        like = tweet.like;
        retweet = tweet.retweet;

        // setting listeners
        ivRetweet = findViewById(R.id.ivRetweet);
        ivReply = findViewById(R.id.ivReply);
        ivLike = findViewById(R.id.ivLike);
    /*
        ivRetweet.setOnClickListener();
        ivReply.setOnClickListener();
        ivLike.setOnClickListener();
    }

        public void onClick(View view){
        if(ivRetweet == view.getId()){
            likeTweet();
        }if(ivLike == view.getId()){
            retweetTweet;
         }else{
            return;
            }

        }
        */
    }

        public void likeTweet() {
            TwitterClient client = TwitterApp.getRestClient(this);
            client.likeTweet(retweet, tweet.id, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    if (retweet == true) {
                        Toast.makeText(getBaseContext(), "retweeted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "unretweeted", Toast.LENGTH_SHORT).show();
                    }
                    retweet = !retweet;
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                }
            });
        }


        public void retweetTweet() {
            TwitterClient client = TwitterApp.getRestClient(this);
            client.retweetTweet(like, tweet.id, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    if (like == true) {
                        Toast.makeText(getBaseContext(), "liked", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "unliked", Toast.LENGTH_SHORT).show();
                    }
                    like = !like;
                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

                }
            });
        }

    }




