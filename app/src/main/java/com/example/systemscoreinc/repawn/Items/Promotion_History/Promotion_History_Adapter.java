package com.example.systemscoreinc.repawn.Items.Promotion_History;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.systemscoreinc.repawn.R;

import java.util.List;

public class Promotion_History_Adapter extends RecyclerView.Adapter<Promotion_History_ViewHolder> {
    private List<Promotion_History_List> mDataset;
    private Context Ctx;

    public Promotion_History_Adapter(Context context, List<Promotion_History_List> myDataset) {
        mDataset = myDataset;
        Ctx = context;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Promotion_History_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.promotion_history_card, parent, false);
        return new Promotion_History_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull Promotion_History_ViewHolder holder, final int position) {

        final Promotion_History_List promlist = mDataset.get(position);
        holder.prom_start.setText(promlist.getDate_start());
        holder.prom_end.setText(promlist.getDate_end());
        holder.prom_pay_id.setText(promlist.getPay_id());
        holder.prom_amount.setText(promlist.getAmount());


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}