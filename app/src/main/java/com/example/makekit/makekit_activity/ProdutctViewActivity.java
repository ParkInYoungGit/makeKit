package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ViewPagerProductAdapter;
import com.example.makekit.makekit_fragment.ProductContentFragment;
import com.example.makekit.makekit_fragment.ProductDetailFragment;
import com.example.makekit.makekit_fragment.ProductQuestionFragment;
import com.example.makekit.makekit_fragment.ProductReviewFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProdutctViewActivity extends AppCompatActivity {

//    private ViewPager2 viewPager;
//    private ViewPagerProductAdapter viewPagerProductAdapter;
//    private ProductContentFragment productContentFragment;
//    private ProductDetailFragment productDetailFragment;
//    private ProductReviewFragment productReviewFragment;
//    private ProductQuestionFragment productQuestionFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerProductAdapter viewPagerProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtct_view);


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

    }

}