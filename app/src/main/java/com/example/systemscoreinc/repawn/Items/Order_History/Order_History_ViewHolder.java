package com.example.systemscoreinc.repawn.Items.Order_History;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.systemscoreinc.repawn.R;

public class Order_History_ViewHolder extends RecyclerView.ViewHolder {

    //  public ImageView pawnImage;
    public TextView orderer_name, date_sent, date_accepted, payment_type, date_ended;
    public LinearLayout linearLayout;

    public Order_History_ViewHolder(@NonNull View itemView) {
        super(itemView);
        orderer_name = itemView.findViewById(R.id.orderer_name);
        date_sent = itemView.findViewById(R.id.date_sent);
        date_accepted = itemView.findViewById(R.id.date_accepted);
        payment_type = itemView.findViewById(R.id.payment_type);
        date_ended = itemView.findViewById(R.id.date_ended);
        linearLayout = itemView.findViewById(R.id.order_layout);


    }
}