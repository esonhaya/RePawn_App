package com.example.systemscoreinc.repawn.Orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.Home.Home_Navigation1;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Orders extends AppCompatActivity {
    Context context;
    Session session;
    RequestQueue rq;
    int rid;
    Order_Adapter oa;
    List<Order_List> ol = new ArrayList<>();
    RecyclerView rv;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl() + "orders.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_orders);
        context = this;
        session = new Session(this);
        rid = session.getID();
        rq = Volley.newRequestQueue(context);
//        price = Float.valueOf(pprice.getText().toString());
        review();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Orders");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        super.onCreate(savedInstanceState);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(Orders.this, Home_Navigation1.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void review() {
        rv = this.findViewById(R.id.rec_orders);
        oa = new Order_Adapter(context, ol);
        rv.setHasFixedSize(true);
        LinearLayoutManager reco = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dec = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        rv.setLayoutManager(reco);
        rv.setAdapter(oa);
        rv.addItemDecoration(dec);
        get_all_requests();
    }

    public void get_all_requests() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject pawnedobject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray item_array = pawnedobject.getJSONArray("orders");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            JSONObject item = item_array.getJSONObject(i);
                            Order_List list = new Order_List(item.getString("product_image"), item.getString("Product_ID"),
                                    item.getString("Product_price"), item.getString("request_type"), item.getString("Product_name")
                                    , item.getString("Date_Sent"));
                            ol.add(list);
                        }
                        oa.notifyDataSetChanged();
                    } else {
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
                params.put("all_requests", "1");
                params.put("user_id", String.valueOf(session.getID()));

                return params;
            }
        };
        rq.add(request);
    }
}
