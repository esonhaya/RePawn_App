package com.example.systemscoreinc.repawn.Home.Items;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class Home_Items_ViewHolder extends RecyclerView.ViewHolder {

    //  public ImageView pawnImage;
    public TextView ProdName, SellName, ItemPrice;
    public LinearLayout linearLayout;
    public ImageView item_image;

    public Home_Items_ViewHolder(@NonNull View itemView) {
        super(itemView);
        ProdName = itemView.findViewById(R.id.product_title);
        SellName = itemView.findViewById(R.id.repawner_name);
        ItemPrice = itemView.findViewById(R.id.item_price);
        linearLayout = itemView.findViewById(R.id.card_layout);
        item_image = itemView.findViewById(R.id.item_image);

    }
}
