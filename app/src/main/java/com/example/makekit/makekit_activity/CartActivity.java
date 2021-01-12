package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.CartAdapter;
import com.example.makekit.makekit_adapter.ProductReviewAdapter;
import com.example.makekit.makekit_asynctask.CartNetworkTask;
import com.example.makekit.makekit_asynctask.ReviewNetworkTask;
import com.example.makekit.makekit_bean.Cart;
import com.example.makekit.makekit_bean.Review;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    TextView orderTotalNext, productTotalPrice, productDeliveryTotalPrice, allProductTotalPrice;
    String macIP, productNo, productQuantity, totalPrice, cartNo, urlAddrBase, urlAddr;
    DecimalFormat myFormatter;
    ArrayList<Cart> carts;

    CartAdapter cartAdapter;

    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    final static String TAG = "CartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        orderTotalNext = findViewById(R.id.tv_total_payment_cart);
        productTotalPrice = findViewById(R.id.productTotalPrice_cart);
        productDeliveryTotalPrice = findViewById(R.id.productDeliveryTotalPrice_cart);
        allProductTotalPrice = findViewById(R.id.allProductTotalPrice_cart);
        recyclerView = findViewById(R.id.recyclerViewCartList);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        productNo = intent.getStringExtra("productNo");
        cartNo = intent.getStringExtra("cartNo");
        //productQuantity = intent.getStringExtra("productQuantity");
        //totalPrice = intent.getStringExtra("totalPrice");

        urlAddrBase = "http://" + macIP + ":8080/makekit/";
        urlAddr = urlAddrBase + "jsp/select_usercart_all.jsp?cartno=" + cartNo;
        Log.v(TAG, "주소" + urlAddr);
        connectSelectData(urlAddr);
        Log.v(TAG, "총금액" + carts.get(0).getTotalPrice());

        // 구매 총 금액
//        int total = Integer.parseInt(products.get(0).getProductPrice()) + 2500;
//        myFormatter = new DecimalFormat("###,###");
//        String formattedStringPrice = myFormatter.format(total);
//        productTotalPrice.setText(formattedStringPrice + "원");

    }

    // select cart
    private void connectSelectData(String urlAddr) {
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(CartActivity.this, urlAddr, "select");

            Object object = cartNetworkTask.execute().get();
            carts = (ArrayList<Cart>) object;

            cartAdapter = new CartAdapter(CartActivity.this, R.layout.custom_cart_layout, carts, urlAddrBase);
            recyclerView.setAdapter(cartAdapter);
            recyclerView.setHasFixedSize(true); // 리사이클러뷰 기존성능 강화
            layoutManager = new LinearLayoutManager(CartActivity.this);
            recyclerView.setLayoutManager(layoutManager);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume cart");
        connectSelectData(urlAddr);
    }

}