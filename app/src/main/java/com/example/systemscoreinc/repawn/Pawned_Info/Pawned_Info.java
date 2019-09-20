package com.example.systemscoreinc.repawn.Pawned_Info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.Home.Items.Home_Items_Adapter;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.glide.slider.library.SliderLayout;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pawned_Info extends AppCompatActivity {
    TextView samp;
    private int choice = 0, seller_id, item_id;
    private Toolbar toolbar;
    private Button btn_order, btn_reserve;
    private TextView item_name, item_desc, seller_name, item_price, item_category, seller_products, cat_products;
    private LinearLayout seller_products_layout, cat_products_layout;
    ImageView product_image, see_all_cat_products, see_all_products;
    Bundle extras;
    Session session;
    Context context;
    String image;
    private SliderLayout mDemoSlider;
    boolean moreSwitcher = false;
    List<ItemList> Sitemlist = new ArrayList<>();
    List<ItemList> Citemlist = new ArrayList<>();
    Home_Items_Adapter Sitems_adapter, Citems_adapter;
    RecyclerView SProduct_view, CProduct_view;
    String item_type, Sseller_name, Scat_name;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl() + "pawned_info.php";
    RequestQueue rq;
    int reservable, image_id;
    String payment_type;
    int type, cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pawned_info);
        extras = getIntent().getExtras();
        context = this;
        session = new Session(context);
        declarestuff();
        assignstuff();
        check_requests();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        item_desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!moreSwitcher) {
                    item_desc.setLines(1);
                    item_desc.setMaxLines(2);
                    moreSwitcher = true;
                } else {
                    item_desc.setLines(1);
                    item_desc.setMaxLines(10);
                    moreSwitcher = false;
                }
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                function_item();
            }
        });
        btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                function_item();

            }
        });

    }

    private void declarestuff() {
        item_name = this.findViewById(R.id.product_name);
        item_desc = this.findViewById(R.id.item_desc);
        seller_name = this.findViewById(R.id.seller_name);
        item_price = this.findViewById(R.id.item_price);
        item_category = this.findViewById(R.id.item_category);
        btn_order = this.findViewById(R.id.btn_order);
        btn_reserve = this.findViewById(R.id.btn_reserve);
        mDemoSlider = findViewById(R.id.slider);
        seller_products = this.findViewById(R.id.seller_products);
        SProduct_view = this.findViewById(R.id.product_recycle);
        CProduct_view = this.findViewById(R.id.cat_product_recycle);
        seller_products_layout = this.findViewById(R.id.seller_products_layout);
        cat_products_layout = this.findViewById(R.id.cat_products_layout);
        cat_products = this.findViewById(R.id.cat_products);
        product_image = this.findViewById(R.id.product_image);
        see_all_cat_products = this.findViewById(R.id.see_all_cat_products);
        see_all_products = this.findViewById(R.id.see_all_products);

        rq = Volley.newRequestQueue(context);
        cat_products.setText("Other " + Scat_name + " Products");
    }

    private void assignstuff() {
        extras = getIntent().getExtras();
        Intent i = getIntent();
        ItemList item = (ItemList) i.getSerializableExtra("item");
        item_name.setText(item.getItem_name());
        item_price.append("" + item.getPrice());
        item_desc.setText(item.getItem_desc());
        seller_name.setText(item.getSeller_name());
        item_category.setText(item.getCat_name());
        seller_products.append(item.getSeller_name());
        seller_id = item.getSeller_id();
        item_type = item.getItem_type();
        item_id = item.getItem_id();
        Picasso.get()
                .load(ip.getUrl_image() + item.getItem_image())
                .fit()
                .into(product_image);
        if (seller_id == session.getID()) {
            btn_order.setVisibility(View.GONE);
            btn_reserve.setVisibility(View.GONE);
        } else {
            btn_order.setVisibility(View.VISIBLE);
            if (reservable == 1) {
                btn_reserve.setVisibility(View.VISIBLE);
            }
        }
        recycleview_related();
    }

    public void function_item() {
        if (type == 1) {
            showAlert();

        } else {
            StringRequest req = new StringRequest(Request.Method.POST, url, response ->
                    MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show(), error -> {

                    }) {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("type", String.valueOf(type));
                    params.put("reserder", "1");
                    params.put("Product_ID", String.valueOf(item_id));
                    params.put("User_ID", String.valueOf(session.getID()));

                    return params;
                }
            };
            rq.add(req);
        }
    }

    public void check_requests() {
        StringRequest req = new StringRequest(Request.Method.POST, url, response -> {
            if (response.equals("1")) {
                btn_order.setVisibility(View.GONE);
            } else if (response.equals("12")) {
                btn_order.setVisibility(View.GONE);
                btn_reserve.setVisibility(View.GONE);
            } else if (response.equals("2")) {
                btn_reserve.setVisibility(View.GONE);

            }
        }, error -> {

        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("check_requests", "1");
                params.put("Product_ID", String.valueOf(item_id));
                params.put("User_ID", String.valueOf(session.getID()));

                return params;
            }
        };
        rq.add(req);
    }

    public void showAlert() {
        String[] choices = {"paypal", "manual"};
        int item_selected = 0;
        AlertDialog purdia = new AlertDialog.Builder(context, R.style.RePawnDialog)
                .setTitle("How do you like to make the purchase?")
                .setSingleChoiceItems(choices, item_selected, (dialogInterface, selectedIndex) ->
                        choice = selectedIndex).setPositiveButton("OK", (dialog, which) -> {
                            if (choice == 1) {
                                payment_type = "paypal";
                            } else {
                                payment_type = "manual";
                            }
                            add_request();

                        }).setNegativeButton("Cancel", (dialogInterface, i) -> {

                        }).create();
        purdia.show();
    }

    private void add_request() {
        StringRequest req = new StringRequest(Request.Method.POST, url, response ->
                MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show(), error -> {

                }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("type", String.valueOf(type));
                params.put("reserder", "1");
                params.put("Product_ID", String.valueOf(item_id));
                params.put("User_ID", String.valueOf(session.getID()));
                params.put("ptype", payment_type);

                return params;
            }
        };
        rq.add(req);
    }

    private void recycleview_related() {
        Sitems_adapter = new Home_Items_Adapter(context, Sitemlist);
        SProduct_view.setHasFixedSize(true);
        LinearLayoutManager item_layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        SProduct_view.setLayoutManager(item_layout);
        SProduct_view.setAdapter(Sitems_adapter);
        LinearLayoutManager Citem_layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        Citems_adapter = new Home_Items_Adapter(context, Citemlist);
        CProduct_view.setHasFixedSize(true);
        CProduct_view.setLayoutManager(Citem_layout);
        CProduct_view.setAdapter(Citems_adapter);
        populateviews();

    }

    private void populateviews() {
        Log.e("seller_id", String.valueOf(seller_id));
        StringRequest get_seller_products = new StringRequest(Request.Method.POST, url, response -> {
            try {

                JSONObject result_object = new JSONObject(response);

                //extracting json array from response string
                JSONArray items_array = result_object.getJSONArray("seller_items");
                Log.e("jsondata", String.valueOf(items_array));
                if (items_array.length() > 0) {
                    for (int i = 0; i < items_array.length(); i++) {

                        JSONObject items_object = items_array.getJSONObject(i);
                        if (item_type.equals("pawned")) {
                            ItemList item = new ItemList(items_object.getString("Product_name"),
                                    items_object.getString("Date_Added"), items_object.getString("seller_name")
                                    , items_object.getString("Category_name"), items_object.getString("Product_Type"),
                                    items_object.getString("Product_image"), items_object.getString("Product_description")
                                    , items_object.getInt("Promoted"), items_object.getInt("Reserved"), items_object.getInt("Ordered"), items_object.getInt("Product_ID"),
                                    items_object.getInt("User_ID"), items_object.getInt("Reservable"), items_object.getInt("Image_ID"),
                                    items_object.getLong("Product_price"));
                            Sitemlist.add(item);
                            if (i > 5) {
                                see_all_products.setVisibility(View.VISIBLE);
                            }
                        }


                    }
                    Sitems_adapter.notifyDataSetChanged();
                } else {
                    seller_products_layout.setVisibility(View.GONE);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("seller_products", "1");
                params.put("except", String.valueOf(item_id));
                params.put("item_type", item_type);
                params.put("user_id", String.valueOf(seller_id));
                return params;
            }
        };
        rq.add(get_seller_products);
        StringRequest get_cat_products = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject result_object = new JSONObject(response);

                    //extracting json array from response string
                    JSONArray items_array = result_object.getJSONArray("cat_items");
                    Log.e("jsondata", String.valueOf(items_array));
                    if (items_array.length() > 0) {
                        for (int i = 0; i < items_array.length(); i++) {

                            JSONObject items_object = items_array.getJSONObject(i);
                            ItemList item = new ItemList(items_object.getString("Product_name"),
                                    items_object.getString("Date_Added"), items_object.getString("seller_name")
                                    , items_object.getString("Category_name"), items_object.getString("Product_Type"),
                                    items_object.getString("Product_image"), items_object.getString("Product_description")
                                    , items_object.getInt("Promoted"), items_object.getInt("Reserved"), items_object.getInt("Ordered"), items_object.getInt("Product_ID"),
                                    items_object.getInt("User_ID"), items_object.getInt("Reservable"), items_object.getInt("Image_ID"),
                                    items_object.getLong("Product_price"));
                            Citemlist.add(item);
                            if (i > 5) {
                                see_all_cat_products.setVisibility(View.VISIBLE);
                            }
                        }
                        Citems_adapter.notifyDataSetChanged();
                    } else {
                        cat_products_layout.setVisibility(View.GONE);
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
                params.put("cat_products", "1");
                params.put("except", String.valueOf(item_id));
                params.put("cat_name", Scat_name);
                return params;
            }
        };
        rq.add(get_cat_products);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.open_gallery) {
            //   Intent to_gall=new Intent(this,)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_gallery, menu);
        return super.onCreateOptionsMenu(menu);
    }

}