package com.example.makekit.makekit_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.graphics.Rect;
import android.nfc.Tag;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.OrderProductListAdapter;
import com.example.makekit.makekit_asynctask.CartNetworkTask;
import com.example.makekit.makekit_asynctask.OrderNetworkTask;

import com.example.makekit.makekit_asynctask.ProductNetworkTask;
import com.example.makekit.makekit_asynctask.UserNetworkTask;
import com.example.makekit.makekit_bean.Cart;

import com.example.makekit.makekit_bean.Order;
import com.example.makekit.makekit_bean.Payment;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_sharVar.SharVar;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class OrderActivity extends AppCompatActivity {

    final static String TAG = "OderActivity";
    //  이메일을 위한 확인
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    //  전화번호를 위한 선언
    public static final String pattern2 = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
    private int _beforeLenght = 0;
    private int _afterLenght = 0;

    DecimalFormat myFormatter;
    //  아이피, url, 기본 세팅 설정
    String macIP, result, email, productNo, count, totalPrice, urlAddrBase, urlJsp, urlImage, url, urlAddrSelect_Resume, cartNo, urlAddrSelect_Resume1, urlAddrBase11, orderNo;
    int orderCount, orderTotalPrice;
    int total = 0;

    //  주문자 기본 정보
    EditText order_userName, order_userTel, order_userAddress, order_userAddressDetail;
//    TextView order_userName, order_userTel, order_userAddress, order_userAddressDetail;
    String orderUserName, orderUserTel, orderUserAddress, orderUserAddressDetail;

    //  수령자 정보
    RecyclerView rv_product_order;
    RecyclerView.Adapter mAdapter = null;
    EditText order_receiverName, order_receiverTel, order_receiverAddress, order_receiverAddressDetail, order_cardNumber, order_cardPW;
    Spinner order_requestComent = null; //   배송 요청사항
    Spinner order_cardSelect = null;
    CheckBox order_info_checkBox;
    LinearLayout ll_card, ll_cardClick;

    //  상품 정보
    WebView order_productImage;
    TextView order_productName, order_productCount, order_productPrice, order_productTotalPrice, order_productDeliveryTotalPrice, order_productSelectPrice;

    //  주문하기 버튼
    Button order_payment_btn;

    //  배송 요청사항 목록
    String[] orderRequest = {
            "배송시 요청사항을 선택해주세요", "부재 시 경비실에 맡겨주세요", "부재 시 택배함에 넣어주세요", "부재 시 집 앞에 놔주세요", "배송 전 연락 바랍니다", "파손의 위험이 있는 상품입니다. 배송 시 주의해주세요", "직접 입력"
    };

    // 카드 선택 목록
    String[] cardName = {
            "카드를 선택해주세요", "현대", "신한", "비씨", "KB국민", "삼성", "롯데", "하나", "NH채움", "우리", "수협", "카카오뱅크", "시티"
    };

    // 유저 기본 정보
    ArrayList<Order> Order;   // 빈, 어댑터
    ArrayList<String> product1;   // 빈, 어댑터
    ArrayList<Cart> carts;   // 빈, 어댑터
    ArrayList<Payment> payments;
    // 구패 제품 정보
    ArrayList<Payment> payment;   // 빈, 어댑터
//    ArrayList<Product> product;

    RecyclerView recyclerView = null;
    RecyclerView.Adapter rAdapter = null;
    RecyclerView.LayoutManager layoutManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.rv_product_order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


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
//        macIP = intent.getStringExtra("macIP");
//        email = intent.getStringExtra("userEmail");
//        email = "qkr@naver.com";
//        product1 = intent.getStringArrayListExtra("productNo");
        productNo = intent.getStringExtra("productNo");
        cartNo = intent.getStringExtra("cartNo");
        count = intent.getStringExtra("productQuantity");
        //totalPrice = intent.getStringExtra("totalPrice");
        carts = (ArrayList<Cart>) intent.getSerializableExtra("productno");


        /////////////////////////////////////////////////////
        // 1/14 Kyeongmi 추가
        /////////////////////////////////////////////////////
        for (int i = 0; i < carts.size(); i++) {
            String no = carts.get(i).getProductNo();
            String price = carts.get(i).getProductPrice();
            String quantity = carts.get(i).getCartQuantity();
            total += Integer.parseInt(price) *  Integer.parseInt(quantity);
            product1.add(no);
            Log.v(TAG, "번호 : " + no);
            Log.v(TAG, "상품 금액 : " + price);
            Log.v(TAG, "수량 : " + quantity);
            Log.v(TAG, "총 금액 : " + total);
        }
        /////////////////////////////////////////////////////

//        Log.v(TAG, macIP);
////        Log.v(TAG, productNo);
//        Log.v(TAG, count);
//        Log.v(TAG, totalPrice);
//        Log.v(TAG, (String) carts.get(0));

        //  사용할 값 인트 변환
//        orderCount = Integer.parseInt(count);
//        orderTotalPrice = Integer.parseInt(totalPrice);
//        Log.v(TAG, "orderCount : " + orderCount);
//        Log.v(TAG, "orderTotalPrice : " + orderTotalPrice);

        /////////////////////////////////////////////////////
        // 1/14 Kyeongmi 추가
        /////////////////////////////////////////////////////
        macIP = SharVar.macIP;
        email = SharVar.userEmail;
        urlAddrBase = SharVar.urlAddrBase;
        urlJsp = urlAddrBase + "jsp/";  // jsp 폴더
        urlImage = urlAddrBase + "image/";  // image 폴더


        /////////////////////////////////////////////////////
        // 기존 사용 변수
        /////////////////////////////////////////////////////
//        // 사용할 폴더 지정
//        urlAddrBase = "http://" + macIP + ":8080/makeKit/";  // 기본 폴더
//        urlJsp = "http://" + macIP + ":8080/makeKit/jsp/";  // jsp 폴더
//        urlImage = "http://" + macIP + ":8080/makeKit/image/";  // image 폴더
        urlAddrSelect_Resume = urlJsp + "order_user_info.jsp?email=" + email;
        urlAddrBase11 = urlAddrBase + "jsp/order_product_select.jsp?cartNo=" + "69" + "&productNo=" + "44";

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
//        order_productImage = findViewById(R.id.order_productImage);
//        order_productName = findViewById(R.id.order_productName);
//        order_productCount = findViewById(R.id.order_productCount);
//        order_productPrice = findViewById(R.id.order_productPrice);
        order_productTotalPrice = findViewById(R.id.allProductTotalPrice_order);
        order_productSelectPrice = findViewById(R.id.productTotalPrice_order);
        order_productDeliveryTotalPrice = findViewById(R.id.productDeliveryTotalPrice_order);

        ////////////////////////////////////////
        // 1/14 경미 추가
        ////////////////////////////////////////

        // card 레이아웃
        ll_card = findViewById(R.id.ll_btnCard_order);
        ll_cardClick = findViewById(R.id.ll_btnClickCard_order);

        // card 정보
        order_cardSelect = findViewById(R.id.cardSelect_order);
        order_cardNumber = findViewById(R.id.cardNumber_order);
        order_cardPW = findViewById(R.id.cardPW_order);
        ////////////////////////////////////////


        //  구매 버튼
        order_payment_btn = findViewById(R.id.order_payment_btn);

        ////////////////////////////////////////
        // 1/14 경미 추가
        ////////////////////////////////////////
        myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(total + carts.size()*2500);
        order_productTotalPrice.setText("" + formattedStringPrice + "원");
        String formattedStringPrice2 = myFormatter.format(total);
        order_productSelectPrice.setText(formattedStringPrice2 + "원");
        String formattedStringPrice1 = myFormatter.format(carts.size()*2500);
        order_productDeliveryTotalPrice.setText(formattedStringPrice1 + "원");
        ////////////////////////////////////////

        // ========================================================== onClickListener

        // 구매버튼 액션
        ////////////////////////////////////////
        // 1/14 경미 추가
        ////////////////////////////////////////
        order_payment_btn.setOnClickListener(onClickListener);
        findViewById(R.id.btnCard_order).setOnClickListener(onClickListener);
        ////////////////////////////////////////

        order_info_checkBox.setOnClickListener(onClickListener);
        order_userTel.addTextChangedListener(changeListener_userTel);
        order_receiverTel.addTextChangedListener(changeListener_receiverTel);
        order_cardNumber.addTextChangedListener(changeListener_cardNumber);

        // ========================================================== 제품 카테고리 스피너 목록 설정
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, orderRequest);
        order_requestComent.setAdapter(adapter);
        order_requestComent.setSelection(0);


        // ========================================================== 카드 카테고리 스피너 목록 설정
        ArrayAdapter<String> cardAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, cardName);
        order_cardSelect.setAdapter(cardAdapter);
        order_cardSelect.setSelection(0);


        // ========================================================== 페이지 띄울때 주문자

        // 실행시 셀렉트 실행
        connectSelectGetData(urlAddrSelect_Resume);   // urlAddr1을  connectSelectGetData의 urlAddr2로 보내준다
