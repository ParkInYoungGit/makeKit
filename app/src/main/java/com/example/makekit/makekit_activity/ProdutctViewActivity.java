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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
    //FrameLayout framelayout;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtct_view);
        Log.v(TAG, "onCreate");

        // 판매자 메일 받아오기
        Intent intent = getIntent();
//        macIP = intent.getStringExtra("macIP");
//        sellerEmail = intent.getStringExtra("sellerEmail");
//        productNo = intent.getStringExtra("productNo");
        macIP = "192.168.219.164";
        productNo = "44";
        //framelayout = findViewById(R.id.framelayout_productview);
        linearLayout = findViewById(R.id.linearlayout_productview);

//        FloatingActionButton fab = findViewById(R.id.fab_prodcutview);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                ScrollView scrollView = findViewById(R.id.sv_productview);
////                scrollView.fullScroll(ScrollView.FOCUS_UP);
//            }
//        });

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

        findViewById(R.id.btnCart_productview).setOnClickListener(mClickListener);
        findViewById(R.id.btnPurchase_productview).setOnClickListener(mClickListener);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
           @Override
           public void onTabSelected(TabLayout.Tab tab) {
               int position = tab.getPosition(); //탭 버튼의 index 참조


               FragmentManager fragmentManager = getSupportFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               Fragment selected = fragmentManager.findFragmentByTag(String.valueOf(position));
               Bundle bundle = new Bundle();

               /* 선택된 탭에 따라 화면에 보이는 프래그먼트 변경 */
               switch(position){
                   case 0:
                       selected = new ProductContentFragment();
                       selected.setArguments(bundle);
                       bundle.putString("macIP", macIP);
                       bundle.putString("productNo", productNo);
                       break;
                   case 1:
                       selected = new ProductDetailFragment();
                       selected.setArguments(bundle);
                       bundle.putString("macIP", macIP);
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
                       fragmentTransaction.show(selected);

                       break;
               }

               ProdutctViewActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.linearlayout_productview, selected).commit();
           }

           @Override
           public void onTabUnselected(TabLayout.Tab tab) {
//               int position = tab.getPosition(); //탭 버튼의 index 참조
//
//               Fragment selected = null;
//               Bundle bundle = new Bundle();
//               bundle.putString("macIP", "192.168.219.164");
//               bundle.putString("productNo", "44");
           }

           @Override
           public void onTabReselected(TabLayout.Tab tab) {

           }
       });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnCart_productview:
                    if(loginCheck() == true){
                        Intent intent = new Intent(ProdutctViewActivity.this, CartActivity.class);
                        startActivity(intent);
                    }

                    break;

                /////////////////////////////////////////////
                // 구매하기 페이지 다시 확인하기!
                /////////////////////////////////////////////
                case R.id.btnPurchase_productview:
                    if(loginCheck() == true){
                        Intent intent = new Intent(ProdutctViewActivity.this, OrderActivity.class);
                        startActivity(intent);
                    }

                    break;
            }
        }
    };

    private boolean loginCheck(){
        ///////////////////////////////
        // email 받아오는 값 확인해서 수정하기

        String userEmail = "qkr@naver.com";

        if(userEmail == null){
            Toast.makeText(this, "로그인이 필요합니다. \n로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProdutctViewActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
    }

}