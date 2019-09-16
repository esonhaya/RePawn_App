package com.example.systemscoreinc.repawn.Home.Pawnshops.All_Pawnshops;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.systemscoreinc.repawn.Home.Pawnshops.PopularList;
import com.example.systemscoreinc.repawn.IpConfig;
import com.example.systemscoreinc.repawn.R;
import com.example.systemscoreinc.repawn.Session;

import java.util.ArrayList;
import java.util.List;

public class All_Pawnshops extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    Bundle extras;
    IpConfig ip = new IpConfig();
    String url = ip.getUrl();

    Session session;
    Context context;
    RecyclerView all_pawnshop;
    List<PopularList> pl = new ArrayList<>();
    All_Pawnshops_Adapter apa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pawnshops);
        context = this;
        session = new Session(this);
        extras = getIntent().getExtras();
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pawnshops");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        pl = (ArrayList<PopularList>) getIntent().getSerializableExtra("pawnshops");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        all_pawnshop = this.findViewById(R.id.all_pawnshops_recycle);
        apa = new All_Pawnshops_Adapter(context, pl);
        LinearLayoutManager rep_layout = new GridLayoutManager(context, 1);
        all_pawnshop.setLayoutManager(rep_layout);
        all_pawnshop.setAdapter(apa);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        apa.filter(s);
        return false;
    }
}