package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.OrderProductListAdapter;
import com.example.makekit.makekit_adapter.SaleProductListAdapter;
import com.example.makekit.makekit_asynctask.NetworkTask_DH;
import com.example.makekit.makekit_asynctask.OrderNetworkTask;
import com.example.makekit.makekit_asynctask.UserNetworkTask;
import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_bean.Payment;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_bean.User;
import com.example.makekit.makekit_fragment.ProductAdapter;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    final static String TAG = "OderActivity";

    //  아이피, url, 기본 세팅 설정
    String macIP, email, productNo, count, totalPrice, urlAddrBase, urlJsp, urlImage, url, urlAddrSelect_Resume, cartNo;
    int orderCount, orderTotalPrice;

    //  주문자 기본 정보
    EditText order_userName, order_userTel, order_userAddress, order_userAddressDetail;
    String orderUserName, orderUserTel, orderUserAddress, orderUserAddressDetail;

    //  수령자 정보
    RecyclerView rv_product_order;
    RecyclerView.Adapter mAdapter = null;
    EditText order_receiverName, order_receiverTel, order_receiverAddress, order_receiverAddressDetail;
    Spinner order_requestComent = null; //   배송 요청사항
    CheckBox order_info_checkBox;

    //  상품 정보
    WebView order_productImage;
    TextView order_productName, order_productCount, order_productPrice, order_productTotalPrice;

    //  주문하기 버튼
    Button order_payment_btn;

    //  배송 요청사항 목록
    String[] orderRequest = {
                "배송시 요청사항을 선택해주세요", "부재 시 경비실에 맡겨주세요", "부재 시 택배함에 넣어주세요", "부재 시 집 앞에 놔주세요", "배송 전 연락 바랍니다", "파손의 위험이 있는 상품입니다. 배송 시 주의해주세요", "직접 입력"
    };

    // 유저 기본 정보
    ArrayList<Order> Order;   // 빈, 어댑터
    ArrayList<String> product1;   // 빈, 어댑터

    // 구패 제품 정보
    ArrayList<Payment> Payment;   // 빈, 어댑터
//    ArrayList<Product> product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order);

        // Thread 사용
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        product1 = new ArrayList<String>();
        Order = new ArrayList<Order>();

        // ==================================================   기본 URL, IP 지정

        // 앞에서 넘겨준 값 받기
        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        productNo = intent.getStringExtra("productNo");
//        email = intent.getStringExtra("userEmail");
        email = "qkr@naver.com";
//        product1 = intent.getStringArrayListExtra("productNo");
//        cartNo = intent.getStringExtra("cartNo");
        count = intent.getStringExtra("productQuantity");
        totalPrice = intent.getStringExtra("totalPrice");
        Log.v(TAG, macIP);
        Log.v(TAG, productNo);
        Log.v(TAG, count);
        Log.v(TAG, totalPrice);

        //  사용할 값 인트 변환
//        orderCount = Integer.parseInt(count);
//        orderTotalPrice = Integer.parseInt(totalPrice);
//        Log.v(TAG, "orderCount : " + orderCount);
//        Log.v(TAG, "orderTotalPrice : " + orderTotalPrice);


        // 사용할 폴더 지정
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";  // 기본 폴더
        urlJsp = "http://" + macIP + ":8080/makeKit/jsp/";  // jsp 폴더
        urlImage = "http://" + macIP + ":8080/makeKit/image/";  // image 폴더
        urlAddrSelect_Resume = urlJsp + "order_user_info.jsp?email=" + email;

        // ==================================================   findIdView

        //  주문자 기본 정보
        order_userName = findViewById(R.id.order_userName);
        order_userTel = findViewById(R.id.order_userTel);
        order_userAddress = findViewById(R.id.order_userAddress);
        order_userAddressDetail = findViewById(R.id.order_userAddressDetail);

        //  수령자 정보
        rv_product_order = findViewById(R.id.rv_product_order);
        order_receiverName = findViewById(R.id.order_receiverName);
        order_receiverTel = findViewById(R.id.order_receiverTel);
        order_receiverAddress = findViewById(R.id.order_receiverAddress);
        order_receiverAddressDetail = findViewById(R.id.order_receiverAddressDetail);
        order_requestComent = findViewById(R.id.order_requestComent);
        order_info_checkBox = findViewById(R.id.order_info_checkBox);

        //  상품 정보
        order_productImage = findViewById(R.id.order_productImage);
        order_productName = findViewById(R.id.order_productName);
        order_productCount = findViewById(R.id.order_productCount);
        order_productPrice = findViewById(R.id.order_productPrice);
        order_productTotalPrice = findViewById(R.id.order_productTotalPrice);

        //  구매 버튼
        order_payment_btn = findViewById(R.id.order_payment_btn);

        // ========================================================== onClickListener

        // 구매버튼 액션
