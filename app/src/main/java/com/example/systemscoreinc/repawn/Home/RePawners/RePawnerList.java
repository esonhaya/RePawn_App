package com.example.systemscoreinc.repawn.Home.RePawners;

import java.io.Serializable;
import java.util.Comparator;

public class RePawnerList implements Serializable, Comparable<RePawnerList> {
    String rname, rpic;
    int rate_count, rate_total, follow_count, user_id;

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

    @Override
    public int compareTo(RePawnerList rePawnerList) {
        return 0;
    }

    public int Follow_Compare(RePawnerList d, RePawnerList d1) {
        return d.follow_count - d1.follow_count;
    }

    public static Comparator<RePawnerList> Name_Compare = (one, other) -> one.rname.compareTo(other.rname);
    public static Comparator<RePawnerList> Follow_Compare = (one, other) -> other.getFollow_count() - one.getFollow_count();
    public static Comparator<RePawnerList> Most_Reviews = (one, other) -> other.getRate_count() - one.getRate_count();
    public static Comparator<RePawnerList> Most_Rated = (one, other) -> other.getRate_total() - one.getRate_total();

}
// return o2.getGrade() - o1.getGrade();

