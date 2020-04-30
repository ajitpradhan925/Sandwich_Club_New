package com.ajitapp.sandwichclubnew.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ajitapp.sandwichclubnew.Tab1;
import com.ajitapp.sandwichclubnew.Tab2;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumberOfTabs;
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.mNumberOfTabs = tabCount;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1 tab1 = new Tab1();
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
