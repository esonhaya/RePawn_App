package com.example.systemscoreinc.repawn.Profile_Related.Follower;

public class Follower_List {
    String seller_name,user_image,date;
    int user_id;

    public String getSeller_name() {
        return seller_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public String getDate() {
        return date;
    }

    public int getUser_id() {
        return user_id;
    }

    public Follower_List(String seller_name, String user_image, String date, int user_id) {
        this.seller_name = seller_name;
        this.user_image = user_image;
        this.date = date;
        this.user_id = user_id;
    }
}
