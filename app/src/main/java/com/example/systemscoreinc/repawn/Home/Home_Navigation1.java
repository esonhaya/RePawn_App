package com.example.systemscoreinc.repawn.Home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.request.RequestOptions;
import com.example.systemscoreinc.repawn.Home.Category.CategoryList;
import com.example.systemscoreinc.repawn.Home.Category.Home_Cat_Adapter;
import com.example.systemscoreinc.repawn.Home.Items.Home_Items_Adapter;
import com.example.systemscoreinc.repawn.Home.Pawnshops.All_Pawnshops.All_Pawnshops;
import com.example.systemscoreinc.repawn.Home.Pawnshops.Home_Pawnshops_Adapter;
import com.example.systemscoreinc.repawn.Home.Pawnshops.PopularList;
import com.example.systemscoreinc.repawn.Home.RePawners.All_RePawners.All_RePawners;
import com.example.systemscoreinc.repawn.Home.RePawners.RePawnerList;
import com.example.systemscoreinc.repawn.Home.RePawners.RePawner_Adapter;
import com.example.systemscoreinc.repawn.Home.Search.Search;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.Items.Pawned;
import com.example.systemscoreinc.repawn.Orders.Orders;
import com.example.systemscoreinc.repawn.Profile_Related.RePawner_Profile;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Home_Navigation1 extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {
    IpConfig ip = new IpConfig();
    String url = ip.getUrl() + "home_navigation1.php";
    TextView tvEmail, pawnshop_all, remats_all, rep_all;
    EditText search_input;
    SearchView search;
    private DrawerLayout mDrawerLayout;
    private SliderLayout mDemoSlider;
    List<PopularList> pawnshop_list = new ArrayList<>();
    List<ItemList> item_list = new ArrayList<>();
    List<CategoryList> cat_list = new ArrayList<>();
    List<RePawnerList> rep_list = new ArrayList<>();
    RecyclerView pawnshop_view, item_view, cat_view, rep_view;
    NavigationView navigationView;
    Context context;
    Session session;
    Home_Pawnshops_Adapter pawnshops_adapter;
    Home_Items_Adapter items_adapter;
    Home_Cat_Adapter cats_adapter;
    RePawner_Adapter rep_adapter;
    RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        session = new Session(this);
        Toolbar appbar = findViewById(R.id.toolbar);
        setSupportActionBar(appbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.nav);
        ab.setDisplayHomeAsUpEnabled(true);
        mDemoSlider = findViewById(R.id.slider);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        context = this;
        slidershow();
        declarestuffs();
        navigationView = findViewById(R.id.nav_view);
        all_onclick();
        tvEmail = navigationView.getHeaderView(0).findViewById(R.id.email_navigation);
        tvEmail.setText(session.getEmail());
        MenuItem notif = navigationView.getMenu().
                findItem(R.id.nav_notif);
        TextView notifView = (TextView) notif.getActionView();
        initializeCountDrawer(notifView);
        location_related();
    }

    public void all_onclick() {
        pawnshop_all.setOnClickListener(v -> {
                Intent to_pawnshops = new Intent(Home_Navigation1.this, All_Pawnshops.class);
                to_pawnshops.putExtra("pawnshops", (ArrayList<PopularList>) pawnshop_list);
                startActivity(to_pawnshops);

        });
        remats_all.setOnClickListener(v -> {
            // startActivity(new Intent(Home_Navigation1.this, See_All_Rematados.class));
        });
        rep_all.setOnClickListener(v -> {
            Intent to_repawners = new Intent(Home_Navigation1.this, All_RePawners.class);
            to_repawners.putExtra("repawners", (ArrayList<RePawnerList>) rep_list);
            startActivity(to_repawners);
        });
        search_input.setOnClickListener(view -> {
            Intent to_search = new Intent(Home_Navigation1.this, Search.class);
            Log.e("items", String.valueOf(rep_list));
            Log.e("pawnshops", String.valueOf(pawnshop_list));
            to_search.putExtra("products", (ArrayList<ItemList>) item_list);
            to_search.putExtra("repawners", (ArrayList<RePawnerList>) rep_list);
            to_search.putExtra("pawnshops", (ArrayList<PopularList>) pawnshop_list);
            startActivity(to_search);
        });


        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    // set item as selected to persist highlight
                    int id = menuItem.getItemId();
                    menuItem.setChecked(true);
                    // close drawer when item is tapped
                    mDrawerLayout.closeDrawers();
                    switch (id) {
                        case R.id.nav_profile:
                            Intent to_prof = new Intent(Home_Navigation1.this, RePawner_Profile.class);
                            to_prof.putExtra("user_id", session.getID());
                            startActivity(to_prof);
                            break;
                        case R.id.nav_items:
//                                Intent to_pawned=new Intent(Home_Navigation1.this, Pawned.class);
//                                startActivity(to_pawned);
                            startActivity(new Intent(Home_Navigation1.this, Pawned.class));
                            break;
                        case R.id.nav_orders:
                            startActivity(new Intent(Home_Navigation1.this, Orders.class));
                            break;

                        case R.id.nav_logout:
                            session.logoutUser();
                            break;
                    }
                    return true;
                });
    }


    private void declarestuffs() {
        pawnshop_all = this.findViewById(R.id.pawnshop_all);
        remats_all = this.findViewById(R.id.remat_all);
        rep_all = this.findViewById(R.id.rep_all);
        pawnshop_view = findViewById(R.id.pawnshops_view);
        item_view = findViewById(R.id.promoted_products_view);
        cat_view = findViewById(R.id.rec_cat);
        rep_view = findViewById(R.id.rep_view);
        search_input = findViewById(R.id.search_edit);
        pawnshop_view.setHasFixedSize(true);
        item_view.setHasFixedSize(true);
        cat_view.setHasFixedSize(true);
        rep_view.setHasFixedSize(true);
        LinearLayoutManager Pawnshops_layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        pawnshop_view.setLayoutManager(Pawnshops_layout);
        cat_view.setLayoutManager(new GridLayoutManager(context, 2));
        item_view.setLayoutManager(new GridLayoutManager(context, 2));
        LinearLayoutManager rep_layout = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        rep_view.setLayoutManager(rep_layout);
        pawnshops_adapter = new Home_Pawnshops_Adapter(Home_Navigation1.this, pawnshop_list);
        items_adapter = new Home_Items_Adapter(Home_Navigation1.this, item_list);
        cats_adapter = new Home_Cat_Adapter(Home_Navigation1.this, cat_list);
        rep_adapter = new RePawner_Adapter(Home_Navigation1.this, rep_list);
        pawnshop_view.setAdapter(pawnshops_adapter);
        item_view.setAdapter(items_adapter);
        cat_view.setAdapter(cats_adapter);
        rep_view.setAdapter(rep_adapter);
        rq = Volley.newRequestQueue(context);
        addPawnshopRecycleView();
        addPromotedItemsRecycleView();
        addMainCatRV();
        addRepawners();
    }

    private void addRepawners() {
        StringRequest preq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray cats = jsonObject.getJSONArray("repawners");
                    Log.e("jsondata", String.valueOf(cats));
                    if (cats.length() > 0) {
                        for (int i = 0; i < cats.length(); i++) {
                            JSONObject cc = cats.getJSONObject(i);

                            RePawnerList rp = new RePawnerList(cc.getString("RePawner_name"), cc.getString("user_image"),
                                    cc.getInt("User_ID"), cc.getInt("ratings_count"), cc.getInt("ratings_total"),
                                    cc.getInt("follow_count"));

                            rep_list.add(rp);
                        }
                        rep_adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("all_repawners", "1");
                return params;
            }
        };
        rq.add(preq);
    }

    private void addMainCatRV() {

        StringRequest preq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray cats = jsonObject.getJSONArray("category");
                    Log.e("jsondata", String.valueOf(cats));
                    if (cats.length() > 0) {
                        for (int i = 0; i < cats.length(); i++) {
                            JSONObject cc = cats.getJSONObject(i);

                            CategoryList clist = new CategoryList(cc.getString("Category_name"),
                                    cc.getInt("Category_ID"), cc.getInt("cat_count"));

                            cat_list.add(clist);
                        }
                        cats_adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("main_cat", "1");
                return params;
            }
        };
        rq.add(preq);
    }

    private void addPromotedItemsRecycleView() {

        StringRequest preq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray items_array = object.getJSONArray("all_items");
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
                            item_list.add(item);
                        }
                        items_adapter.notifyDataSetChanged();
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
                params.put("get_all_items", "1");

                return params;
            }
        };
        rq.add(preq);
    }


    private void addPawnshopRecycleView() {
        StringRequest preq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray items_array = object.getJSONArray("pawnshops");
                    if (items_array.length() > 0) {
                        for (int i = 0; i < items_array.length(); i++) {
                            JSONObject item_object = items_array.getJSONObject(i);
                            PopularList pawnshop = new PopularList(item_object.getString("Pawnshop_name"),
                                    item_object.getString("Pawnshop_address"), item_object.getString("activation_date")
                                    , item_object.getString("user_image"), item_object.getInt("ratings_count"),
                                    item_object.getInt("User_ID"), item_object.getInt("ratings_total"),
                                    item_object.getInt("follow_count"), item_object.getDouble("latitude"),
                                    item_object.getDouble("longitude"));
                            pawnshop_list.add(pawnshop);
                        }
                        pawnshops_adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            public final void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("get_all_pawnshops", "1");
                return params;
            }
        };
        rq.add(preq);

    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void slidershow() {
        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();
        listUrl.add("https://www.revive-adserver.com/media/GitHub.jpg");
        listName.add("JPG - Github");

        listUrl.add("https://tctechcrunch2011.files.wordpress.com/2017/02/android-studio-logo.png");
        listName.add("PNG - Android Studio");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", listName.get(i));
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    public void location_related() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void initializeCountDrawer(TextView notif) {

        //Gravity property aligns the text
        notif.setGravity(Gravity.CENTER_VERTICAL);
        notif.setTypeface(null, Typeface.BOLD);
        notif.setTextColor(Color.RED);
        notif.setText("99+");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        final android.support.v7.app.AlertDialog exitdia = new android.support.v7.app.AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle("Are you sure you want to exit?")

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //   Log.d("Rematado_Info", "Sending atomic bombs to Jupiter");
                        finish();
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        exitdia.show();
        Button p_button = exitdia.getButton(DialogInterface.BUTTON_POSITIVE);
        p_button.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
        Button n_button = exitdia.getButton(DialogInterface.BUTTON_NEGATIVE);
        n_button.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));

    }
}