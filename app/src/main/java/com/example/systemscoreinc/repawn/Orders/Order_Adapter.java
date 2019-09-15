package com.example.systemscoreinc.repawn.Orders;

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
import com.example.systemscoreinc.repawn.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Order_Adapter extends RecyclerView.Adapter<Order_ViewHolder> {
    private List<Order_List> mDataset;
    private Context Ctx;
    IpConfig ip = new IpConfig();

    public Order_Adapter(Context context, List<Order_List> myDataset) {
        mDataset = myDataset;
        Ctx = context;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Order_ViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.order_card, parent, false);
        return new Order_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull Order_ViewHolder holder, final int position) {
        final Order_List item = mDataset.get(position);
        holder.product_name.setText(item.getProduct_name());
        Picasso.get()
                .load(ip.getUrl_image() + item.getImage_url())
                .fit()
                .into(holder.product_image);
        String status = item.getStatus();
        if (status.equals("accepted")) {
            holder.request_status.setTextColor(ContextCompat.getColor(Ctx, R.color.colorPrimaryDark));
        } else if (status.equals("declined")) {
            holder.item_ribbon.setBackgroundColor(Color.RED);
        }
        String rtype=item.getRequest_type();
            if(rtype.equals("order")) {
                holder.item_ribbon.setBackgroundColor(ContextCompat.getColor(Ctx, R.color.colorPrimaryDark));
            } else {
                holder.item_ribbon.setBackgroundColor(Color.BLUE);
            }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pinfo = new Intent(Ctx, Order_Info.class);
                pinfo.putExtra("product_id", item.getProduct_id());
                pinfo.putExtra("request_type", item.getRequest_type());
                pinfo.putExtra("request_status", item.getStatus());
                Ctx.startActivity(pinfo);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
