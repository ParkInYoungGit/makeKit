package com.example.makekit.makekit_adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import java.util.ArrayList;
import java.util.List;

public class ViewPagerProductAdapter  extends FragmentPagerAdapter {


    private final List<Fragment> mfmg = new ArrayList<>();
    private final List<String> mTitles = new ArrayList<>();

    public ViewPagerProductAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return mfmg.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return mTitles.get(position);
    }

    public void AddFrmt(Fragment fragment, String titles){

        mfmg.add(fragment);
        mTitles.add(titles);
    }

}
