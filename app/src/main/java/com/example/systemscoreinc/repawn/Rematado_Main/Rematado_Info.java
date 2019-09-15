package com.example.systemscoreinc.repawn.Rematado_Main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.Config.Config;
import com.example.systemscoreinc.repawn.Home.Home_Navigation1;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class Rematado_Info extends AppCompatActivity {
    TextView samp;
    private int choice = 0;
    private Toolbar toolbar;
    private Button purchase_btn, reserve_btn;
    private TextView remat_name, remat_desc, pawnshop_name, remat_price, remat_cat;
    private static final int PAYPAL_REQUEST_CODE = 7171;
    IpConfig ip;
    String url = ip.getUrl();
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)

            .clientId(Config.PAYPAL_CLIENT__ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rematado_info);
        final Bundle extras = getIntent().getExtras();
        final Context context = this;
        final Session session = new Session(context);
        Intent intent = new Intent(this, PayPalService.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        remat_name = this.findViewById(R.id.rematado_name);
        remat_name.setText(extras.getString("rname"));
        remat_desc = this.findViewById(R.id.rematado_info);
        remat_desc.setText(extras.getString("res_desc"));
        pawnshop_name = this.findViewById(R.id.rematado_pawnshop_name);
        pawnshop_name.setText(extras.getString("pname"));
        remat_cat = this.findViewById(R.id.rematado_category);
        remat_cat.setText(extras.getString("res_cat"));
        remat_price = this.findViewById(R.id.rematado_price);
        remat_price.setText("₱ " + String.format("%.2f", extras.getDouble("price")));
        reserve_btn = this.findViewById(R.id.res_btn);
        purchase_btn = this.findViewById(R.id.btn_order);
        TableLayout.LayoutParams param = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT,
                1.0f
        );
        if (extras.getInt("reservable") != 1) {
            reserve_btn.setVisibility(View.INVISIBLE);
            purchase_btn.setLayoutParams(param);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Intent intent = new Intent(Rematado_Info.this, See_All_Rematados.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
            }
        });
        final TextView see_all_remat = this.findViewById(R.id.see_all_rematados);
        see_all_remat.setOnClickListener(new View.OnClickListener() {
            boolean moreSwitcher = false;

            @Override

            public void onClick(View v) {

                if (!moreSwitcher) {
                    remat_desc.setLines(1);
                    remat_desc.setMaxLines(3);

                    moreSwitcher = true;
                    see_all_remat.setText("SEE MORE");
                } else {
                    remat_desc.setLines(1);
                    remat_desc.setMaxLines(10);

                    moreSwitcher = false;
                    see_all_remat.setText("COLLAPSE");
                }
            }
        });


        purchase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] singleChoiceItems = getResources().getStringArray(R.array.purchase_choices);
                int itemSelected = 0;

                AlertDialog purdia = new AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                        .setTitle("Order Confirmation")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                choice = selectedIndex;
                                //  Toast.makeText(context,""+choice,Toast.LENGTH_LONG).show();
                                switch (choice) {
                                    case 0:

                                        break;
                                }
                            }
                            //  purchase_off(context,extras,session);
                            //  startActivity(new Intent(getApplicationContext(),Items.class));
                            //   Log.d("Rematado_Info", "Sending atomic bombs to Jupiter");
//                                if (choice == 0) {
//                                    AlertDialog onconf = new AlertDialog.Builder(context, R.style.RePawnDialog)
//                                            .setTitle("Are you sure you want to purchase online?")
//                                            .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    purchase_online(context,extras,session,extras.getDouble("price"),extras.getString("rname"),extras.getString("pname"));
//                                                }
//                                            })
//                                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                }
//                                            })
//                                            .create();
//                                    onconf.setCanceledOnTouchOutside(false);
//
//                                    onconf.show();
//                                    Button p_btn = onconf.getButton(DialogInterface.BUTTON_POSITIVE);
//                                    p_btn.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
//                                    Button n_btn = onconf.getButton(DialogInterface.BUTTON_NEGATIVE);
//                                    n_btn.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
//                                } else {
//                                    AlertDialog onconf = new AlertDialog.Builder(context, R.style.RePawnDialog)
//                                            .setTitle("Are you sure you want to purchase manually?")
//                                            .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    purchase_off(context,extras,session);
//                                                }
//                                            })
//                                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                }
//                                            })
//                                            .create();
//                                    onconf.setCanceledOnTouchOutside(false);
//
//                                    onconf.show();
//
//                                }


                        })

                        .create();
                purdia.setCanceledOnTouchOutside(false);
                purdia.show();
            }
        });
        reserve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog onconf = new AlertDialog.Builder(context, R.style.RePawnDialog)
                        .setTitle("Are you sure you want to reserve this item?")
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                reserveconfirm(context, extras, session);
                                AlertDialog onres = new AlertDialog.Builder(context, R.style.RePawnDialog)
                                        .setMessage("You successfully reserved " + extras.getString("rname") + "")
                                        .setPositiveButton("GOT IT", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(Rematado_Info.this, Home_Navigation1.class));
                                            }
                                        })
                                        .create();
                                onres.show();
                                Button pos_btn = onres.getButton(DialogInterface.BUTTON_POSITIVE);
                                pos_btn.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create();
                onconf.setCanceledOnTouchOutside(false);

                onconf.show();
                Button p_btn = onconf.getButton(DialogInterface.BUTTON_POSITIVE);
                p_btn.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
                Button n_btn = onconf.getButton(DialogInterface.BUTTON_NEGATIVE);
                n_btn.setTextColor(getApplication().getResources().getColor(R.color.colorPrimary));
            }
        });

    }

    public void purchase_off(Context context, final Bundle extras, final Session session) {
        RequestQueue rq = Volley.newRequestQueue(context);
        url = url + "order.php";
        StringRequest preq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("rep_id", String.valueOf(session.getID()));
                params.put("rem_id", String.valueOf(extras.getInt("rid")));
                return params;
            }
        };
        rq.add(preq);

    }

    //    public void purchase_online(Context context,final Bundle extras, final Session session,Double price,String r_name, String pawn_name){
//        PayPalPayment payment = new PayPalPayment(new BigDecimal(price), "PHP", "Payment for"+r_name+" to "+pawn_name+"",
//                PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(this, PaymentActivity.class);
//
//        // send the same configuration for restart resiliency
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//
//        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
//        RequestQueue rq=Volley.newRequestQueue(context);
//        String url = ((MyApplication) this.getApplication()).getUrl() + "order.php";
//        StringRequest preq = new StringRequest(Request.Method.POST, url,new Response.Listener<String>(){
//
//
//            @Override
//            public void onResponse(String response) {
//
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("rep_id", String.valueOf(session.getID()));
//                params.put("rem_id",String.valueOf(extras.getInt("rid")));
//                params.put("online","online");
//                return params;
//            }
//        };
//        rq.add(preq);
//
//    }
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void reserveconfirm(Context context, final Bundle extras, final Session session) {
        RequestQueue rq = Volley.newRequestQueue(context);
        url = url + "reserve.php";
        StringRequest preq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("rep_id", String.valueOf(session.getID()));
                params.put("rem_id", String.valueOf(extras.getInt("rid")));
                return params;
            }
        };
        rq.add(preq);
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


}
