package com.example.makekit.makekit_activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ViewPagerReviewAdapter;
import com.example.makekit.makekit_fragment.ReviewListFragment;
import com.example.makekit.makekit_fragment.WriteReviewFragment;
import com.google.android.material.tabs.TabLayout;

public class ReviewListActivity extends AppCompatActivity {

    final static String TAG = "ReviewListActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerReviewAdapter viewPagerReviewAdapter;

    String email, macIP, productNo, orderNo;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewlist);

        // 나중에는 밑에 지정해놓은 거 없애고 이 세줄 사용 ----------------------------------------------
//        SharedPreferences sf = getSharedPreferences("appData", MODE_PRIVATE);
//        macIP = sf.getString("macIP","");
//        email = sf.getString("useremail","");


        macIP = "192.168.43.244";
        email = "jordy@naver.com";

        // 화면 구성
        linearLayout = findViewById(R.id.linearlayout_reviewList);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout_reviewList);
        viewPager = (ViewPager) findViewById(R.id.viewpager_reviewList);

        viewPagerReviewAdapter = new ViewPagerReviewAdapter(getSupportFragmentManager());

        // Add Fragment
        viewPagerReviewAdapter.AddFrmt(new WriteReviewFragment(macIP, email), "구매후기 쓰기");
        viewPagerReviewAdapter.AddFrmt(new ReviewListFragment(macIP, email), "내가 쓴 구매후기");

        viewPager.setAdapter(viewPagerReviewAdapter);
        tabLayout.setupWithViewPager(viewPager);

    } // onCreate End -----------------------------------------------------------------


} // END ----------------------------------------------------------------------------