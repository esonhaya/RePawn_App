package com.example.systemscoreinc.repawn.Home.Notifications;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Notifications_Adapter extends RecyclerView.Adapter<Notifications_ViewHolder> {
    private List<Notifications_List> mDataset;
    private Context Ctx;
    IpConfig ip = new IpConfig();
    private ArrayList<Notifications_List> arraylist;

    public Notifications_Adapter(Context context, List<Notifications_List> myDataset) {
        mDataset = myDataset;
        Ctx = context;
        this.arraylist = new ArrayList<>();
        this.arraylist.addAll(mDataset);
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Notifications_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.notification_card, parent, false);
        return new Notifications_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull Notifications_ViewHolder holder, final int position) {

        final Notifications_List nlist = mDataset.get(position);
//        holder.pawnTitle.setText(poplist.getP_name());
//        //    holder.pawnAdd.setText(poplist.getPadd());
//        holder.pawnCount.setText((poplist.getRate_count()) + " reviews");
//        float rating = (float) poplist.getRate_total() / poplist.getRate_count();
//        Picasso.get()
//                .load(ip.getUrl_image() + poplist.getP_image())
//                .fit()
//                .into(holder.pawnImage);

//        holder.pawnRate.setRating(rating);
                Picasso.get()
                .load(ip.getUrl_image() + nlist.getNotif_image())
                .fit()
                .into(holder.sell_image);
        try {
            holder.sell_date.setText(date_ago(nlist.getDate_posted()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.linearLayout.setOnClickListener(v -> {
//            Intent to_pawnshop_page = new Intent(Ctx, Pawnshop_Page.class);
//            //   to_pawnshop_page.putExtra("user_id", poplist.getP_id());
//            Ctx.startActivity(to_pawnshop_page);
        });
    }

    public String date_ago(String adate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date past = format.parse(adate);
        Date now = new Date();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
        long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
        long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
        if (seconds < 60) {
            return seconds + " seconds ago";
        } else if (minutes < 60) {
            return minutes + " minutes ago";
        } else if (hours < 24) {
            return hours + " hours ago";
        } else {
            return days + " days ago";
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}