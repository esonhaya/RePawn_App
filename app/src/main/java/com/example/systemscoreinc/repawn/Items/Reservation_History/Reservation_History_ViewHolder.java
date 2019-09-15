package com.example.systemscoreinc.repawn.Items.Reservation_History;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.systemscoreinc.repawn.R;

public class Reservation_History_ViewHolder extends RecyclerView.ViewHolder {

    //  public ImageView pawnImage;
    public TextView orderer_name, date_sent, date_accepted, date_ended;
    public LinearLayout linearLayout;

    public Reservation_History_ViewHolder(@NonNull View itemView) {
        super(itemView);
        orderer_name = itemView.findViewById(R.id.orderer_name);
        date_sent = itemView.findViewById(R.id.date_sent);
        date_accepted = itemView.findViewById(R.id.date_accepted);
        date_ended = itemView.findViewById(R.id.date_ended);
        linearLayout = itemView.findViewById(R.id.order_layout);


    }
}