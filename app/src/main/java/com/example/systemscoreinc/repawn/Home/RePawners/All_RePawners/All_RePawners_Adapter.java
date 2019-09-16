package com.example.systemscoreinc.repawn.Home.RePawners.All_RePawners;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.systemscoreinc.repawn.Home.RePawners.RePawnerList;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.Profile_Related.RePawner_Profile;
import com.example.systemscoreinc.repawn.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class All_RePawners_Adapter extends RecyclerView.Adapter<All_RePawners_ViewHolder> {
    private List<RePawnerList> mDataset;
    private Context Ctx;
    IpConfig ip = new IpConfig();
    private ArrayList<RePawnerList> arraylist;

    public All_RePawners_Adapter(Context context, List<RePawnerList> myDataset) {
        mDataset = myDataset;
        Ctx = context;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(mDataset);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public All_RePawners_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.all_repawners_card, parent, false);
        return new All_RePawners_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull All_RePawners_ViewHolder holder, final int position) {

        final RePawnerList list = mDataset.get(position);
        holder.rep_name.setText(list.getRname());
        holder.follow_count.setText(list.getFollow_count() + " followers");
        float rate_avg = 0;
        if (list.getRate_count() != 0 && list.getRate_total() != 0) {
            rate_avg = list.getRate_total() / list.getRate_count();
        }
        holder.rep_rate.setRating(rate_avg);
        Picasso.get()
                .load(ip.getUrl_image() + list.getRpic())
                .fit()
                .into(holder.rep_image);
        holder.linearLayout.setOnClickListener(v -> {
            Intent to_pawnshop_page = new Intent(Ctx, RePawner_Profile.class);
            to_pawnshop_page.putExtra("user_id", list.getUser_id());
            Ctx.startActivity(to_pawnshop_page);


        });
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mDataset.clear();
        if (charText.length() == 0) {
            mDataset.addAll(arraylist);
        } else {
            for (RePawnerList wp : arraylist) {
                if (wp.getRname().toLowerCase(Locale.getDefault()).contains(charText)) {
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
