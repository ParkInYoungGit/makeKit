package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.makekit.R;

import java.text.DecimalFormat;

public class CartActivity extends AppCompatActivity {

    TextView orderTotalNext;
    String macIP, productNo, productQuantity, totalPrice;
    DecimalFormat myFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        orderTotalNext = findViewById(R.id.tv_total_payment_cart);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        productNo = intent.getStringExtra("productNo");
        //productQuantity = intent.getStringExtra("productQuantity");
        //totalPrice = intent.getStringExtra("totalPrice");


        // 구매 총 금액
//        int total = Integer.parseInt(products.get(0).getProductPrice()) + 2500;
//        myFormatter = new DecimalFormat("###,###");
//        String formattedStringPrice = myFormatter.format(total);
//        productTotalPrice.setText(formattedStringPrice + "원");

    }
}