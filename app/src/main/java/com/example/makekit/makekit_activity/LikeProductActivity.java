package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.LikeProductAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_sharVar.SharVar;

import java.util.ArrayList;

public class LikeProductActivity extends AppCompatActivity {

    ArrayList<Product> products = null;
    RecyclerView recyclerView = null;
    RecyclerView.Adapter mAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;
    String email, macIP, urlAddrBase;
    Button likeSeller, likeProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_like);

        Intent intent = getIntent();
        email = SharVar.userEmail;
        //email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");

        recyclerView = findViewById(R.id.recyclerViewLike);
        likeSeller = findViewById(R.id.likeSeller_btn);
        likeProduct = findViewById(R.id.likeProduct_btn);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        products = new ArrayList<Product>();
        likeProduct.setOnClickListener(mClickListener);
        likeSeller.setOnClickListener(mClickListener);
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.likeSeller_btn:
                    Intent intent = new Intent(LikeProductActivity.this, LikeSellerActivity.class);
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("useremail", email);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.likeProduct_btn:
                    Intent intent1 = new Intent(LikeProductActivity.this, LikeProductActivity.class);
                    intent1.putExtra("macIP", macIP);
                    intent1.putExtra("useremail", email);
                    startActivity(intent1);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        urlAddrBase = SharVar.urlAddrBase;
        // urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        connectGetData();
        mAdapter = new LikeProductAdapter(LikeProductActivity.this, R.layout.search_layout, products, urlAddrBase+"image/");
        recyclerView.setAdapter(mAdapter);

    }

    private void connectGetData(){
        try {
            NetworkTask_DH networkTask = new NetworkTask_DH(LikeProductActivity.this, urlAddrBase+"jsp/getLikeProductAll.jsp?userinfo_userEmail="+email, "search");        // 불러오는게 똑같아서
            Object obj = networkTask.execute().get();
            products = (ArrayList<Product>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}