package com.example.systemscoreinc.repawn.Home.Notifications;

public class Notifications_List {
    int notif_id, link_id, type, checked;
    String date_posted, notif_image, message;

    public int getLink_id() {
        return link_id;
    }

    public String getMessage() {
        return message;
    }

    public String getNotif_image() {
        return notif_image;
    }

    public int getNotif_id() {
        return notif_id;
    }

    public Notifications_List(int notif_id, int link_id, int type, int checked, String date_posted, String notif_image, String message) {
        this.notif_id = notif_id;
        this.link_id = link_id;
        this.type = type;
        this.checked = checked;
        this.date_posted = date_posted;
        this.notif_image = notif_image;
        this.message = message;
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
    public String toString() {
        return "Notification: " + message + ", " + date_posted;
    }

}
