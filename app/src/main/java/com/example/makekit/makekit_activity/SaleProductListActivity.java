package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.SaleProductListAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Order;

import java.util.ArrayList;

public class SaleProductListActivity extends AppCompatActivity {

    ArrayList<Order> orders = null;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    String email, macIP, urlAddrBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_product_list);

        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");

        recyclerView = findViewById(R.id.recyclerViewSaleList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        orders = new ArrayList<Order>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        connectGetData();
        mAdapter = new SaleProductListAdapter(SaleProductListActivity.this, R.layout.sales_product_list_layout, orders, urlAddrBase+"image/");
        recyclerView.setAdapter(mAdapter);
    }

    private void connectGetData(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(SaleProductListActivity.this, urlAddrBase+"jsp/getSalesList.jsp?userinfo_userEmail="+email, "getSalesList");        // 불러오는게 똑같아서
            Object obj = networkTask.execute().get();
            orders = (ArrayList<Order>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}