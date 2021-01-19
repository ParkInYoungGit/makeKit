package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.CartAdapter;
import com.example.makekit.makekit_asynctask.CartNetworkTask;
import com.example.makekit.makekit_bean.Cart;
import com.example.makekit.makekit_sharVar.SharVar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements OnChangedPrice{

    TextView orderTotalNext, productTotal, productDeliveryTotalPrice, allProductTotalPrice;
    CheckBox selectAll, itemSelect;
    String macIP, productNo, productQuantity, totalPrice, cartNo, urlAddrBase, urlAddr;
    DecimalFormat myFormatter;
    ArrayList<Cart> carts;
    ArrayList<String> productNums;
    Button btnDelete;
    ImageView btnHome;

    CartAdapter cartAdapter;

    private RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    final static String TAG = "CartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cart);

        orderTotalNext = findViewById(R.id.tv_total_payment_cart);
        btnDelete = findViewById(R.id.btn_cart_delete);
        productTotal = findViewById(R.id.productTotalPrice_cart);
        productDeliveryTotalPrice = findViewById(R.id.productDeliveryTotalPrice_cart);
        allProductTotalPrice = findViewById(R.id.allProductTotalPrice_cart);
        recyclerView = findViewById(R.id.recyclerViewCartList);
        selectAll = findViewById(R.id.cb_cart_selectall);
        btnHome = findViewById(R.id.img_home_cart);

        Intent intent = getIntent();
        //macIP = intent.getStringExtra("macIP");

        /////////////////////////////////////////
        // 1/17 kyeongmi productNo 주석처리
        /////////////////////////////////////////
        //productNo = intent.getStringExtra("productNo");

        cartNo = intent.getStringExtra("cartNo");
        //productQuantity = intent.getStringExtra("productQuantity");
        //totalPrice = intent.getStringExtra("totalPrice");

        urlAddrBase = SharVar.urlAddrBase;
//        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        urlAddr = urlAddrBase + "jsp/select_usercart_all.jsp?cartno=" + cartNo;
        Log.v(TAG, "주소" + urlAddr);
        connectSelectData(urlAddr);
//        Log.v(TAG, "총금액" + carts.get(0).getTotalPrice());

        Log.v(TAG, "선택값 : " + cartAdapter.checkBoxCheckedReturn());

        if(carts.size() == 0){
            orderTotalNext.setClickable(false);
        }

        orderTotalNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                for(int i=0; i<cartAdapter.checkBoxCheckedReturn().size(); i++){
//                    String no = cartAdapter.checkBoxCheckedReturn().get(i).getProductNo();
//                    Log.v(TAG, "번호 : " + no);
////                    productNums.add(no);
//                }
                if(carts.size() != 0) {
                    Log.v(TAG, "체크박스 : " + cartAdapter.checkBoxCheckedReturn().size());
                    Log.v(TAG, "카트 사이즈 : " + carts.size());
                    if (cartAdapter.checkBoxCheckedReturn().size() == 0) {
                        Toast.makeText(CartActivity.this, "구매하실 상품을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                       // Toast.makeText(CartActivity.this, "구매 시작해주세요.", Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(CartActivity.this, OrderActivity.class);
                    intent1.putExtra("macIP", macIP);
                    intent1.putExtra("cartNo", cartNo);
                    intent1.putExtra("productno", cartAdapter.checkBoxCheckedReturn());
                    startActivity(intent1);
                    }
                } else {
                    Toast.makeText(CartActivity.this, "장바구니가 비어있습니다.", Toast.LENGTH_SHORT).show();
                    orderTotalNext.setClickable(false);
                    orderTotalNext.setBackgroundColor(getResources().getColor(R.color.gray));
                    orderTotalNext.setTextColor(getResources().getColor(R.color.black));

                }
            }
        });

        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (selectAll.isChecked()){
                    cartAdapter.checkBoxOperation(true);

                }else {
                    cartAdapter.checkBoxOperation(false);
                }

            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(carts.size() != 0) {
                    cartAdapter.connectDeleteData();
                    connectSelectData(urlAddr);
                    productTotal.setText("0원");
                    productDeliveryTotalPrice.setText("0원");
                    allProductTotalPrice.setText("0원");

                    orderTotalNext.setText("구매하기");
                    orderTotalNext.setBackgroundColor(getResources().getColor(R.color.gray));
                    orderTotalNext.setTextColor(getResources().getColor(R.color.black));
                    selectAll.setChecked(false);
                } else {
                    btnDelete.setClickable(false);
                }


