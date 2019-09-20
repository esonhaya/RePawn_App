package com.example.systemscoreinc.repawn.Pawnshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.example.systemscoreinc.repawn.Home.Home_Navigation1;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pawnshop_Page extends AppCompatActivity {
    Toolbar toolbar;
    Bundle extras;
    TextView pawnshop_name, pawnshop_description, pawnshop_rating, pawnshop_review_count, pawnshop_contact,
            pawnshop_address, pawnshop_email, pawnshop_followers, pawnshop_sold, no_items_prompt, feedback_content,
            no_feedbacks_prompt, see_others, Follow_ID;
    int user_id;
    TextInputLayout edit_feedback_text_input;
    EditText edit_feedback_text_edit;
    ImageView profile_image, see_all_products;
    RequestQueue rq;
    boolean moreSwitcher = false;
    Context context;
    List<ItemList> itemlist = new ArrayList<>();
    RecyclerView Items_View, FRatings_View;
    Home_Items_Adapter items_adapter;
    Feedback_Ratings_Adapter fratings_adapter;
    List<Feedback_Ratings_List> fratingslist_min = new ArrayList<>();
    List<Feedback_Ratings_List> fratingslist = new ArrayList<>();
    Session session;
    RatingBar review_bar;
    Button btn_cancel, btn_submit, btn_delete;
    MaterialButton btn_follow, btn_unfollow;
    int update_feedback = 0, follower_id = 0;
    int if_follow = 0;
    double lat, lng;
    IpConfig ip = new IpConfig();
    String pname, url = ip.getUrl() + "seller_page.php", rep_image;
    String user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pawnshop__page);
        context = this;
        session = new Session(this);
        extras = getIntent().getExtras();
        rq = Volley.newRequestQueue(context);
        Log.e("rep_id", String.valueOf(session.getID()));
        user_id = extras.getInt("user_id");
        Log.e("seller_id", String.valueOf(user_id));
        declarestuff();
        getimage();
        get_Pawnshop_info();
        get_Pawnshop_items();
        get_repawner_review();
        get_Feedback_Ratings();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pawnshop Page");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Pawnshop_Page.this, Home_Navigation1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    private void declarestuff() {

        pawnshop_name = this.findViewById(R.id.pawnshop_name);
        pawnshop_description = this.findViewById(R.id.pawnshop_description);
        pawnshop_rating = this.findViewById(R.id.seller_rating);
        pawnshop_review_count = this.findViewById(R.id.seller_review_count);
        pawnshop_contact = this.findViewById(R.id.pawnshop_contact);
        pawnshop_address = this.findViewById(R.id.pawnshop_address);
        pawnshop_email = this.findViewById(R.id.pawnshop_email);
        pawnshop_followers = this.findViewById(R.id.seller_followers);
        pawnshop_sold = this.findViewById(R.id.seller_sold);
        pawnshop_description.setOnClickListener(pawnshop_click_listener);
        Items_View = this.findViewById(R.id.product_recycle);
        items_adapter = new Home_Items_Adapter(context, itemlist);
        no_items_prompt = this.findViewById(R.id.no_items_prompt);
        no_feedbacks_prompt = this.findViewById(R.id.no_feedbacks_prompt);
        profile_image = this.findViewById(R.id.profile_image);
        review_bar = this.findViewById(R.id.review_bar);
        see_all_products = this.findViewById(R.id.see_all_followers);
        feedback_content = this.findViewById(R.id.feedback_content);
        edit_feedback_text_edit = this.findViewById(R.id.feede);
        edit_feedback_text_input = this.findViewById(R.id.feedi);
        btn_cancel = this.findViewById(R.id.btn_cancel);
        btn_submit = this.findViewById(R.id.btn_submit);
        see_all_products = this.findViewById(R.id.see_all_followers);
        FRatings_View = this.findViewById(R.id.far_recycle);
        feedback_content.setOnClickListener(pawnshop_click_listener);
        btn_submit.setOnClickListener(pawnshop_click_listener);
        btn_cancel.setOnClickListener(pawnshop_click_listener);
        fratings_adapter = new Feedback_Ratings_Adapter(context, fratingslist_min);
        see_others = this.findViewById(R.id.see_others);
        see_others.setOnClickListener(pawnshop_click_listener);
        btn_follow = this.findViewById(R.id.btn_follow);
        btn_unfollow = this.findViewById(R.id.btn_unfollow);
        btn_follow.setOnClickListener(pawnshop_click_listener);
        btn_unfollow.setOnClickListener(pawnshop_click_listener);
        Follow_ID = this.findViewById(R.id.Follow_ID);
        btn_delete = this.findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(pawnshop_click_listener);

    }

    private void delete_feedback() {
        StringRequest follow_status = new StringRequest(Request.Method.POST, url,
                response -> MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show(),
                error -> {

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

    private void get_Pawnshop_items() {
        //   Log.e("seller_id", String.valueOf(seller_id));
        Items_View.setHasFixedSize(true);
        LinearLayoutManager Pawnshops_layout = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        Items_View.setLayoutManager(Pawnshops_layout);
        Items_View.setAdapter(items_adapter);
        StringRequest get_pawnshop_info = new StringRequest(Request.Method.POST, url, response -> {
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
                                user_id, items_object.getInt("Reservable"), items_object.getInt("Image_ID"),
                                items_object.getLong("Product_price"));
                        if (i > 5) {
                            see_all_products.setVisibility(View.VISIBLE);
                        }
                        itemlist.add(item);

                    }
                    items_adapter.notifyDataSetChanged();
                    no_items_prompt.setVisibility(View.GONE);
                } else {
                    no_items_prompt.setVisibility(View.VISIBLE);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("seller_products", "1");
                params.put("item_type", "rematado");
                return params;
            }
        };
        rq.add(get_pawnshop_info);

    }

    private void get_Pawnshop_info() {
        StringRequest get_pawnshop_info = new StringRequest(Request.Method.POST, url, response -> {
            try {

                JSONObject result_object = new JSONObject(response);

                //extracting json array from response string
                JSONArray pawnshop_array = result_object.getJSONArray("pawnshop_info");
                if (pawnshop_array.length() > 0) {
                    for (int i = 0; i < pawnshop_array.length(); i++) {
                        JSONObject pawnshop_object = pawnshop_array.getJSONObject(i);
                        lat = pawnshop_object.getDouble("latitude");
                        lng = pawnshop_object.getDouble("longitude");
                        pname = pawnshop_object.getString("Pawnshop_name");
                        pawnshop_name.setText(pname);
                        pawnshop_description.setText(pawnshop_object.getString("Pawnshop_description"));
                        int ratings_total = pawnshop_object.getInt("ratings_total");
                        int ratings_count = pawnshop_object.getInt("ratings_count");
                        int avg_ratings = 0;
                        if (ratings_total != 0) {
                            avg_ratings = ratings_total / ratings_count;
                        }
                        pawnshop_rating.setText(String.valueOf(avg_ratings));
                        pawnshop_review_count.setText(String.valueOf(ratings_count));
                        pawnshop_contact.setText("Contact: " + pawnshop_object.getString("Pawnshop_contact"));
                        pawnshop_address.setText("Address: " + pawnshop_object.getString("Pawnshop_address"));
                        pawnshop_email.setText("Email: " + pawnshop_object.getString("Pawnshop_email"));
                        pawnshop_followers.setText(pawnshop_object.getString("follow_count") + " followers");
                        pawnshop_followers.setText(pawnshop_object.getString("follow_count") + " followers");
                        pawnshop_sold.setText(pawnshop_object.getString("items_sold") + " sold");
                        follower_id = pawnshop_object.getInt("Followed_ID");
                        user_image = pawnshop_object.getString("user_image");
                        Follow_ID.setText("" + follower_id);
                        Log.d("follower_id", String.valueOf(follower_id));
                        check_if_following();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("pawnshop_info", "1");
                return params;
            }
        };
        rq.add(get_pawnshop_info);


    }

    private View.OnClickListener pawnshop_click_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.pawnshop_description:
                    if (!moreSwitcher) {
                        pawnshop_description.setLines(1);
                        pawnshop_description.setMaxLines(2);
                        moreSwitcher = true;
                    } else {
                        pawnshop_description.setLines(1);
                        pawnshop_description.setMaxLines(10);
                        moreSwitcher = false;
                    }
                    break;
                case R.id.see_all_followers:
                    // Intent to_all_products=new Intent(context,)
                case R.id.btn_cancel:
                    review_bar.setIsIndicator(true);
                    feedback_content.setVisibility(View.VISIBLE);
                    edit_feedback_text_edit.setVisibility(View.GONE);
                    edit_feedback_text_input.setVisibility(View.GONE);
                    btn_cancel.setVisibility(View.GONE);
                    btn_submit.setVisibility(View.GONE);
                    btn_delete.setVisibility(View.GONE);
                    break;
                case R.id.feedback_content:
                    review_bar.setIsIndicator(false);
                    feedback_content.setVisibility(View.GONE);
                    edit_feedback_text_edit.setVisibility(View.VISIBLE);
                    edit_feedback_text_input.setVisibility(View.VISIBLE);
                    btn_cancel.setVisibility(View.VISIBLE);
                    btn_submit.setVisibility(View.VISIBLE);
                    edit_feedback_text_edit.setText(feedback_content.getText());
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
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                    break;
                default:
                    break;

            }
        }
    };

    public void resetActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void post_feedback() {
        StringRequest get_pawnshop_info = new StringRequest(Request.Method.POST, url, response -> {
            MDToast mdToast = MDToast.makeText(context, response, MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
            mdToast.show();
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("post_feedback", String.valueOf(user_id));
                params.put("rid", String.valueOf(session.getID()));
                params.put("user_id", String.valueOf(user_id));
                params.put("update_feedback", String.valueOf(update_feedback));
                params.put("rating", String.valueOf(review_bar.getRating()));
                params.put("feedback", String.valueOf(edit_feedback_text_edit.getText()));
                return params;
            }
        };
        rq.add(get_pawnshop_info);


    }

    public void get_Feedback_Ratings() {
        FRatings_View.setHasFixedSize(true);
        LinearLayoutManager FRatings_layout = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        FRatings_View.setLayoutManager(FRatings_layout);
        FRatings_View.setAdapter(fratings_adapter);
        StringRequest get_feedback_ratings = new StringRequest(Request.Method.POST, url, response -> {
            JSONObject result_object = null;
            try {
                result_object = new JSONObject(response);

                //extracting json array from response string

                JSONArray feedback_array = result_object.getJSONArray("feedback_ratings");
                Log.e("array", String.valueOf(feedback_array));
                if (feedback_array.length() > 0) {
                    for (int i = 0; i < feedback_array.length(); i++) {
                        JSONObject feedback_object = feedback_array.getJSONObject(i);
                        Feedback_Ratings_List fratings = new Feedback_Ratings_List(feedback_object.getString
                                ("RePawner_Fname") + " " + feedback_object.getString("RePawner_Lname"),
                                feedback_object.getString("Date_Added"), feedback_object.getString
                                ("Feedback"), feedback_object.getString("Rating"),
                                feedback_object.getString("user_image"));
                        fratingslist.add(fratings);
                        if (i <= 5) {
                            fratingslist_min.add(fratings);
                        }
                    }
                    if (fratingslist.size() > 5) {
                        see_others.setVisibility(View.VISIBLE);
                    }
                    fratings_adapter.notifyDataSetChanged();
                    no_feedbacks_prompt.setVisibility(View.GONE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {

        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("feedback_ratings", "1");
                params.put("user_id", String.valueOf(user_id));
                params.put("rid", String.valueOf(session.getID()));

                return params;
            }
        };
        rq.add(get_feedback_ratings);
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
                params.put("followed_id", String.valueOf(follower_id));
                params.put("user_id", String.valueOf(session.getID()));
                params.put("seller_id", String.valueOf(user_id));
                params.put("to_follow", String.valueOf(follow));

                return params;
            }
        };
        rq.add(follow_pawnshop);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == R.id.action_locate) {
            Intent to_locate = new Intent(Pawnshop_Page.this, Pawnshop_Locate.class);
            to_locate.putExtra("lat", lat);
            to_locate.putExtra("lng", lng);
            to_locate.putExtra("name", pname);
            to_locate.putExtra("user_image", user_image);
            startActivity(to_locate);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.not_followed, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void check_if_following() {
        StringRequest follow_status = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if_follow = Integer.valueOf(response);
                Log.e("follow", String.valueOf(if_follow));
                if (if_follow == 1) {
                    btn_follow.setVisibility(View.GONE);
                    btn_unfollow.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("followed_id", String.valueOf(follower_id));
                params.put("check_following", "1");
                params.put("rid", String.valueOf(session.getID()));
                return params;
            }
        };
        rq.add(follow_status);
    }

    public void getimage() {
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rep_image = response;
                Picasso.get()
                        .load(ip.getUrl_image() + rep_image)
                        .fit()
                        .into(profile_image);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
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

}
