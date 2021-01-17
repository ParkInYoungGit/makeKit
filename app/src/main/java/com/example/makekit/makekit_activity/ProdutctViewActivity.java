package com.example.makekit.makekit_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makekit.R;
import com.example.makekit.makekit_adapter.ViewPagerProductAdapter;
import com.example.makekit.makekit_asynctask.CartNetworkTask;
import com.example.makekit.makekit_asynctask.ProductNetworkTask;
import com.example.makekit.makekit_asynctask.WishlistNetworkTask;
import com.example.makekit.makekit_bean.Cart;
import com.example.makekit.makekit_bean.Product;
import com.example.makekit.makekit_fragment.ProductContentFragment;
import com.example.makekit.makekit_fragment.ProductDetailFragment;
import com.example.makekit.makekit_fragment.ProductQuestionFragment;
import com.example.makekit.makekit_fragment.ProductReviewFragment;
import com.example.makekit.makekit_sharVar.SharVar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;


/////////////////////////////////////////////////////
// 앞 activity에서 받아오는 intent 값 다시 확인해서 수정하기!
/////////////////////////////////////////////////////


public class ProdutctViewActivity extends AppCompatActivity {
    final static String TAG = "ProdutctViewActivity";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerProductAdapter viewPagerProductAdapter;
    SlidingUpPanelLayout slidingUpPanelLayout;
    ImageView imgOption;
    TextView productTotalPrice, purchaseNumInput;
    Button btnPlus, btnMinus;
    String cartNumber;
    int count = 1;
    ArrayList<Cart> carts;

    String sellerEmail ,productNo, macIP, urlAddr, urlAddrBase, userEmail, urlAddr1, cartNo, result, urlAddr2,urlAddr3, urlAddr4, urlAddr5;
    //FrameLayout framelayout;
    LinearLayout ll_close, ll_open, openContent, openTotalPrice, openDeliveryMethod;
    ArrayList<Product> products;

    DecimalFormat myFormatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_produtct_view);
        Log.v(TAG, "onCreate");

        /////////////////////////////////////////////////
        // 값 받아오는 걸로 넘기기
        /////////////////////////////////////////////////

        Intent intent = getIntent();
//        macIP = intent.getStringExtra("macIP");
        productNo = intent.getStringExtra("productNo");
        Log.v(TAG, "productNo" + productNo);

        macIP = SharVar.macIP;
        userEmail = SharVar.userEmail;
        urlAddrBase = SharVar.urlAddrBase;
        //macIP = "192.168.219.164";


//        productNo = "64";

//        userEmail = "qkr@naver.com";

