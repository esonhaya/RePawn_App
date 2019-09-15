package com.example.systemscoreinc.repawn.Home.Pawnshops;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class Home_Pawnshop_ViewHolder extends RecyclerView.ViewHolder {

       public ImageView pawnImage;
    public TextView pawnTitle, pawnCount;
    public LinearLayout linearLayout;
   // public TextView pawnAdd;
    public RatingBar pawnRate;

    public Home_Pawnshop_ViewHolder(@NonNull View itemView) {
        super(itemView);
        pawnTitle = itemView.findViewById(R.id.pawnshop_title);
        // pawnAdd = itemView.findViewById(R.id.pawnshop_address);
         pawnImage=itemView.findViewById(R.id.pawnshop_image);
        pawnCount = itemView.findViewById(R.id.reviews_count);
        pawnRate = itemView.findViewById(R.id.review_bar);
        linearLayout = itemView.findViewById(R.id.card_layout);
    }
}
