package com.example.systemscoreinc.repawn.Login_and_Before;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.Home.Home_Navigation1;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.example.systemscoreinc.repawn.Utils;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Session session;
    TextInputLayout passwordTextInput;
    TextInputEditText passwordEditText, pass;
    MaterialButton to_reg, login;
    EditText email;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl()+"account.php";
    Context context;
    RequestQueue rq;
    String semail, spass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(this);
        context = this;
        rq = Volley.newRequestQueue(context);
        declare_stuff();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        to_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent to_reg = new Intent(LoginActivity.this, Register.class);
                startActivity(to_reg);
            }

        });
        if (session.loggedin()) {
            if (session.activated() == 1) {
                    startActivity(new Intent(LoginActivity.this, Home_Navigation1.class));
            } else {
            }

            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean noErrors = true;
                final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                        v.getRootView(), TextInputLayout.class);
                for (TextInputLayout textInputLayout : textInputLayouts) {
                    String editTextString = textInputLayout.getEditText().getText().toString();
                    if (editTextString.isEmpty()) {
                        textInputLayout.setError(getResources().getString(R.string.error_empty));
                        noErrors = false;
                    } else {
                        textInputLayout.setError(null);
                    }
                }
                if (!check_pass(passwordEditText.getText())) {
                    noErrors = false;
                    if (passwordEditText.getText().length() == 0) {
                        passwordTextInput.setError(getResources().getString(R.string.error_empty));
                    } else {
                        passwordTextInput.setError(getString(R.string.shr_error_password_log));
                    }
                }

                if (noErrors) {

                    semail = String.valueOf(email.getText());
                    spass = String.valueOf(pass.getText());
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(!response.equals("0")) {
                                int user_id = Integer.valueOf(response);
                                loginSuccess(user_id, semail, 1);
                            }
                            else{
                                MDToast.makeText(context,"Wrong password/email, try again",MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", semail);
                            params.put("pass", spass);
                            params.put("login", "1");
                            return params;
                        }
                    };
                    rq.add(request);
                }

            }
        });
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (check_pass(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                }
                return false;
            }
        });


        final TextView forgot_pass = this.findViewById(R.id.forgot_password);
        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_fpass = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(to_fpass);
            }
        });
    }

    public boolean check_pass(@Nullable Editable text) {
        return text.length() >= 5;
    }

    private void displayToast(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private void loginSuccess(int id, String email, int activated) {
        session.setLoggedin(true, activated);
        session.createUserLoginSession(id, email, activated);
        if (activated == 1) {
                startActivity(new Intent(LoginActivity.this, Home_Navigation1.class));

        } else {
            //  startActivity(new Intent(LoginActivity.this, EmailValidation.class));
        }
        finish();
    }

    private void declare_stuff() {
        passwordTextInput = this.findViewById(R.id.password_text_input);
        passwordEditText = this.findViewById(R.id.password_edit_text);
        email = this.findViewById(R.id.email_edit_input);
     ///   String[] emails = getResources().getStringArray(R.array.emails_array);
     //   ArrayAdapter<String> adapter =
              //  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, emails);
    //    email.setAdapter(adapter);
     //   email.setDropDownBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.colorPrimary)));
        pass = this.findViewById(R.id.password_edit_text);
        to_reg = this.findViewById(R.id.to_reg_button);
        login = this.findViewById(R.id.login_button);
    }


}
