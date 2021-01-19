package com.example.makekit.makekit_activity;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ViewPagerReviewAdapter;
import com.example.makekit.makekit_fragment.ReviewListFragment;
import com.example.makekit.makekit_fragment.WriteReviewFragment;
import com.example.makekit.makekit_sharVar.SharVar;
import com.google.android.material.tabs.TabLayout;

public class ReviewListActivity extends AppCompatActivity {

    final static String TAG = "ReviewListActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerReviewAdapter viewPagerReviewAdapter;

    String email, macIP, urlJsp, productNo, orderNo;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reviewlist);

        // SharedPreferences 저장소 ----------------------------------------------
        SharedPreferences sf = getSharedPreferences("appData", MODE_PRIVATE);
        macIP = sf.getString("macIP","");
        email = sf.getString("useremail","");

        // SharVar 저장소 --------------------------------------------------------
        macIP = SharVar.macIP;
        email = SharVar.userEmail;
        urlJsp = SharVar.urlAddrBase;


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

    // 화면 touch 시 키보드 숨기기
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }


} // END ----------------------------------------------------------------------------