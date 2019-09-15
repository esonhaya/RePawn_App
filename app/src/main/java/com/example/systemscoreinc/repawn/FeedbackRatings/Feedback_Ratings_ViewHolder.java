package com.example.systemscoreinc.repawn.FeedbackRatings;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class Feedback_Ratings_ViewHolder extends RecyclerView.ViewHolder {
    public TextView review_name, review_date, review_content;
    public LinearLayout review_layout;
    public RatingBar review_bar;
    public ImageView review_image;


    public Feedback_Ratings_ViewHolder(@NonNull View frview) {
        super(frview);
        review_name = frview.findViewById(R.id.review_name);
        review_image = frview.findViewById(R.id.review_image);
        review_date = frview.findViewById(R.id.review_date);
        review_content = frview.findViewById(R.id.review_content);
        review_bar = frview.findViewById(R.id.review_bar);
        review_layout = frview.findViewById(R.id.review_layout);


    }
}
