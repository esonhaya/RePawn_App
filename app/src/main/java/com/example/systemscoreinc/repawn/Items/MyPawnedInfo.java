package com.example.systemscoreinc.repawn.Items;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.systemscoreinc.repawn.Config.Config;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.Items.Order_History.Order_History_Adapter;
import com.example.systemscoreinc.repawn.Items.Order_History.Order_History_List;
import com.example.systemscoreinc.repawn.Items.Pending_Request.Pending_Request_Adapter;
import com.example.systemscoreinc.repawn.Items.Pending_Request.Pending_Request_List;
import com.example.systemscoreinc.repawn.Items.Promotion_History.Promotion_History;
import com.example.systemscoreinc.repawn.Items.Promotion_History.Promotion_History_List;
import com.example.systemscoreinc.repawn.Items.Reservation_History.Reservation_History_Adapter;
import com.example.systemscoreinc.repawn.Items.Reservation_History.Reservation_History_List;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyPawnedInfo extends AppCompatActivity {

    IpConfig ip = new IpConfig();
    Session session;
    Toolbar toolbar;
    Context context;
    Bundle extras;
    RequestQueue rq;
    List<Pending_Request_List> order_list_min = new ArrayList<>();
    List<Pending_Request_List> order_list = new ArrayList<>();
    List<Pending_Request_List> reserve_list = new ArrayList<>();
    List<Pending_Request_List> reserve_list_min = new ArrayList<>();
    List<Promotion_History_List> prom_hist_list = new ArrayList<>();
    List<Order_History_List> ohl = new ArrayList<>();
    List<Order_History_List> ohl_current = new ArrayList<>();
    List<Reservation_History_List> rhl = new ArrayList<>();
    List<Reservation_History_List> rhl_current = new ArrayList<>();
    Order_History_Adapter oha;
    Reservation_History_Adapter rha;
    Pending_Request_Adapter order_adapter;
    Pending_Request_Adapter reserve_adapter;
    String status;
    ImageView product_image;
    TextView pname, pdesc, pprice, pcat, daysleft, promhistory, all_pending_orders, all_pending_reserve, nop, nrp;
    LinearLayout reserve_layout, order_layout, payment_layout, order_info_layout, reserve_info_layout;
    TextView paypal_id, pay_amount, Date_paid;
    TextView saoh, sarh;
    RecyclerView rec_orders, rec_reserve, order_history, reserve_history;
    String spname, spdesc, spcat, url = ip.getUrl() + "pawned_info.php", item_image;
    Long spprice;
    int pid, res;
    Button seller_confirm;
    int amount, choice, order_details_id;
    int reserved, ordered, promoted;
    String spayment_type = "manual";
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)

            .clientId(Config.PAYPAL_CLIENT__ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        rq = Volley.newRequestQueue(context);
        session = new Session(this);
        setContentView(R.layout.my_pawned_info);
        from_this();
        getfromextra();
        recycler_related();
        promhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_prom = new Intent(context, Promotion_History.class);
                to_prom.putExtra("promotion_list", (Serializable) prom_hist_list);
                startActivity(to_prom);

            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void getfromextra() {
        extras = getIntent().getExtras();
        Intent i = getIntent();
        ItemList item = (ItemList) i.getSerializableExtra("item");
        pid = item.getItem_id();
        res = item.getReservable();
        ordered = item.getOrdered();
        reserved = item.getReserved();
        spname = item.getItem_name();
        spcat = item.getCat_name();
        spdesc = item.getItem_desc();
        spprice = item.getPrice();
        item_image = item.getItem_image();
        promoted = item.getPromoted();
        Picasso.get()
                .load(ip.getUrl_image() + item_image)
                .fit()
                .into(product_image);
        pname.setText(spname);
        pprice.setText("₱ " + spprice);
        pcat.setText(spcat);
        pdesc.setText(spdesc);
        toolbar.setTitle(spname);
        getPromotionDays();
        get_order_details();
        get_reserve_details();
        Visibility_All();
    }

    public void Visibility_All() {

        if (ordered == 1 && spayment_type.equals("manual")) {
            seller_confirm.setVisibility(View.VISIBLE);

        }
        if(spayment_type.equals("paypal")){
            payment_layout.setVisibility(View.VISIBLE);
            get_payment_details();
        }

    }

    public void recycler_related() {
        order_adapter = new Pending_Request_Adapter(context, order_list_min);
        reserve_adapter = new Pending_Request_Adapter(context, reserve_list_min);
        oha = new Order_History_Adapter(context, ohl_current);
        rha = new Reservation_History_Adapter(context, rhl_current);
        rec_orders.setHasFixedSize(true);
        rec_reserve.setHasFixedSize(true);
        order_history.setHasFixedSize(true);
        reserve_history.setHasFixedSize(true);
        LinearLayoutManager reco = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dec = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        LinearLayoutManager recr = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager llm_oh = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager llm_rh = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rec_orders.setLayoutManager(reco);
        rec_orders.setAdapter(order_adapter);
        rec_orders.addItemDecoration(dec);
        rec_reserve.setLayoutManager(recr);
        rec_reserve.setAdapter(reserve_adapter);
        rec_reserve.addItemDecoration(dec);
        order_history.setLayoutManager(llm_oh);
        order_history.setAdapter(oha);
        order_history.addItemDecoration(dec);
        reserve_history.setLayoutManager(llm_rh);
        reserve_history.setAdapter(rha);
        reserve_history.addItemDecoration(dec);

        pending_requests();
        promotion_history();
    }


    public void getPromotionDays() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("0")) {
                    daysleft.setText("Not under promotion");
                } else {
                    daysleft.setText(response + " days left");
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
                params.put("pid", String.valueOf(pid));
                params.put("getdays", "1");

                return params;
            }
        };
        rq.add(request);

    }

    public void promotion_history() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject historyobject = new JSONObject(response);
                    JSONArray history_array = historyobject.getJSONArray("history");
                    if (history_array.length() > 0) {
                        for (int i = 0; i < history_array.length(); i++) {
                            JSONObject history = history_array.getJSONObject(i);
                            Promotion_History_List prolist = new Promotion_History_List(history.getString("Date_Started"),
                                    history.getString("Date_To_End"), history.getString("Paypal_Payment_ID"),
                                    history.getString("Amount"));

                            prom_hist_list.add(prolist);
                        }
                    } else {
                        promhistory.setVisibility(View.GONE);
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
                params.put("pid", String.valueOf(pid));
                params.put("get_prom_history", "1");

                return params;
            }
        };
        rq.add(request);
    }

    public void delete_item() {

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MDToast toast = MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS);
                toast.show();
                Intent intent = new Intent(MyPawnedInfo.this, Pawned.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pid", String.valueOf(pid));
                params.put("delete_item", "1");
                params.put("item_name", spname);
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
        if (item.getItemId() == R.id.edit_pawned) {

            Intent edit_info = new Intent(context, Edit_Pawned_Info.class);
            edit_info.putExtra("pname", spname);
            edit_info.putExtra("pid", pid);
            edit_info.putExtra("pcat", spcat);
            edit_info.putExtra("pprice", spprice);
            edit_info.putExtra("pdesc", spdesc);
            edit_info.putExtra("reservable", res);
            context.startActivity(edit_info);
        }
        if (item.getItemId() == R.id.delete_pawned) {
            android.support.v7.app.AlertDialog onconf = new android.support.v7.app.AlertDialog.Builder(context, R.style.RePawnDialog)
                    .setTitle("Are you sure you want to delete this item? If it's under a promotion, no refund will be offered.")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            editall(view,context,Integer.toString(extras.getInt("pid")));
                            delete_item();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .create();
            onconf.show();
            if (item.getItemId() == R.id.promote_pawned) {
                String[] promote_amount = {"100 pesos for 1 week, 200 pesos for 2 weeks,300 pesos for 1 month"};
                int item_selected = 0;
                android.support.v7.app.AlertDialog prom = new android.support.v7.app.AlertDialog.Builder(context, R.style.RePawnDialog)
                        .setTitle("Promotion Options: Paypal Only")
                        .setSingleChoiceItems(promote_amount, item_selected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                choice = selectedIndex;
                                switch (choice) {
                                    case 0:
                                        amount = 100;
                                        break;
                                    case 1:
                                        amount = 200;
                                        break;
                                    case 2:
                                        amount = 300;
                                        break;
                                    default:
                                        break;

                                }
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pay_with_paypal();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                prom.show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void pay_with_paypal() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "PHP", "Payment for promotion",
                PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);

        // send the same configuration for restart resiliency
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, 0);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                    // TODO: send 'confirm' to your server for verification.
                    // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                    // for more details.
                    String paymentDetails = confirm.toJSONObject().toString(4);
                    Log.i("paymentExample", paymentDetails);

                    //Starting a new activity for the payment details and also putting the payment details with intent
                    startActivity(new Intent(this, ConfirmationActivity.class)
                            .putExtra("PaymentDetails", paymentDetails)
                            .putExtra("PaymentAmount", amount)
                            .putExtra("pawned_id", pid)
                            .putExtra("rep_id", session.getID()));

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    public void from_this() {
        pname = this.findViewById(R.id.product_name);
        pdesc = this.findViewById(R.id.item_desc);
        pprice = this.findViewById(R.id.item_price);
        pcat = this.findViewById(R.id.item_category);
        daysleft = this.findViewById(R.id.days_left);
        all_pending_orders = this.findViewById(R.id.all_pending_orders);
        all_pending_reserve = this.findViewById(R.id.all_pending_reserve);
        all_pending_reserve.setOnClickListener(all_click);
        all_pending_orders.setOnClickListener(all_click);
        reserve_layout = this.findViewById(R.id.reserve_layout);
        order_layout = this.findViewById(R.id.order_layout);
        rec_orders = this.findViewById(R.id.rec_orders);
        rec_reserve = this.findViewById(R.id.rec_reserve);
        toolbar = findViewById(R.id.toolbar);
        promhistory = this.findViewById(R.id.promotion_history);
        product_image = this.findViewById(R.id.product_image);
        //under order layout
        //layouts
        order_info_layout = this.findViewById(R.id.order_info_layout);
        payment_layout = this.findViewById(R.id.payment_layout);
        reserve_info_layout = this.findViewById(R.id.reserve_info_layout);
        //textviews
        paypal_id = this.findViewById(R.id.paypal_id);
        pay_amount = this.findViewById(R.id.pay_amount);
        Date_paid = this.findViewById(R.id.Date_Paid);
        order_history = this.findViewById(R.id.order_history);
        reserve_history = this.findViewById(R.id.reserve_history);
        seller_confirm = this.findViewById(R.id.seller_confirm);
        saoh = this.findViewById(R.id.see_all_order_history);
        sarh = this.findViewById(R.id.sarh);
        nop = this.findViewById(R.id.no_orders_prompt);
        nrp = this.findViewById(R.id.no_reserve_prompt);
        seller_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        params.put("pid", String.valueOf(pid));
                        params.put("confirm_transaction_seller", "1");
                        return params;
                    }
                };
                rq.add(request);
            }
        });
        saoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        sarh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    private void get_order_details() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject pawnedobject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray item_array = pawnedobject.getJSONArray("order_history");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            JSONObject info = item_array.getJSONObject(i);
                            Order_History_List list = new Order_History_List(info.getInt("Order_Details_ID"),
                                    info.getInt("User_ID"), info.getInt("cancelled")
                                    , info.getInt("Payment_ID"), info.getInt("Seller_confirmation"),
                                    info.getInt("Buyer_confirmation"), info.getString("Date_Sent")
                                    , info.getString("Payment_Type"), info.getString("Date_End"),
                                    info.getString("Date_Accepted"), info.getString("buyer_name"));
                            ohl.add(list);
                            if (i == 0 && info.getInt("cancelled") == 0) {
                                ohl_current.add(list);
                            }
                            if (i == 0 && info.getInt("cancelled") == 1) {
                                nop.setVisibility(View.VISIBLE);
                            }
                        }
                        oha.notifyDataSetChanged();

                    } else {
                        nop.setVisibility(View.VISIBLE);
                        saoh.setVisibility(View.GONE);
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
                params.put("pid", String.valueOf(pid));
                params.put("get_order_info", "1");
                return params;
            }
        };
        rq.add(request);

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
                            Reservation_History_List list = new Reservation_History_List(info.getString("Date_Started"),
                                    info.getString("Date_End"), info.getString("Date_Accepted"),
                                    info.getString("buyer_name"), info.getInt("User_ID"), info.getInt("cancelled"));
                            if (i == 0 && info.getInt("cancelled") == 0) {
                                rhl_current.add(list);
                            }
                            if (i == 0 && info.getInt("cancelled") == 1) {
                                nrp.setVisibility(View.VISIBLE);
                            }
                            rhl.add(list);
                        }

                    } else {
                        nrp.setVisibility(View.VISIBLE);
                        sarh.setVisibility(View.GONE);
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
                params.put("pid", String.valueOf(pid));
                params.put("get_reservation_info", "1");
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
                            Date_paid.append(info.getString("Date_Added"));
                            seller_confirm.setVisibility(View.VISIBLE);

                        }

                    } else {
                        payment_layout.setVisibility(View.GONE);
                        seller_confirm.setVisibility(View.GONE);

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
                params.put("order_details_id", String.valueOf(order_details_id));
                params.put("get_payment_info", "1");
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

    private View.OnClickListener all_click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.all_pending_orders:


                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (status.equals("available")) {
            getMenuInflater().inflate(R.menu.pawned_available, menu);
        }
        if (status.equals("promoted")) {
            getMenuInflater().inflate(R.menu.pawned_promoted, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public void pending_requests() {
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
                            Pending_Request_List list = new Pending_Request_List(item.getString("user_name")
                                    , "order", item.getString("Status"),
                                    item.getString("Date_Started"), item.getString("user_image")
                                    , item.getString("Order_Product_ID"), item.getString("Order_Details_ID"),
                                    item.getString("User_ID"), item.getString("Payment_Type"), String.valueOf(pid));
                            order_list.add(list);
                            if (i < 3) {
                                order_list_min.add(list);
                            }
                            if (i > 3) {
                                all_pending_orders.setVisibility(View.VISIBLE);
                            }
                        }
                        order_adapter.notifyDataSetChanged();
                    } else {
                        order_layout.setVisibility(View.GONE);
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
                params.put("Product_ID", String.valueOf(pid));
                params.put("order_requests", "1");

                return params;
            }
        };
        rq.add(request);
        StringRequest request2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("response", response);
                    JSONObject pawnedobject = new JSONObject(response);
                    //extracting json array from response string
                    JSONArray item_array = pawnedobject.getJSONArray("reservations");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            JSONObject item = item_array.getJSONObject(i);
                            Pending_Request_List list = new Pending_Request_List(item.getString("user_name")
                                    , "reserve", item.getString("Status"),
                                    item.getString("Date_Started"), item.getString("user_image"),
                                    item.getString("Reservation_Product_ID"),
                                    item.getString("Reservation_Details_ID"), item.getString("User_ID"),
                                    "reservation", String.valueOf(pid));
                            reserve_list.add(list);
                            if (i < 3) {
                                reserve_list_min.add(list);
                            }
                            if (i > 3) {
                                all_pending_reserve.setVisibility(View.VISIBLE);
                            }
                        }
                        reserve_adapter.notifyDataSetChanged();
                    } else {
                        reserve_layout.setVisibility(View.GONE);
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
                params.put("Product_ID", String.valueOf(pid));
                params.put("reserve_requests", "1");
                return params;
            }
        };
        rq.add(request2);
    }

}
