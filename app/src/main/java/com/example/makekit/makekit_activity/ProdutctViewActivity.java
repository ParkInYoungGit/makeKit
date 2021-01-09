package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ViewPagerProductAdapter;
import com.example.makekit.makekit_fragment.ProductContentFragment;
import com.example.makekit.makekit_fragment.ProductDetailFragment;
import com.example.makekit.makekit_fragment.ProductQuestionFragment;
import com.example.makekit.makekit_fragment.ProductReviewFragment;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


/////////////////////////////////////////////////////
// 앞 activity에서 받아오는 intent 값 다시 확인해서 수정하기!
/////////////////////////////////////////////////////


public class ProdutctViewActivity extends AppCompatActivity {
    final static String TAG = "ProdutctViewActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerProductAdapter viewPagerProductAdapter;

    String sellerEmail ,productNo, macIP;
    FrameLayout framelayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtct_view);
        Log.v(TAG, "onCreate");

        // 판매자 메일 받아오기
        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        sellerEmail = intent.getStringExtra("sellerEmail");
        productNo = intent.getStringExtra("productNo");
        framelayout = findViewById(R.id.framelayout_productview);

        FloatingActionButton fab = findViewById(R.id.fab_prodcutview);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ScrollView scrollView = findViewById(R.id.sv_productview);
//                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_productview);
        viewPager = (ViewPager) findViewById(R.id.viewpager_productview);

        viewPagerProductAdapter = new ViewPagerProductAdapter(getSupportFragmentManager());

        //     Add Fragment
        viewPagerProductAdapter.AddFrmt(new ProductContentFragment(), "상품설명");
        viewPagerProductAdapter.AddFrmt(new ProductDetailFragment(), "상세정보");
        viewPagerProductAdapter.AddFrmt(new ProductReviewFragment(), "후기");
        viewPagerProductAdapter.AddFrmt(new ProductQuestionFragment(), "문의");

        viewPager.setAdapter(viewPagerProductAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               int position = tab.getPosition(); //탭 버튼의 index 참조

               Fragment selected = null;
               Bundle bundle = new Bundle();

               /* 선택된 탭에 따라 화면에 보이는 프래그먼트 변경 */
               switch(position){
                   case 0:
                       selected = new ProductContentFragment();
                       selected.setArguments(bundle);
                       bundle.putString("sellerEmail", sellerEmail);
                       bundle.putString("productNo", productNo);
                       break;
                   case 1:
                       selected = new ProductDetailFragment();
                       selected.setArguments(bundle);
                       bundle.putString("sellerEmail", sellerEmail);
                       bundle.putString("productNo", productNo);
                       break;
                   case 2:
                       selected = new ProductReviewFragment();
                       selected.setArguments(bundle);
                       bundle.putString("macIP", macIP);
                       bundle.putString("productNo", productNo);
                       break;

                   case 3:
                       selected = new ProductQuestionFragment();
                       selected.setArguments(bundle);
                       bundle.putString("sellerEmail", sellerEmail);
                       bundle.putString("productNo", productNo);
                       Log.v(TAG, String.valueOf(bundle));

                       break;
                   default:

                       break;
               }

               ProdutctViewActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_productview, selected).commit();
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {

           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
    }



}