package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.OrderViewAdapter;
import com.example.makekit.makekit_asynctask.OrderNetworkTask;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_bean.Payment;
import com.example.makekit.makekit_sharVar.SharVar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderViewActivity extends AppCompatActivity {

    Button gotoMain_orderview;

    String email, macIP, urlAddrBase, urlAddr;

    //  주문 정보
    TextView orderView_Date_TV, orderView_Number_TV;

    //  배송 정보
    TextView order_userName, order_userTel, order_userAddress, order_userAddressDetail;

    //  상품 정보
    WebView order_productImage;
    TextView order_productName, order_productQuantity, order_productTotalPrice;
    ListView listView;

    //  결제 정보
    TextView orderView_orderBank, orderView_orderCardNo, orderView_orderDate, orderView_orderTotalPrice;

    // setText String
    String srt_orderView_Date_TV, str_orderView_Number_TV, str_order_userName, str_order_userTel, str_order_userAddress, str_order_userAddressDetail, str_order_productImage, str_orderView_orderTotalNo;
    String str_order_productName, str_order_productQuantity, str_order_productTotalPrice, str_orderView_orderBank, str_orderView_orderCardNo, str_orderView_orderDate, str_orderView_orderTotalPrice;

    OrderViewAdapter adapter;
    ArrayList<Order> orders;
    ArrayList<Payment> orderdetail;
    Order order;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_view);


        orders = new ArrayList<Order>();


        Intent intent = getIntent();
        email = intent.getStringExtra("useremail");
        macIP = intent.getStringExtra("macIP");

        // 값 확인 후 변경
        //srt_orderView_Date_TV = intent.getStringExtra("orderView_Date_TV");
        str_orderView_Number_TV = intent.getStringExtra("orderView_Number_TV");
        str_order_userName = intent.getStringExtra("order_userName");
        str_order_userTel = intent.getStringExtra("order_userTel");
        str_order_userAddress = intent.getStringExtra("order_userAddress");
        str_order_userAddressDetail = intent.getStringExtra("order_userAddressDetail");
        //str_order_productImage = intent.getStringExtra("order_productImage");
        //str_order_productName = intent.getStringExtra("order_productName");
        //str_order_productQuantity = intent.getStringExtra("order_productQuantity");
        //str_order_productTotalPrice = intent.getStringExtra("order_productTotalPrice");
        str_orderView_orderBank = intent.getStringExtra("orderView_orderBank");
        str_orderView_orderCardNo = intent.getStringExtra("orderView_orderCardNo");
        str_orderView_orderDate = intent.getStringExtra("orderView_orderDate");
        str_orderView_orderTotalPrice = intent.getStringExtra("orderView_orderTotalPrice");


        // product select
        urlAddr = SharVar.urlAddrBase + "jsp/select_orderdetail_all.jsp?orderno=" + str_orderView_Number_TV;
        connectSelectData(urlAddr);


        //orderView_Date_TV= findViewById(R.id.orderView_Date_TV);
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
        gotoMain_orderview= findViewById(R.id.gotoMain_orderview);
        listView = findViewById(R.id.orderView_ListView);
//        order = new Order(str_order_productName, str_order_productQuantity, str_order_productTotalPrice, str_order_productImage);
//        orders.add(order);
       // orderView_Date_TV.setText("주문일자  " + orderdetail.get(0).getOrderDate());
        orderView_Number_TV.setText("주문번호  " + str_orderView_Number_TV);
        order_userName.setText(str_order_userName);
        order_userTel.setText(str_order_userTel);
        order_userAddress.setText(str_order_userAddress);
        order_userAddressDetail.setText(str_order_userAddressDetail);
        orderView_orderBank.setText(str_orderView_orderBank);
        orderView_orderCardNo.setText(str_orderView_orderCardNo);
        orderView_orderDate.setText(orderdetail.get(0).getOrderDate());

        gotoMain_orderview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderViewActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        DecimalFormat myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice2 = myFormatter.format(Integer.parseInt(str_orderView_orderTotalPrice));
        orderView_orderTotalPrice.setText(formattedStringPrice2 + "원");

    }

    @Override
    protected void onResume() {
        super.onResume();
        urlAddrBase = SharVar.urlAddrBase + "jsp";
        //urlAddrBase = "http://" + macIP + ":8080/makeKit/jsp";
        adapter = new OrderViewAdapter(OrderViewActivity.this, R.layout.custom_order_view, orderdetail, urlAddrBase);
        listView.setAdapter(adapter);
    }


    // select OrderNo
    private void connectSelectData(String urlAddr) {
        try {
            OrderNetworkTask orderNetworkTask = new OrderNetworkTask(OrderViewActivity.this, urlAddr, "selectProduct");

            Object object = orderNetworkTask.execute().get();
            orderdetail = (ArrayList<Payment>) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /////////////////////////////////////////////////////

}
