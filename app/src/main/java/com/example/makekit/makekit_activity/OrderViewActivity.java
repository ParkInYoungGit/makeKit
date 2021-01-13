package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.makekit.R;

public class OrderViewActivity extends AppCompatActivity {

    String email, macIP;

    //  주문 정보
    TextView orderView_Date_TV, orderView_Number_TV;

    //  배송 정보
    TextView order_userName, order_userTel, order_userAddress, order_userAddressDetail;

    //  상품 정보
    WebView order_productImage;
    TextView order_productName, order_productQuantity, order_productTotalPrice;

    //  결제 정보
    TextView orderView_orderBank, orderView_orderCardNo, orderView_orderDate, orderView_orderTotalPrice;

    // setText String
    String srt_orderView_Date_TV, str_orderView_Number_TV, str_order_userName, str_order_userTel, str_order_userAddress, str_order_userAddressDetail, str_order_productImage;
    String str_order_productName, str_order_productQuantity, str_order_productTotalPrice, str_orderView_orderBank, str_orderView_orderCardNo, str_orderView_orderDate, str_orderView_orderTotalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);

        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");
        srt_orderView_Date_TV = intent.getStringExtra("orderView_Date_TV");
        str_orderView_Number_TV = intent.getStringExtra("orderView_Number_TV");
        str_order_userName = intent.getStringExtra("order_userName");
        str_order_userTel = intent.getStringExtra("order_userTel");
        str_order_userAddress = intent.getStringExtra("order_userAddress");
        str_order_userAddressDetail = intent.getStringExtra("order_userAddressDetail");
        str_order_productImage = intent.getStringExtra("order_productImage");
        str_order_productName = intent.getStringExtra("order_productName");
        str_order_productQuantity = intent.getStringExtra("order_productQuantity");
        str_order_productTotalPrice = intent.getStringExtra("order_productTotalPrice");
        str_orderView_orderBank = intent.getStringExtra("orderView_orderBank");
        str_orderView_orderCardNo = intent.getStringExtra("orderView_orderCardNo");
        str_orderView_orderDate = intent.getStringExtra("orderView_orderDate");
        str_orderView_orderTotalPrice = intent.getStringExtra("orderView_orderTotalPrice");

        orderView_Date_TV= findViewById(R.id.orderView_Date_TV);
        orderView_Number_TV= findViewById(R.id.orderView_Number_TV);
        order_userName= findViewById(R.id.order_userName);
        order_userTel= findViewById(R.id.order_userTel);
        order_userAddress= findViewById(R.id.order_userAddress);
        order_userAddressDetail= findViewById(R.id.order_userAddressDetail);
        order_productImage= findViewById(R.id.order_productImage);
        order_productName= findViewById(R.id.order_productName);
        order_productQuantity= findViewById(R.id.order_productQuantity);
        order_productTotalPrice= findViewById(R.id.order_productTotalPrice);
        orderView_orderBank= findViewById(R.id.orderView_orderBank);
        orderView_orderCardNo= findViewById(R.id.orderView_orderCardNo);
        orderView_orderDate= findViewById(R.id.orderView_orderDate);
        orderView_orderTotalPrice= findViewById(R.id.orderView_orderTotalPrice);

        orderView_Date_TV.setText(srt_orderView_Date_TV);
        orderView_Number_TV.setText(str_orderView_Number_TV);
        order_userName.setText(str_order_userName);
        order_userTel.setText(str_order_userTel);
        order_userAddress.setText(str_order_userAddress);
        order_userAddressDetail.setText(str_order_userAddressDetail);
        order_productName.setText(str_order_productName);
        order_productQuantity.setText(str_order_productQuantity);
        order_productTotalPrice.setText(str_order_productTotalPrice);
        orderView_orderBank.setText(str_orderView_orderBank);
        orderView_orderCardNo.setText(str_orderView_orderCardNo);
        orderView_orderDate.setText(str_orderView_orderDate);
        orderView_orderTotalPrice.setText(str_orderView_orderTotalPrice);

        order_productImage.setWebViewClient(new WebViewClient());
        // Enable JavaScript
        order_productImage.getSettings().setJavaScriptEnabled(true);
        order_productImage.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // Enable Zoom
        order_productImage.getSettings().setBuiltInZoomControls(true);
        order_productImage.getSettings().setSupportZoom(true);
        order_productImage.getSettings().setSupportZoom(true); //zoom mode 사용.
        order_productImage.getSettings().setDisplayZoomControls(false); //줌 컨트롤러를 안보이게 셋팅.


        // Adjust web display
        order_productImage.setBackgroundColor(0);
        order_productImage.getSettings().setLoadWithOverviewMode(true);
        order_productImage.getSettings().setUseWideViewPort(true);
        order_productImage.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        order_productImage.setInitialScale(15);
        order_productImage.loadUrl(str_order_productImage);


    }
}
