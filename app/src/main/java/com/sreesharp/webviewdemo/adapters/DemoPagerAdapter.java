package com.sreesharp.webviewdemo.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.sreesharp.webviewdemo.fragments.ListviewFragment;
import com.sreesharp.webviewdemo.fragments.WebviewFragment;

//FragmentPagerAdapter for supplying the fragment to view pager hosted in MainActivity
public class DemoPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 2;
    private final String[] tabTitles = new String[] { "Web view", "List view" };

    public DemoPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) //First tab
            return new WebviewFragment();
        else
            return new ListviewFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
