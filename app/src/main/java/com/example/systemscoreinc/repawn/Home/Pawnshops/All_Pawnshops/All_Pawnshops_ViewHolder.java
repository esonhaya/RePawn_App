package com.example.systemscoreinc.repawn.Home.Pawnshops.All_Pawnshops;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.systemscoreinc.repawn.R;

public class All_Pawnshops_ViewHolder extends RecyclerView.ViewHolder {
    public TextView pshop_name, follow_count;
    public RatingBar pshop_rate;
    public LinearLayout linearLayout;
    public ImageView pshop_image;

    public All_Pawnshops_ViewHolder(@NonNull View itemView) {
        super(itemView);
        pshop_image = itemView.findViewById(R.id.pawnshop_image);
        pshop_name = itemView.findViewById(R.id.pawnshop_name);
        pshop_rate = itemView.findViewById(R.id.review_bar);
        follow_count = itemView.findViewById(R.id.follower_count);
        linearLayout = itemView.findViewById(R.id.card_layout);


    }
}
