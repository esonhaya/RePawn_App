package com.example.systemscoreinc.repawn.Profile_Related;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.android.volley.*;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.example.systemscoreinc.repawn.Utils;
import com.squareup.picasso.*;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EditProfile extends AppCompatActivity {
    MaterialButton edit_basic, change_email, confirm_email_change, change_pass, confirm_change_pass;
    EditText fnamee, mnamee, lnamee, cone, datetext, emaile, passe, opasse, npasse;
    Button datebutton;
    TextInputLayout emaili, passi, opassi, npassi;
    Toolbar toolbar;
    String Spic, bdate, Semail, Spass, Sopass, Snpass;
    int fage, user_id, y, m, d;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl() + "account.php";
    LinearLayout edit_layout, change_email_layout, change_pass_layout;
    Context context;
    Session session;
    RequestQueue rq;
    DatePickerDialog dpd;
    ImageView profile_image;
    Bundle extra;
    Boolean noErrors = true;
    String image;
    Uri filePath;
    private Bitmap bitmap;
    Picasso.Builder builder;
    LruCache picassoCache;
    private static final int IMAGE_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Profile");
        extra = getIntent().getExtras();
        context = this;
        session = new Session(this);
        rq = Volley.newRequestQueue(context);
        builder = new Picasso.Builder(context);
        picassoCache = new LruCache(context);
        builder.memoryCache(picassoCache);
        get_this();
        get_this_account();
        populate_from_extra();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                finish();
            }
        });
    }

    private void get_this() {
        edit_layout = this.findViewById(R.id.edit_layout);
        datetext = this.findViewById(R.id.datetext);
        profile_image = this.findViewById(R.id.profile_image);
        fnamee = this.findViewById(R.id.edit_fname_text_edit);
        mnamee = this.findViewById(R.id.edit_mname_text_edit);
        lnamee = this.findViewById(R.id.edit_lname_text_edit);
        cone = this.findViewById(R.id.edit_con_text_edit);
        emaile = this.findViewById(R.id.emaile);
        edit_basic = this.findViewById(R.id.edit_basic);
        datebutton = this.findViewById(R.id.date_button);
        change_email_layout = this.findViewById(R.id.change_email_layout);
        change_pass_layout = this.findViewById(R.id.change_pass_layout);
        emaili = this.findViewById(R.id.emaili);
        passi = this.findViewById(R.id.passi);
        opassi = this.findViewById(R.id.opassi);
        npassi = this.findViewById(R.id.npassi);
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dpd.getDatePicker().setMaxDate(new Date().getTime());
                dpd.updateDate(y, m, d);
                dpd.show();
            }
        });
    }

    private void get_this_account() {
        change_email = this.findViewById(R.id.change_email);
        confirm_email_change = this.findViewById(R.id.confirm_email_change);
        change_pass = this.findViewById(R.id.change_pass);
        confirm_change_pass = this.findViewById(R.id.confirm_change_pass);
        emaile = this.findViewById(R.id.emaile);
        passe = this.findViewById(R.id.passe);
        opasse = this.findViewById(R.id.opasse);
        npasse = this.findViewById(R.id.npasse);
        setListener();
    }

    public void setListener() {
        change_email.setOnClickListener(edit_prof_listener);
        edit_basic.setOnClickListener(edit_prof_listener);
        change_pass.setOnClickListener(edit_prof_listener);
        confirm_email_change.setOnClickListener(edit_prof_listener);
        confirm_change_pass.setOnClickListener(edit_prof_listener);
        profile_image.setOnClickListener(edit_prof_listener);
    }


    private void populate_from_extra() {
        user_id = extra.getInt("user_id");
        Semail = extra.getString("email");
        fnamee.setText(extra.getString("fname"));
        lnamee.setText(extra.getString("lname"));
        mnamee.setText(extra.getString("mname"));
        emaile.setText(Semail);
        cone.setText(extra.getString("contact"));
        bdate = extra.getString("bdate");
        y = getFromCalendar(bdate, Calendar.YEAR);
        m = getFromCalendar(bdate, Calendar.MONTH);
        m+=1;
        Log.e("monnth", String.valueOf(m));
        d = getFromCalendar(bdate, Calendar.DAY_OF_MONTH);
        fage = getAge(y, m, d);
        datetext.setText(y + "/" +m + "/" + d);
        Spic = extra.getString("user_image");
        Picasso.get()
                .load(ip.getUrl_image() + Spic)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(new Target() {

                    @Override
                    public void onBitmapLoaded(final Bitmap bmap, Picasso.LoadedFrom from) {
                        /* Save the bitmap or do something with it here */
                        bitmap = bmap;
                        // Set it in the ImageView
                        profile_image.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
        datepickerstuff();


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


    private void edit_basic() {
        if (bitmap != null) {
            image = imageToString(bitmap);
        }
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                Intent to_prof = new Intent(EditProfile.this, RePawner_Profile.class);

                to_prof.putExtra("user_id", user_id);
                startActivity(to_prof);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fname", fnamee.getText().toString());
                params.put("lname", lnamee.getText().toString());
                params.put("con", cone.getText().toString());
                params.put("mname", mnamee.getText().toString());
                params.put("datetext", datetext.getText().toString());
                params.put("user_image", image);
                params.put("edit_basic", "1");
                params.put("user_id", String.valueOf(user_id));
                return params;
            }
        };
        rq.add(req);
    }

    private void email_change() {

        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_INFO).show();
                finish();
            }
        }, error -> {

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", Semail);
                params.put("pass", Spass);
                params.put("account_update", "1");
                params.put("user_id", String.valueOf(user_id));
                return params;
            }
        };
        rq.add(req);

    }

    private void change_pass() {
        Sopass = opasse.getText().toString();
        Snpass = npasse.getText().toString();
        StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MDToast.makeText(context, response, MDToast.LENGTH_LONG, MDToast.TYPE_INFO).show();
                resetActivity();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("npass", Snpass);
                params.put("pass", Sopass);
                params.put("account_update", "1");
                params.put("user_id", String.valueOf(user_id));
                return params;
            }
        };
        rq.add(req);

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

    public void datepickerstuff() {
        dpd = new DatePickerDialog(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK, new DatePickerDialog.OnDateSetListener() {
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
        }, y, m-1, d);
    }

    private View.OnClickListener edit_prof_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.change_email:
                    emaili.setVisibility(View.VISIBLE);
                    passi.setVisibility(View.VISIBLE);
                    confirm_email_change.setVisibility(View.VISIBLE);
                    change_email.setVisibility(View.GONE);
                    break;
                case R.id.confirm_email_change:
                    display_dialog("email");
                    break;
                case R.id.edit_basic:
                    display_dialog("basic");
                    break;
                case R.id.change_pass:
                    opassi.setVisibility(View.VISIBLE);
                    npassi.setVisibility(View.VISIBLE);
                    confirm_change_pass.setVisibility(View.VISIBLE);
                    change_pass.setVisibility(View.GONE);
                    break;
                case R.id.confirm_change_pass:
                    display_dialog("pass");
                    break;
                case R.id.profile_image:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), IMAGE_REQUEST_CODE);
                default:
                    break;
            }

        }
    };

    private void display_dialog(final String type) {
        android.support.v7.app.AlertDialog onconf = new android.support.v7.app.AlertDialog.Builder(context, R.style.RePawnDialog)
                .setTitle("Save changes?")
                .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (type.equals("email")) {
                            check_email_errors();
                            //   email_change();
                        } else if (type.equals("pass")) {
                            check_pass_errors();
                        } else if (type.equals("basic")) {
                            check_basic_errors();
                            //   edit_basic();
                        }

                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();
        onconf.show();
    }

    private void check_email_errors() {
        noErrors = true;
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                change_email_layout, TextInputLayout.class);
        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editTextString = textInputLayout.getEditText().getText().toString();
            if (editTextString.isEmpty()) {
                textInputLayout.setError(getResources().getString(R.string.error_empty));
                noErrors = false;
            } else {
                textInputLayout.setError(null);
            }
        }
        Semail = emaile.getText().toString();
        Spass = passe.getText().toString();
        if (noErrors) {
            if (!isEmailValid(Semail)) {
                noErrors = false;
                MDToast.makeText(context, "Input valid email address", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
            } else {
                email_change();
            }
        }
    }

    private void check_pass_errors() {
        noErrors = true;
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                change_pass_layout, TextInputLayout.class);
        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editTextString = textInputLayout.getEditText().getText().toString();
            if (editTextString.isEmpty()) {
                textInputLayout.setError(getResources().getString(R.string.error_empty));
                noErrors = false;
            } else {
                textInputLayout.setError(null);
            }
            Spass = opasse.getText().toString();
            Snpass = npasse.getText().toString();
            if (Spass.equals("") || Snpass.equals("")) {
                noErrors = false;
                MDToast.makeText(context, "Please input both fields", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
            }
            if (noErrors) {
                change_pass();
            }
        }

    }

    private void check_basic_errors() {
        noErrors = true;
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                edit_layout, TextInputLayout.class);
        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editTextString = textInputLayout.getEditText().getText().toString();
            if (editTextString.isEmpty()) {
                textInputLayout.setError(getResources().getString(R.string.error_empty));
                noErrors = false;
            } else {
                textInputLayout.setError(null);
            }
        }
        if (noErrors) {
            if (fage < 18) {
                noErrors = false;
                MDToast.makeText(context, "Input valid age", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
            } else {
                edit_basic();
            }
        }

    }

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

    public void resetActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}