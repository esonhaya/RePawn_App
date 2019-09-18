package com.example.systemscoreinc.repawn.Home.Notifications;

public class Notifications_List {
    int link_id, type, checked;
    String date_posted,notif_image;

    public int getLink_id() {
        return link_id;
    }

    public String getNotif_image() {
        return notif_image;
    }

    public Notifications_List(int link_id, int type, int checked, String date_posted, String notif_image) {
        this.link_id = link_id;
        this.type = type;
        this.checked = checked;
        this.date_posted = date_posted;
        this.notif_image = notif_image;
    }

    public int getType() {
        return type;
    }

    public String getDate_posted() {
        return date_posted;
    }

    public int getChecked() {
        return checked;
    }

}
