package com.example.systemscoreinc.repawn.Profile_Related;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.FeedbackRatings.Feedback_Ratings;
import com.example.systemscoreinc.repawn.FeedbackRatings.Feedback_Ratings_Adapter;
import com.example.systemscoreinc.repawn.FeedbackRatings.Feedback_Ratings_List;
import com.example.systemscoreinc.repawn.Home.Items.Home_Items_Adapter;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.Profile_Related.Follower.Follower_Adapter;
import com.example.systemscoreinc.repawn.Profile_Related.Follower.Follower_List;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RePawner_Profile extends AppCompatActivity {
    TextView seller_rating, seller_review_count, seller_followers, seller_sold, no_followers_prompt, no_feedback_prompt,
            seller_name, seller_email, seller_con, no_items_prompt, see_others, feedback_content;
    Toolbar toolbar;
    LinearLayout products_layout, feedback_layout, followers_layout, feedbacks_layout;
    ImageView seller_image, see_all_products, see_all_followers, profile_image;
    RecyclerView Items_View, FRatings_View, Follower_View;
    EditText feede;
    TextInputLayout feedi;
    Button btn_submit, btn_cancel, btn_follow, btn_unfollow, btn_delete;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl()+"seller_page.php", mname, user_image, fname, lname, contact, email, bdate, rep_image, followed_id;
    RatingBar review_bar;
    Bundle extra;
    Session session;
    Context context;
    int user_id, rating, update_feedback, if_follow;
    RequestQueue rq;
    Home_Items_Adapter items_adapter;
    Feedback_Ratings_Adapter fratings_adapter;
    Follower_Adapter follower_adapter;
    List<Feedback_Ratings_List> fratingslist_min = new ArrayList<>();
    List<Feedback_Ratings_List> fratingslist = new ArrayList<>();
    List<ItemList> itemlist = new ArrayList<>();
    List<Follower_List> follow_list = new ArrayList<>();
    List<Follower_List> follow_list_min = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repawner_profile);
        session = new Session(this);
        context = this;
        extra = getIntent().getExtras();
        rq = Volley.newRequestQueue(context);
        getThis();
        getProfileInfo();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("RePawner Profile");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                finish();
            }
        });


    }

    private void if_invisible() {
        if (user_id == session.getID()) {
            products_layout.setVisibility(View.GONE);
            feedback_layout.setVisibility(View.GONE);
            btn_unfollow.setVisibility(View.GONE);
            btn_follow.setVisibility(View.GONE);
            get_followers();

        } else {
            check_if_following();
            getimage();
            get_repawner_review();
            get_Seller_items();
            followers_layout.setVisibility(View.GONE);

        }
        get_Feedback_Ratings();
    }


    private void getProfileInfo() {

        //     final ArrayList<String> info=new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray array = object.getJSONArray("repawner_info");
                    Log.e("jsondata", String.valueOf(array));
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject info = array.getJSONObject(i);
                        fname = info.getString("RePawner_Fname");
                        lname = info.getString("RePawner_Lname");
                        mname = info.getString("RePawner_Mname");
                        email = info.getString("RePawner_email");
                        if (session.getID() == user_id) {
                            session.setEmail(email);
                        }
                        followed_id = info.getString("Followed_ID");
                        contact = info.getString("RePawner_contact");
                        user_image = info.getString("user_image");
                        bdate = info.getString("RePawner_bday");
                        Picasso.get()
                                .load(ip.getUrl_image() + user_image)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .fit()
                                .into(seller_image);
                        seller_name.setText(info.getString("seller_name"));
                        seller_email.setText(email);
                        seller_con.setText(contact);
                        int ratings_total = info.getInt("ratings_total");
                        int ratings_count = info.getInt("ratings_count");
                        if (ratings_total == 0 && ratings_count == 0) {
                            rating = 0;
                        } else {
                            rating = ratings_total / ratings_count;
                        }
                        seller_rating.setText(String.valueOf(rating));
                        seller_review_count.setText(String.valueOf(ratings_count));
                        seller_followers.setText(info.getString("follow_count") + " followers");
                        seller_sold.setText(info.getString("items_sold") + " sold");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  error.printStackTrace();
            }
        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("repawner_info", "1");
                params.put("user_id", String.valueOf(user_id));

                return params;
            }


        };
        rq.add(request);
    }

    private void get_followers() {

        follower_adapter = new Follower_Adapter(context, follow_list_min);
        Follower_View.setHasFixedSize(true);
        LinearLayoutManager Pawnshops_layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dec = new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        Follower_View.setLayoutManager(Pawnshops_layout);
        Follower_View.setAdapter(follower_adapter);
        Follower_View.addItemDecoration(dec);
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject result_object = null;
                try {
                    result_object = new JSONObject(response);

                    //extracting json array from response string
                    JSONArray items_array = result_object.getJSONArray("followers");
                    Log.e("jsondata", String.valueOf(items_array));
                    if (items_array.length() > 0) {
                        for (int i = 0; i < items_array.length(); i++) {
                            JSONObject item = items_array.getJSONObject(i);
                            Follower_List follower = new Follower_List(item.getString("seller_name"),
                                    item.getString("user_image"), item.getString("datef")
                                    , item.getInt("user_id"));
                            follow_list.add(follower);
                            if (i <= 3) {
                                follow_list_min.add(follower);
                            }
                            follower_adapter.notifyDataSetChanged();
                            no_followers_prompt.setVisibility(View.GONE);
                        }
                        Log.e("# of followers", String.valueOf(follow_list.size()));
                        if (follow_list.size() > 5) {
                            Log.e("came here", "1");
                            see_all_followers.setVisibility(View.VISIBLE);
                        }
                    } else {
                        no_followers_prompt.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("get_followers", "1");
                return params;
            }
        };
        rq.add(req);
    }

    private void get_Seller_items() {
        items_adapter = new Home_Items_Adapter(context, itemlist);
        Items_View.setHasFixedSize(true);
        LinearLayoutManager Pawnshops_layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        Items_View.setLayoutManager(Pawnshops_layout);
        Items_View.setAdapter(items_adapter);
        StringRequest get_pawnshop_info = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject result_object = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray items_array = result_object.getJSONArray("seller_items");
                    Log.e("jsondata", String.valueOf(items_array));
                    if (items_array.length() > 0) {
                        for (int i = 0; i < items_array.length(); i++) {
                            JSONObject items_object = items_array.getJSONObject(i);
                            ItemList item = new ItemList(items_object.getString("Product_name"),
                                    items_object.getString("Date_Added"), items_object.getString("seller_name")
                                    , items_object.getString("Category_name"), items_object.getString("Product_Type"),
                                    items_object.getString("Product_image"), items_object.getString("Product_description")
                                    , items_object.getInt("Promoted"), items_object.getInt("Reserved"), items_object.getInt("Ordered"), items_object.getInt("Product_ID"),
                                    session.getID(), items_object.getInt("Reservable"), items_object.getInt("Image_ID"),
                                    items_object.getLong("Product_price"));
                            if (i > 5) {
                                see_all_products.setVisibility(View.VISIBLE);
                            }
                            itemlist.add(item);

                        }
                        items_adapter.notifyDataSetChanged();
                        no_items_prompt.setVisibility(View.GONE);
                    } else {
                        see_all_products.setVisibility(View.GONE);

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
                params.put("user_id", String.valueOf(user_id));
                params.put("seller_products", "1");
                params.put("item_type", "pawned");
                return params;
            }
        };
        rq.add(get_pawnshop_info);

    }

    public void get_Feedback_Ratings() {
        fratings_adapter = new Feedback_Ratings_Adapter(context, fratingslist_min);
        FRatings_View.setHasFixedSize(true);
        LinearLayoutManager FRatings_layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        FRatings_View.setLayoutManager(FRatings_layout);
        FRatings_View.setAdapter(fratings_adapter);
        StringRequest get_feedback_ratings = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject result_object = null;
                try {
                    result_object = new JSONObject(response);

                    //extracting json array from response string
                    JSONArray feedback_array = result_object.getJSONArray("feedback_ratings");
                    if (feedback_array.length() > 0) {
                        for (int i = 0; i < feedback_array.length(); i++) {
                            JSONObject feedback_object = feedback_array.getJSONObject(i);
                            Feedback_Ratings_List fratings = new Feedback_Ratings_List(feedback_object.getString
                                    ("RePawner_Fname") + " " + feedback_object.getString("RePawner_Lname"),
                                    feedback_object.getString("Date_Added"), feedback_object.getString
                                    ("Feedback"), feedback_object.getString("Rating"),
                                    feedback_object.getString("RePawner_image"));
                            fratingslist.add(fratings);
                            if (i <= 5) {
                                fratingslist_min.add(fratings);
                            }
                        }
                        if (fratingslist.size() > 5) {
                            see_others.setVisibility(View.VISIBLE);
                        }
                        fratings_adapter.notifyDataSetChanged();
                        no_feedback_prompt.setVisibility(View.GONE);
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
                params.put("feedback_ratings", String.valueOf(user_id));
                params.put("pid", String.valueOf(user_id));
                params.put("rid", String.valueOf(session.getID()));

                return params;
            }
        };
        rq.add(get_feedback_ratings);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {

        }
        if (item.getItemId() == R.id.edit_prof) {
            Intent to_edit_prof = new Intent(RePawner_Profile.this, EditProfile.class);
            to_edit_prof.putExtra("user_id", user_id);
            to_edit_prof.putExtra("fname", fname);
            to_edit_prof.putExtra("lname", lname);
            to_edit_prof.putExtra("mname", mname);
            to_edit_prof.putExtra("email", email);
            to_edit_prof.putExtra("contact", contact);
            to_edit_prof.putExtra("user_image", user_image);
            to_edit_prof.putExtra("bdate", bdate);
            startActivity(to_edit_prof);
        }

        return super.onOptionsItemSelected(item);
    }

    public void follow_function(final int follow) {
        StringRequest follow_pawnshop = new StringRequest(Request.Method.POST, url, response -> {

            MDToast.makeText(context, response, MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
            resetActivity();

        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("follow_this", "1");
                params.put("followed_id", String.valueOf(followed_id));
                params.put("user_id", String.valueOf(session.getID()));
                params.put("seller_id", String.valueOf(user_id));
                params.put("to_follow", String.valueOf(follow));

                return params;
            }
        };
        rq.add(follow_pawnshop);
    }

    private View.OnClickListener repawner_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.see_all_followers:
                    // Intent to_all_products=new Intent(context,)
                case R.id.btn_cancel:
                    review_bar.setIsIndicator(true);
                    feedback_content.setVisibility(View.VISIBLE);
                    feedi.setVisibility(View.GONE);
                    btn_cancel.setVisibility(View.GONE);
                    btn_submit.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.GONE);
                    break;
                case R.id.feedback_content:
                    review_bar.setIsIndicator(false);
                    feedback_content.setVisibility(View.GONE);
                    feedi.setVisibility(View.VISIBLE);
                    btn_cancel.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                    btn_delete.setVisibility(View.VISIBLE);
                    feede.setText(feedback_content.getText());
                    if (feedback_content.getText() != "") {
                        btn_delete.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.btn_submit:
                    post_feedback();
                    resetActivity();
                    break;
                case R.id.btn_follow:
                    follow_function(1);

                    break;
                case R.id.btn_unfollow:
                    follow_function(0);

                    break;
                case R.id.see_others:
                    Intent to_more_feedback = new Intent(context, Feedback_Ratings.class);
                    to_more_feedback.putExtra("feedback_list", (ArrayList<Feedback_Ratings_List>) fratingslist);
                    context.startActivity(to_more_feedback);
                case R.id.btn_delete:
                    delete_feedback();
                default:
                    break;

            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user_id == session.getID()) {
            getMenuInflater().inflate(R.menu.profile_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void getimage() {
        StringRequest req = new StringRequest(Request.Method.POST, url, response -> {
            rep_image = response;
            Picasso.get()
                    .load(ip.getUrl_image() + rep_image)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .fit()
                    .into(profile_image);
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("get_image", "1");
                params.put("user_id", String.valueOf(session.getID()));

                return params;
            }
        };
        rq.add(req);
    }

    public void post_feedback() {
        StringRequest get_pawnshop_info = new StringRequest(Request.Method.POST, url, response -> {
            MDToast mdToast = MDToast.makeText(context, response, MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
            mdToast.show();
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("post_feedback", "1");
                params.put("rid", String.valueOf(session.getID()));
                params.put("user_id", String.valueOf(user_id));
                params.put("update_feedback", String.valueOf(update_feedback));
                params.put("rating", String.valueOf(review_bar.getRating()));
                params.put("feedback", String.valueOf(feede.getText()));
                return params;
            }
        };
        rq.add(get_pawnshop_info);


    }

    private void get_repawner_review() {
        StringRequest get_pawnshop_info = new StringRequest(Request.Method.POST, url, response -> {
            try {

                JSONObject result_object = new JSONObject(response);
                //extracting json array from response string
                JSONArray rep_feedback_array = result_object.getJSONArray("repawner_feedback");
                Log.e("jsondata", String.valueOf(rep_feedback_array));
                if (rep_feedback_array.length() > 0) {
                    update_feedback = 1;
                    JSONObject feedback_object = rep_feedback_array.getJSONObject(0);
                    review_bar.setRating(feedback_object.getLong("Rating"));
                    feedback_content.setText(feedback_object.getString("Feedback"));
                    feedback_content.setTextColor(getResources().getColor(R.color.colorDGray));
                }

            } catch (JSONException e1) {


            }
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("repawner_feedback", "1");
                params.put("rid", String.valueOf(session.getID()));
                return params;
            }
        };
        rq.add(get_pawnshop_info);
    }

    private void check_if_following() {
        StringRequest follow_status = new StringRequest(Request.Method.POST, url, response -> {
            if_follow = Integer.valueOf(response);
            Log.e("follow", String.valueOf(if_follow));
            if (if_follow == 1) {
                btn_follow.setVisibility(View.GONE);
                btn_unfollow.setVisibility(View.VISIBLE);
            }
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("followed_id", String.valueOf(followed_id));
                params.put("check_following", "1");
                params.put("rid", String.valueOf(session.getID()));
                return params;
            }
        };
        rq.add(follow_status);
    }

    private void delete_feedback() {
        StringRequest follow_status = new StringRequest(Request.Method.POST, url, response -> {
            MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("delete_feedback", "1");
                params.put("user_id", String.valueOf(user_id));
                params.put("rid", String.valueOf(session.getID()));
                return params;
            }
        };
        rq.add(follow_status);
    }

    private void getThis() {
        seller_name = this.findViewById(R.id.seller_name);
        seller_email = this.findViewById(R.id.seller_email);
        seller_con = this.findViewById(R.id.seller_con);
        seller_image = this.findViewById(R.id.follow_image);
        seller_rating = this.findViewById(R.id.seller_rating);
        seller_review_count = this.findViewById(R.id.seller_review_count);
        seller_followers = this.findViewById(R.id.seller_followers);
        seller_sold = this.findViewById(R.id.seller_sold);
        no_items_prompt = this.findViewById(R.id.no_products_prompt);
        no_feedback_prompt = this.findViewById(R.id.no_feedback_prompt);
        no_followers_prompt = this.findViewById(R.id.no_followers_prompt);
        products_layout = this.findViewById(R.id.products_layout);
        feedback_layout = this.findViewById(R.id.feedback_layout);
        followers_layout = this.findViewById(R.id.followers_layout);
        feedbacks_layout = this.findViewById(R.id.feedbacks_layout);
        see_others = this.findViewById(R.id.see_others);
        see_all_followers = this.findViewById(R.id.see_all_followers);
        see_all_followers.setVisibility(View.GONE);
        see_all_products = this.findViewById(R.id.see_all_products);
        user_id = extra.getInt("user_id");
        Follower_View = this.findViewById(R.id.follower_recycle);
        FRatings_View = this.findViewById(R.id.far_recycle);
        Items_View = this.findViewById(R.id.product_recycle);
        profile_image = this.findViewById(R.id.profile_image);
        feedback_content = this.findViewById(R.id.feedback_content);
        btn_cancel = this.findViewById(R.id.btn_cancel);
        btn_submit = this.findViewById(R.id.btn_submit);
        review_bar = this.findViewById(R.id.review_bar);
        btn_delete = this.findViewById(R.id.btn_delete);
        feedi = this.findViewById(R.id.feedi);
        feede = this.findViewById(R.id.feede);
        btn_follow = this.findViewById(R.id.btn_follow);
        btn_unfollow = this.findViewById(R.id.btn_unfollow);
        feedback_content.setOnClickListener(repawner_click);
        btn_cancel.setOnClickListener(repawner_click);
        btn_submit.setOnClickListener(repawner_click);
        btn_delete.setOnClickListener(repawner_click);
        btn_follow = this.findViewById(R.id.btn_follow);
        btn_unfollow = this.findViewById(R.id.btn_unfollow);

        if_invisible();

    }

    public void resetActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}

