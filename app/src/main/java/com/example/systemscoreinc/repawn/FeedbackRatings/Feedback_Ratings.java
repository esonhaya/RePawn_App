package com.example.systemscoreinc.repawn.FeedbackRatings;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;

import java.util.ArrayList;
import java.util.List;

public class Feedback_Ratings extends AppCompatActivity {
    private Toolbar toolbar;
    Bundle extras;
    IpConfig ip=new IpConfig();
    String url=ip.getUrl();

    Session session;
    Context context;
    RecyclerView feedback_view;
    List<Feedback_Ratings_List> feedback_list = new ArrayList<>();
    Feedback_Ratings_Adapter feedback_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback__ratings);
        context = this;
        session = new Session(this);
        extras = getIntent().getExtras();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback and Ratings");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        feedback_list = (ArrayList<Feedback_Ratings_List>) getIntent().getSerializableExtra("feedback_list");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        feedback_view = this.findViewById(R.id.product_recycle);
        feedback_adapter = new Feedback_Ratings_Adapter(context, feedback_list);
        feedback_view.setHasFixedSize(true);
        LinearLayoutManager Pawnshops_layout = new GridLayoutManager(context, 1);
        feedback_view.setLayoutManager(Pawnshops_layout);
        feedback_view.setAdapter(feedback_adapter);
    }
}
