package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.databinding.ActivityDetailsBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class DetailsActivity extends AppCompatActivity {

    public static final int MAX_TWEET_LENGTH = 280;
    
    Boolean like;
    Boolean retweet;

    Tweet tweet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTweetDetails();
    }

    void setTweetDetails() {
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));


        Glide.with(this).load(tweet.user.profileImageUrl).transform(new RoundedCornersTransformation(40, 20)).into(ivProfileImage);
        if (!tweet.imageUrl.equals("")) {
            ivTweetPhotoDetails.setVisibility(View.VISIBLE);
            Glide.with(this).load(tweet.imageUrl).transform(new RoundedCornersTransformation(30, 10)).into(ivTweetPhotoDetails);
        }
        binding.tvBody.setText(tweet.body);
        binding.tvScreenName.setText(tweet.user.screenName);
        binding.tvName.setText(tweet.user.name);
        binding.tvTimeStamp.setText(ParseRelativeDate.getRelativeTimeAgo(tweet.createdAt));
        binding.tvLikeCount.setText(String.valueOf(tweet.likeCount));
        binding.tvRetweetCount.setText(String.valueOf(tweet.retweetCount));
        binding.btnPublishReply = findViewById(R.id.btnPublishReply);


        binding.like = tweet.like;
        binding.retweet = tweet.retweet;

        // setting listeners
        ivRetweet = findViewById(R.id.ivRetweet);
        ivReply = findViewById(R.id.ivReply);
        ivLike = findViewById(R.id.ivLike);

        ivRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retweetTweet();
            }
        });
        btnPublishReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyTweet();
            }
        });
        ivLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeTweet();
            }
        });

        etReply.setText("@" + tweet.user.screenName);
    }


    private void replyTweet() {
        TwitterClient client = TwitterApp.getRestClient(this);
        String tweetContent = etReply.getText().toString();

        if (tweetContent.isEmpty()) {
            Toast.makeText(DetailsActivity.this, "Tweet can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (tweetContent.length() > MAX_TWEET_LENGTH) {
            Toast.makeText(DetailsActivity.this, "Tweet is too long", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(DetailsActivity.this, tweetContent, Toast.LENGTH_SHORT).show();
        client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i("DetailsActivity", "onSuccess to publish the reply");
                try {
                    Tweet tweet = Tweet.fromJson(json.jsonObject);
                    Log.i("DetailsActivity", "published reply says: " + tweet.body);
                    Intent intent = new Intent();
                    intent.putExtra("tweet", Parcels.wrap(tweet));
                    setResult(RESULT_OK, intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("DetailsActivity", "onFailure to reply to tweet", throwable);
            }
        });


    }

    public void retweetTweet() {
        TwitterClient client = TwitterApp.getRestClient(this);
        client.retweetTweet(retweet, tweet.id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                if (retweet == true) {
                    ivRetweet.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_retweet_stroke));
                } else {
                    ivRetweet.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_retweet));
                }
                retweet = !retweet;
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("DetailsActivity", " retweet is not working", throwable);
            }
        });
    }


    public void likeTweet() {
        TwitterClient client = TwitterApp.getRestClient(this);
        client.likeTweet(like, tweet.id, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                if (like == true) {
                    ivLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_heart_stroke));
                } else {
                    ivLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_vector_heart));
                }
                like = !like;
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("DetailsActivity", " like is not working", throwable);

            }
        });
    }

}




