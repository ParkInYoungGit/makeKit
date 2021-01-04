package com.example.makekit.makekit_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.SectionPageAdapter;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {

    private ViewPager mViewPager;
//    SectionPageAdapter adapter = new SectionPageAdapter(getActivity().getSupportFragmentManager());
//    SectionPageAdapter adapter = new SectionPageAdapter(getChildFragmentManager());
    Button btnStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Fragment는 Activity가 아니기때문에 리턴값과 레이아웃을 변수로 정해준다.
        View v = inflater.inflate(R.layout.fragment_home, container, false);


        // 앱소개 뷰페이저
        mViewPager = (ViewPager) v.findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(3);

        setupViewPager(mViewPager);   // 뷰페이지 불러오기
        CircleIndicator indicator = (CircleIndicator) v.findViewById(R.id.indicator); // 인디케이터 불러오기
        indicator.setViewPager(mViewPager);  // 인디케이터 안에 페이저처리




        return v;

    }

    public void setupViewPager(ViewPager viewPager) {
        SectionPageAdapter adapter = new SectionPageAdapter(getFragmentManager());
        adapter.addFragment(new BannerViewFragmentFirst(), "1");
        adapter.addFragment(new BannerViewFragmentSecond(), "2");
        adapter.addFragment(new BannerViewFragmentThird(), "3");

        viewPager.setAdapter(adapter);
    }


}