//                cartAdapter.checkBoxCheckedReturn();
//                Log.v(TAG, "번호 : " + cartAdapter.checkBoxCheckedReturn().get(0).getProductNo());
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(CartActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    // select cart
    private void connectSelectData(String urlAddr) {
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(CartActivity.this, urlAddr, "select");

            Object object = cartNetworkTask.execute().get();
            carts = (ArrayList<Cart>) object;

            cartAdapter = new CartAdapter(CartActivity.this, R.layout.custom_cart_layout, carts, urlAddrBase, macIP, this);
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
        selectAll.setChecked(false);
        String productprice ="";
        String productcount ="";
        String deliveryprice ="";
        int price = 0;
        super.onResume();
        Log.v(TAG, "onResume cart");
        connectSelectData(urlAddr);

        for (int i=0; i<carts.size(); i++){
            productprice = carts.get(i).getProductPrice();
            productcount = carts.get(i).getCartQuantity();
            price += Integer.parseInt(productprice) * Integer.parseInt(productcount);
            Log.v(TAG, "price : " + String.valueOf(price));

        }
        myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(price);
        String formattedStringPrice1 = myFormatter.format(carts.size() * 2500);
        String formattedStringPrice2 = myFormatter.format(carts.size() * 2500 + price);

        productTotal.setText("0원");
        productDeliveryTotalPrice.setText("0원");
        allProductTotalPrice.setText("0원");

        orderTotalNext.setText("구매하기");
        orderTotalNext.setBackgroundColor(getResources().getColor(R.color.gray));
        orderTotalNext.setTextColor(getResources().getColor(R.color.black));



//        if(carts.size() > cartAdapter.checkBoxCheckedReturn().size()){
//            selectAll.setChecked(false);
//        }

//        Intent intent = getIntent();
//
//        String pricecheck = intent.getStringExtra("price");
//        Log.v(TAG, "price2 : " + pricecheck);

    }

    @Override
    public void changedPrice(int productTotalPrice, int deliveryPrice, int totalPrice) {
        if(productTotalPrice == 0) {
            productTotal.setText("0원");
            productDeliveryTotalPrice.setText("0원");
            allProductTotalPrice.setText("0원");
            orderTotalNext.setText("구매하기");
            orderTotalNext.setBackgroundColor(getResources().getColor(R.color.gray));
            orderTotalNext.setTextColor(getResources().getColor(R.color.black));
        } else {
            myFormatter = new DecimalFormat("###,###");
            String formattedStringPrice = myFormatter.format(productTotalPrice);
            String formattedStringPrice1 = myFormatter.format(deliveryPrice);
            String formattedStringPrice2 = myFormatter.format(totalPrice);
            Log.v(TAG, "메인 가격변경 리스너 들어옴!!!");
            productTotal.setText(formattedStringPrice + "원");
            productDeliveryTotalPrice.setText(formattedStringPrice1 + "원");
            allProductTotalPrice.setText(formattedStringPrice2 + "원");
            orderTotalNext.setText("총 " + formattedStringPrice2 + "원 주문하기");
            orderTotalNext.setBackgroundColor(getResources().getColor(R.color.brown));
            orderTotalNext.setTextColor(getResources().getColor(R.color.white));
        }
    }
}