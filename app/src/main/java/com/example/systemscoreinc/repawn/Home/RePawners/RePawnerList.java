package com.example.systemscoreinc.repawn.Home.RePawners;

import java.io.Serializable;

public class RePawnerList implements Serializable
{
    String rname,rpic;
    int rate_count,rate_total,follow_count,user_id;

    public String getRname() {
        return rname;
    }

    public int getRate_count() {
        return rate_count;
    }

    public int getRate_total() {
        return rate_total;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public String getRpic() {
        return rpic;
    }

    public int getUser_id() {
        return user_id;
    }

    public RePawnerList(String rname, String rpic, int user_id, int rate_count, int rate_total, int follow_count) {
        this.rname = rname;
        this.rpic = rpic;
        this.user_id = user_id;
        this.rate_count = rate_count;
        this.rate_total = rate_total;
        this.follow_count = follow_count;
    }
}
