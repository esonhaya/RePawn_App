package com.example.systemscoreinc.repawn.Home.RePawners;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.systemscoreinc.repawn.R;

public class RePawner_ViewHolder extends RecyclerView.ViewHolder {

    public ImageView rep_image;
    public TextView rep_name;
    public LinearLayout linearLayout;
    // public TextView pawnAdd;

    public RePawner_ViewHolder(@NonNull View itemView) {
        super(itemView);
        rep_image = itemView.findViewById(R.id.rep_image);
        rep_name = itemView.findViewById(R.id.rep_name);
        linearLayout = itemView.findViewById(R.id.card_layout);
    }
}
