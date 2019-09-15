package com.example.systemscoreinc.repawn.Items.Promotion_History;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;

import java.util.ArrayList;
import java.util.List;

public class Promotion_History extends AppCompatActivity {
    Context context;
    Bundle extras;
    //int pid;
    List<Promotion_History_List> promlist = new ArrayList<>();
    Promotion_History_Adapter history_adapter;
    RequestQueue rq;
    IpConfig ip=new IpConfig();
    String url=ip.getUrl();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion__history);
        context = this;
        rq = Volley.newRequestQueue(context);
        extras = getIntent().getExtras();
       // pid = extras.getInt("pid");
        promlist = (ArrayList<Promotion_History_List>) getIntent().getSerializableExtra("promotion_list");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Promotion History");
        declarestuffs();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // perform whatever you want on back arrow click
                finish();
            }
        });
    }

    private void declarestuffs() {

        final RecyclerView promview = findViewById(R.id.promotion_view);
        promview.setHasFixedSize(true);
        GridLayoutManager historylayout = new GridLayoutManager(this, 1);
        promview.setLayoutManager(historylayout);
        history_adapter = new Promotion_History_Adapter(context, promlist);
        promview.setAdapter(history_adapter);

    }

}
