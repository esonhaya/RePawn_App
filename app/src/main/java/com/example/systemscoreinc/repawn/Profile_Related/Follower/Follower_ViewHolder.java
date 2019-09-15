package com.example.systemscoreinc.repawn.Profile_Related.Follower;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class Follower_ViewHolder extends RecyclerView.ViewHolder {

    public ImageView follow_image;
    public TextView follow_name, follow_date;
    public LinearLayout card_layout;

    public Follower_ViewHolder(@NonNull View itemView) {
        super(itemView);
        card_layout = itemView.findViewById(R.id.card_layout);
        follow_name = itemView.findViewById(R.id.follow_name);
        follow_date = itemView.findViewById(R.id.follow_date);
        follow_image = itemView.findViewById(R.id.follow_image);
    }
}
