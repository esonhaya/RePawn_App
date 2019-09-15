package com.example.systemscoreinc.repawn.Items;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Utils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Edit_Pawned_Info extends AppCompatActivity {

    private EditText pname, pcat, pdesc, pprice;
    private MaterialButton editbutton, catbutton;
    private RadioGroup preserve;
    private TextInputLayout pname_layout;
    Toolbar toolbar;
    Bundle extras;
    Bitmap bimage, breceipt;
    String simage, sreceipt;
     Uri filePath;
    Context context;
    ImageView product_image, receipt_image;
    LinearLayout edit_layout;
    RequestQueue rq;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl()+"edit_pawned.php";
    int pid;
    String Spname;
    long price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pawned);
        extras = getIntent().getExtras();
        context = this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit Pawned ");
        rq = Volley.newRequestQueue(context);
        this_stuff();
        get_extra();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click

                finish();
            }
        });

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                android.support.v7.app.AlertDialog onconf = new android.support.v7.app.AlertDialog.Builder(context, R.style.RePawnDialog)
                        .setTitle("Save changes?")
                        .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editall();
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
        });


    }


    private void get_extra() {
        Spname=extras.getString("pname");
        pname.setText(Spname);
        pcat.setText(extras.getString("pcat"));
        pdesc.setText(extras.getString("pdesc"));
        price = extras.getLong("pprice");
        pprice.setText(String.valueOf(price));
        //   String  pawned_id=Integer.toString(extras.getInt("Pawned_ID"));
        int choosen = extras.getInt("reservable");
        pid = extras.getInt("pid");
        if (choosen == 1) {
            preserve.check(R.id.yes);
        } else {
            preserve.check(R.id.no);
        }
        Picasso.get()
                .load(ip.getUrl_image()+Spname+".jpg")
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(new Target() {

                    @Override
                    public void onBitmapLoaded(final Bitmap bmap, Picasso.LoadedFrom from) {
                        /* Save the bitmap or do something with it here */
                       bimage = bmap;
                        // Set it in the ImageView
                        product_image.setImageBitmap(bimage);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
        Picasso.get()
                .load(ip.getUrl_image() + Spname+"rec.jpg")
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(new Target() {

                    @Override
                    public void onBitmapLoaded(final Bitmap bmap, Picasso.LoadedFrom from) {
                        /* Save the bitmap or do something with it here */
                        breceipt = bmap;
                        // Set it in the ImageView
                        receipt_image.setImageBitmap(breceipt);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

    }

    private void editall() {
        if (breceipt == null || bimage == null) {
            MDToast.makeText(context, "Please input all images", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
        } else {
            simage = imageToString(bimage);
            sreceipt = imageToString(breceipt);
            int reserve_id = preserve.getCheckedRadioButtonId();
            String reserve;
            if (reserve_id == R.id.yes)
                reserve = "1";
            else
                reserve = "0";
            final String reserve_pin = reserve;
            boolean noErrors = true;
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
            price = Long.valueOf(pprice.getText().toString());
            if(price<= 0) {
                MDToast mdToast = MDToast.makeText(context, "input valid price", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
                mdToast.show();
                noErrors = false;
            }
            if (noErrors) {

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")) {
                            pname_layout.setError("name's already been used");
                            //    Toast.makeText(context, pid+"", Toast.LENGTH_SHORT).show();
                        } else
                            startActivity(new Intent(Edit_Pawned_Info.this, Pawned.class));
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
                        params.put("product_name", pname.getText().toString());
                        params.put("description", pdesc.getText().toString());
                        params.put("update_product", "1");
                        params.put("category", pcat.getText().toString());
                        params.put("res", reserve_pin);
                        params.put("product_id",String.valueOf(pid));
                        params.put("price", String.valueOf(price));
                        params.put("image", simage);
                        params.put("receipt", sreceipt);

                        return params;
                    }
                };
                rq.add(request);


            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            filePath = data.getData();
            if (requestCode == 1) {

                //Get image
                try {
                    bimage = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    product_image.setImageBitmap(bimage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    breceipt = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    receipt_image.setImageBitmap(breceipt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void pickImage(int req) {
        Intent photoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoIntent.setType("image/*");
        photoIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photoIntent, "Complete action using"), req);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    private void this_stuff() {
        editbutton = this.findViewById(R.id.done_add);
        catbutton = this.findViewById(R.id.cat_button);
        pname = this.findViewById(R.id.pname_edit);
        pcat = this.findViewById(R.id.cat_text);
        pdesc = this.findViewById(R.id.pdesc_edit);
        pprice = this.findViewById(R.id.pprice_edit);
        preserve = this.findViewById(R.id.p_reservable);
        pname_layout = this.findViewById(R.id.pname_input);
        edit_layout = this.findViewById(R.id.edit_layout);
        product_image = this.findViewById(R.id.product_image);
        receipt_image = this.findViewById(R.id.receipt_image);
        product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(1);
            }
        });
        receipt_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(2);
            }
        });

    }
}
