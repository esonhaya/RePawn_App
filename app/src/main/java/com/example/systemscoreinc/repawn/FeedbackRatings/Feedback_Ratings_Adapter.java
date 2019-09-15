package com.example.systemscoreinc.repawn.FeedbackRatings;

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
import java.util.Date;
import java.util.List;

public class Feedback_Ratings_Adapter extends RecyclerView.Adapter<Feedback_Ratings_ViewHolder> {
    private List<Feedback_Ratings_List> mDataset;
    private Context Ctx;
    private Date date;
    IpConfig ip=new IpConfig();

    public Feedback_Ratings_Adapter(Context context, List<Feedback_Ratings_List> myDataset) {
        mDataset = myDataset;
        Ctx = context;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Feedback_Ratings_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.feedback_ratings, parent, false);
        return new Feedback_Ratings_ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull Feedback_Ratings_ViewHolder holder, final int position) {
        final Feedback_Ratings_List frlist = mDataset.get(position);
        Picasso.get()
                .load(ip.getUrl_image() + frlist.getReview_image())
                .fit()
                .into(holder.review_image);
        holder.review_name.setText(frlist.getReview_name());
        holder.review_bar.setRating(Float.valueOf(frlist.getReview_bar()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date = null;
        try {
            date = simpleDateFormat.parse(frlist.getReview_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat convetDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        String sdate = convetDateFormat.format(date);
        holder.review_date.setText(sdate);
        holder.review_content.setText(frlist.getReview_content());


    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}