package com.example.systemscoreinc.repawn.Items;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Adapter used to show a simple grid of products.
 */
public class Pawned_Items_Adapter extends RecyclerView.Adapter<My_Pawned_ViewHolder> implements Serializable {
    private List<ItemList> mDataset;
    private Context Ctx;
    IpConfig ip = new IpConfig();

    public Pawned_Items_Adapter(Context context, List<ItemList> myDataset) {
        mDataset = myDataset;
        Ctx = context;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public My_Pawned_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.items_card, parent, false);
        return new My_Pawned_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull My_Pawned_ViewHolder holder, final int position) {

        final ItemList item = mDataset.get(position);
        holder.Pawn_Name.setText(item.getItem_name());
        Picasso.get()
                .load(ip.getUrl_image() + item.getItem_image())
                .fit()
                .into(holder.product_image);
        int ordered = item.getOrdered();
        int reserved = item.getReserved();
        if (ordered == 0) {
            holder.item_ribbon.setBackgroundColor(ContextCompat.getColor(Ctx, R.color.colorPrimaryDark));
        }
        if (ordered == 1) {
            holder.item_ribbon.setBackgroundColor(Color.BLUE);
        }
        if(reserved==1) {
            holder.item_ribbon.setBackgroundColor(Color.YELLOW);
        }
        if(item.getPromoted()==1){
            holder.item_ribbon.setBackgroundColor(Color.parseColor("#FF8C00"));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date = null;
        try {
            date = simpleDateFormat.parse(item.getDate_added());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat convetDateFormat = new SimpleDateFormat("MM/dd/yyyy ");
        String sdate = convetDateFormat.format(date);
        holder.Date_Posted.setText("posted on " + sdate);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pinfo = new Intent(Ctx, MyPawnedInfo.class);
                pinfo.putExtra("item", item);

                Ctx.startActivity(pinfo);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}