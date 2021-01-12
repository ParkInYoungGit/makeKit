package com.example.makekit.makekit_activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ViewPagerProductAdapter;
import com.example.makekit.makekit_fragment.ReviewListFragment;
import com.example.makekit.makekit_fragment.WriteReviewFragment;
import com.google.android.material.tabs.TabLayout;

public class ReviewListActivity extends AppCompatActivity {

    final static String TAG = "ReviewListActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerProductAdapter viewPagerProductAdapter;

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

        viewPagerProductAdapter = new ViewPagerProductAdapter(getSupportFragmentManager());

        // Add Fragment
        viewPagerProductAdapter.AddFrmt(new WriteReviewFragment(macIP, productNo), "구매후기 쓰기");
        viewPagerProductAdapter.AddFrmt(new ReviewListFragment(macIP, productNo), "내가 쓴 구매후기");

        viewPager.setAdapter(viewPagerProductAdapter);
        tabLayout.setupWithViewPager(viewPager);

    } // onCreate End -----------------------------------------------------------------


} // END ----------------------------------------------------------------------------