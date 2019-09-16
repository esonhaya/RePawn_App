package com.example.systemscoreinc.repawn.Home.RePawners.All_RePawners;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.systemscoreinc.repawn.R;

public class All_RePawners_ViewHolder extends RecyclerView.ViewHolder {
    public TextView rep_name, follow_count;
    public RatingBar rep_rate;
    public LinearLayout linearLayout;
    public ImageView rep_image;

    public All_RePawners_ViewHolder(@NonNull View itemView) {
        super(itemView);
        rep_image = itemView.findViewById(R.id.rep_image);
        rep_name = itemView.findViewById(R.id.rep_name);
        rep_rate = itemView.findViewById(R.id.review_bar);
        follow_count = itemView.findViewById(R.id.follower_count);
        linearLayout = itemView.findViewById(R.id.card_layout);


    }
}
