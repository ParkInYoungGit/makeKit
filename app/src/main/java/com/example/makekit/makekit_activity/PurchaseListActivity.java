package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.PurchaseListAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Order;

import java.util.ArrayList;

public class PurchaseListActivity extends AppCompatActivity {

    String TAG = "PurchaseReal";
    ArrayList<Order> orders;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    String email, macIP, urlAddrBase, url2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_list);
        Log.v(TAG, "onCreat");
        orders = new ArrayList<Order>();
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";

        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");

        recyclerView = findViewById(R.id.PurchaseList_RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
    @Override
    protected void onResume() {
        super.onResume();
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        connectGetData();
        mAdapter = new PurchaseListAdapter(PurchaseListActivity.this, R.layout.purchase_list_layout, orders, urlAddrBase+"image/", email, macIP);
        recyclerView.setAdapter(mAdapter);
    }

    private void connectGetData() {
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(PurchaseListActivity.this, urlAddrBase + "jsp/purchase_list.jsp?userEmail="+email, "purchaseList");
            Object obj = networkTask.execute().get();
            orders = (ArrayList<Order>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}