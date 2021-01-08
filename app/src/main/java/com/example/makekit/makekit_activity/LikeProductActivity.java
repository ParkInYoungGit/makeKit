package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.LikeProductAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.ChattingBean;
import com.example.makekit.makekit_bean.Product;

import java.util.ArrayList;

public class LikeProductActivity extends AppCompatActivity {

    ArrayList<Product> products = null;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    String email, macIP, urlAddrBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");

        recyclerView = findViewById(R.id.recyclerViewLike);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        connectGetData();
        mAdapter = new LikeProductAdapter(LikeProductActivity.this, R.layout.search_layout, products);
        recyclerView.setAdapter(mAdapter);
    }

    private void connectGetData(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(LikeProductActivity.this, urlAddrBase+"/jsp/getLikeProductAll.jsp?userinfo_userEmail="+email, "search");        // 불러오는게 똑같아서
            Object obj = networkTask.execute().get();
            products = (ArrayList<Product>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}