//        for(int i = 0; i < product1.size(); i++){
//            connectProductSelectGetData(i);
//
//        }


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


        // 주소록 선택을 위한 이벤트 클릭 리스너
        order_receiverAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//클릭했을 경우 발생할 이벤트 작성
                    Intent intent = new Intent(OrderActivity.this, WebViewActivity.class);
                    intent.putExtra("macIP", macIP);
                    startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
                }
                return false;
            }
        });

    } // ------------ End onCreat

    // CheckBox를 사용해서 주문자정보와 배송지 정보가 같을경우 체크로 값 넣어주는 온클릭
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

                    break;

                case R.id.order_payment_btn:

                    /////////////////////////////////////////////////////
                    // 1/14 Kyeongmi 추가
                    // 수정하기
                    /////////////////////////////////////////////////////
                    int count1 = 0;
                    int count2 = 0;

                    String orderreceiver = order_receiverName.getText().toString();
                    String orderrcvaddress = order_receiverAddress.getText().toString();
                    String orderrcvaddressdetail = order_receiverAddressDetail.getText().toString();
                    String orderrcvphone = order_receiverTel.getText().toString();
                    //String ordertotalprice = order_productTotalPrice.getText().toString();
                    String orderbank = (String) order_cardSelect.getSelectedItem();
                    String ordercardno = order_cardNumber.getText().toString();
                    String ordercardpw = order_cardPW.getText().toString();


                    if(orderreceiver.trim().length()==0){
                        Toast.makeText(OrderActivity.this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();

                    } else if(orderrcvaddress.trim().length()==0){
                        Toast.makeText(OrderActivity.this, "주소을 입력해주세요", Toast.LENGTH_SHORT).show();

                    } else if(orderrcvphone.trim().length()==0) {
                        Toast.makeText(OrderActivity.this, "휴대폰 번호를 입력해주세요", Toast.LENGTH_SHORT).show();

                    } else if(orderbank.trim().length()==0){
                        Toast.makeText(OrderActivity.this, "카드사를 선택해주세요", Toast.LENGTH_SHORT).show();

                    } else if(ordercardno.trim().length()==0){
                        Toast.makeText(OrderActivity.this, "카드 번호를 입력해주세요", Toast.LENGTH_SHORT).show();

                    } else if(ordercardpw.trim().length()==0){
                        Toast.makeText(OrderActivity.this, "카드 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();

                    } else {

//                    String orderbank = order_receiverName.getText().toString();
//                    String ordercardno = order_receiverName.getText().toString();
//                    String ordercardpw = order_receiverName.getText().toString();

                        String urlAddr1 = urlAddrBase + "jsp/insert_order_orderinfo.jsp?useremail=" + email + "&orderreceiver=" + orderreceiver + "&orderrcvaddress=" + orderrcvaddress;
                        String urlAddr2 = "&orderrcvaddressdetail=" + orderrcvaddressdetail + "&orderrcvphone=" + orderrcvphone + "&ordertotalprice=" + (total + carts.size() * 2500) + "&orderbank=" + orderbank + "&ordercardno=" + ordercardno + "&ordercardpw=" + ordercardpw;
                        connectInsertData(urlAddr1 + urlAddr2);
                        String urlAddr3 = urlAddrBase + "jsp/select_orderno_orderdetail.jsp?useremail=" + email;
                        connectSelectData(urlAddr3);


                        // 선택된 주문 상품 orderdetail table 추가
                        String urlAddr4 = urlAddrBase + "jsp/insert_order_orderdetail.jsp?useremail=" + email + "&orderno=" + orderNo;
                        for (int i = 0; i < carts.size(); i++) {
                            String urlAddr5 = "&productno=" + carts.get(i).getProductNo() + "&orderquantity=" + carts.get(i).getCartQuantity();
                            count1 += Integer.parseInt(connectInsertData(urlAddr4 + urlAddr5));
                            Log.v(TAG, "count : " + count);
                            Log.v(TAG, "urlAddr5 : " + urlAddr5);
                        }

                            // 선택된 주문 상품 cartdetail table 삭제
                            String urlAddr6 = urlAddrBase + "jsp/delete_cartdetail.jsp?useremail=" + email;
                            for (int i = 0; i < carts.size(); i++) {
                                String urlAddr7 = "&productno=" + carts.get(i).getProductNo();
                                count2 += Integer.parseInt(connectInsertData(urlAddr6 + urlAddr7));
                                Log.v(TAG, "count2 : " + count2);
                            }

                        if (count1 == carts.size() && result.equals("1") ||  count1 == 1 && count2 == 0) {
                            //Toast.makeText(OrderActivity.this, "입력 성공하였습니다.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(OrderActivity.this, OrderViewActivity.class);
                            intent.putExtra("useremail", SharVar.userEmail);
                            intent.putExtra("macIP", SharVar.macIP);
                            intent.putExtra("order_userName", order_receiverName.getText().toString());
                            intent.putExtra("order_userTel", order_receiverTel.getText().toString());
                            intent.putExtra("order_userAddress", order_receiverAddress.getText().toString());
                            intent.putExtra("order_userAddressDetail", order_receiverAddressDetail.getText().toString());
                            intent.putExtra("orderView_orderBank", (String) order_cardSelect.getSelectedItem());
                            intent.putExtra("orderView_orderCardNo", order_cardNumber.getText().toString());
                            intent.putExtra("orderView_orderDate", order_cardPW.getText().toString());
                            intent.putExtra("orderView_orderTotalPrice", Integer.toString(total));
                            intent.putExtra("orderView_Number_TV", orderNo);
                            startActivity(intent);

                        } else {
                            Toast.makeText(OrderActivity.this, "입력 실패하였습니다.", Toast.LENGTH_SHORT).show();

                        }
                    }

                    break;


                case R.id.btnCard_order:
                    ll_card.setVisibility(v.INVISIBLE);
                    ll_cardClick.setVisibility(v.VISIBLE);

                    break;

                /////////////////////////////////////////////////////

            }
        }
    };


    // ============================================================================================ 선택 제품 기본정보 가져오는 Select
