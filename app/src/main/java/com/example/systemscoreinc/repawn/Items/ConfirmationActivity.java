package com.example.systemscoreinc.repawn.Items;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConfirmationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl()+"confirmation.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Context context = this;
        Session session = new Session(context);
        //Getting Intent
        Intent intent = getIntent();
        Log.e("pid", intent.getStringExtra("pawned_id"));

        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));
            Log.e("paypal info",String.valueOf(jsonDetails));
            to_database(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"), intent.getStringExtra("pawned_id"));
            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus = (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount + " PHP");
    }

    private void to_database(final JSONObject jsonDetails, final String amount, final String pid) {
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
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

                try {
                    params.put("promotion_pawned", "1");
                    params.put("paypal_id", jsonDetails.getString("id"));
                    params.put("pid", pid);
                    params.put("amount", amount);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };
        rq.add(preq);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == R.id.conf) {
            Intent to_prof = new Intent(ConfirmationActivity.this, Pawned.class);
            to_prof.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(to_prof);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.confirmation, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
