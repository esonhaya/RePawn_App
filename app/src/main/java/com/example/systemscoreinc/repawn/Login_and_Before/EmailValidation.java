package com.example.systemscoreinc.repawn.Login_and_Before;//package com.example.systemscoreinc.repawn;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.design.button.MaterialButton;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class EmailValidation extends AppCompatActivity {
//    Session session;
//    String user_id;
//    HashMap<String, String> user;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_eval);
//        final TextView acode = this.findViewById(R.id.access_code_edit_text);
//        final Context context = this;
//        final TextView send = this.findViewById(R.id.send_another);
//        MaterialButton confirm = this.findViewById(R.id.confirm_code);
//        session = new Session(this);
////        final Bundle ext=getIntent().getExtras();
////        user_id=ext.getString("user_id");
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                RequestQueue rq = Volley.newRequestQueue(context);
//                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        //  startActivity(new Intent(Register.this,EmailValidation.class));
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("gen_code", "1");
//                        params.put("rep_id", String.valueOf(session.getID()));
//
//                        return params;
//                    }
//                };
//
//
//                rq.add(request);
//            }
//        });
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = ((MyApplication) getApplication()).getUrl() + "check_code.php?acode=" + acode.getText() + "&uid=" + session.getID() + "";
//                url = url.replace(" ", "%20");
//                RequestQueue rq = Volley.newRequestQueue(context);
//                StringRequest sr = new StringRequest(Request.Method.GET, url, new Response.Listener() {
//                    @Override
//                    public void onResponse(Object response) {
//                        this.onResponse((String) response);
//                    }
//
//                    public final void onResponse(String response) {
//                        if (response.equals("1")) {
//                            session.resetSes();
//                            session.justactivated();
//                            session.toasnew();
//                            startActivity(new Intent(EmailValidation.this, LoginActivity.class));
//                            // Toast.makeText(EmailValidation.this, ""+session.activated()+"", Toast.LENGTH_SHORT).show();
//                        } else
//                            Toast.makeText(EmailValidation.this, "Invalid Access Code: " + acode.getText() + "", Toast.LENGTH_LONG).show();
//
//
//                    }
//
//
//                }, new Response.ErrorListener() {
//                    public final void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EmailValidation.this, error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//
//
//                rq.add(sr);
//            }
////               Intent to_log=new Intent(EmailValidation.this,LoginActivity.class);
////               startActivity(to_log);
//
//
//        });
//    }
//
//    public void onBackPressed() {
//
//    }
//}