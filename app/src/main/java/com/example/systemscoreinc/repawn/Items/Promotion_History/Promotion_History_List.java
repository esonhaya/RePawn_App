package com.example.systemscoreinc.repawn.Items.Promotion_History;

import java.io.Serializable;

public class Promotion_History_List implements Serializable {
    private String date_start, date_end, pay_id, amount;

    public String getDate_start() {
        return date_start;
    }

    public String getDate_end() {
        return date_end;
    }

    public String getPay_id() {
        return pay_id;
    }

    public String getAmount() {
        return amount;
    }

    public Promotion_History_List(String date_start, String date_end, String pay_id, String amount) {
        this.date_start = date_start;
        this.date_end = date_end;
        this.pay_id = pay_id;
        this.amount = amount;
    }
}


