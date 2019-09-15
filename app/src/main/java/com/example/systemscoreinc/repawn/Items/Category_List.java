package com.example.systemscoreinc.repawn.Items;

public class Category_List {
    int cat_id,parent_id;
            String cat_name;

    public int getCat_id() {
        return cat_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public Category_List(int cat_id, int parent_id, String cat_name) {
        this.cat_id = cat_id;
        this.parent_id = parent_id;
        this.cat_name = cat_name;
    }
}
