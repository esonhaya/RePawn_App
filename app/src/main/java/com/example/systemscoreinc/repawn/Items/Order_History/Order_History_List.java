package com.example.systemscoreinc.repawn.Items.Order_History;

public class Order_History_List {
    //    SELECT `Order_Details_ID`, `Date_Sent`, `accepted`, `cancelled`, `Payment_Type`, `Date_End`, `Date_Accepted`,
//            `Payment_ID`, `Seller_confirmation`, `Buyer_confirmation` FROM `order_details` WHERE 1
    int oid,buyer_id, cancelled, pay_id, seller_confirmation, buyer_confirmation;
    String date_sent, pay_type, date_end, date_accepted,buyer_name;


    public String getBuyer_name() {
        return buyer_name;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public Order_History_List(int oid, int buyer_id, int cancelled, int pay_id, int seller_confirmation, int buyer_confirmation,
                              String date_sent, String pay_type, String date_end, String date_accepted, String buyer_name) {
        this.oid = oid;
        this.buyer_id=buyer_id;
        this.cancelled = cancelled;
        this.pay_id = pay_id;
        this.seller_confirmation = seller_confirmation;
        this.buyer_confirmation = buyer_confirmation;
        this.date_sent = date_sent;
        this.pay_type = pay_type;
        this.date_end = date_end;
        this.date_accepted = date_accepted;
        this.buyer_name=buyer_name;
    }
    public int getOid() {
        return oid;
    }
    public int getCancelled() {
        return cancelled;
    }

    public int getPay_id() {
        return pay_id;
    }

    public int getSeller_confirmation() {
        return seller_confirmation;
    }

    public int getBuyer_confirmation() {
        return buyer_confirmation;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public String getPay_type() {
        return pay_type;
    }

    public String getDate_end() {
        return date_end;
    }

    public String getDate_accepted() {
        return date_accepted;
    }
}
