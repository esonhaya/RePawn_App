package com.example.systemscoreinc.repawn.Home.Category;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class Home_Cat_ViewHolder extends RecyclerView.ViewHolder {

    //  public ImageView pawnImage;
    public TextView CatName;
    public LinearLayout linearLayout;

    public Home_Cat_ViewHolder(@NonNull View itemView) {
        super(itemView);
        CatName = itemView.findViewById(R.id.cat_name);
        linearLayout = itemView.findViewById(R.id.card_layout);

    }
}