//    protected void order() {
//        Log.v(TAG, "order in");
//        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
//        connectProductSelectGetData();
//        mAdapter = new OrderProductListAdapter(OrderActivity.this, R.layout.custom_order_product, Payment, urlAddrBase+"image/");
//        Log.v(TAG, "order : " + Payment);
//        rv_product_order.setAdapter(mAdapter);
//
//    }

    /////////////////////////////////////////////////////
    // 1/14 Kyeongmi 추가
    // cart 상품 정보 가져오기
    /////////////////////////////////////////////////////
    private void connectProductSelectGetData() {

        mAdapter = new OrderProductListAdapter(OrderActivity.this, R.layout.custom_order_product, carts, urlAddrBase + "image/");
        rv_product_order.setAdapter(mAdapter);

        Log.v(TAG, "mAdapter mAdapter : " + mAdapter);
        Log.v(TAG, "mAdapter rv_product_order : " + mAdapter);

    }
    /////////////////////////////////////////////////////

    // ============================================================================================  유저 기본정보 가져오는 Select
    @Override
    public void onResume() {
        super.onResume();
        // Select
        urlAddrSelect_Resume = urlJsp + "order_user_info.jsp?email=" + email;
        connectSelectGetData(urlAddrSelect_Resume);
        connectProductSelectGetData();

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

    // 아무 곳이나 터치시 키보드 접기
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    // phone text
    TextWatcher changeListener_userTel = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            _beforeLenght = s.length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            _afterLenght = s.length();
            // 삭제 중
            if (_beforeLenght > _afterLenght) {
                // 삭제 중에 마지막에 -는 자동으로 지우기
                if (s.toString().endsWith("-")) {
                    order_userTel.setText(s.toString().substring(0, s.length() - 1));
                }
            }
            // 입력 중
            else if (_beforeLenght < _afterLenght) {
                if (_afterLenght == 4 && s.toString().indexOf("-") < 0) {
                    order_userTel.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                } else if (_afterLenght == 9) {
                    order_userTel.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                } else if (_afterLenght == 14) {
                    order_userTel.setText(s.toString().subSequence(0, 13) + "-" + s.toString().substring(13, s.length()));
                }
            }
            order_userTel.setSelection(order_userTel.length());

        }

        @Override
        public void afterTextChanged(Editable s) {
            String phoneCheck = order_userTel.getText().toString().trim();
            boolean flag = Pattern.matches(pattern2, phoneCheck);

            if (phoneCheck.length() == 0) {
                order_userTel.setError(null);
            } else {
                if (flag == false) {
                    order_userTel.setError("휴대폰 번호를 다시 입력해주세요.");
                }

            }
        }
    };
    // phone text
    TextWatcher changeListener_receiverTel = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            fieldCheck.setText("");
