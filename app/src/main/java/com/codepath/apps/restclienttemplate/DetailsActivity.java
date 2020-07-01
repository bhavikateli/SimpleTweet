package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

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
    TextView tvRetweetLabel;
    TextView tvLikeLabel;

    Tweet tweet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTweetDetails();
    }

     void setTweetDetails() {
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        //when do we use ITEMVIEW. ?? ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
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

         ivRetweet = findViewById(R.id.ivRetweet);
         ivReply = findViewById(R.id.ivReply);
         ivLike = findViewById(R.id.ivLike);





     }
}
