package com.example.systemscoreinc.repawn.Home.Pawnshops;

import java.io.Serializable;
import java.util.Comparator;

public class PopularList implements Serializable {
    private String p_name, padd, p_date_joined, p_image;
    private int rate_count, p_id, follow_count;
    private int rate_total;
    private double lat, lng;

    public String getPadd() {
        return padd;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public int getRate_total() {
        return rate_total;
    }

    public String getP_date_joined() {
        return p_date_joined;
    }

    public PopularList(String p_name, String padd, String p_date_joined, String p_image, int rate_count, int p_id, int rate_total,
                       int follow_count, double lat, double lng) {
        this.p_name = p_name;
        this.padd = padd;
        this.p_date_joined = p_date_joined;
        this.p_image = p_image;
        this.rate_count = rate_count;
        this.p_id = p_id;
        this.rate_total = rate_total;
        this.follow_count = follow_count;
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getP_image() {
        return p_image;
    }


    public int getRate_count() {
        return rate_count;
    }

    public int getP_id() {
        return p_id;
    }

    public String getP_name() {

        return p_name;
    }

    public static Comparator<PopularList> Follow_Compare = (one, other) -> other.getFollow_count() - one.getFollow_count();
    public static Comparator<PopularList> Most_Reviews = (one, other) -> other.getRate_count() - one.getRate_count();
    public static Comparator<PopularList> Most_Rated = (one, other) -> other.getRate_total() - one.getRate_total();


}
