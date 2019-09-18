package com.example.systemscoreinc.repawn.Home.Pawnshops;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.Pawnshop.Pawnshop_Page;
import com.example.systemscoreinc.repawn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Adapter used to show a simple grid of products.
 */
public class Home_Pawnshops_Adapter extends RecyclerView.Adapter<Home_Pawnshop_ViewHolder> {
    private List<PopularList> mDataset;
    private Context Ctx;
    IpConfig ip = new IpConfig();
    private ArrayList<PopularList> arraylist;
    public Home_Pawnshops_Adapter(Context context, List<PopularList> myDataset) {
        mDataset = myDataset;
        Ctx = context;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(mDataset);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Home_Pawnshop_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.shr_pawnshop_card, parent, false);
        return new Home_Pawnshop_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull Home_Pawnshop_ViewHolder holder, final int position) {

        final PopularList poplist = mDataset.get(position);
        holder.pawnTitle.setText(poplist.getP_name());
        //    holder.pawnAdd.setText(poplist.getPadd());
        holder.pawnCount.setText((poplist.getRate_count()) + " reviews");
        float rating = (float) poplist.getRate_total() / poplist.getRate_count();
        Picasso.get()
                .load(ip.getUrl_image() + poplist.getP_image())
                .fit()
                .into(holder.pawnImage);
        holder.pawnRate.setRating(rating);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_pawnshop_page = new Intent(Ctx, Pawnshop_Page.class);
                to_pawnshop_page.putExtra("user_id", poplist.getP_id());
                Ctx.startActivity(to_pawnshop_page);
            }
        });
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mDataset.clear();
        if (charText.length() == 0) {
            mDataset.addAll(arraylist);
        } else {
            for (PopularList wp : arraylist) {
                if (wp.getP_name().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mDataset.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}