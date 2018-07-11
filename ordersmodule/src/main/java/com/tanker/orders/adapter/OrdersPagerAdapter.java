package com.tanker.orders.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tanker.orders.view.OrderListFragment;

import java.util.ArrayList;

public class OrdersPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<OrderListFragment> fragments;
    private final String[] titles;
    private String TAG = "OrdersPagerAdapter";

    public OrdersPagerAdapter(FragmentManager fm, String[] mTitles, ArrayList<OrderListFragment> fragments) {
        super(fm);
        this.titles = mTitles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


}
