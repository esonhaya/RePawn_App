package com.example.systemscoreinc.repawn.Items.Reservation_History;

public class Reservation_History_List {
    String Date_Started,Date_End,Date_Accepted,buyer_name;
    int buyer_id,cancelled;

    public String getDate_Started() {
        return Date_Started;
    }

    public String getDate_End() {
        return Date_End;
    }

    public String getDate_Accepted() {
        return Date_Accepted;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public int getCancelled() {
        return cancelled;
    }

    public Reservation_History_List(String date_Started, String date_End, String date_Accepted, String buyer_name, int buyer_id, int cancelled) {
        Date_Started = date_Started;
        Date_End = date_End;
        Date_Accepted = date_Accepted;
        this.buyer_name = buyer_name;
        this.buyer_id = buyer_id;
        this.cancelled = cancelled;
    }
}
