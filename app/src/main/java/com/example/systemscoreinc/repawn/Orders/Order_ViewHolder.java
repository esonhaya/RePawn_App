package com.example.systemscoreinc.repawn.Orders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.systemscoreinc.repawn.R;

public class Order_ViewHolder extends RecyclerView.ViewHolder {

    //  public ImageView pawnImage;
    public TextView product_name, request_price, request_status,date_sent;
    public LinearLayout linearLayout;
    public View item_ribbon;
    public ImageView product_image;

    public Order_ViewHolder(@NonNull View itemView) {
        super(itemView);
        product_name = itemView.findViewById(R.id.product_name);
        product_image = itemView.findViewById(R.id.product_image);
        request_price = itemView.findViewById(R.id.request_price);
        item_ribbon = itemView.findViewById(R.id.item_ribbon);
        request_status = itemView.findViewById(R.id.request_status);
        date_sent=itemView.findViewById(R.id.date_sent);
        linearLayout = itemView.findViewById(R.id.my_pawned_llayout);


    }
}
