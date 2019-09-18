package com.example.systemscoreinc.repawn.Home.Notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.example.systemscoreinc.repawn.ItemList;
import com.example.systemscoreinc.repawn.R;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    List<ItemList> itemlist;
    Context context;
    Bundle extra;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        i = getIntent();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        getSupportActionBar().setTitle("Search");
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });
        context = this;
        extra = getIntent().getExtras();
        // popRecycle();
    }


    public void getPawnshops(View rootView) {
//        poplist = (ArrayList<PopularList>) i.getSerializableExtra("pawnshops");
//        Log.e("list", String.valueOf(poplist));
//        hpa = new All_Pawnshops_Adapter(context, poplist);
//        pawn_view = rootView.findViewById(R.id.pawnshop_view);
//        pawn_view.setHasFixedSize(true);
//        pawn_view.setLayoutManager(new GridLayoutManager(context, 1));
//        pawn_view.setAdapter(hpa);
//        ps = rootView.findViewById(R.id.pawnshop_spinner);
//        ps.setItems("Sort by Popularity", "Sort by Most Reviewed", "Sort by Most Rated");
//        ps.setOnItemSelectedListener((view, position, id, item) -> {
//
//            switch (position) {
//                case 0:
//                    Collections.sort(poplist, PopularList.Follow_Compare);
//                    hpa.notifyDataSetChanged();
//                    break;
//                case 1:
//                    Collections.sort(poplist, PopularList.Most_Reviews);
//                    hpa.notifyDataSetChanged();
//                    break;
//                case 2:
//                    Collections.sort(poplist, PopularList.Most_Rated);
//                    hpa.notifyDataSetChanged();
//                    break;
//            }
//
//        });
    }

    public void getNew_Notif(View rootView) {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new New_Notifications(), "NEW");
        //   adapter.addFragment(new Checked_Notifications(), "CHECKED");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
