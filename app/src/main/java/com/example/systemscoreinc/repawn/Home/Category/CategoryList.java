package com.example.systemscoreinc.repawn.Home.Category;

public class CategoryList {
    private String cat_name;
    private int cat_id, cat_parent, cat_counts;

    public String getCat_name() {
        return cat_name;
    }

    public int getCat_id() {
        return cat_id;
    }


    public int getCat_counts() {
        return cat_counts;
    }

    public CategoryList(String cat_name, int cat_id, int cat_counts) {
        this.cat_name = cat_name;
        this.cat_id = cat_id;
        this.cat_counts = cat_counts;
    }
}
