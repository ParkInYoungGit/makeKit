package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.SalesListAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

public class SalesListActivity extends AppCompatActivity {

    String TAG = "SalesReal";
    ArrayList<Order> orders;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    String email, macIP, urlAddrBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sales_list);
        Log.v(TAG, "onCreat");
        orders = new ArrayList<Order>();

        Intent intent = getIntent();
//        email = intent.getStringExtra("useremail");
//        macIP = intent.getStringExtra("macIP");
        macIP = SharVar.macIP;
        email = SharVar.userEmail;
        urlAddrBase = SharVar.urlAddrBase;

        recyclerView = findViewById(R.id.SaleList_RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
        mAdapter = new SalesListAdapter(SalesListActivity.this, R.layout.sales_list_layout, orders, urlAddrBase+"image/", email, macIP);
        recyclerView.setAdapter(mAdapter);
    }

    private void connectGetData() {
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(SalesListActivity.this, urlAddrBase + "jsp/getSalesRealList.jsp?userEmail="+email, "getRealSalesList");
            Object obj = networkTask.execute().get();
            orders = (ArrayList<Order>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}