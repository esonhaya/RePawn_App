package com.example.systemscoreinc.repawn.Profile_Related.Follower;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.Profile_Related.RePawner_Profile;
import com.example.systemscoreinc.repawn.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Follower_Adapter extends RecyclerView.Adapter<Follower_ViewHolder> {
    private List<Follower_List> mDataset;
    private Context Ctx;
    IpConfig ip=new IpConfig();

    public Follower_Adapter(Context context, List<Follower_List> myDataset) {
        mDataset = myDataset;
        Ctx = context;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Follower_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                  int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.follower_card, parent, false);
        return new Follower_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull Follower_ViewHolder holder, final int position) {

        final Follower_List list = mDataset.get(position);
        holder.follow_name.setText(list.getSeller_name());
        holder.follow_date.setText(list.getDate());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(list.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat convetDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String sdate = convetDateFormat.format(date);
        holder.follow_date.setText(sdate);
        Picasso.get()
                .load(ip.getUrl_image() +list.getUser_image())
                .fit()
                .into(holder.follow_image);
        holder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_follow_profile = new Intent(Ctx, RePawner_Profile.class);
                to_follow_profile.putExtra("user_id", list.user_id);
                Ctx.startActivity(to_follow_profile);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}