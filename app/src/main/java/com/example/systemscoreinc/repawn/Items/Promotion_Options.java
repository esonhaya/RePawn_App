package com.example.systemscoreinc.repawn.Items;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.systemscoreinc.repawn.Config.Config;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.paypal.android.sdk.payments.*;
import org.json.JSONException;

import java.math.BigDecimal;

public class Promotion_Options extends AppCompatActivity {
    RadioGroup options;
    Button confirm;
    private static final int PAYPAL_REQUEST_CODE = 123;
    private String value;
    private int rid;
    private String pid;
    Toolbar toolbar;
    private static PayPalConfiguration config = new PayPalConfiguration()

            // Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)

            .clientId(Config.PAYPAL_CLIENT__ID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion__options);
        Context context = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Payment Options");
        Session session = new Session(context);
        confirm = this.findViewById(R.id.pay_btn);
        rid = session.getID();
        final Bundle extras = getIntent().getExtras();
        pid = String.valueOf(extras.getInt("pid"));
        options = this.findViewById(R.id.option_group);
        TextView sam = this.findViewById(R.id.textView);
        sam.setText("Payment Options");
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = options.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.wan:
                        value = "100";
                        break;
                    case R.id.too:
                        value = "200";
                        break;
                    case R.id.tree:
                        value = "300";
                        break;
                    default:
                        break;
                }
                paymentonline();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click

                Intent intent = new Intent(Promotion_Options.this, Pawned.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    public void paymentonline() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(value)), "PHP", "Payment for promotion",
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
                            .putExtra("PaymentAmount", value)
                            .putExtra("pawned_id", pid)
                            .putExtra("rep_id", rid));

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
