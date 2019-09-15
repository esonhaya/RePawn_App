package com.example.systemscoreinc.repawn.Items;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
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
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pawned extends AppCompatActivity {
    Session session;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl() + "pawned.php";
    RequestQueue rq;
    RecyclerView pview;
    Pawned_Items_Adapter pawned_adapter;
    List<ItemList> myitems = new ArrayList<>();

    String user_id;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pawned2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        session = new Session(this);
        user_id = String.valueOf(session.getID());
        declarestuffs();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void declarestuffs() {
        pview = findViewById(R.id.pawned_view);
        pview.setHasFixedSize(true);
        GridLayoutManager pawnedlayout = new GridLayoutManager(this, 1);
        pview.setLayoutManager(pawnedlayout);
        pawned_adapter = new Pawned_Items_Adapter(Pawned.this, myitems);
        pview.setAdapter(pawned_adapter);
        rq = Volley.newRequestQueue(context);
        addPawnedRV();
    }


    private void addPawnedRV() {
        StringRequest preq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject pawnedobject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray item_array = pawnedobject.getJSONArray("seller_items");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            JSONObject items_object = item_array.getJSONObject(i);
                            ItemList item = new ItemList(items_object.getString("Product_name"),
                                    items_object.getString("Date_Added"), items_object.getString("seller_name")
                                    , items_object.getString("Category_name"), items_object.getString("Product_Type"),
                                    items_object.getString("Product_image"), items_object.getString("Product_description")
                                    , items_object.getInt("Promoted"), items_object.getInt("Reserved"), items_object.getInt("Ordered"), items_object.getInt("Product_ID"),
                                    session.getID(), items_object.getInt("Reservable"), items_object.getInt("Image_ID"),
                                    items_object.getLong("Product_price"));
                            myitems.add(item);

                        }
                        pawned_adapter.notifyDataSetChanged();
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

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("my_products", "1");
                params.put("item_type", "pawned");
                params.put("user_id", String.valueOf(session.getID()));
                return params;
            }
        };
        rq.add(preq);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(Pawned.this, Home_Navigation1.class));
        }
        if (item.getItemId() == R.id.add_item) {
            Intent to_prof = new Intent(Pawned.this, AddPawned.class);
            startActivity(to_prof);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.with_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
