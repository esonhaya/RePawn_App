package com.example.systemscoreinc.repawn.Items;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class My_Pawned_ViewHolder extends RecyclerView.ViewHolder {

    //  public ImageView pawnImage;
    public TextView Pawn_Name, Date_Posted, Pawn_Status;
    public LinearLayout linearLayout;
    public View item_ribbon;
    public ImageView product_image;

    public My_Pawned_ViewHolder(@NonNull View itemView) {
        super(itemView);
        Pawn_Name = itemView.findViewById(R.id.product_name);
        product_image = itemView.findViewById(R.id.product_image);
        Date_Posted = itemView.findViewById(R.id.pawned_date_posted);
        item_ribbon = itemView.findViewById(R.id.item_ribbon);
        linearLayout = itemView.findViewById(R.id.my_pawned_llayout);


    }
}
