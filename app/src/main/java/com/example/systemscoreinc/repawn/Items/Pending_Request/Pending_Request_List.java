package com.example.systemscoreinc.repawn.Items.Pending_Request;

public class Pending_Request_List {
    String user_name,request_type,request_status,request_started,user_image,request_id,request_details_id,user_id,payment_type,
    product_id;

    public String getUser_name() {
        return user_name;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getRequest_started() {
        return request_started;
    }

    public String getRequest_type() {
        return request_type;
    }


    public String getRequest_details_id() {
        return request_details_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public Pending_Request_List(String user_name, String request_type, String request_status, String request_started,
                                String user_image, String request_id, String request_details_id, String user_id,
                                String payment_type,String product_id) {
        this.user_name = user_name;
        this.request_type = request_type;
        this.request_status = request_status;
        this.request_started = request_started;
        this.user_image = user_image;
        this.request_id = request_id;
        this.request_details_id = request_details_id;
        this.user_id = user_id;
        this.payment_type = payment_type;
        this.product_id=product_id;
    }

    public String getUser_image() {
        return user_image;
    }

    public String getRequest_status() {
        return request_status;
    }

}


