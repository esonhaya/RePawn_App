package com.example.systemscoreinc.repawn;

import java.io.Serializable;

public class ItemList implements Serializable {

    private String item_name,date_added,seller_name, cat_name,item_type, item_image,item_desc;
    private int reserved,ordered,promoted;

    public int getPromoted() {
        return promoted;
    }

    public int getReserved() {
        return reserved;
    }

    public int getOrdered() {
        return ordered;
    }

    public int getReservable() {
        return reservable;
    }

    public int getImage_id() {
        return image_id;
    }

    public ItemList(String item_name, String date_added, String seller_name, String cat_name, String item_type, String item_image,
                    String item_desc,int promoted,int reserved, int ordered, int item_id, int seller_id, int reservable, int image_id,
                    long price) {
        this.item_name = item_name;
        this.date_added = date_added;
        this.seller_name = seller_name;
        this.cat_name = cat_name;
        this.item_type = item_type;
        this.item_image = item_image;
        this.item_desc = item_desc;
        this.reserved = reserved;
        this.promoted=promoted;
        this.ordered = ordered;
        this.item_id = item_id;
        this.seller_id = seller_id;
        this.reservable = reservable;
        this.image_id = image_id;
        this.price = price;
    }
    private int item_id, seller_id,reservable,image_id;
    private long price;

    public String getItem_name() {
        return item_name;
    }

    public String getDate_added() {
        return date_added;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public String getCat_name() {
        return cat_name;
    }

    public int getItem_id() {
        return item_id;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public String getItem_image() {
        return item_image;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public String getItem_type() {
        return item_type;
    }

    public long getPrice() {
        return price;
    }


}
