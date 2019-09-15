package com.example.systemscoreinc.repawn.Orders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order_Info extends AppCompatActivity {
    TextView date_accepted, buyer_name, payment_type, reservation_start_date, reservation_end_date, date_order_ended,
            paypal_id, pay_amount, Date_paid;
    TextView product_name, item_price, item_desc, item_category, seller_name, pending_message;
    LinearLayout order_layout, payment_layout, order_info_layout, reserve_info_layout;
    Context context;
    Session session;
    RequestQueue rq;
    ImageView product_image;
    Button buyer_confirm, change_payment, cancel_request, pay_pal;
    int rid;
    Order_Adapter oa;
    List<Order_List> ol;
    RecyclerView rv;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl()+"order_info.php";
    Bundle extra;
    String product_id, request_type, request_status, order_details_id, spayment_type = "manual", reserve_id;
    Long price;
    String descrip, category;
    int seller_id,already_paid;
    int choice, selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__info);
        context = this;
        session = new Session(this);
        rid = session.getID();
        rq = Volley.newRequestQueue(context);
        extra = getIntent().getExtras();
        product_id = extra.getString("product_id");
        request_type = extra.getString("request_type");
        request_status = extra.getString("request_status");

//        price = Float.valueOf(pprice.getText().toString());
        declare_this();
        visible_what();
