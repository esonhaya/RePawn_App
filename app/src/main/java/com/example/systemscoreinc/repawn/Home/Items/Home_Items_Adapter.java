package com.example.systemscoreinc.repawn.Home.Items;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.Pawned_Info.Pawned_Info;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Adapter used to show a simple grid of products.
 */
public class Home_Items_Adapter extends RecyclerView.Adapter<Home_Items_ViewHolder> {
    private List<ItemList> mDataset;
    private Context Ctx;
    private Session session;
    IpConfig ip=new IpConfig();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    // Provide a suitable constructor (depends on the kind of dataset)
    public Home_Items_Adapter(Context context, List<ItemList> myDataset) {
        mDataset = myDataset;
        Ctx = context;
        session = new Session(Ctx);

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Home_Items_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.shr_promoted_rep, parent, false);
        return new Home_Items_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull Home_Items_ViewHolder holder, final int position) {

        final ItemList ppllist = mDataset.get(position);
        if (ppllist.getItem_type().equals("pawned")) {
            holder.SellName.setTextColor(Color.parseColor("#33ccff"));
        } else {
            holder.SellName.setTextColor(Color.parseColor("#FF8C00"));

        }
        Picasso.get()
                .load(ip.getUrl_image() +ppllist.getItem_image())
                .fit()
                .into(holder.item_image);
        holder.ProdName.setText(ppllist.getItem_name());
        holder.SellName.setText(ppllist.getSeller_name());
        holder.ItemPrice.setText("₱ " + ppllist.getPrice());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent to_item = new Intent(Ctx, Pawned_Info.class);
                to_item.putExtra("item", ppllist);
                Ctx.startActivity(to_item);

            }
        });

        //  holder.productTitle.setText(); // Here's your value

        // Return the size of your dataset (invoked by the layout manager)
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}