package com.example.systemscoreinc.repawn.Items;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;
import com.example.systemscoreinc.repawn.Utils;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPawned extends AppCompatActivity {
    private EditText pname, pcat, pdesc, pprice;
    private MaterialButton addbutton, catbutton;
    private RadioGroup preserve;
    private TextInputLayout pname_layout;
    private HorizontalScrollView hview;
    private LinearLayout lview;
    private LinearLayout add_view;
    Button seller_confirm;
    ImageView image, receipt;
    Bitmap bimage, breceipt;
    private RelativeLayout rl;
    private Button get_images;
    int id_count = 1, rid;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    Button btn_images, cat_button;
    float price;
    Context context;
    Session session;
    RequestQueue rq;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl() + "addpawned.php";
    String Simage, Sreceipt;
    private Uri filePath;
    List<Category_List> clist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pawned);
        context = this;
        session = new Session(this);
        rid = session.getID();
        declaring_from_view();
        get_categories();
        rq = Volley.newRequestQueue(context);
//        price = Float.valueOf(pprice.getText().toString());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_pawned();
            }
        });
    }

    public void get_categories() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                try {
                   object = new JSONObject(response);
                    JSONArray item_array = object.getJSONArray("orders");
                    if (item_array.length() > 0) {
                        for (int i = 0; i < item_array.length(); i++) {
                            //extracting json array from response string
                            JSONObject item = item_array.getJSONObject(i);
                            Category_List list = new Category_List(item.getInt("Category_ID"), item.getInt("Parent_ID"),
                                    item.getString("Category_name"));
                            clist.add(list);
                        }
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
                params.put("get_categories", "1");
                return params;
            }
        };
        rq.add(request);
    }

    public void pickImage(int req) {
        Intent photoIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoIntent.setType("image/*");
        photoIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photoIntent, "Complete action using"), req);
    }


    public void add_pawned() {
        price = Float.valueOf(pprice.getText().toString());
        int reserve_id = preserve.getCheckedRadioButtonId();
        String reserve;
        if (reserve_id == R.id.yes)
            reserve = "1";
        else
            reserve = "0";
        final String reserve_pin = reserve;
        boolean noErrors = true;
        final List<TextInputLayout> textInputLayouts = Utils.findViewsWithType(
                add_view, TextInputLayout.class);
        for (TextInputLayout textInputLayout : textInputLayouts) {
            String editTextString = textInputLayout.getEditText().getText().toString();
            if (editTextString.isEmpty()) {
                textInputLayout.setError(getResources().getString(R.string.error_empty));
                noErrors = false;
            } else {
                textInputLayout.setError(null);
            }
        }
        if (price <= 0) {
            MDToast mdToast = MDToast.makeText(context, "input valid price", MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
            mdToast.show();
            noErrors = false;
        }
        if (noErrors) {
            if (breceipt == null || bimage == null) {
                MDToast.makeText(context, "Please input all images", MDToast.LENGTH_LONG, MDToast.TYPE_ERROR).show();
            } else {
                Simage = imageToString(bimage);
                Sreceipt = imageToString(breceipt);
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("1")) {
                            pname_layout.setError("name's already been used");
                            //   Toast.makeText(context, pid+"", Toast.LENGTH_SHORT).show();
                        } else
                            MDToast.makeText(context, "You've sucesfully added an item", MDToast.LENGTH_LONG, MDToast.TYPE_SUCCESS).show();
                        Intent intent = new Intent(AddPawned.this, Pawned.class);
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
                        params.put("product_name", pname.getText().toString());
                        params.put("description", pdesc.getText().toString());
                        params.put("add_product", "1");
                        params.put("category", pcat.getText().toString());
                        params.put("res", reserve_pin);
                        params.put("user_id", String.valueOf(rid));
                        params.put("price", String.valueOf(price));
                        params.put("image", Simage);
                        params.put("receipt", Sreceipt);
                        return params;
                    }
                };
                rq.add(request);
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(AddPawned.this, Pawned.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void declaring_from_view() {
        addbutton = this.findViewById(R.id.done_add);
        catbutton = this.findViewById(R.id.cat_button);
        pname = this.findViewById(R.id.pname_edit);
        pcat = this.findViewById(R.id.cat_text);
        pdesc = this.findViewById(R.id.pdesc_edit);
        pprice = this.findViewById(R.id.pprice_edit);
        preserve = this.findViewById(R.id.p_reservable);
        pname_layout = this.findViewById(R.id.pname_input);
        add_view = this.findViewById(R.id.add_view);
        image = this.findViewById(R.id.product_image);
        receipt = this.findViewById(R.id.receipt_image);
        rl = this.findViewById(R.id.rl);
        cat_button = this.findViewById(R.id.cat_button);
        cat_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Make your selection");
//                builder.setItems(clist, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int item) {
//                        // Do something with the selection
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setTitle("Make your selection");
//                builder.setItems(clist, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int item) {
//                        // Do something with the selection
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(1);
            }
        });
        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage(2);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        } else {
            filePath = data.getData();
            if (requestCode == 1) {

                //Get image
                try {
                    bimage = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    image.setImageBitmap(bimage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    breceipt = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    receipt.setImageBitmap(breceipt);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

}