//        get_order_details();
//        get_reserve_details();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Order");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void visible_what() {
        product_info();
        if (request_status.equals("pending")) {
            pending_message.setVisibility(View.VISIBLE);
        } else if (request_type.equals("order")) {
            buyer_confirm.setVisibility(View.VISIBLE);
            get_order_details();
            get_reserve_details();
            if (spayment_type.equals("paypal")) {
                get_payment_details();
                payment_layout.setVisibility(View.VISIBLE);
            }
        } else {
            get_reserve_details();
        }

    }

    private void declare_this() {
        //product_name,item_price,item_desc,item_category,seller_name;
        product_name = this.findViewById(R.id.product_name);
        item_price = this.findViewById(R.id.item_price);
        item_desc = this.findViewById(R.id.item_desc);
        item_category = this.findViewById(R.id.item_category);
        seller_name = this.findViewById(R.id.seller_name);
        order_layout = this.findViewById(R.id.order_layout);
        order_info_layout = this.findViewById(R.id.order_info_layout);
        payment_layout = this.findViewById(R.id.payment_layout);
        reserve_info_layout = this.findViewById(R.id.reserve_info_layout);
        pending_message = this.findViewById(R.id.pending_message);
        //textviews
        date_accepted = this.findViewById(R.id.date_accepted);
        date_order_ended = this.findViewById(R.id.date_order_ended);
        payment_type = this.findViewById(R.id.payment_type);
        reservation_start_date = this.findViewById(R.id.reservation_start_date);
        reservation_end_date = this.findViewById(R.id.reservation_end_date);
        paypal_id = this.findViewById(R.id.paypal_id);
        pay_amount = this.findViewById(R.id.pay_amount);
        Date_paid = this.findViewById(R.id.Date_Paid);
        product_image = this.findViewById(R.id.product_image);
        buyer_confirm = this.findViewById(R.id.buyer_confirm);
        change_payment = this.findViewById(R.id.change_payment);
        cancel_request = this.findViewById(R.id.cancel_request);
        pay_pal=this.findViewById(R.id.pay_pal);
        pay_pal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        buyer_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm_buyer();
            }
        });
        change_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_payment();
            }
        });
        cancel_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (request_type.equals("order")) {
                    cancel_order();
                } else {
                    cancel_reserve();
                }
            }
        });

    }

    public void cancel_order() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.equals("1")) {
                    startActivity(new Intent(Order_Info.this, Orders.class));
                } else {
                    MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("odi", String.valueOf(order_details_id));
                params.put("pid", product_id);
                params.put("cancel_order", "1");
                return params;
            }
        };
        rq.add(request);
    }

    public void cancel_reserve() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (!response.equals("1")) {
                    startActivity(new Intent(Order_Info.this, Orders.class));
                } else {
                    MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("rid", reserve_id);
                params.put("pid", product_id);
                params.put("cancel_reserve", "1");
                return params;
            }
        };
        rq.add(request);
    }

    public void change_payment() {
        selected = 1;
        String[] choices = {"Paypal", "Manual"};
        if (spayment_type.equals("paypal")) {
            selected = 0;
        }
        android.support.v7.app.AlertDialog prom = new android.support.v7.app.AlertDialog.Builder(context, R.style.RePawnDialog)
                .setTitle("Payment Option")
                .setSingleChoiceItems(choices, selected, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                        choice = selectedIndex;
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (choice != selected) {
                            update_ptype();
                        }

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
    }

    public void update_ptype() {
        if (choice == 0) {
            spayment_type = "paypal";
        } else {
            spayment_type = "manual";
        }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("odi", String.valueOf(order_details_id));
                params.put("ptype", spayment_type);
                params.put("update_payment", "1");
                return params;
            }
        };
        rq.add(request);
    }

    public void confirm_buyer() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("odi", String.valueOf(order_details_id));
                params.put("pid", product_id);
                params.put("confirm_transaction_buyer", "1");
                return params;
            }
        };
        rq.add(request);
    }

    public void get_payment_details() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject pawnedobject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray item_array = pawnedobject.getJSONArray("payment_info");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            JSONObject info = item_array.getJSONObject(i);
                            paypal_id.append(info.getString("Paypal_Payment_ID"));
                            pay_amount.append(info.getString("Amount"));
                            buyer_confirm.setVisibility(View.VISIBLE);
                            String da = info.getString("Date_Added");
                            Date_paid.append(date_formatter(da));

                        }

                    } else {
                        payment_layout.setVisibility(View.GONE);
                        buyer_confirm.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("oid", String.valueOf(order_details_id));
                params.put("get_payment_info", "1");
                return params;
            }
        };
        rq.add(request);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void get_reserve_details() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject pawnedobject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray item_array = pawnedobject.getJSONArray("reservation_info");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            JSONObject info = item_array.getJSONObject(i);
                            String da = info.getString("Date_Accepted");
                            reservation_start_date.append(date_formatter(da));
                            reserve_id = info.getString("Reservation_Details_ID");
                            if (info.getString("Date_End") == null) {
                                reservation_end_date.append("not yet");
                            } else {
                                da = info.getString("Date_End");
                                reservation_end_date.append(date_formatter(da));
                            }

                        }

                    } else {
                        reserve_info_layout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pid", product_id);
                params.put("get_reservation_info", "1");
                return params;
            }
        };
        rq.add(request);
    }


    private void product_info() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject pawnedobject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray item_array = pawnedobject.getJSONArray("product_info");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            JSONObject info = item_array.getJSONObject(i);
                            Picasso.get()
                                    .load(ip.getUrl_image() + info.getString("Product_image"))
                                    .fit()
                                    .into(product_image);
                            price = info.getLong("Product_price");
                            product_name.setText(info.getString("Product_name"));
                            item_price.append(String.valueOf(price));
                            descrip = info.getString("Product_description");
                            category = info.getString("Category_name");
                            item_desc.setText(descrip);
                            item_category.setText(category);
                            seller_name.setText(info.getString("seller_name"));


                        }

                    } else {
                        order_info_layout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pid", product_id);
                params.put("product_info", "1");
                return params;
            }
        };
        rq.add(request);

    }


    private void get_order_details() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject pawnedobject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray item_array = pawnedobject.getJSONArray("order_info");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            JSONObject info = item_array.getJSONObject(i);
                            String da = info.getString("Date_Accepted");
                            spayment_type = info.getString("Payment_Type");
                            date_accepted.append(date_formatter(da));
                            order_details_id = info.getString("Order_Details_ID");
                            int buyer_confirm=info.getInt("Buyer_confirmation");
                            if(buyer_confirm==1){
                                pay_pal.setVisibility(View.GONE);
                            }
                            if (info.getString("Date_End") == null) {

                                date_order_ended.append("not yet");
                            } else {
                                da = info.getString("Date_End");
                                date_order_ended.append(date_formatter(da));
                            }

                            payment_type.append(spayment_type);


                        }

                    } else {
                        order_info_layout.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pid", product_id);
                params.put("get_order_info", "1");
                return params;
            }
        };
        rq.add(request);

    }

    public String date_formatter(String sdate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Date date = null;
        try {
            date = simpleDateFormat.parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat convetDateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm ");
        return convetDateFormat.format(date);
    }
}