//        urlAddrBase = "http://" + macIP + ":8080/makeKit/";
        urlAddr = urlAddrBase + "jsp/product_productview_content.jsp?productno=" + productNo;
        urlAddr1 = urlAddrBase + "jsp/cartno_productview_check.jsp?useremail=" + userEmail;

        tabLayout = (TabLayout) findViewById(R.id.tabLayout_productview);
        viewPager = (ViewPager) findViewById(R.id.viewpager_productview);

        viewPagerProductAdapter = new ViewPagerProductAdapter(getSupportFragmentManager());

        //     Add Fragment
        viewPagerProductAdapter.AddFrmt(new ProductContentFragment(macIP, productNo, userEmail), "상품설명");
        viewPagerProductAdapter.AddFrmt(new ProductReviewFragment(macIP, productNo), "후기");
        viewPagerProductAdapter.AddFrmt(new ProductQuestionFragment(macIP, productNo), "문의");
        viewPagerProductAdapter.AddFrmt(new ProductDetailFragment(macIP, productNo, userEmail), "판매자정보");

        viewPager.setAdapter(viewPagerProductAdapter);
        tabLayout.setupWithViewPager(viewPager);
        btnPlus = findViewById(R.id.btnPlusProudct_productview);
        btnMinus= findViewById(R.id.btnMinusProudct_productview);

        // 슬라이드 레이어
        // 슬라이드 레이어
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout_productview);

        imgOption = findViewById(R.id.imgOption_productview);

        ll_close = findViewById(R.id.llBottomSheet_productview);
        ll_open = findViewById(R.id.llBottomSheetOpen_productview);
        openContent = findViewById(R.id.llBottomSheetContent_productview);
        openTotalPrice = findViewById(R.id.ll_totalPrice_productview);
        openDeliveryMethod = findViewById(R.id.ll_Delivery_productview);

        productTotalPrice = findViewById(R.id.productTotalPrice_productview);
        purchaseNumInput = findViewById(R.id.purchaseNum_productview);
        purchaseNumInput.setText("1");


        imgOption.setImageResource(R.drawable.optionup);
        ll_close.setVisibility(View.VISIBLE);
        ll_open.setVisibility(View.INVISIBLE);
        openContent.setVisibility(View.INVISIBLE);
        openTotalPrice.setVisibility(View.INVISIBLE);
        openDeliveryMethod.setVisibility(View.INVISIBLE);

        connectSelectData();
        if(SharVar.userEmail.equals("")) {

        } else {
            connectSelectCartData(urlAddr1);
            // user cart 번호
            cartNo = cartNumber;
        }
        int total = Integer.parseInt(products.get(0).getProductPrice()) + 2500;
        Log.v(TAG, String.valueOf(total));
        myFormatter = new DecimalFormat("###,###");
        String formattedStringPrice = myFormatter.format(total);
        productTotalPrice.setText(formattedStringPrice + "원");


        findViewById(R.id.btnCart_productview).setOnClickListener(mClickListener);
        findViewById(R.id.btnPurchase_productview).setOnClickListener(mClickListener);
        findViewById(R.id.btnCartOpen_productview).setOnClickListener(mClickListener);
        findViewById(R.id.btnPurchaseOpen_productview).setOnClickListener(mClickListener);
        btnMinus.setOnClickListener(mClickListener);
        btnPlus.setOnClickListener(mClickListener);

        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                imgOption.setImageResource(R.drawable.optionup);
                ll_close.setVisibility(View.VISIBLE);
                ll_open.setVisibility(View.INVISIBLE);
                openContent.setVisibility(View.INVISIBLE);
                openTotalPrice.setVisibility(View.INVISIBLE);
                openDeliveryMethod.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState.name().toString().equalsIgnoreCase("Collapsed")){
                    // 닫혔을때 처리하는 부분
                    imgOption.setImageResource(R.drawable.optionup);
                    ll_close.setVisibility(View.VISIBLE);
                    ll_open.setVisibility(View.INVISIBLE);
                    openContent.setVisibility(View.INVISIBLE);
                    openTotalPrice.setVisibility(View.INVISIBLE);
                    openDeliveryMethod.setVisibility(View.INVISIBLE);

                }else if(newState.name().equalsIgnoreCase("Expanded")){

                    // 열렸을때 처리하는 부분
                    imgOption.setImageResource(R.drawable.downoption);
                    ll_open.setVisibility(View.VISIBLE);
                    ll_close.setVisibility(View.INVISIBLE);
                    openContent.setVisibility(View.VISIBLE);
                    openTotalPrice.setVisibility(View.VISIBLE);
                    openDeliveryMethod.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.btnMinusProudct_productview:
                    if(purchaseNumInput.getText().toString().equals("1")){
                        count = 1;
                        Toast.makeText(ProdutctViewActivity.this, "최수 수량은 1개입니다.", Toast.LENGTH_SHORT).show();
                        purchaseNumInput.setText("1");

                    } else {
                        count--;
                        purchaseNumInput.setText(""+count);
                        int total = (Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500;

                        myFormatter = new DecimalFormat("###,###");
                        String formattedStringPrice = myFormatter.format(total);
                        productTotalPrice.setText(formattedStringPrice + " 원");

                    }
                    break;

                case R.id.btnPlusProudct_productview:
                    count++;
                    purchaseNumInput.setText(""+count);

                    int total = (Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500;
                    myFormatter = new DecimalFormat("###,###");
                    String formattedStringPrice = myFormatter.format(total);
                    productTotalPrice.setText(formattedStringPrice + " 원");

                    break;

                case R.id.btnCart_productview:

                    if(loginCheck() == true){
                        if(slidingUpPanelLayout.getPanelState().toString().equalsIgnoreCase("COLLAPSED")) {
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

                        } else {
                            Intent intent = new Intent(ProdutctViewActivity.this, CartActivity.class);
                            intent.putExtra("productNo", productNo);
                            intent.putExtra("macIP", macIP);
                            intent.putExtra("productQuantity", purchaseNumInput.getText().toString());
                            Log.v(TAG, purchaseNumInput.getText().toString());
                            Log.v(TAG, String.valueOf(count));
                            //intent.putExtra("totalPrice", Integer.toString((Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500));
                            startActivity(intent);

                        }
                    }

                    break;


                case R.id.btnPurchase_productview:

                    if(loginCheck() == true){
                        if(slidingUpPanelLayout.getPanelState().toString().equalsIgnoreCase("COLLAPSED")) {
                            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);

                        } else {
                            Intent intent = new Intent(ProdutctViewActivity.this, OrderActivity.class);
                            intent.putExtra("productNo", productNo);
                            intent.putExtra("macIP", macIP);
                            intent.putExtra("productQuantity", count);
                            intent.putExtra("totalPrice", Integer.toString((Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500));
                            startActivity(intent);
                        }
                    }

                    break;

                case R.id.btnCartOpen_productview:
                    if(loginCheck() == true) {
                        ////////////////////////////////
                        // 1.13 추가 kyeongmi
                        ////////////////////////////////
                        result = null;
                        urlAddr3 = urlAddrBase + "jsp/cartdetail_productview_check.jsp?productno=" + productNo + "&cartno=" + cartNo;
                        if(connectSelectCartCheckData(urlAddr3).equals("0")){
                            urlAddr2 = urlAddrBase + "jsp/insert_cart_all.jsp?useremail=" + userEmail + "&productno=" + productNo + "&cartquantity=" + count + "&cartno=" + cartNo;
                            connectInsertCartData(urlAddr2);
                            ////////////////////////////////

                            if (result.equals("1")) {
                                Intent intent = new Intent(ProdutctViewActivity.this, CartActivity.class);
                                intent.putExtra("cartNo", cartNo);
                                intent.putExtra("productNo", productNo);
                                intent.putExtra("macIP", macIP);
                                intent.putExtra("userEmail", userEmail);
                                //intent.putExtra("totalPrice", Integer.toString((Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500));
                                startActivity(intent);

                            } else {
                                Toast.makeText(ProdutctViewActivity.this, "입력에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                            ////////////////////////////////
                        } else {
                            urlAddr5 = urlAddrBase + "jsp/selectqnt_productview_cart.jsp?productno=" + productNo + "&cartno=" + cartNo;
                            int cartQnt = Integer.parseInt(connectSelectCartQntData(urlAddr5));
                            int setQnt = Integer.parseInt(purchaseNumInput.getText().toString());

                            // update 추가
                            urlAddr4 = urlAddrBase + "jsp/update_cart_change.jsp?productno=" + productNo + "&cartno=" + cartNo + "&cartquantity=" + Integer.toString((cartQnt+setQnt));
                            if(connectUpdateCartData(urlAddr4).equals("1")){
                                Toast.makeText(ProdutctViewActivity.this, "장바구니 수량 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProdutctViewActivity.this, CartActivity.class);
                                intent.putExtra("cartNo", cartNo);
                                intent.putExtra("productNo", productNo);
                                intent.putExtra("macIP", macIP);
                                intent.putExtra("userEmail", userEmail);
                                //intent.putExtra("totalPrice", Integer.toString((Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500));
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ProdutctViewActivity.this, "장바구니 수량 변경 실패하였습니다.", Toast.LENGTH_SHORT).show();

                            }
                        }
                        ////////////////////////////////
                    }
                    break;

                case R.id.btnPurchaseOpen_productview:

                    if(loginCheck() == true) {
                        ////////////////////////////
                        // 1/15 경미 추가
                        ///////////////////////////
                        carts = new ArrayList<>();
                        Cart cart = new Cart("", productNo, purchaseNumInput.getText().toString(), products.get(0).getProductName(), products.get(0).getProductFilename(), products.get(0).getProductPrice());
                        carts.add(cart);
                        Log.v(TAG, "cart : " + String.valueOf(cart));
                        Intent intent = new Intent(ProdutctViewActivity.this, OrderActivity.class);
                        intent.putExtra("productno", carts);
                        intent.putExtra("productNo", productNo);
                        intent.putExtra("cartNo", "");
                        intent.putExtra("productPrice", products.get(0).getProductPrice());
                        intent.putExtra("productQuantity", purchaseNumInput.getText().toString());


                        Log.v(TAG, purchaseNumInput.getText().toString());
                        Log.v(TAG, String.valueOf(count));
//                        intent.putExtra("productQuantity", count);
                        intent.putExtra("totalPrice", Integer.toString((Integer.parseInt(products.get(0).getProductPrice()) * count) + 2500));
                        startActivity(intent);
                        finish();
                    }
            }
        }
    };


    private boolean loginCheck(){
        ///////////////////////////////
        // email 받아오는 값 확인해서 수정하기

        if(userEmail == null || userEmail.equals("")){
            Toast.makeText(this, "로그인이 필요합니다. \n로그인 후 이용해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProdutctViewActivity.this, LoginActivity.class);
            intent.putExtra("macIP", macIP);
            startActivity(intent);
            finish();
            return false;
        }
        return true;
    }


    // select content
    private void connectSelectData() {
        try {
            ProductNetworkTask productNetworkTask = new ProductNetworkTask(ProdutctViewActivity.this, urlAddr, "select");

            Object object = productNetworkTask.execute().get();
            products = (ArrayList<Product>) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // select cartNo
    private void connectSelectCartData(String urlAddr) {
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(ProdutctViewActivity.this, urlAddr, "selectCartNo");

            Object object = cartNetworkTask.execute().get();
            cartNumber = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // insert cartdetail
    private void connectInsertCartData(String urlAddr) {
        result = "";
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(ProdutctViewActivity.this, urlAddr, "insert");

            Object object = cartNetworkTask.execute().get();
            result = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // select cartCheck (동일상품 있는지 체크)
    private String connectSelectCartCheckData(String urlAddr) {
        result = "";
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(ProdutctViewActivity.this, urlAddr, "selectCartCheck");

            Object object = cartNetworkTask.execute().get();
            result = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // select cart 수량
    private String connectSelectCartQntData(String urlAddr) {
        result = "";
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(ProdutctViewActivity.this, urlAddr, "selectCartQnt");

            Object object = cartNetworkTask.execute().get();
            result = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // update cartdetail
    private String connectUpdateCartData(String urlAddr) {
        result = "";
        try {
            CartNetworkTask cartNetworkTask = new CartNetworkTask(ProdutctViewActivity.this, urlAddr, "update");

            Object object = cartNetworkTask.execute().get();
            result = (String) object;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}