package com.example.newsapp.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.newsapp.Fragments.NewsFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    public PagerAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int pos) {
        Fragment fragment = null;
        switch (pos)
        {
            case 0:

                return  NewsFragment.NewInstance("business");


            case 1:

                return NewsFragment.NewInstance("health");

            case 2:

                return NewsFragment.NewInstance("technology");

            case 3:

                return NewsFragment.NewInstance("sports");

            case 4:


                return NewsFragment.NewInstance("entertainment");

            case 5:


                return NewsFragment.NewInstance("science");


            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
