package com.example.systemscoreinc.repawn.Orders;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class Order_Reserve_Pager_Adapter extends FragmentPagerAdapter {



    public Order_Reserve_Pager_Adapter(FragmentManager fm) {

        super(fm);

    }



    @Override

    public Fragment getItem(int position) {

        // getItem is called to instantiate the fragment for the given page.

        // Return a PlaceholderFragment (defined as a static inner class below).

        switch (position) {
            case 0:

            case 1:

            default:

                return null;

        }

    }



    @Override

    public int getCount() {

        // Show 3 total pages.

        return 2;

    }



    @Override

    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0:

                return "Orders";

            case 1:

                return "Reserves";

        }

        return null;

    }

}