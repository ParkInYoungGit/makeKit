package com.example.makekit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.makekit.makekit_activity.SalesListActivity;
import com.example.makekit.makekit_adapter.PurchaseListAdapter;
import com.example.makekit.makekit_adapter.SalesListAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Order;

import java.util.ArrayList;

public class PurchaseListActivity extends AppCompatActivity {

    String TAG = "PurchaseReal";
    ArrayList<Order> orders;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    String email, macIP, urlAddrBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);
        Log.v(TAG, "onCreat");
        orders = new ArrayList<Order>();

        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");

        recyclerView = findViewById(R.id.SaleList_RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
    @Override
    protected void onResume() {
        super.onResume();
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        connectGetData();
        mAdapter = new PurchaseListAdapter(PurchaseListActivity.this, R.layout.sales_list_layout, orders, urlAddrBase+"image/");
        recyclerView.setAdapter(mAdapter);
    }

    private void connectGetData() {
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(PurchaseListActivity.this, urlAddrBase + "jsp/getSalesRealList.jsp?userEmail="+email, "getRealSalesList");
            Object obj = networkTask.execute().get();
            orders = (ArrayList<Order>) obj;
            Log.v(TAG, orders.get(0).getOrderCardPw());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}