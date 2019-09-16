package com.example.systemscoreinc.repawn.Home.RePawners.All_RePawners;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.systemscoreinc.repawn.Home.RePawners.RePawnerList;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;

import java.util.ArrayList;
import java.util.List;

public class All_RePawners extends AppCompatActivity {
    private Toolbar toolbar;
    Bundle extras;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl();

    Session session;
    Context context;
    RecyclerView all_rep;
    List<RePawnerList> rl = new ArrayList<>();
    All_RePawners_Adapter ara;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_repawners);
        context = this;
        session = new Session(this);
        extras = getIntent().getExtras();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("RePawners");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        rl = (ArrayList<RePawnerList>) getIntent().getSerializableExtra("repawners");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        all_rep = this.findViewById(R.id.all_repawners_recycle);
        ara = new All_RePawners_Adapter(context, rl);
        all_rep.setHasFixedSize(true);
        LinearLayoutManager rep_layout = new GridLayoutManager(context, 1);
        all_rep.setLayoutManager(rep_layout);
        all_rep.setAdapter(ara);
    }
}