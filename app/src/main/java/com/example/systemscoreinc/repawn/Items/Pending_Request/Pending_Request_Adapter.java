package com.example.systemscoreinc.repawn.Items.Pending_Request;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.Items.Pawned;
import com.example.systemscoreinc.repawn.Profile_Related.RePawner_Profile;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Pending_Request_Adapter extends RecyclerView.Adapter<Pending_Request_ViewHolder> {
    List<Pending_Request_List> mDataset;
    Context Ctx;
    IpConfig ip = new IpConfig();
    String request_id, request_details;
    RequestQueue rq;
    String type, pay_type = "";
    String product_id;
    Pending_Request_List list;
    Session session;

    public Pending_Request_Adapter(Context context, List<Pending_Request_List> myDataset) {
        mDataset = myDataset;
        Ctx = context;
        session = new Session(context);
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public Pending_Request_ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View layoutView = LayoutInflater.from(Ctx).inflate(R.layout.pending_request_card, parent, false);
        return new Pending_Request_ViewHolder(layoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull Pending_Request_ViewHolder holder, final int position) {
        rq = Volley.newRequestQueue(Ctx);
        list = mDataset.get(position);
        request_id = list.getRequest_id();
        request_details = list.getRequest_details_id();
        type = list.getRequest_type();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Picasso.get()
                .load(ip.getUrl_image() + list.getUser_image())
                .fit()
                .into(holder.request_image);
        Date date = null;
        try {
            date = simpleDateFormat.parse(list.getRequest_started());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat convetDateFormat = new SimpleDateFormat("MM/dd/yyyy ");
        String sdate = convetDateFormat.format(date);

        holder.request_name.setText(list.getUser_name());
        holder.request_date.setText(sdate);
        if (list.getRequest_type().equals("order")) {
            pay_type = list.getPayment_type();
            holder.payment_type.setText(pay_type);
            if (pay_type.equals("manual")) {
                holder.payment_type.setTextColor(ContextCompat.getColor(Ctx, R.color.colorDGray));
            } else {
                holder.payment_type.setTextColor(Color.BLUE);
            }
        }
        holder.linearLayout.setOnLongClickListener(v -> {
            AlertDialog onconf = new AlertDialog.Builder(Ctx, R.style.RePawnDialog)
                    .setTitle("Accept this Request")
                    .setPositiveButton("YES", (dialog, which) -> request_decision(1, list.getRequest_type()))
                    .setNegativeButton("NO", (dialog, which) -> request_decision(0, list.getRequest_type()))
                    .create();
            onconf.show();
            return true;
        });
        holder.linearLayout.setOnClickListener(view -> {
            Intent to_follow_profile = new Intent(Ctx, RePawner_Profile.class);
            to_follow_profile.putExtra("user_id", Integer.valueOf(list.user_id));
            Ctx.startActivity(to_follow_profile);
        });

    }

    public void request_decision(final int decision, final String type) {

        StringRequest req = new StringRequest(Request.Method.POST, ip.getUrl() + "pending_request.php", response -> {
            MDToast.makeText(Ctx, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
            Ctx.startActivity(new Intent(Ctx, Pawned.class));

        }
                , error -> {

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("request_id", request_id);
                params.put("request_details", request_details);
                params.put("request_decision", "1");
                params.put("decision", String.valueOf(decision));
                params.put("type", type);
                params.put("seller_id", String.valueOf(session.getID()));
                params.put("pay_type", pay_type);
                params.put("user_id", list.user_id);
                params.put("pid", list.getProduct_id());

                return params;
            }
        };
        rq.add(req);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}