//        order_payment_btn.setOnClickListener(onClickListener);
        order_info_checkBox.setOnClickListener(onClickListener);


        // ========================================================== 제품 카테고리 스피너 목록 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, orderRequest);
        order_requestComent.setAdapter(adapter);
        order_requestComent.setSelection(0);


        // ========================================================== 페이지 띄울때 주문자

        // 실행시 셀렉트 실행
        connectSelectGetData(urlAddrSelect_Resume);   // urlAddr1을  connectSelectGetData의 urlAddr2로 보내준다
        connectProductSelectGetData();
        //  기본 정보 가져오기
        orderUserName = Order.get(0).getUserName();
        orderUserTel = Order.get(0).getUserTel();
        orderUserAddress = Order.get(0).getUserAddress();
        orderUserAddressDetail = Order.get(0).getUserAddressDetail();

        //  주문자 입력란에 정보 넣기
        order_userName.setText(orderUserName);
        order_userTel.setText(orderUserTel);
        order_userAddress.setText(orderUserAddress);
        order_userAddressDetail.setText(orderUserAddressDetail);
//
//        // order_info_checkBox
//        order_info_checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                order_userName.getText().toString();
//                order_userTel.getText().toString();
//                order_userAddress.getText().toString();
//                order_userAddressDetail.getText().toString();
//
//                order_userName.setText(orderUserName);
//                order_userTel.setText(orderUserTel);
//                order_userAddress.setText(orderUserAddress);
//                order_userAddressDetail.setText(orderUserAddressDetail);
//            }
//        })


    } // ------------ End onCreat
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.order_info_checkBox:      // CheckBox 기본 정보 적용
                        order_userName.getText().toString();
                        order_userTel.getText().toString();
                        order_userAddress.getText().toString();
                        order_userAddressDetail.getText().toString();
                        if (order_info_checkBox.isChecked()) {
                            order_receiverName.setText(orderUserName);
                            order_receiverTel.setText(orderUserTel);
                            order_receiverAddress.setText(orderUserAddress);
                            order_receiverAddressDetail.setText(orderUserAddressDetail);
                        } else {
                            order_receiverName.setText("");
                            order_receiverTel.setText("");
                            order_receiverAddress.setText("");
                            order_receiverAddressDetail.setText("");
                        }


                }
            }
        };

    // 제품 정보를 가져오는 Select



    // ============================================================================================ 선택 제품 기본정보 가져오는 Select
    protected void order() {
        Log.v(TAG, "order in");
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        connectProductSelectGetData();
        mAdapter = new OrderProductListAdapter(OrderActivity.this, R.layout.custom_order_product, Payment, urlAddrBase+"image/");
        Log.v(TAG, "order : " + Payment);
        rv_product_order.setAdapter(mAdapter);

    }

    private void connectProductSelectGetData(){

//        for (int i = 0; i < product1.size(); i++) {
//            try {
//                Log.v(TAG, product1.get(i));
////                OrderNetworkTask orderNetworkTask = new OrderNetworkTask(OrderActivity.this, urlAddrBase + "jsp/order_product_select.jsp?cartNo=" +cartNo+ "&productNo=" +product1.get(i));        // 불러오는게 똑같아서
//                OrderNetworkTask orderNetworkTask = new OrderNetworkTask(OrderActivity.this, urlAddrBase + "jsp/order_product_select.jsp?cartNo=" +"69"+ "&productNo=" +"44");        // 불러오는게 똑같아서
//                Object obj = orderNetworkTask.execute().get();
//                Payment = (ArrayList<Payment>) obj;
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
            try {
                Log.v(TAG, "connectProductSelectGetData in");
//                OrderNetworkTask orderNetworkTask = new OrderNetworkTask(OrderActivity.this, urlAddrBase + "jsp/order_product_select.jsp?cartNo=" +cartNo+ "&productNo=" +product1.get(i));        // 불러오는게 똑같아서
                OrderNetworkTask orderNetworkTask = new OrderNetworkTask(OrderActivity.this, urlAddrBase + "jsp/order_product_select.jsp?cartNo=" +"69"+ "&productNo=" +"44");        // 불러오는게 똑같아서
                Object obj = orderNetworkTask.execute().get();
                Payment = (ArrayList<Payment>) obj;
                Log.v(TAG, "connectProductSelectGetData urlAddrBase" + urlAddrBase);

            } catch (Exception e) {
                e.printStackTrace();
//

        }
    }


    // ============================================================================================  유저 기본정보 가져오는 Select
    @Override
    public void onResume() {
        super.onResume();
        // Select
        urlAddrSelect_Resume = urlJsp + "order_user_info.jsp?email=" + email;
        connectSelectGetData(urlAddrSelect_Resume);
        Log.v(TAG, "onResume()");
    }

    // NetworkTask에서 값을 가져오는 메소드 (Select)  String urlAddr2는 urlAddr1을 받아서 UserNetworkTask로 보내준다
    private ArrayList<Order> connectSelectGetData(String urlSelect) {

        try {
            OrderNetworkTask orderNetworkTask = new OrderNetworkTask(OrderActivity.this, urlSelect, "selectOrder");
            Object obj = orderNetworkTask.execute().get();
            Order = (ArrayList<Order>) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Order;
    }

}