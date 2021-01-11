package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.makekit.R;
import com.example.makekit.makekit_fragment.ProductAdapter;

public class OrderActivity extends AppCompatActivity {

    //  아이피, url, 기본 세팅 설정
    String macIP, email, productNo, urlAddrBase, urlJsp, urlImage, url, urlAddr;


    //  주문자 기본 정보
    EditText order_userName, order_userTel, order_userAddress, order_userAddressDetail;

    //  수령자 정보
    EditText order_receiverName, order_receiverTel, order_receiverAddress, order_receiverAddressDetail;
    Spinner order_requestComent = null; //   배송 요청사항

    //  상품 정보
    ImageView order_productImage;
    TextView order_productName, order_productCount, order_productPrice, order_productTotalPrice;

    //  주문하기 버튼
    Button order_payment_btn;

    //  배송 요청사항 목록
    String[] orderRequest = {
                "배송시 요청사항을 선택해주세요", "부재 시 경비실에 맡겨주세요", "부재 시 택배함에 넣어주세요", "부재 시 집 앞에 놔주세요", "배송 전 연락 바랍니다", "파손의 위험이 있는 상품입니다. 배송 시 주의해주세요", "직접 입력"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Thread 사용
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        // ==================================================   기본 URL, IP 지정

        // 아이피와 이메일 받기
//        Intent intent = getIntent();
//        macIP = intent.getStringExtra("macIP");
//        productNo = intent.getStringExtra("productNo");
//        count = intent.getStringExtra("productQuantity");
//        totalPrice = intent.getStringExtra("totalPrice");

        macIP = "192.168.2.2";

        // 사용할 폴더 지정
        urlAddrBase = "http://" + macIP + ":8080/makeKit/";  // 기본 폴더
        urlJsp = "http://" + macIP + ":8080/makeKit/jsp/";  // jsp 폴더
        urlImage = "http://" + macIP + ":8080/makeKit/image/";  // image 폴더

        // ==================================================   findIdView

        //  주문자 기본 정보
        order_userName = findViewById(R.id.order_userName);
        order_userTel = findViewById(R.id.order_userTel);
        order_userAddress = findViewById(R.id.order_userAddress);
        order_userAddressDetail = findViewById(R.id.order_userAddressDetail);

        //  수령자 정보
        order_receiverName = findViewById(R.id.order_receiverName);
        order_receiverTel = findViewById(R.id.order_receiverTel);
        order_receiverAddress = findViewById(R.id.order_receiverAddress);
        order_receiverAddressDetail = findViewById(R.id.order_receiverAddressDetail);

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


        // ========================================================== 제품 카테고리 스피너 목록 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, orderRequest);
        order_requestComent.setAdapter(adapter);
        order_requestComent.setSelection(0);

    } //------------ End onCreat


}