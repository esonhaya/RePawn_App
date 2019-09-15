package com.example.systemscoreinc.repawn.Items.Pending_Request;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.systemscoreinc.repawn.R;

public class Pending_Request_ViewHolder extends RecyclerView.ViewHolder {

    //  public ImageView pawnImage;
    public TextView request_name, request_date, payment_type;
    public ImageView request_image;
    public LinearLayout linearLayout;

    public Pending_Request_ViewHolder(@NonNull View itemView) {
        super(itemView);
        request_name = itemView.findViewById(R.id.request_name);
        request_date = itemView.findViewById(R.id.request_date);
        request_image = itemView.findViewById(R.id.request_image);
        linearLayout = itemView.findViewById(R.id.card_layout);
        payment_type = itemView.findViewById(R.id.payment_type);

    }
}
