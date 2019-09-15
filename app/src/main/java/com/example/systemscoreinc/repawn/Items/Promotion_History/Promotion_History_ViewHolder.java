package com.example.systemscoreinc.repawn.Items.Promotion_History;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.R;

public class Promotion_History_ViewHolder extends RecyclerView.ViewHolder {
    public TextView prom_start, prom_end, prom_pay_id, prom_amount;
    public LinearLayout history_layout;

    public Promotion_History_ViewHolder(@NonNull View itemView) {
        super(itemView);
        prom_start = itemView.findViewById(R.id.prom_start);
        prom_end = itemView.findViewById(R.id.prom_end);
        prom_pay_id = itemView.findViewById(R.id.prom_pay_id);
        prom_amount = itemView.findViewById(R.id.prom_amount);
        history_layout = itemView.findViewById(R.id.history_layout);


    }
}
