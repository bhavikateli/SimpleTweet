package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.models.User;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {

    Context context;
    List<User> followers;

    public ProfileAdapter(Context context, List<User> followers) {
        this.context = context;
        this.followers = followers;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = followers.get(position);

        holder.bind(user);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_following_followers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFollowers;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFollowers = itemView.findViewById(R.id.tvFollowers);
        }

        public void bind(User user) {
            tvFollowers.setText(user.name);

        }
    }
}
