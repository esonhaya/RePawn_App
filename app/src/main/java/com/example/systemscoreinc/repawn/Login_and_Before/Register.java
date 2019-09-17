package com.example.systemscoreinc.repawn.Login_and_Before;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.*;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.example.systemscoreinc.repawn.Utils;
import com.github.tntkhang.gmailsenderlibrary.GMailSender;
import com.github.tntkhang.gmailsenderlibrary.GmailListener;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Register extends AppCompatActivity {
    Session session;
    String getID;
    int fage, y = 1990, m = 2, d = 1;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl() + "account.php";
    EditText fname, lname, mname, email, con, pass, datetext;
    MaterialButton datebutton, sign_up;
    TextInputLayout email_layout;
    Context context;
    ImageView profile_image;
    LinearLayout register_layout;
    boolean noErrors;
    RequestQueue rq;
    private static final int IMAGE_REQUEST_CODE = 3;
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Bitmap bitmap;
    private Uri filePath;
    String filename, image, filepath;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //    String url="192.168.8.100/RePawn/add_user.php";
        get_this();
        sign_up.setOnClickListener(v -> {
            check_errors();
            if (noErrors) {
                register();
            }
        });

        TextView tagr = this.findViewById(R.id.terms_agreement);
        tagr.setOnClickListener(v -> {
            //navigateTo(new TermsConditions(), true);
            Intent to_tac = new Intent(Register.this, TermsConditions.class);
            startActivity(to_tac);
        });

        final DatePickerDialog dpd = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
            @Override

            public void onDateSet(DatePicker view, int tyear, int tmonth, int tdayOfMonth) {
                y = tyear;
                m = tmonth;
                d = tdayOfMonth;
                datetext.setText(y + "-" + (m + 1) + "-" + d);

                int age = getAge(y, m, d);
                if (age < 18) {
                    MDToast mdToast = MDToast.makeText(context, "Age should be legal", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                    mdToast.show();
                }

                fage = age;

            }
        }, y, m - 1, d);
        datebutton.setOnClickListener(v -> {

            dpd.getDatePicker().setMaxDate(new Date().getTime());
            dpd.updateDate(y, m, d);
            dpd.show();
        });

    }

    private int getFromCalendar(String strDate, int field) {
        int result = -1;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");// this is your date format "12/24/2013" = "MM/dd/yyy"
            Date date = formatter.parse(strDate);//convert to date
            Calendar cal = Calendar.getInstance();// get calendar instance
            cal.setTime(date);//set the calendar date to your date
            result = cal.get(field); // get the required field
            return result;//return the result.
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }


        return age;
    }

    public void register() {
        image = imageToString(bitmap);
        //   Log.e("image",image);
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            if (response.equals("1")) {
                MDToast.makeText(context, "Either your email or contact # is already been used", MDToast.LENGTH_LONG,
                        MDToast.TYPE_ERROR).show();
            } else {
                //   getID(email.getText().toString(), context);
                gen_code();
                MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                Intent to_log = new Intent(Register.this, LoginActivity.class);
                startActivity(to_log);
            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("first_name", fname.getText().toString());
                params.put("last_name", lname.getText().toString());
                params.put("mid_name", mname.getText().toString());
                params.put("birth_day", datetext.getText().toString());
                params.put("email", email.getText().toString());
                params.put("contact", con.getText().toString());
                params.put("pass", pass.getText().toString());
                params.put("user_image", image);
                params.put("register_user", "1");
                return params;
            }
        };
        rq.add(request);

    }

    public void gen_code() {
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            send_email(response);
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("gen_code", "1");
                params.put("email", email.getText().toString());
                return params;
            }
        };
        rq.add(request);
    }

    private void send_email(String response) {
        GMailSender.withAccount("jeebsonhaya3@gmail.com", "fairytail1234")
                .withTitle("RePawn Access Code")
                .withBody("Your access code is " + response)
                .withSender(getString(R.string.app_name))
                .toEmailAddress(email.getText().toString()) // one or multiple addresses separated by a comma
                .withListenner(new GmailListener() {
                    @Override
                    public void sendSuccess() {
                        Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void sendFail(String err) {
                        Toast.makeText(Register.this, "Fail: " + err, Toast.LENGTH_SHORT).show();
                    }
                })
                .send();
    }

    public void check_errors() {
        noErrors = true;
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                register_layout, TextInputLayout.class);
        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editTextString = textInputLayout.getEditText().getText().toString();
            if (editTextString.isEmpty()) {
                textInputLayout.setError(getResources().getString(R.string.error_empty));
                noErrors = false;
            } else {
                textInputLayout.setError(null);
            }
        }
        if (fage < 18) {
            MDToast mdToast = MDToast.makeText(context, "Age should be legal", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
            mdToast.show();
            noErrors = false;
        }
        if (!isEmailValid(email.getText().toString())) {
            email_layout.setError("invalid email");
            noErrors = false;
        }
        if (bitmap == null) {
            MDToast.makeText(context, "Please set up your profile picture", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
            noErrors = false;
        }
    }

    private void getID(final String email, Context context) {
        RequestQueue rq = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("get_email", "1");
                return params;
            }
        };
        rq.add(request);

//        session.justactivated();
//        Intent to_val = new Intent(Register.this, LoginActivity.class);
//        startActivity(to_val);
    }

    private void get_this() {
        context = this;
        session = new Session(this);
        rq = Volley.newRequestQueue(context);
        register_layout = this.findViewById(R.id.register_layout);
        datebutton = this.findViewById(R.id.date_button);
        fname = this.findViewById(R.id.signup_fname_text_edit);
        lname = this.findViewById(R.id.signup_lname_text_edit);
        mname = this.findViewById(R.id.signup_mname_text_edit);
        email = this.findViewById(R.id.signup_email_edit_text);
        con = this.findViewById(R.id.signup_con_edit_text);
        pass = this.findViewById(R.id.signup_pass_edit_text);
        email_layout = this.findViewById(R.id.signup_email_text_input);
        sign_up = this.findViewById(R.id.Sign_Up);
        datetext = this.findViewById(R.id.datetext);
        profile_image = this.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(register_click);
        Picasso.get()
                .load(ip.getUrl_image() + "default.jpg")
                .fit()
                .into(profile_image);

    }

    private View.OnClickListener register_click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.profile_image:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}


