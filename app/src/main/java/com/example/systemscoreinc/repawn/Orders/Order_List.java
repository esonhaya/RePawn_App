package com.example.systemscoreinc.repawn.Orders;

import java.io.Serializable;

public class Order_List implements Serializable {

    String image_url,payment_type,seller_name,seller_id,product_id,product_price,request_type,status,product_name,date_sent;
    public Order_List(String image_url, String product_id, String product_price, String request_type,
                      String product_name,String date_sent) {
        this.image_url = image_url;
        this.product_id = product_id;
        this.product_price = product_price;
        this.request_type = request_type;
        this.product_name = product_name;
        this.date_sent=date_sent;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public String getProduct_name() {
        return product_name;
    }
    public String getImage_url() {
        return image_url;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_price() {
        return product_price;
    }

    public String getRequest_type() {
        return request_type;
    }

    public String getStatus() {
        return status;
    }


}
