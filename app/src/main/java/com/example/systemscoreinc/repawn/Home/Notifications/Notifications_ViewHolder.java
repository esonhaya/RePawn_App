package com.example.systemscoreinc.repawn.Home.Notifications;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class Notifications_ViewHolder extends RecyclerView.ViewHolder {

    public ImageView sell_image;
    public TextView sell_name, sell_date;
    public LinearLayout linearLayout;

    public Notifications_ViewHolder(@NonNull View itemView) {
        super(itemView);
        sell_image = itemView.findViewById(R.id.seller_image);
        sell_name = itemView.findViewById(R.id.notif_name);
        sell_date = itemView.findViewById(R.id.notif_date);
        linearLayout = itemView.findViewById(R.id.card_layout);
    }
}