//            fieldCheck.setText("");
            _beforeLenght = s.length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //fieldCheck.setText("");
            _afterLenght = s.length();
            // 삭제 중
            if (_beforeLenght > _afterLenght) {
                // 삭제 중에 마지막에 -는 자동으로 지우기
                if (s.toString().endsWith("-")) {
                    order_receiverTel.setText(s.toString().substring(0, s.length() - 1));
                }
            }
            // 입력 중
            else if (_beforeLenght < _afterLenght) {
                if (_afterLenght == 4 && s.toString().indexOf("-") < 0) {
                    order_receiverTel.setText(s.toString().subSequence(0, 3) + "-" + s.toString().substring(3, s.length()));
                } else if (_afterLenght == 9) {
                    order_receiverTel.setText(s.toString().subSequence(0, 8) + "-" + s.toString().substring(8, s.length()));
                } else if (_afterLenght == 14) {
                    order_receiverTel.setText(s.toString().subSequence(0, 13) + "-" + s.toString().substring(13, s.length()));
                }
            }
            order_receiverTel.setSelection(order_receiverTel.length());

        }

        @Override
        public void afterTextChanged(Editable s) {
            String phoneCheck = order_receiverTel.getText().toString().trim();
            boolean flag = Pattern.matches(pattern2, phoneCheck);


                if (phoneCheck.length() == 0) {
                    order_receiverTel.setError(null);
                } else {
                    if (flag == false) {
                        order_receiverTel.setError("휴대폰 번호를 다시 입력해주세요.");
                    }
                }
            }
        };



    // email 입력란 text 변경 시 listener
    TextWatcher changeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) { // email 입력 시 DB 드가기 전에 정리
            if (order_userAddress.getText().toString().trim().length() != 0) {
                validateEdit(s);
            }
        }
    };

    // email 형식 일치 확인
    private void validateEdit(Editable s) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()) {
            order_userAddress.setError("이메일 형식으로 입력해주세요!");
        } else {
            order_userAddress.setError(null);
        }
    }

    /////////////////////////////////////////////////////
    // 1/14 Kyeongmi 추가
    /////////////////////////////////////////////////////

    // cardnum text
    TextWatcher changeListener_cardNumber = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            _beforeLenght = s.length();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            _afterLenght = s.length();
            // 삭제 중
            if (_beforeLenght > _afterLenght) {
                // 삭제 중에 마지막에 -는 자동으로 지우기
                if (s.toString().endsWith("-")) {
                    order_cardNumber.setText(s.toString().substring(0, s.length() - 1));
                }
            }
            // 입력 중
            else if (_beforeLenght < _afterLenght) {
                if (_afterLenght == 5 && s.toString().indexOf("-") < 0) {
                    order_cardNumber.setText(s.toString().subSequence(0, 4) + "-" + s.toString().substring(4, s.length()));
                } else if (_afterLenght == 10) {
                    order_cardNumber.setText(s.toString().subSequence(0, 9) + "-" + s.toString().substring(9, s.length()));
                } else if (_afterLenght == 15) {
                    order_cardNumber.setText(s.toString().subSequence(0, 14) + "-" + s.toString().substring(14, s.length()));
                }
            }
            order_cardNumber.setSelection(order_cardNumber.length());

        }

        @Override
        public void afterTextChanged(Editable s) {
//            String cardNumCheck = order_cardNumber.getText().toString().trim();
//            boolean flag = Pattern.matches(pattern2, cardNumCheck);
//
//            if (cardNumCheck.length() == 0) {
//                order_userTel.setError(null);
//            } else {
//                if (flag == false) {
//                    order_userTel.setError("카드번호를 다시 입력해주세요.");
//                }
//
//            }
        }
    };



    // insert orderinfo
    private String connectInsertData(String urlAddr) {
        result = "";
        try {
            OrderNetworkTask productNetworkTask = new OrderNetworkTask(OrderActivity.this, urlAddr, "insert");

            Object object = productNetworkTask.execute().get();
            result = (String) object;
            Log.v(TAG, "입력 결과값 : " + result);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return result;
    }

    // select OrderNo
    private void connectSelectData(String urlAddr) {
        orderNo = "";
        try {
            OrderNetworkTask orderNetworkTask = new OrderNetworkTask(OrderActivity.this, urlAddr, "select");

            Object object = orderNetworkTask.execute().get();
            orderNo = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /////////////////////////////////////////////////////


    // ======================= 주소록 선택 이벤트
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        order_receiverAddress.setText(data);
                    }
                }
                break;
        }
    }

}//===================
