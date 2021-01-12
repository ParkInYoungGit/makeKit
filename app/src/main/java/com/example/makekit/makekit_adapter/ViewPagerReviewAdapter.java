package com.example.makekit.makekit_adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerReviewAdapter extends FragmentPagerAdapter {


    private final List<Fragment> reviewFM = new ArrayList<>();
    private final List<String> RTitles = new ArrayList<>();

    public ViewPagerReviewAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return reviewFM.get(position);
    }

    @Override
    public int getCount() {
        return RTitles.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return RTitles.get(position);
    }

    public void AddFrmt(Fragment fragment, String titles){

        reviewFM.add(fragment);
        RTitles.add(titles);
    }

}
