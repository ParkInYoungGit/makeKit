package com.example.makekit.makekit_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.makekit.makekit_fragment.ProductContentFragment;
import com.example.makekit.makekit_fragment.ProductDetailFragment;
import com.example.makekit.makekit_fragment.ProductReviewFragment;

import java.util.ArrayList;

public class ViewPagerProductAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> items;

    public ViewPagerProductAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> items) {
        super(fm);
        this.items = new ArrayList<Fragment>();
        items.add(new ProductContentFragment());
        items.add(new ProductDetailFragment());
        items.add(new ProductReviewFragment());
        items.add(new ProductReviewFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
